-- ============================================================
-- CRM XTART - Esquema Completo de Base de Datos
-- Proyecto Intermodular 1º DAM
-- Modelo relacional en 3FN para el sistema CRM
-- Incluye: tablas, procedimientos, triggers, datos de prueba,
--          consultas de negocio, roles y permisos
-- ============================================================

CREATE DATABASE IF NOT EXISTS crm_xtart
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crm_xtart;

-- ============================================================
-- 1. TABLAS DEL MODELO RELACIONAL
-- ============================================================

-- -------------------------------------------------------
-- Persona (superclase)
-- -------------------------------------------------------
CREATE TABLE Persona (
    id_persona INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro DATE NOT NULL
);

-- -------------------------------------------------------
-- Comercial (hereda de Persona)
-- -------------------------------------------------------
CREATE TABLE Comercial (
    id_comercial INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT NOT NULL UNIQUE,
    codigo_comercial VARCHAR(20) NOT NULL UNIQUE,
    apellidos VARCHAR(100) NOT NULL,
    zona_geografica VARCHAR(100),
    fecha_alta DATE NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------------------
-- ClientePotencial (hereda de Persona)
-- -------------------------------------------------------
CREATE TABLE ClientePotencial (
    id_potencial INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT NOT NULL UNIQUE,
    empresa VARCHAR(150),
    fuente_captacion VARCHAR(100),
    estado ENUM('nuevo','en seguimiento','perdido','convertido') DEFAULT 'nuevo',
    fecha_primer_contacto DATE NOT NULL,
    id_comercial INT,
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_comercial) REFERENCES Comercial(id_comercial)
        ON DELETE SET NULL ON UPDATE CASCADE
);

-- -------------------------------------------------------
-- ClienteFormal (hereda de Persona)
-- -------------------------------------------------------
CREATE TABLE ClienteFormal (
    id_formal INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT NOT NULL UNIQUE,
    codigo_cliente VARCHAR(20) NOT NULL UNIQUE,
    nif_cif VARCHAR(15) NOT NULL UNIQUE,
    razon_social VARCHAR(200) NOT NULL,
    direccion_fiscal VARCHAR(255),
    condiciones_pago ENUM('contado','30','60','90') DEFAULT 'contado',
    descuento_habitual DECIMAL(5,2) DEFAULT 0.00,
    estado ENUM('activo','inactivo') DEFAULT 'activo',
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------------------
-- Producto
-- -------------------------------------------------------
CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio_unitario DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoria VARCHAR(100)
);

-- -------------------------------------------------------
-- Pedido
-- -------------------------------------------------------
CREATE TABLE Pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha_pedido DATE NOT NULL,
    id_cliente_formal INT NOT NULL,
    id_comercial INT NOT NULL,
    estado ENUM('pendiente','en curso','servido','anulado') DEFAULT 'pendiente',
    FOREIGN KEY (id_cliente_formal) REFERENCES ClienteFormal(id_formal)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_comercial) REFERENCES Comercial(id_comercial)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- -------------------------------------------------------
-- LineaPedido (clave primaria compuesta)
-- -------------------------------------------------------
CREATE TABLE LineaPedido (
    id_pedido INT NOT NULL,
    id_linea INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10,2) NOT NULL,
    descuento_linea DECIMAL(5,2) DEFAULT 0.00,
    PRIMARY KEY (id_pedido, id_linea),
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- -------------------------------------------------------
-- Factura
-- -------------------------------------------------------
CREATE TABLE Factura (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(30) NOT NULL UNIQUE,
    fecha_emision DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    id_cliente_formal INT NOT NULL,
    id_pedido INT NOT NULL UNIQUE,
    base_imponible DECIMAL(12,2) NOT NULL,
    tipo_iva DECIMAL(4,2) NOT NULL DEFAULT 21.00,
    total DECIMAL(12,2) NOT NULL,
    estado ENUM('pendiente','cobrada','vencida','anulada') DEFAULT 'pendiente',
    FOREIGN KEY (id_cliente_formal) REFERENCES ClienteFormal(id_formal)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- -------------------------------------------------------
-- AuditoriaFactura (tabla para el trigger de auditoria)
-- -------------------------------------------------------
CREATE TABLE AuditoriaFactura (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    id_factura INT NOT NULL,
    estado_anterior VARCHAR(20),
    estado_nuevo VARCHAR(20) NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    fecha_cambio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_factura) REFERENCES Factura(id_factura)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ============================================================
-- 2. STORED PROCEDURES
-- ============================================================

DELIMITER //

-- -------------------------------------------------------
-- PROCEDIMIENTO: convertirClientePotencial
-- Convierte un cliente potencial en cliente formal:
--   1. Crea el registro en ClienteFormal
--   2. Marca el potencial como 'convertido'
-- -------------------------------------------------------
CREATE PROCEDURE convertirClientePotencial (
    IN p_id_potencial INT,
    IN p_codigo_cliente VARCHAR(20),
    IN p_nif_cif VARCHAR(15),
    IN p_razon_social VARCHAR(200),
    IN p_direccion_fiscal VARCHAR(255),
    IN p_condiciones_pago VARCHAR(10),
    IN p_descuento_habitual DECIMAL(5,2)
)
BEGIN
    DECLARE v_id_persona INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Obtener la persona asociada al cliente potencial
    SELECT id_persona INTO v_id_persona
    FROM ClientePotencial
    WHERE id_potencial = p_id_potencial
      AND estado != 'convertido'
      AND estado != 'perdido';

    IF v_id_persona IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El cliente potencial no existe o ya esta convertido/perdido';
    END IF;

    -- Crear el cliente formal
    INSERT INTO ClienteFormal (
        id_persona, codigo_cliente, nif_cif, razon_social,
        direccion_fiscal, condiciones_pago, descuento_habitual, estado
    ) VALUES (
        v_id_persona, p_codigo_cliente, p_nif_cif, p_razon_social,
        p_direccion_fiscal, p_condiciones_pago, p_descuento_habitual, 'activo'
    );

    -- Marcar el potencial como convertido
    UPDATE ClientePotencial
    SET estado = 'convertido'
    WHERE id_potencial = p_id_potencial;

    COMMIT;
END //

-- -------------------------------------------------------
-- PROCEDIMIENTO: generarFactura
-- Genera automaticamente una factura al servir un pedido:
--   1. Cambia el estado del pedido a 'servido'
--   2. Calcula base imponible desde las lineas
--   3. Crea la factura asociada
-- -------------------------------------------------------
CREATE PROCEDURE generarFactura (IN p_id_pedido INT)
BEGIN
    DECLARE v_id_cliente INT;
    DECLARE v_base DECIMAL(12,2);
    DECLARE v_iva DECIMAL(4,2);
    DECLARE v_total DECIMAL(12,2);
    DECLARE v_num_factura VARCHAR(30);
    DECLARE v_fecha_emision DATE;
    DECLARE v_fecha_vencimiento DATE;
    DECLARE v_condiciones VARCHAR(10);
    DECLARE v_dias INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Verificar que el pedido existe y no esta ya servido/anulado
    SELECT id_cliente_formal, estado
    INTO v_id_cliente, @estado_actual
    FROM Pedido
    WHERE id_pedido = p_id_pedido;

    IF @estado_actual IN ('servido', 'anulado') THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El pedido ya fue servido o anulado';
    END IF;

    -- Calcular base imponible sumando lineas
    SELECT COALESCE(SUM(
        ROUND(cantidad * precio_unitario * (1 - descuento_linea / 100), 2)
    ), 0) INTO v_base
    FROM LineaPedido
    WHERE id_pedido = p_id_pedido;

    IF v_base = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El pedido no tiene lineas o el total es cero';
    END IF;

    -- Obtener condiciones de pago del cliente
    SELECT condiciones_pago INTO v_condiciones
    FROM ClienteFormal
    WHERE id_formal = v_id_cliente;

    -- Determinar los dias de vencimiento segun condiciones
    SET v_dias = CASE v_condiciones
        WHEN 'contado' THEN 0
        WHEN '30' THEN 30
        WHEN '60' THEN 60
        WHEN '90' THEN 90
        ELSE 30
    END;

    -- Fijar IVA general (se podria parametrizar por producto)
    SET v_iva = 21.00;
    SET v_total = ROUND(v_base * (1 + v_iva / 100), 2);
    SET v_fecha_emision = CURDATE();
    SET v_fecha_vencimiento = DATE_ADD(v_fecha_emision, INTERVAL v_dias DAY);

    -- Generar numero de factura (serie F + año + correlativo)
    SET v_num_factura = CONCAT('F-', YEAR(v_fecha_emision), '-',
        LPAD((SELECT COALESCE(COUNT(*), 0) + 1 FROM Factura), 4, '0'));

    -- Insertar la factura
    INSERT INTO Factura (
        numero_factura, fecha_emision, fecha_vencimiento,
        id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado
    ) VALUES (
        v_num_factura, v_fecha_emision, v_fecha_vencimiento,
        v_id_cliente, p_id_pedido, v_base, v_iva, v_total, 'pendiente'
    );

    -- Marcar pedido como servido
    UPDATE Pedido SET estado = 'servido' WHERE id_pedido = p_id_pedido;

    COMMIT;
END //

DELIMITER ;

-- ============================================================
-- 3. TRIGGERS
-- ============================================================

DELIMITER //

-- -------------------------------------------------------
-- TRIGGER: trg_auditar_cambio_estado_factura
-- Auditoria: registra en AuditoriaFactura cualquier cambio
-- de estado en la tabla Factura.
-- -------------------------------------------------------
CREATE TRIGGER trg_auditar_cambio_estado_factura
AFTER UPDATE ON Factura
FOR EACH ROW
BEGIN
    IF OLD.estado != NEW.estado THEN
        INSERT INTO AuditoriaFactura (
            id_factura, estado_anterior, estado_nuevo, usuario, fecha_cambio
        ) VALUES (
            NEW.id_factura,
            OLD.estado,
            NEW.estado,
            CURRENT_USER(),
            NOW()
        );
    END IF;
END //

-- -------------------------------------------------------
-- TRIGGER: trg_descontar_stock_al_insertar_linea
-- Automatizacion: al insertar una linea de pedido, descuenta
-- la cantidad del stock del producto correspondiente.
-- -------------------------------------------------------
CREATE TRIGGER trg_descontar_stock_al_insertar_linea
AFTER INSERT ON LineaPedido
FOR EACH ROW
BEGIN
    UPDATE Producto
    SET stock = stock - NEW.cantidad
    WHERE id_producto = NEW.id_producto;
END //

DELIMITER ;

-- ============================================================
-- 4. DATOS DE PRUEBA
-- ============================================================

-- -------------------------------------------------------
-- 4a. Comerciales (5)
-- -------------------------------------------------------
INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES
('Carlos',   'carlos.lopez@email.com',    '612345678', '2026-01-15'),
('Maria',    'maria.rodriguez@email.com', '698765432', '2026-02-01'),
('Laura',    'laura.garcia@email.com',    '633221144', '2026-02-15'),
('Javier',   'javier.martin@email.com',   '644556677', '2026-03-01'),
('Elena',    'elena.diaz@email.com',      '655887799', '2026-03-10');

INSERT INTO Comercial (id_persona, codigo_comercial, apellidos, zona_geografica, fecha_alta) VALUES
(1, 'COM001', 'Lopez Garcia',    'Norte', '2026-01-15'),
(2, 'COM002', 'Rodriguez Perez', 'Sur',   '2026-02-01'),
(3, 'COM003', 'Garcia Fernandez', 'Este', '2026-02-15'),
(4, 'COM004', 'Martin Sanchez',   'Oeste', '2026-03-01'),
(5, 'COM005', 'Diaz Lopez',       'Centro', '2026-03-10');

-- -------------------------------------------------------
-- 4b. Clientes Potenciales (10)
-- -------------------------------------------------------
INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES
('Ana',      'ana.martinez@email.com',     '611223344', '2026-02-10'),
('Pedro',    'pedro.sanchez@email.com',    '622334455', '2026-03-05'),
('Sofia',    'sofia.lopez@email.com',      '633445566', '2026-03-12'),
('Miguel',   'miguel.ramirez@email.com',   '644556677', '2026-03-20'),
('Lucia',    'lucia.fernandez@email.com',  '655667788', '2026-04-01'),
('David',    'david.gonzalez@email.com',   '666778899', '2026-04-05'),
('Carmen',   'carmen.ruiz@email.com',      '677889900', '2026-04-10'),
('Alejandro','alejandro.torres@email.com', '688990011', '2026-04-15'),
('Isabel',   'isabel.navarro@email.com',   '699001122', '2026-04-20'),
('Raul',     'raul.jimenez@email.com',     '610203040', '2026-04-25');

INSERT INTO ClientePotencial (id_persona, empresa, fuente_captacion, estado, fecha_primer_contacto, id_comercial) VALUES
(6,  'TechCorp',     'Web',           'nuevo',         '2026-02-10', 1),
(7,  'InnovaTech',   'Recomendacion', 'en seguimiento','2026-03-05', 2),
(8,  'DataSoft',     'LinkedIn',      'nuevo',         '2026-03-12', 3),
(9,  'CloudSys',     'Feria sector',  'en seguimiento','2026-03-20', 4),
(10, 'NetSolutions', 'Email',         'perdido',       '2026-04-01', 5),
(11, 'WebPro',       'Web',           'nuevo',         '2026-04-05', 1),
(12, 'MobileApps',   'Recomendacion', 'en seguimiento','2026-04-10', 2),
(13, 'SmartDev',     'LinkedIn',      'nuevo',         '2026-04-15', 3),
(14, 'AlphaTech',    'Feria sector',  'perdido',       '2026-04-20', 4),
(15, 'BetaCorp',     'Email',         'nuevo',         '2026-04-25', 5);

-- -------------------------------------------------------
-- 4c. Clientes Formales (15)
-- Nota: los id_persona continuan desde el 16
-- -------------------------------------------------------
INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES
('Jorge',    'jorge.blanco@email.com',    '611111111', '2025-06-01'),
('Marta',    'marta.rios@email.com',      '622222222', '2025-06-15'),
('Alberto',  'alberto.vega@email.com',    '633333333', '2025-07-01'),
('Rosa',     'rosa.molina@email.com',     '644444444', '2025-07-15'),
('Antonio',  'antonio.cruz@email.com',    '655555555', '2025-08-01'),
('Teresa',   'teresa.soto@email.com',     '666666666', '2025-08-15'),
('Francisco','francisco.muro@email.com',  '677777777', '2025-09-01'),
('Patricia', 'patricia.pena@email.com',   '688888888', '2025-09-15'),
('Manuel',   'manuel.calvo@email.com',    '699999999', '2025-10-01'),
('Eva',      'eva.marquez@email.com',     '610101010', '2025-10-15'),
('Roberto',  'roberto.sanz@email.com',    '620202020', '2025-11-01'),
('Nuria',    'nuria.gil@email.com',       '630303030', '2025-11-15'),
('Victor',   'victor.oraa@email.com',     '640404040', '2025-12-01'),
('Silvia',   'silvia.lara@email.com',     '650505050', '2025-12-15'),
('Adrian',   'adrian.mora@email.com',     '660606060', '2026-01-01');

INSERT INTO ClienteFormal (id_persona, codigo_cliente, nif_cif, razon_social, direccion_fiscal, condiciones_pago, descuento_habitual, estado) VALUES
(16, 'CLI001', 'A12345678', 'Jorge Blanco SL',       'Calle Mayor 1',          '30',  5.00, 'activo'),
(17, 'CLI002', 'B23456789', 'Marta Rios SA',         'Avda. Principal 10',     '60',  3.00, 'activo'),
(18, 'CLI003', 'C34567890', 'Alberto Vega SL',       'Plaza Central 5',        'contado', 0.00, 'activo'),
(19, 'CLI004', 'D45678901', 'Rosa Molina SA',        'Calle Sol 20',           '30',  2.00, 'activo'),
(20, 'CLI005', 'E56789012', 'Antonio Cruz SL',       'Avda. Mar 15',           '90',  7.00, 'activo'),
(21, 'CLI006', 'F67890123', 'Teresa Soto SA',        'Calle Luna 8',           '30',  4.00, 'activo'),
(22, 'CLI007', 'G78901234', 'Francisco Muro SL',     'Calle Estrella 3',       '60',  0.00, 'inactivo'),
(23, 'CLI008', 'H89012345', 'Patricia Pena SA',      'Avda. Sol 25',           'contado', 1.50, 'activo'),
(24, 'CLI009', 'I90123456', 'Manuel Calvo SL',       'Calle Rio 12',           '30',  6.00, 'activo'),
(25, 'CLI010', 'J01234567', 'Eva Marquez SA',        'Plaza Mayor 7',          '90',  8.00, 'activo'),
(26, 'CLI011', 'K12345678', 'Roberto Sanz SL',       'Calle Olivo 4',          '60',  2.50, 'activo'),
(27, 'CLI012', 'L23456789', 'Nuria Gil SA',          'Avda. Monte 18',         '30',  3.50, 'activo'),
(28, 'CLI013', 'M34567890', 'Victor Oraa SL',        'Calle Pino 9',           'contado', 0.00, 'inactivo'),
(29, 'CLI014', 'N45678901', 'Silvia Lara SA',        'Calle Roble 22',         '60',  5.00, 'activo'),
(30, 'CLI015', 'O56789012', 'Adrian Mora SL',        'Avda. Cedro 14',         '30',  4.00, 'activo');

-- -------------------------------------------------------
-- 4d. Productos (10)
-- -------------------------------------------------------
INSERT INTO Producto (codigo, nombre, descripcion, precio_unitario, stock, categoria) VALUES
('PROD001', 'Consultoria Cloud',    'Servicio de migracion a la nube',     1500.00, 10, 'Servicios'),
('PROD002', 'Desarrollo Web',       'Creacion de sitios web corporativos', 2500.00, 15, 'Servicios'),
('PROD003', 'App Movil',            'Desarrollo de aplicaciones moviles',  3500.00, 8,  'Servicios'),
('PROD004', 'Servidor Dedicated',   'Servidor dedicado mensual',            800.00, 20, 'Infraestructura'),
('PROD005', 'Licencia Software',    'Licencia anual de software CRM',      1200.00, 50, 'Software'),
('PROD006', 'Seguridad Informatica','Auditoria de seguridad',              2000.00, 5,  'Servicios'),
('PROD007', 'Hosting Web',          'Hosting compartido anual',             300.00, 30, 'Infraestructura'),
('PROD008', 'Dominio .com',         'Registro de dominio anual',            25.00,  100, 'Infraestructura'),
('PROD009', 'Mantenimiento Web',    'Mantenimiento mensual web',            400.00, 25, 'Servicios'),
('PROD010', 'Formacion TI',         'Curso de formacion presencial',        1800.00, 12, 'Servicios');

-- -------------------------------------------------------
-- 4e. Pedidos (20) y sus Lineas
-- -------------------------------------------------------
INSERT INTO Pedido (fecha_pedido, id_cliente_formal, id_comercial, estado) VALUES
('2026-01-10', 1,  1, 'servido'),
('2026-01-15', 2,  2, 'servido'),
('2026-01-20', 3,  3, 'servido'),
('2026-02-01', 4,  4, 'servido'),
('2026-02-10', 5,  5, 'servido'),
('2026-02-15', 6,  1, 'servido'),
('2026-02-20', 7,  2, 'anulado'),
('2026-03-01', 8,  3, 'servido'),
('2026-03-05', 9,  4, 'servido'),
('2026-03-10', 10, 5, 'servido'),
('2026-03-15', 11, 1, 'pendiente'),
('2026-03-20', 12, 2, 'en curso'),
('2026-03-25', 13, 3, 'pendiente'),
('2026-04-01', 14, 4, 'servido'),
('2026-04-05', 15, 5, 'servido'),
('2026-04-10', 1,  1, 'pendiente'),
('2026-04-15', 2,  2, 'pendiente'),
('2026-04-20', 3,  3, 'en curso'),
('2026-04-25', 4,  4, 'pendiente'),
('2026-05-01', 5,  5, 'pendiente');

-- Lineas de Pedido (cada pedido tiene 1-3 lineas)
INSERT INTO LineaPedido (id_pedido, id_linea, id_producto, cantidad, precio_unitario, descuento_linea) VALUES
-- Pedido 1
(1, 1, 1, 2, 1500.00, 0),
(1, 2, 2, 1, 2500.00, 10),
-- Pedido 2
(2, 1, 3, 1, 3500.00, 0),
(2, 2, 4, 3, 800.00,  15),
(2, 3, 5, 2, 1200.00, 0),
-- Pedido 3
(3, 1, 6, 1, 2000.00, 0),
(3, 2, 7, 2, 300.00,  5),
-- Pedido 4
(4, 1, 8,  5, 25.00,   0),
(4, 2, 9,  1, 400.00,  0),
(4, 3, 10, 2, 1800.00, 10),
-- Pedido 5
(5, 1, 1, 1, 1500.00, 0),
(5, 2, 3, 1, 3500.00, 5),
-- Pedido 6
(6, 1, 2, 2, 2500.00, 0),
(6, 2, 4, 1, 800.00,  0),
-- Pedido 7
(7, 1, 5, 3, 1200.00, 0),
-- Pedido 8
(8, 1, 6, 1, 2000.00,  0),
(8, 2, 7, 3, 300.00,   0),
(8, 3, 9, 2, 400.00,   5),
-- Pedido 9
(9, 1, 1,  1, 1500.00, 0),
(9, 2, 8,  10, 25.00,  0),
(9, 3, 10, 1, 1800.00, 0),
-- Pedido 10
(10, 1, 3, 2, 3500.00, 0),
(10, 2, 4, 1, 800.00,  10),
-- Pedido 11
(11, 1, 2, 1, 2500.00, 0),
(11, 2, 5, 2, 1200.00, 0),
-- Pedido 12
(12, 1, 1, 2, 1500.00, 5),
(12, 2, 6, 1, 2000.00, 0),
(12, 3, 8, 5, 25.00,   0),
-- Pedido 13
(13, 1, 7, 2, 300.00, 0),
-- Pedido 14
(14, 1, 9,  1, 400.00,  0),
(14, 2, 10, 2, 1800.00, 0),
-- Pedido 15
(15, 1, 1, 1, 1500.00, 0),
(15, 2, 3, 1, 3500.00, 10),
-- Pedido 16
(16, 1, 4, 2, 800.00, 0),
-- Pedido 17
(17, 1, 5, 1, 1200.00, 0),
(17, 2, 2, 1, 2500.00, 0),
-- Pedido 18
(18, 1, 6, 1, 2000.00, 5),
(18, 2, 8, 3, 25.00,   0),
-- Pedido 19
(19, 1, 7, 2, 300.00,  0),
(19, 2, 9, 1, 400.00,  0),
(19, 3, 1, 1, 1500.00, 0),
-- Pedido 20
(20, 1, 10, 1, 1800.00, 0),
(20, 2, 3,  1, 3500.00, 0);

-- -------------------------------------------------------
-- 4f. Facturas (se generan automaticamente con el SP,
--     pero insertamos las correspondientes a pedidos
--     ya servidos y las de prueba directa)
-- -------------------------------------------------------
INSERT INTO Factura (numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado) VALUES
('F-2026-0001', '2026-01-10', '2026-02-09', 1, 1,  4750.00, 21.00, 5747.50, 'cobrada'),
('F-2026-0002', '2026-01-15', '2026-03-16', 2, 2,  6040.00, 21.00, 7308.40, 'cobrada'),
('F-2026-0003', '2026-01-20', '2026-01-20', 3, 3,  2570.00, 21.00, 3109.70, 'cobrada'),
('F-2026-0004', '2026-02-01', '2026-03-03', 4, 4,  4325.00, 21.00, 5233.25, 'cobrada'),
('F-2026-0005', '2026-02-10', '2026-05-11', 5, 5,  4800.00, 21.00, 5808.00, 'vencida'),
('F-2026-0006', '2026-02-15', '2026-03-17', 6, 6,  5800.00, 21.00, 7018.00, 'cobrada'),
('F-2026-0007', '2026-03-01', '2026-04-30', 8, 8,  3240.00, 21.00, 3920.40, 'pendiente'),
('F-2026-0008', '2026-03-05', '2026-04-04', 9, 9,  3550.00, 21.00, 4295.50, 'pendiente'),
('F-2026-0009', '2026-03-10', '2026-06-08', 10, 10, 7400.00, 21.00, 8954.00, 'pendiente'),
('F-2026-0010', '2026-04-01', '2026-05-31', 14, 14, 4000.00, 21.00, 4840.00, 'pendiente'),
('F-2026-0011', '2026-04-05', '2026-07-04', 15, 15, 4650.00, 21.00, 5626.50, 'pendiente');

-- ============================================================
-- 5. CONSULTAS DE NEGOCIO (SELECT)
-- ============================================================

-- -------------------------------------------------------
-- 5.1. Listado de clientes potenciales por comercial y estado
-- -------------------------------------------------------
SELECT
    c.codigo_comercial,
    CONCAT(p.nombre, ' ', c.apellidos) AS comercial,
    cp.estado,
    cp.id_potencial,
    pe.nombre AS contacto,
    cp.empresa,
    cp.fecha_primer_contacto
FROM Comercial c
JOIN Persona p ON c.id_persona = p.id_persona
JOIN ClientePotencial cp ON c.id_comercial = cp.id_comercial
JOIN Persona pe ON cp.id_persona = pe.id_persona
ORDER BY c.codigo_comercial, cp.estado, cp.fecha_primer_contacto;

-- -------------------------------------------------------
-- 5.2. Top 5 clientes por importe comprado en el ultimo año
-- -------------------------------------------------------
SELECT
    cf.codigo_cliente,
    cf.razon_social,
    p.nombre AS contacto,
    ROUND(SUM(f.total), 2) AS importe_total
FROM Factura f
JOIN ClienteFormal cf ON f.id_cliente_formal = cf.id_formal
JOIN Persona p ON cf.id_persona = p.id_persona
WHERE f.fecha_emision >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)
  AND f.estado != 'anulada'
GROUP BY cf.id_formal
ORDER BY importe_total DESC
LIMIT 5;

-- -------------------------------------------------------
-- 5.3. Facturas pendientes de cobro ordenadas por vencimiento
-- -------------------------------------------------------
SELECT
    f.numero_factura,
    f.fecha_emision,
    f.fecha_vencimiento,
    f.base_imponible,
    f.tipo_iva,
    f.total,
    DATEDIFF(f.fecha_vencimiento, CURDATE()) AS dias_restantes,
    cf.razon_social,
    cf.codigo_cliente
FROM Factura f
JOIN ClienteFormal cf ON f.id_cliente_formal = cf.id_formal
WHERE f.estado = 'pendiente'
ORDER BY f.fecha_vencimiento ASC;

-- -------------------------------------------------------
-- 5.4. Ranking de comerciales por importe vendido en el trimestre
-- -------------------------------------------------------
SELECT
    c.codigo_comercial,
    CONCAT(p.nombre, ' ', c.apellidos) AS comercial,
    c.zona_geografica,
    COUNT(DISTINCT f.id_factura) AS num_facturas,
    ROUND(COALESCE(SUM(f.total), 0), 2) AS importe_vendido
FROM Comercial c
JOIN Persona p ON c.id_persona = p.id_persona
LEFT JOIN Pedido pd ON c.id_comercial = pd.id_comercial
LEFT JOIN Factura f ON pd.id_pedido = f.id_pedido
    AND f.fecha_emision >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)
    AND f.estado != 'anulada'
GROUP BY c.id_comercial
ORDER BY importe_vendido DESC;

-- -------------------------------------------------------
-- 5.5. Pedidos en estado "pendiente" con antiguedad superior a 15 dias
-- -------------------------------------------------------
SELECT
    pd.id_pedido,
    pd.fecha_pedido,
    DATEDIFF(CURDATE(), pd.fecha_pedido) AS dias_pendiente,
    cf.razon_social,
    CONCAT(pe.nombre, ' ', c.apellidos) AS comercial,
    COALESCE(ROUND(SUM(lp.cantidad * lp.precio_unitario * (1 - lp.descuento_linea / 100)), 2), 0) AS total_estimado
FROM Pedido pd
JOIN ClienteFormal cf ON pd.id_cliente_formal = cf.id_formal
JOIN Comercial c ON pd.id_comercial = c.id_comercial
JOIN Persona pe ON c.id_persona = pe.id_persona
LEFT JOIN LineaPedido lp ON pd.id_pedido = lp.id_pedido
WHERE pd.estado = 'pendiente'
  AND DATEDIFF(CURDATE(), pd.fecha_pedido) > 15
GROUP BY pd.id_pedido
ORDER BY dias_pendiente DESC;

-- ============================================================
-- 6. SENTENCIAS UPDATE Y DELETE DE EJEMPLO
-- ============================================================

-- UPDATE: cambio de estado de un pedido
UPDATE Pedido SET estado = 'anulado' WHERE id_pedido = 13;

-- UPDATE: baja logica de un cliente formal
UPDATE ClienteFormal SET estado = 'inactivo' WHERE id_formal = 3;

-- DELETE: baja fisica de un cliente potencial perdido (solo ejemplo,
-- se propaga en cascada a Persona)
-- DELETE FROM ClientePotencial WHERE id_potencial = 5;

-- ============================================================
-- 7. ROLES Y PERMISOS (DCL)
-- ============================================================

-- Crear roles
CREATE ROLE IF NOT EXISTS 'admin_crm', 'comercial_crm', 'auditor_crm';

-- Admin: todos los privilegios sobre crm_xtart
GRANT ALL PRIVILEGES ON crm_xtart.* TO 'admin_crm';

-- Comercial: SELECT, INSERT, UPDATE en tablas de negocio
GRANT SELECT, INSERT, UPDATE ON crm_xtart.Persona TO 'comercial_crm';
GRANT SELECT, INSERT, UPDATE ON crm_xtart.ClientePotencial TO 'comercial_crm';
GRANT SELECT, INSERT, UPDATE ON crm_xtart.ClienteFormal TO 'comercial_crm';
GRANT SELECT, INSERT, UPDATE ON crm_xtart.Pedido TO 'comercial_crm';
GRANT SELECT, INSERT, UPDATE ON crm_xtart.LineaPedido TO 'comercial_crm';
GRANT SELECT ON crm_xtart.Producto TO 'comercial_crm';
GRANT SELECT ON crm_xtart.Factura TO 'comercial_crm';
GRANT SELECT ON crm_xtart.Comercial TO 'comercial_crm';
GRANT EXECUTE ON PROCEDURE crm_xtart.convertirClientePotencial TO 'comercial_crm';
GRANT EXECUTE ON PROCEDURE crm_xtart.generarFactura TO 'comercial_crm';

-- Auditor: solo lectura en todas las tablas
GRANT SELECT ON crm_xtart.* TO 'auditor_crm';

-- Nota: para crear usuarios y asignar roles:
-- CREATE USER 'admin1'@'localhost' IDENTIFIED BY 'admin123';
-- GRANT 'admin_crm' TO 'admin1'@'localhost';
-- SET DEFAULT ROLE 'admin_crm' TO 'admin1'@'localhost';

-- ============================================================
-- 8. VERIFICACION: llamadas a los SP de ejemplo
-- ============================================================

-- Convertir cliente potencial id=1 (Ana Martinez / TechCorp) a formal
-- CALL convertirClientePotencial(1, 'CLI016', 'P56789012', 'TechCorp SL',
--     'Calle Tecnologia 5', '30', 3.00);

-- Generar factura para pedido id=12
-- CALL generarFactura(12);

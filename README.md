CRM XTART - SISTEMA DE GESTIÓN DE RELACIONES CON CLIENTES
========================================================================

Este archivo contiene la documentación e instrucciones del ecosistema tecnológico 
del "CRM XTART", un sistema integral y distribuido para la gestión del ciclo 
comercial de una PYME. Desarrollado como Proyecto Intermodular de la 3ª Evaluación 
para el 1º curso de Desarrollo de Aplicaciones Multiplataforma (DAM) en 
XTART Formación Profesional (Mayo de 2026).

El sistema integra de forma transversal las competencias de cinco módulos:
- Bases de Datos (BD)
- Programación (PROG)
- Lenguajes de Marcas (LM)
- Entornos de Desarrollo (ED)
- Sistemas Informáticos (SI)

------------------------------------------------------------------------
1. EQUIPO DE DESARROLLO Y ROLES REVISADOS
------------------------------------------------------------------------

* Javier Stampa:
  - Desarrollador Principal de Base de Datos y Sistemas (BD, SI).
  - Diseño conceptual, relacional y físico del motor de datos centralizado 
    en MySQL/MariaDB (scripts DDL/DML, triggers de auditoría e inventario, 
    procedimientos almacenados transaccionales complejos y roles DCL).
  - Aprovisionamiento, dimensionamiento, configuración de red (IP estática), 
    protocolo RDP, apertura de reglas perimetrales de Firewall y despliegue 
    del stack en la máquina virtual autónoma corriendo Windows Server.

* Joel Guadalix:
  - Desarrollador Principal de Backend Java Swing (PROG).
  - Codificación completa de la aplicación cliente nativa de escritorio en Java SE 
    aplicando el patrón arquitectónico estructurado DAO.
  - Abstracción de flujos de E/S mediante conectividad JDBC tipada y consultas 
    parametrizadas protegidas (try-with-resources), herencia avanzada de objetos 
    (Persona), y automatización de pruebas de robustez aisladas con JUnit.
  - Diseño y maquetación de la interfaz de usuario Swing (Login, marcos MenuVentana 
    y rejillas tabulares dinámicas JTable) junto con el módulo de persistencia 
    por serialización binaria (.dat) de flujos de objetos con ObjectOutputStream.

* Francisco José:
  - Desarrollador de Frontend Web y API REST (LM, ED).
  - Diseño y programación del middleware del sistema mediante la construcción de 
    una API REST intermedia en Python utilizando el micro-framework Flask.
  - Configuración de controladores y mapeo de cursores a diccionarios JSON puros 
    para el intercambio ágil de información remota por el puerto 5000.
  - Diseño visual, maquetación semántica estructurada del Dashboard gerencial web 
    (HTML5), estilizado adaptativo en modo oscuro con clases utilitarias de 
    Tailwind CSS y programación de la interactividad mediante peticiones web asíncronas 
    (AJAX/jQuery) con motores de búsqueda local en tiempo real (keyup).

------------------------------------------------------------------------
2. ARQUITECTURA DEL SISTEMA
------------------------------------------------------------------------

El ecosistema de software adopta un modelo distribuido Cliente-Servidor de N Capas:

  [ Cliente de Escritorio (Java) ] --------> (Puerto 3306 - JDBC) ------+
  (Desarrollado por Joel Guadalix)                                     |
                                                                       v
  [ Dashboard Gerencial Web ] ---> (Puerto 5000 - JSON) ---> [ MÁQUINA VIRTUAL ]
  (Maquetado por Francisco José)                             Windows Server
                                                             IP: 192.168.56.10
                                                             (Configurado por 
                                                              Javier Stampa)
                                                                       |
  [ API REST (Python + Flask) ] ---> (Acceso Interno SQL) ------> [ MySQL/MariaDB ]
  (Programado por Francisco José)                            (Esquema crm_xtart)
                                                             (Creado por 
                                                              Javier Stampa)

------------------------------------------------------------------------
3. ESTRUCTURA DE DIRECTORIOS DEL REPOSITORIO
------------------------------------------------------------------------

trabajo_final_3t/
├── 01_BasesDeDatos/
│   ├── crm_schema.sql          # Script físico de tablas, triggers y procedimientos.
│   └── diagrama_er.png         # Diagrama de Entidad/Relación lógico-conceptual.
├── 02_Programacion/
│   ├── src/crm/
│   │   ├── dao/             # Data Access Object (Persistencia JDBC).
│   │   ├── database/        # Utilidad de conexión de red (ConexionBD.java).
│   │   ├── exception/       # Excepciones personalizadas (NifInvalidoException).
│   │   ├── main/            # Punto de entrada de la aplicación (Main.java).
│   │   ├── model/           # Clases del modelo OO (Herencia de Persona).
│   │   └── view/            # Formularios e interfaz gráfica Swing (Login, Ventanas).
│   └── javadoc/             # Documentación técnica compilada nativa en HTML.
├── 03_LenguajesDeMarcas/
│   └── crm_xtart/
│       ├── bd/              # Repositorio de acceso a datos interno de Python.
│       ├── templates/       # Vistas e interfaz web (index.html, menu.html).
│       └── main.py          # Servidor web principal del backend de Flask.
└── 04_EntornosDeDesarrollo/
    └── main.py              # Scripts de prueba y despliegue secundario de la API REST.

------------------------------------------------------------------------
4. COMPONENTES TÉCNICOS POR MÓDULO
------------------------------------------------------------------------

4.1. BASES DE DATOS (BD) - Diseñado por Javier Stampa
* Normalización (3FN): Esquema relacional optimizado. Se extrae la entidad débil 
  'LineaPedido' para resolver la relación muchos a muchos (N:M) entre Pedido y Producto.
* Procedimientos Almacenados:
  - 'convertirClientePotencial': Conversión atómica y segura de un Lead a Cliente Formal 
    mediante bloques de control transaccional explícito (START TRANSACTION / COMMIT / ROLLBACK).
  - 'generarFactura': Calcula las bases imponibles e importes netos históricos basándose en las 
    líneas de pedido, determinando las fechas fiscales de vencimiento según el contrato del cliente.
* Triggers Automatizados:
  - 'trg_descontar_stock_al_insertar_linea': Resta de forma matemática el inventario físico 
    en la tabla 'Producto' tras confirmarse una nueva línea de compra.
  - 'trg_auditar_cambio_estado_factura': Inserta registros históricos de seguridad de forma 
    automatizada en 'AuditoriaFactura' ante variaciones de estados fiscales.
* Control de Accesos (DCL): Roles de mínimo privilegio asignando accesos restrictivos para 
  'admin_crm', 'comercial_crm' (Java) y 'auditor_crm' (Python).

4.2. PROGRAMACIÓN (PROG) - Desarrollado por Joel Guadalix
* Abstracción y Patrón DAO: Aislamiento total de las sentencias SQL de la lógica Swing, 
  encapsulando flujos mediante bloques try-with-resources para prevenir fugas de memoria.
* Paradigma de Herencia POO: Implementación de una superclase abstracta 'Persona' (con modificadores 
  protected) de la cual heredan de forma especializada 'Comercial' y 'ClientePotencial'.
* Copia de Seguridad por Serialización: Implementación de la interfaz 'Serializable' para empaquetar 
  en binario el estado completo del software en memoria hacia un archivo local estructurado (.dat).

4.3. LENGUAJES DE MARCAS (LM) Y ENTORNOS DE DESARROLLO (ED) - Por Francisco José
* API REST de Python: Construida bajo Flask, expone endpoints que retornan colecciones serializadas 
  en formato JSON puro tras conectarse de forma nativa a MySQL utilizando cursores dictionary (clave-valor).
* Dashboard Ejecutivo SPA: Frontend estructurado bajo marcas semánticas de HTML5 que opera bajo la 
  filosofía de una sola página utilizando transiciones e interacciones por anclas lógicas.
* Estilos y Dinamismo: Diseño visual adaptativo en modo oscuro (Dark Mode) mediante clases de utilidad de 
  Tailwind CSS. Los listados consumen la API asíncronamente mediante jQuery ($.getJSON), procesando búsquedas 
  y filtrados de texto en tiempo real de forma local en la memoria del cliente.

4.4. SISTEMAS INFORMÁTICOS (SI) - Implementado por Javier Stampa
* Infraestructura Virtual: Servidor autónomo bajo Windows Server en Oracle VirtualBox dotado de 4 GB de 
  memoria RAM estricta, 2 vCPUs y 40 GB de almacenamiento dinámico.
* Direccionamiento de Red: Configuración en modo adaptador Solo Anfitrión con mapeo de IP Estática fija 
  (192.168.56.10) para estabilizar el consumo de la base de datos y la API REST.
* Firewall de Seguridad: Reglas de Entrada abriendo exclusivamente los puertos críticos: TCP 3389 (RDP), 
  TCP 3306 (MySQL remoto), TCP 80 (Apache HTTP) y TCP 5000 (Flask API).
  
------------------------------------------------------------------------

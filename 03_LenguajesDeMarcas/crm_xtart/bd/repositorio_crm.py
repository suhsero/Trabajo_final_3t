from bd.conexion import crear_conexion

def obtener_comerciales():
    con = crear_conexion()
    cur = con.cursor(dictionary=True)
    sql = """
        SELECT c.id_comercial, p.nombre, c.apellidos, p.email, p.telefono, c.zona_geografica, c.fecha_alta 
        FROM Comercial c 
        JOIN Persona p ON c.id_persona = p.id_persona
    """
    cur.execute(sql)
    resultados = cur.fetchall()
    con.close()
    return resultados

def obtener_clientes_potenciales():
    con = crear_conexion()
    cur = con.cursor(dictionary=True)
    sql = """
        SELECT cp.id_potencial, p.nombre, cp.empresa, p.email, p.telefono, cp.fuente_captacion, cp.estado, cp.fecha_primer_contacto 
        FROM ClientePotencial cp 
        JOIN Persona p ON cp.id_persona = p.id_persona
    """
    cur.execute(sql)
    resultados = cur.fetchall()
    con.close()
    return resultados

def obtener_clientes_formales():
    con = crear_conexion()
    cur = con.cursor(dictionary=True)
    sql = """
        SELECT cf.id_formal, cf.codigo_cliente, cf.nif_cif, cf.razon_social, p.email, p.telefono, cf.condiciones_pago, cf.descuento_habitual, cf.estado 
        FROM ClienteFormal cf 
        JOIN Persona p ON cf.id_persona = p.id_persona
    """
    cur.execute(sql)
    resultados = cur.fetchall()
    con.close()
    return resultados

def obtener_pedidos():
    con = crear_conexion()
    cur = con.cursor(dictionary=True)
    sql = """
        SELECT pe.id_pedido, pe.fecha_pedido, cf.razon_social AS cliente, CONCAT(p.nombre, ' ', c.apellidos) AS comercial, pe.estado 
        FROM Pedido pe 
        JOIN ClienteFormal cf ON pe.id_cliente_formal = cf.id_formal
        JOIN Comercial c ON pe.id_comercial = c.id_comercial
        JOIN Persona p ON c.id_persona = p.id_persona
    """
    cur.execute(sql)
    resultados = cur.fetchall()
    con.close()
    return resultados

def obtener_facturas():
    con = crear_conexion()
    cur = con.cursor(dictionary=True)
    sql = """
        SELECT f.id_factura, f.numero_factura, f.fecha_emision, f.fecha_vencimiento, cf.razon_social AS cliente, f.total, f.estado 
        FROM Factura f 
        JOIN ClienteFormal cf ON f.id_cliente_formal = cf.id_formal
    """
    cur.execute(sql)
    resultados = cur.fetchall()
    con.close()
    return resultados
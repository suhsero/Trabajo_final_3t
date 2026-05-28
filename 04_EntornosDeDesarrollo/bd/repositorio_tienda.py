
from bd.conexion import crear_conexion

def actualizar_producto(titulo, descripcion, precio,id):
    con = crear_conexion()
    cur = con.cursor()
    sql = "update libros set titulo = ?, descripcion = ?, precio = ? where id = ?"
    cur.execute(sql, (titulo, descripcion, precio,id))
    con.commit()
    con.close()

def obtener_producto_por_id(id):
    con = crear_conexion()
    cur = con.cursor()
    sql = "select * from libros where id = ?"
    cur.execute(sql, (id,))
    libro = cur.fetchone()
    con.close()
    return libro

def borrar_producto(id):
    con = crear_conexion()
    cur = con.cursor()
    sql = "delete from libros where id = ?"
    cur.execute(sql, (id,))
    con.commit()
    con.close()

def registrar_producto(titulo,descripcion,precio):
    con = crear_conexion()
    cur = con.cursor()
    sql = "insert into libros(titulo,descripcion,precio) values(?,?,?)"
    cur.execute(sql, (titulo,descripcion,precio) )
    con.commit()
    con.close()

def obtener_productos():
    con = crear_conexion()
    cur = con.cursor()
    cur.execute("select * from libros")
    filas = cur.fetchall()
    libros = [dict(row) for row in filas]
    con.close()
    return libros

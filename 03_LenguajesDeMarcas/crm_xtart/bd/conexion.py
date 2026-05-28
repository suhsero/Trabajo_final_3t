import mysql.connector

def crear_conexion():
    # Configuración adaptada a los parámetros por defecto de MySQL en XAMPP
    conexion = mysql.connector.connect(
        host="localhost",       # Cambiar por la IP de la MV si ejecutas desde el host físico
        user="root",            # Usuario Administrador por defecto de XAMPP
        password="",            # XAMPP no incluye contraseña por defecto
        database="crm_xtart"    # Nombre de la base de datos definida en tu archivo SQL
    )
    print("----- Conexión OK -----")
    return conexion
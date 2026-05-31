import mysql.connector

def crear_conexion():
    # Configuración adaptada a los parámetros por defecto de MySQL en XAMPP
    conexion = mysql.connector.connect(
        host="192.168.56.10",   # IP estática Host-Only de la MV Windows Server 2022
        user="root",            # Usuario Administrador por defecto de XAMPP
        password="",            # XAMPP no incluye contraseña por defecto
        database="crm_xtart"    # Nombre de la base de datos definida en tu archivo SQL
    )
    print("----- Conexión OK -----")
    return conexion
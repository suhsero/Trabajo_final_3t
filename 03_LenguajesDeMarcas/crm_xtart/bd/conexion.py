import mysql.connector

def crear_conexion():
    # Configuración adaptada a los parámetros por defecto de MySQL en XAMPP
    conexion = mysql.connector.connect(
        host="127.0.0.1",       # Conexión local dentro de la VM (Flask y MySQL en la misma máquina)
        user="root",            # Usuario Administrador por defecto de XAMPP
        password="",            # XAMPP no incluye contraseña por defecto
        database="crm_xtart",   # Nombre de la base de datos definida en tu archivo SQL
        use_pure=True           # Forzar implementación Python pura (evita crash de extensión C nativa)
    )
    print("----- Conexión OK -----")
    return conexion
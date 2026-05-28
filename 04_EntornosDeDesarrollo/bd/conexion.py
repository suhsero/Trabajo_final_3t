import sqlite3

def crear_conexion():
    conexion = sqlite3.connect("bd_libros.db", check_same_thread=False)
    conexion.row_factory = sqlite3.Row # filas como diccionarios
    cursor = conexion.cursor()
    # activar el uso de claves foraneas
    cursor.execute("PRAGMA foreign_keys = ON;") 
    # crear las tablas
    sql_crear_tabla_libros = """
    CREATE TABLE IF NOT EXISTS libros (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        titulo TEXT NOT NULL,
        descripcion TEXT NOT NULL,
        precio REAL NOT NULL CHECK (precio >= 0),
        creado_en TEXT DEFAULT CURRENT_TIMESTAMP
    );
    """
    conexion.execute(sql_crear_tabla_libros)
    conexion.commit()
    # preparar unos registros iniciales si la tabla libros ha sido creada
    cursor.execute("SELECT COUNT(*) FROM libros")
    if cursor.fetchone()[0] == 0:
        libros_iniciales = [
            ("Python Básico", "Introducción a la programación en Python", 29.99),
            ("Flask Web Development", "Crear aplicaciones web con Flask", 39.99),
            ("Bases de Datos SQL", "Aprende SQL desde cero", 34.99)
        ]
        cursor.executemany(
            "INSERT INTO libros (titulo, descripcion, precio) VALUES (?, ?, ?)",
            libros_iniciales
        )
        conexion.commit()


    print("----- conexion ok -----")
    return conexion
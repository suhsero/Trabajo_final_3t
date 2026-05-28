from flask import Flask, render_template, jsonify, request
import bd.repositorio_tienda as repositorio_tienda

#vamos a avanzar la tienda con flask 
# que es mas facil para ofrecer html
# fastapi no esta soportado por pythonanywhere

app = Flask(__name__)

# RUTAS para ofrecer vistas - html

# ruta publica
@app.route("/")
def inicio():
    return render_template("index.html")

# ruta para administracion
@app.route("/admin-libros")
def admin_libros():
    return render_template("admin-libros.html")

@app.route("/admin-usuarios")
def admin_usuarios():
    return render_template("admin-usuarios.html")

@app.route("/admin-editar")
def admin_editar():
    # primero obtengo la id del registro a editar
    id = request.args.get("id") #esta es la id incluida en el enlace con ?
    # obtengo todos los datos del registro a editar
    libro_con_todo = repositorio_tienda.obtener_producto_por_id(id)
    # muestro la vista donde el usuario pueda editar dichos datos
    return render_template("admin-libros-editar.html", libro = libro_con_todo)


# RUTAS para el API

@app.route("/libros")
def libros():
    # pedirlos a la bd
    return jsonify(repositorio_tienda.obtener_productos())

@app.route("/libros-registrar", methods = ["POST"])
def libros_registrar():
    # recoger los valores enviados desde admin.html
    titulo = request.form["titulo"]
    descripcion = request.form["descripcion"]
    precio = request.form["precio"]
    repositorio_tienda.registrar_producto(titulo,descripcion,precio)
    return "ok"

@app.route("/libros-borrar", methods = ["POST"])
def libros_borrar():
    id = request.form["id"]
    repositorio_tienda.borrar_producto(id)
    return "ok"

@app.route("/libros-guardar-cambios", methods = ["POST"])
def libros_guardar_cambios():
    titulo = request.form["titulo"]
    descripcion = request.form["descripcion"]
    precio = request.form["precio"]
    id = request.form["id"]
    repositorio_tienda.actualizar_producto(titulo, descripcion, precio,id)
    return "ok"

if __name__ == "__main__":
    app.run(debug=True)
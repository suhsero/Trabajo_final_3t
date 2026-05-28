from flask import Flask, render_template, jsonify
import bd.repositorio_crm as repositorio_crm

app = Flask(__name__)

# --- VISTAS WEB (HTML) ---
@app.route("/")
def inicio():
    return render_template("index.html")


# --- ENDPOINTS API REST (JSON) ---
@app.route("/api/comerciales")
def api_comerciales():
    return jsonify(repositorio_crm.obtener_comerciales())

@app.route("/api/potenciales")
def api_potenciales():
    return jsonify(repositorio_crm.obtener_clientes_potenciales())

@app.route("/api/formales")
def api_formales():
    return jsonify(repositorio_crm.obtener_clientes_formales())

@app.route("/api/pedidos")
def api_pedidos():
    return jsonify(repositorio_crm.obtener_pedidos())

@app.route("/api/facturas")
def api_facturas():
    return jsonify(repositorio_crm.obtener_facturas())


if __name__ == "__main__":
    app.run(debug=True)
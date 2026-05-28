package crm.model;

public class LineaPedido {
    private int idPedido;
    private int idLinea;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;
    private double descuentoLinea;

    public LineaPedido(int idPedido, int idLinea, int idProducto, int cantidad,
                       double precioUnitario, double descuentoLinea) {
        this.idPedido = idPedido;
        this.idLinea = idLinea;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuentoLinea = descuentoLinea;
    }

    public LineaPedido() {}

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdLinea() { return idLinea; }
    public void setIdLinea(int idLinea) { this.idLinea = idLinea; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getDescuentoLinea() { return descuentoLinea; }
    public void setDescuentoLinea(double descuentoLinea) { this.descuentoLinea = descuentoLinea; }
}
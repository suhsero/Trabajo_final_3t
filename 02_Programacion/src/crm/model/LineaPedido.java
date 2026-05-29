package crm.model;

/**
 * Representa una línea de detalle dentro de un pedido.
 * Cada línea contiene un producto, la cantidad solicitada y el precio aplicado.
 *
 * @author Javier
 * @version 1.0
 */
public class LineaPedido {
    private int idPedido;
    private int idLinea;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;
    private double descuentoLinea;

    /**
     * Constructor completo.
     *
     * @param idPedido       identificador del pedido al que pertenece la línea
     * @param idLinea        número de línea dentro del pedido
     * @param idProducto     identificador del producto
     * @param cantidad       unidades solicitadas
     * @param precioUnitario precio unitario aplicado en el momento del pedido
     * @param descuentoLinea porcentaje de descuento aplicado a esta línea
     */
    public LineaPedido(int idPedido, int idLinea, int idProducto, int cantidad,
                       double precioUnitario, double descuentoLinea) {
        this.idPedido = idPedido;
        this.idLinea = idLinea;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuentoLinea = descuentoLinea;
    }

    /** Constructor vacío requerido para instanciación vía DAO. */
    public LineaPedido() {}

    /** @return ID del pedido al que pertenece esta línea */
    public int getIdPedido() { return idPedido; }
    /** @param idPedido nuevo ID de pedido */
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    /** @return número de línea dentro del pedido */
    public int getIdLinea() { return idLinea; }
    /** @param idLinea nuevo número de línea */
    public void setIdLinea(int idLinea) { this.idLinea = idLinea; }

    /** @return ID del producto de esta línea */
    public int getIdProducto() { return idProducto; }
    /** @param idProducto nuevo ID de producto */
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    /** @return cantidad de unidades pedidas */
    public int getCantidad() { return cantidad; }
    /** @param cantidad nueva cantidad */
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    /** @return precio unitario aplicado */
    public double getPrecioUnitario() { return precioUnitario; }
    /** @param precioUnitario nuevo precio unitario */
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    /** @return porcentaje de descuento aplicado a la línea */
    public double getDescuentoLinea() { return descuentoLinea; }
    /** @param descuentoLinea nuevo porcentaje de descuento */
    public void setDescuentoLinea(double descuentoLinea) { this.descuentoLinea = descuentoLinea; }
}

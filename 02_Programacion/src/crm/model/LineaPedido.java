package crm.model;

/**
 * Representa una línea de detalle dentro de un pedido.
 * Cada línea corresponde a un producto, su cantidad y precio.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
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
     * Constructor completo con todos los campos de la línea de pedido.
     *
     * @param idPedido        ID del pedido al que pertenece esta línea.
     * @param idLinea         Número de línea dentro del pedido.
     * @param idProducto      ID del producto incluido en la línea.
     * @param cantidad        Unidades del producto solicitadas.
     * @param precioUnitario  Precio por unidad en el momento del pedido.
     * @param descuentoLinea  Descuento específico aplicado a esta línea (0.0 - 100.0).
     */
    public LineaPedido(int idPedido, int idLinea, int idProducto, int cantidad, double precioUnitario, double descuentoLinea) {
        this.idPedido = idPedido;
        this.idLinea = idLinea;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuentoLinea = descuentoLinea;
    }

    /**
     * Constructor vacío para mapeo desde ResultSet.
     */
    public LineaPedido() {
    }

    /**
     * Devuelve el ID del pedido al que pertenece esta línea.
     *
     * @return ID del pedido.
     */
    public int getIdPedido() {
        return this.idPedido;
    }

    /**
     * Establece el ID del pedido al que pertenece esta línea.
     *
     * @param idPedido Nuevo ID de pedido.
     */
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    /**
     * Devuelve el número de línea dentro del pedido.
     *
     * @return Número de línea.
     */
    public int getIdLinea() {
        return this.idLinea;
    }

    /**
     * Establece el número de línea.
     *
     * @param idLinea Nuevo número de línea.
     */
    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    /**
     * Devuelve el ID del producto incluido en esta línea.
     *
     * @return ID del producto.
     */
    public int getIdProducto() {
        return this.idProducto;
    }

    /**
     * Establece el producto de esta línea.
     *
     * @param idProducto Nuevo ID de producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Devuelve la cantidad de unidades solicitadas.
     *
     * @return Cantidad de unidades.
     */
    public int getCantidad() {
        return this.cantidad;
    }

    /**
     * Establece la cantidad de unidades.
     *
     * @param cantidad Nueva cantidad.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el precio unitario del producto en el momento del pedido.
     *
     * @return Precio unitario.
     */
    public double getPrecioUnitario() {
        return this.precioUnitario;
    }

    /**
     * Establece el precio unitario.
     *
     * @param precioUnitario Nuevo precio unitario.
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Devuelve el descuento aplicado a esta línea concreta.
     *
     * @return Descuento en porcentaje.
     */
    public double getDescuentoLinea() {
        return this.descuentoLinea;
    }

    /**
     * Establece el descuento de esta línea.
     *
     * @param descuentoLinea Nuevo porcentaje de descuento.
     */
    public void setDescuentoLinea(double descuentoLinea) {
        this.descuentoLinea = descuentoLinea;
    }
}

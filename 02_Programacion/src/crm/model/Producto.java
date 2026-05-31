package crm.model;

/**
 * Representa un producto o servicio del catálogo del CRM.
 * Es referenciado desde las líneas de pedido.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class Producto {
    private int idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private int stock;
    private String categoria;

    /**
     * Constructor completo con todos los campos del producto.
     *
     * @param idProducto     Identificador único del producto.
     * @param codigo         Código único de referencia del producto.
     * @param nombre         Nombre comercial del producto.
     * @param descripcion    Descripción detallada.
     * @param precioUnitario Precio por unidad (sin IVA).
     * @param stock          Unidades disponibles en almacén.
     * @param categoria      Categoría o familia del producto.
     */
    public Producto(int idProducto, String codigo, String nombre, String descripcion,
                    double precioUnitario, int stock, String categoria) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
        this.categoria = categoria;
    }

    /**
     * Constructor vacío para mapeo desde ResultSet.
     */
    public Producto() {
    }

    /**
     * Devuelve el identificador único del producto.
     *
     * @return ID del producto.
     */
    public int getIdProducto() {
        return this.idProducto;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param idProducto Nuevo ID.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Devuelve el código de referencia del producto.
     *
     * @return Código del producto.
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Establece el código de referencia.
     *
     * @param codigo Nuevo código.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el nombre comercial del producto.
     *
     * @return Nombre del producto.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la descripción detallada del producto.
     *
     * @return Descripción.
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion Nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el precio unitario sin IVA.
     *
     * @return Precio unitario.
     */
    public double getPrecioUnitario() {
        return this.precioUnitario;
    }

    /**
     * Establece el precio unitario.
     *
     * @param precioUnitario Nuevo precio.
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Devuelve el stock disponible del producto.
     *
     * @return Unidades en almacén.
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Establece el stock del producto.
     *
     * @param stock Nuevo valor de stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Devuelve la categoría del producto.
     *
     * @return Categoría.
     */
    public String getCategoria() {
        return this.categoria;
    }

    /**
     * Establece la categoría del producto.
     *
     * @param categoria Nueva categoría.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

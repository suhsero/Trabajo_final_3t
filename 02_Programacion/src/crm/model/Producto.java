package crm.model;

/**
 * Representa un producto del catálogo del CRM.
 * Almacena la información comercial y de stock de cada artículo.
 *
 * @author Javier
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
     * Constructor completo.
     *
     * @param idProducto    identificador único en BD
     * @param codigo        código de referencia del producto
     * @param nombre        nombre comercial del producto
     * @param descripcion   descripción detallada
     * @param precioUnitario precio de venta unitario (sin IVA)
     * @param stock         unidades disponibles en almacén
     * @param categoria     categoría a la que pertenece el producto
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

    /** Constructor vacío requerido para instanciación vía DAO. */
    public Producto() {}

    /** @return identificador único del producto */
    public int getIdProducto() { return idProducto; }
    /** @param idProducto nuevo identificador */
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    /** @return código de referencia */
    public String getCodigo() { return codigo; }
    /** @param codigo nuevo código */
    public void setCodigo(String codigo) { this.codigo = codigo; }

    /** @return nombre comercial */
    public String getNombre() { return nombre; }
    /** @param nombre nuevo nombre */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return descripción del producto */
    public String getDescripcion() { return descripcion; }
    /** @param descripcion nueva descripción */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** @return precio unitario sin IVA */
    public double getPrecioUnitario() { return precioUnitario; }
    /** @param precioUnitario nuevo precio unitario */
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    /** @return unidades disponibles en almacén */
    public int getStock() { return stock; }
    /** @param stock nuevo valor de stock */
    public void setStock(int stock) { this.stock = stock; }

    /** @return categoría del producto */
    public String getCategoria() { return categoria; }
    /** @param categoria nueva categoría */
    public void setCategoria(String categoria) { this.categoria = categoria; }
}

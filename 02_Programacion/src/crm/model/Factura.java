package crm.model;

import java.time.LocalDate;

/**
 * Representa una factura emitida a un cliente a partir de un pedido servido.
 * Contiene los datos fiscales, importes y estado de cobro del documento.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class Factura {
    private int idFactura;
    private String numeroFactura;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private int idClienteFormal;
    private int idPedido;
    private double baseImponible;
    private double tipoIva;
    private double total;
    private String estado;

    /**
     * Constructor completo con todos los campos de la factura.
     *
     * @param idFactura        Identificador único de la factura.
     * @param numeroFactura    Número de factura (serie + correlativo, único).
     * @param fechaEmision     Fecha en que se emite la factura.
     * @param fechaVencimiento Fecha límite de pago.
     * @param idClienteFormal  ID del cliente al que se emite la factura.
     * @param idPedido         ID del pedido asociado.
     * @param baseImponible    Importe sin IVA.
     * @param tipoIva          Porcentaje de IVA aplicado (ej: 21.0).
     * @param total            Importe total (base + IVA).
     * @param estado           Estado de la factura (pendiente, cobrada, vencida, anulada).
     */
    public Factura(int idFactura, String numeroFactura, LocalDate fechaEmision, LocalDate fechaVencimiento,
                   int idClienteFormal, int idPedido, double baseImponible, double tipoIva, double total, String estado) {
        this.idFactura = idFactura;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.idClienteFormal = idClienteFormal;
        this.idPedido = idPedido;
        this.baseImponible = baseImponible;
        this.tipoIva = tipoIva;
        this.total = total;
        this.estado = estado;
    }

    /**
     * Constructor vacío para mapeo desde ResultSet.
     */
    public Factura() {
    }

    /**
     * Devuelve el identificador único de la factura.
     *
     * @return ID de la factura.
     */
    public int getIdFactura() {
        return this.idFactura;
    }

    /**
     * Establece el identificador de la factura.
     *
     * @param idFactura Nuevo ID.
     */
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * Devuelve el número de factura (serie + correlativo).
     *
     * @return Número de factura.
     */
    public String getNumeroFactura() {
        return this.numeroFactura;
    }

    /**
     * Establece el número de factura.
     *
     * @param numeroFactura Nuevo número de factura.
     */
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    /**
     * Devuelve la fecha de emisión de la factura.
     *
     * @return Fecha de emisión.
     */
    public LocalDate getFechaEmision() {
        return this.fechaEmision;
    }

    /**
     * Establece la fecha de emisión.
     *
     * @param fechaEmision Nueva fecha de emisión.
     */
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Devuelve la fecha de vencimiento del pago.
     *
     * @return Fecha de vencimiento.
     */
    public LocalDate getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    /**
     * Establece la fecha de vencimiento.
     *
     * @param fechaVencimiento Nueva fecha de vencimiento.
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Devuelve el ID del cliente formal al que se emite la factura.
     *
     * @return ID del cliente formal.
     */
    public int getIdClienteFormal() {
        return this.idClienteFormal;
    }

    /**
     * Establece el cliente formal de la factura.
     *
     * @param idClienteFormal Nuevo ID de cliente.
     */
    public void setIdClienteFormal(int idClienteFormal) {
        this.idClienteFormal = idClienteFormal;
    }

    /**
     * Devuelve el ID del pedido del que deriva esta factura.
     *
     * @return ID del pedido asociado.
     */
    public int getIdPedido() {
        return this.idPedido;
    }

    /**
     * Establece el pedido asociado a la factura.
     *
     * @param idPedido Nuevo ID de pedido.
     */
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    /**
     * Devuelve la base imponible (importe sin IVA).
     *
     * @return Base imponible.
     */
    public double getBaseImponible() {
        return this.baseImponible;
    }

    /**
     * Establece la base imponible.
     *
     * @param baseImponible Nueva base imponible.
     */
    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    /**
     * Devuelve el tipo de IVA aplicado.
     *
     * @return Porcentaje de IVA.
     */
    public double getTipoIva() {
        return this.tipoIva;
    }

    /**
     * Establece el tipo de IVA.
     *
     * @param tipoIva Nuevo porcentaje de IVA.
     */
    public void setTipoIva(double tipoIva) {
        this.tipoIva = tipoIva;
    }

    /**
     * Devuelve el importe total de la factura (base + IVA).
     *
     * @return Total factura.
     */
    public double getTotal() {
        return this.total;
    }

    /**
     * Establece el importe total de la factura.
     *
     * @param total Nuevo total.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Devuelve el estado de la factura.
     *
     * @return Estado (pendiente, cobrada, vencida, anulada).
     */
    public String getEstado() {
        return this.estado;
    }

    /**
     * Establece el estado de la factura.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

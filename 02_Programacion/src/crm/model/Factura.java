package crm.model;

import java.time.LocalDate;

/**
 * Representa una factura emitida a un cliente formal.
 * Contiene los datos fiscales, importes y estado de cobro.
 *
 * @author Javier
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
     * Constructor completo.
     *
     * @param idFactura       identificador único en BD
     * @param numeroFactura   número de factura (ej. FAC-2026-001)
     * @param fechaEmision    fecha de emisión de la factura
     * @param fechaVencimiento fecha límite de pago
     * @param idClienteFormal  identificador del cliente al que se factura
     * @param idPedido        identificador del pedido facturado
     * @param baseImponible   importe antes de IVA
     * @param tipoIva         porcentaje de IVA aplicado
     * @param total           importe total (base + IVA)
     * @param estado          estado de cobro (pendiente, pagada, vencida)
     */
    public Factura(int idFactura, String numeroFactura, LocalDate fechaEmision, LocalDate fechaVencimiento,
                   int idClienteFormal, int idPedido, double baseImponible, double tipoIva,
                   double total, String estado) {
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

    /** Constructor vacío requerido para instanciación vía DAO. */
    public Factura() {}

    /** @return identificador único de la factura */
    public int getIdFactura() { return idFactura; }
    /** @param idFactura nuevo identificador */
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    /** @return número de factura */
    public String getNumeroFactura() { return numeroFactura; }
    /** @param numeroFactura nuevo número de factura */
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    /** @return fecha de emisión */
    public LocalDate getFechaEmision() { return fechaEmision; }
    /** @param fechaEmision nueva fecha de emisión */
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    /** @return fecha de vencimiento */
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    /** @param fechaVencimiento nueva fecha de vencimiento */
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    /** @return ID del cliente formal */
    public int getIdClienteFormal() { return idClienteFormal; }
    /** @param idClienteFormal nuevo ID de cliente */
    public void setIdClienteFormal(int idClienteFormal) { this.idClienteFormal = idClienteFormal; }

    /** @return ID del pedido asociado */
    public int getIdPedido() { return idPedido; }
    /** @param idPedido nuevo ID de pedido */
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    /** @return base imponible (sin IVA) */
    public double getBaseImponible() { return baseImponible; }
    /** @param baseImponible nueva base imponible */
    public void setBaseImponible(double baseImponible) { this.baseImponible = baseImponible; }

    /** @return porcentaje de IVA aplicado */
    public double getTipoIva() { return tipoIva; }
    /** @param tipoIva nuevo porcentaje de IVA */
    public void setTipoIva(double tipoIva) { this.tipoIva = tipoIva; }

    /** @return importe total (base + IVA) */
    public double getTotal() { return total; }
    /** @param total nuevo total */
    public void setTotal(double total) { this.total = total; }

    /** @return estado de cobro de la factura */
    public String getEstado() { return estado; }
    /** @param estado nuevo estado */
    public void setEstado(String estado) { this.estado = estado; }
}

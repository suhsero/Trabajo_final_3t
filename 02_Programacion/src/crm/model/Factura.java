package crm.model;

import java.time.LocalDate;

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

    public Factura(int idFactura, String numeroFactura, LocalDate fechaEmision, LocalDate fechaVencimiento, int idClienteFormal, int idPedido, double baseImponible, double tipoIva, double total, String estado) {
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

    public Factura() {
    }

    public int getIdFactura() {
        return this.idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getNumeroFactura() {
        return this.numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getFechaEmision() {
        return this.fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdClienteFormal() {
        return this.idClienteFormal;
    }

    public void setIdClienteFormal(int idClienteFormal) {
        this.idClienteFormal = idClienteFormal;
    }

    public int getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getBaseImponible() {
        return this.baseImponible;
    }

    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public double getTipoIva() {
        return this.tipoIva;
    }

    public void setTipoIva(double tipoIva) {
        this.tipoIva = tipoIva;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

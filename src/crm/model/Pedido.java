package crm.model;

import java.time.LocalDateTime;

public class Pedido {
    private int idPedido;
    private LocalDateTime fechaPedido;
    private int idClienteFormal;
    private int idComercial;
    private String estado;

    public Pedido(int idPedido, LocalDateTime fechaPedido, int idClienteFormal, int idComercial, String estado) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.idClienteFormal = idClienteFormal;
        this.idComercial = idComercial;
        this.estado = estado;
    }

    public Pedido() {}

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public int getIdClienteFormal() { return idClienteFormal; }
    public void setIdClienteFormal(int idClienteFormal) { this.idClienteFormal = idClienteFormal; }

    public int getIdComercial() { return idComercial; }
    public void setIdComercial(int idComercial) { this.idComercial = idComercial; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
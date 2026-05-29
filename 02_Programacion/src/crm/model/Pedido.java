package crm.model;

import java.time.LocalDateTime;

/**
 * Representa un pedido realizado por un cliente formal.
 * Contiene los datos básicos del encargo antes de la facturación.
 *
 * @author Javier
 * @version 1.0
 */
public class Pedido {
    private int idPedido;
    private LocalDateTime fechaPedido;
    private int idClienteFormal;
    private int idComercial;
    private String estado;

    /**
     * Constructor completo.
     *
     * @param idPedido       identificador único del pedido en BD
     * @param fechaPedido    fecha y hora de creación del pedido
     * @param idClienteFormal identificador del cliente formal que realiza el pedido
     * @param idComercial    identificador del comercial que gestiona el pedido
     * @param estado         estado actual del pedido (pendiente, en curso, servido, anulado)
     */
    public Pedido(int idPedido, LocalDateTime fechaPedido, int idClienteFormal, int idComercial, String estado) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.idClienteFormal = idClienteFormal;
        this.idComercial = idComercial;
        this.estado = estado;
    }

    /** Constructor vacío requerido para instanciación vía DAO. */
    public Pedido() {}

    /** @return identificador único del pedido */
    public int getIdPedido() { return idPedido; }
    /** @param idPedido nuevo identificador */
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    /** @return fecha y hora de creación del pedido */
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    /** @param fechaPedido nueva fecha del pedido */
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    /** @return ID del cliente formal asociado */
    public int getIdClienteFormal() { return idClienteFormal; }
    /** @param idClienteFormal nuevo ID de cliente formal */
    public void setIdClienteFormal(int idClienteFormal) { this.idClienteFormal = idClienteFormal; }

    /** @return ID del comercial que gestiona el pedido */
    public int getIdComercial() { return idComercial; }
    /** @param idComercial nuevo ID de comercial */
    public void setIdComercial(int idComercial) { this.idComercial = idComercial; }

    /** @return estado actual del pedido */
    public String getEstado() { return estado; }
    /** @param estado nuevo estado */
    public void setEstado(String estado) { this.estado = estado; }
}

package crm.model;

import java.time.LocalDateTime;

/**
 * Representa un pedido realizado por un cliente formal.
 * Contiene los datos de cabecera del pedido; las líneas de detalle
 * se gestionan en {@link LineaPedido}.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class Pedido {
    private int idPedido;
    private LocalDateTime fechaPedido;
    private int idClienteFormal;
    private int idComercial;
    private String estado;

    /**
     * Constructor completo con todos los campos del pedido.
     *
     * @param idPedido        Identificador único del pedido (autonumérico).
     * @param fechaPedido     Fecha y hora en que se registró el pedido.
     * @param idClienteFormal ID del cliente formal que realiza el pedido.
     * @param idComercial     ID del comercial que gestiona el pedido.
     * @param estado          Estado del pedido (pendiente, en curso, servido, anulado).
     */
    public Pedido(int idPedido, LocalDateTime fechaPedido, int idClienteFormal, int idComercial, String estado) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.idClienteFormal = idClienteFormal;
        this.idComercial = idComercial;
        this.estado = estado;
    }

    /**
     * Constructor vacío para mapeo desde ResultSet.
     */
    public Pedido() {
    }

    /**
     * Devuelve el identificador único del pedido.
     *
     * @return ID del pedido.
     */
    public int getIdPedido() {
        return this.idPedido;
    }

    /**
     * Establece el identificador del pedido.
     *
     * @param idPedido Nuevo ID de pedido.
     */
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    /**
     * Devuelve la fecha y hora en que se registró el pedido.
     *
     * @return Fecha y hora del pedido.
     */
    public LocalDateTime getFechaPedido() {
        return this.fechaPedido;
    }

    /**
     * Establece la fecha y hora del pedido.
     *
     * @param fechaPedido Nueva fecha de pedido.
     */
    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    /**
     * Devuelve el ID del cliente formal asociado.
     *
     * @return ID del cliente formal.
     */
    public int getIdClienteFormal() {
        return this.idClienteFormal;
    }

    /**
     * Establece el cliente formal del pedido.
     *
     * @param idClienteFormal Nuevo ID de cliente formal.
     */
    public void setIdClienteFormal(int idClienteFormal) {
        this.idClienteFormal = idClienteFormal;
    }

    /**
     * Devuelve el ID del comercial que gestiona el pedido.
     *
     * @return ID del comercial.
     */
    public int getIdComercial() {
        return this.idComercial;
    }

    /**
     * Establece el comercial responsable del pedido.
     *
     * @param idComercial Nuevo ID de comercial.
     */
    public void setIdComercial(int idComercial) {
        this.idComercial = idComercial;
    }

    /**
     * Devuelve el estado actual del pedido.
     *
     * @return Estado (pendiente, en curso, servido, anulado).
     */
    public String getEstado() {
        return this.estado;
    }

    /**
     * Establece el estado del pedido.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

package crm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa a un contacto comercial que aún no ha formalizado ninguna compra.
 * Es el primer paso del embudo de ventas del CRM.
 * Hereda de {@link ClienteBase} e implementa {@link Serializable} para
 * permitir la persistencia en fichero mediante ObjectOutputStream.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class ClientePotencial extends ClienteBase implements Serializable {
    private int idPotencial;
    private String empresa;
    private String fuenteCaptacion;
    private LocalDate fechaPrimerContacto;
    private int idComercialAsignado;

    /**
     * Constructor completo con todos los campos del cliente potencial.
     *
     * @param idPersona            ID de la tabla Persona.
     * @param nombre               Nombre del contacto.
     * @param email                Correo electrónico único.
     * @param telefono             Teléfono de contacto.
     * @param fechaRegistro        Fecha de alta en el sistema.
     * @param idPotencial          ID propio de la tabla ClientePotencial.
     * @param empresa              Nombre de la empresa del contacto.
     * @param fuenteCaptacion      Cómo se captó el contacto (web, feria, referido, etc.).
     * @param estado               Estado actual (nuevo, en seguimiento, perdido, convertido).
     * @param fechaPrimerContacto  Fecha en que se realizó el primer contacto.
     * @param idComercialAsignado  ID del comercial responsable de este potencial.
     */
    public ClientePotencial(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro,
                            int idPotencial, String empresa, String fuenteCaptacion, String estado,
                            LocalDate fechaPrimerContacto, int idComercialAsignado) {
        super(idPersona, nombre, email, telefono, fechaRegistro, estado);
        this.idPotencial = idPotencial;
        this.empresa = empresa;
        this.fuenteCaptacion = fuenteCaptacion;
        this.fechaPrimerContacto = fechaPrimerContacto;
        this.idComercialAsignado = idComercialAsignado;
    }

    /**
     * Constructor vacío para mapeo desde ResultSet o deserialización.
     */
    public ClientePotencial() {
    }

    /**
     * Devuelve el identificador de la tabla ClientePotencial.
     *
     * @return ID del cliente potencial.
     */
    public int getIdPotencial() {
        return this.idPotencial;
    }

    /**
     * Establece el identificador del cliente potencial.
     *
     * @param idPotencial Nuevo ID.
     */
    public void setIdPotencial(int idPotencial) {
        this.idPotencial = idPotencial;
    }

    /**
     * Devuelve el nombre de la empresa del contacto.
     *
     * @return Nombre de la empresa.
     */
    public String getEmpresa() {
        return this.empresa;
    }

    /**
     * Establece el nombre de la empresa.
     *
     * @param empresa Nuevo nombre de empresa.
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * Devuelve la fuente de captación del contacto.
     *
     * @return Fuente de captación (web, feria, referido, etc.).
     */
    public String getFuenteCaptacion() {
        return this.fuenteCaptacion;
    }

    /**
     * Establece la fuente de captación.
     *
     * @param fuenteCaptacion Nueva fuente de captación.
     */
    public void setFuenteCaptacion(String fuenteCaptacion) {
        this.fuenteCaptacion = fuenteCaptacion;
    }

    /**
     * Devuelve la fecha del primer contacto con este potencial.
     *
     * @return Fecha del primer contacto.
     */
    public LocalDate getFechaPrimerContacto() {
        return this.fechaPrimerContacto;
    }

    /**
     * Establece la fecha del primer contacto.
     *
     * @param fechaPrimerContacto Nueva fecha de primer contacto.
     */
    public void setFechaPrimerContacto(LocalDate fechaPrimerContacto) {
        this.fechaPrimerContacto = fechaPrimerContacto;
    }

    /**
     * Devuelve el ID del comercial asignado a este potencial.
     *
     * @return ID del comercial responsable.
     */
    public int getIdComercialAsignado() {
        return this.idComercialAsignado;
    }

    /**
     * Establece el comercial asignado a este potencial.
     *
     * @param idComercialAsignado ID del nuevo comercial asignado.
     */
    public void setIdComercialAsignado(int idComercialAsignado) {
        this.idComercialAsignado = idComercialAsignado;
    }
}

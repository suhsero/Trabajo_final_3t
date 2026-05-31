package crm.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa a un miembro del equipo de ventas de la empresa.
 * Hereda de {@link Persona} añadiendo los datos propios del rol comercial.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class Comercial extends Persona {
    private int idComercial;
    private String codigoComercial;
    private String apellidos;
    private String zonaGeografica;
    private LocalDate fechaAlta;

    /**
     * Constructor completo con todos los campos del comercial.
     *
     * @param idPersona        ID de la tabla Persona.
     * @param nombre           Nombre del comercial.
     * @param email            Correo electrónico corporativo.
     * @param telefono         Teléfono de contacto.
     * @param fechaRegistro    Fecha de registro en el sistema.
     * @param idComercial      ID propio de la tabla Comercial.
     * @param codigoComercial  Código único del comercial (ej: COM-01).
     * @param apellidos        Apellidos del comercial.
     * @param zonaGeografica   Zona geográfica asignada.
     * @param fechaAlta        Fecha en que se incorporó a la empresa.
     */
    public Comercial(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro,
                     int idComercial, String codigoComercial, String apellidos, String zonaGeografica, LocalDate fechaAlta) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.idComercial = idComercial;
        this.codigoComercial = codigoComercial;
        this.apellidos = apellidos;
        this.zonaGeografica = zonaGeografica;
        this.fechaAlta = fechaAlta;
    }

    /**
     * Constructor vacío para mapeo desde ResultSet.
     */
    public Comercial() {
    }

    /**
     * Devuelve el identificador de la tabla Comercial.
     *
     * @return ID del comercial.
     */
    public int getIdComercial() {
        return this.idComercial;
    }

    /**
     * Establece el identificador del comercial.
     *
     * @param idComercial Nuevo ID.
     */
    public void setIdComercial(int idComercial) {
        this.idComercial = idComercial;
    }

    /**
     * Devuelve el código único del comercial.
     *
     * @return Código de comercial.
     */
    public String getCodigoComercial() {
        return this.codigoComercial;
    }

    /**
     * Establece el código único del comercial.
     *
     * @param codigoComercial Nuevo código.
     */
    public void setCodigoComercial(String codigoComercial) {
        this.codigoComercial = codigoComercial;
    }

    /**
     * Devuelve los apellidos del comercial.
     *
     * @return Apellidos.
     */
    public String getApellidos() {
        return this.apellidos;
    }

    /**
     * Establece los apellidos del comercial.
     *
     * @param apellidos Nuevos apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Devuelve la zona geográfica asignada al comercial.
     *
     * @return Zona geográfica.
     */
    public String getZonaGeografica() {
        return this.zonaGeografica;
    }

    /**
     * Establece la zona geográfica del comercial.
     *
     * @param zonaGeografica Nueva zona.
     */
    public void setZonaGeografica(String zonaGeografica) {
        this.zonaGeografica = zonaGeografica;
    }

    /**
     * Devuelve la fecha de alta del comercial en la empresa.
     *
     * @return Fecha de incorporación.
     */
    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    /**
     * Establece la fecha de alta del comercial.
     *
     * @param fechaAlta Nueva fecha de alta.
     */
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}

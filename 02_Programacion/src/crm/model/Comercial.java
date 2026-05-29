package crm.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa a un agente comercial de la empresa.
 * Extiende {@link Persona} con datos específicos del rol comercial.
 *
 * @author Javier
 * @version 1.0
 */
public class Comercial extends Persona {
    private int idComercial;
    private String codigoComercial;
    private String apellidos;
    private String zonaGeografica;
    private LocalDate fechaAlta;

    /**
     * Constructor completo.
     *
     * @param idPersona      identificador único en la tabla Persona
     * @param nombre         nombre del comercial
     * @param email          correo electrónico
     * @param telefono       teléfono de contacto
     * @param fechaRegistro  fecha y hora de alta en el sistema
     * @param idComercial    identificador único en la tabla Comercial
     * @param codigoComercial código alfanumérico del comercial
     * @param apellidos      apellidos del comercial
     * @param zonaGeografica zona geográfica que gestiona
     * @param fechaAlta      fecha de incorporación a la empresa
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

    /** Constructor vacío requerido para instanciación vía DAO. */
    public Comercial() {
        super();
    }

    /** @return identificador único en la tabla Comercial */
    public int getIdComercial() { return idComercial; }
    /** @param idComercial nuevo identificador */
    public void setIdComercial(int idComercial) { this.idComercial = idComercial; }

    /** @return código alfanumérico del comercial */
    public String getCodigoComercial() { return codigoComercial; }
    /** @param codigoComercial nuevo código */
    public void setCodigoComercial(String codigoComercial) { this.codigoComercial = codigoComercial; }

    /** @return apellidos del comercial */
    public String getApellidos() { return apellidos; }
    /** @param apellidos nuevos apellidos */
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    /** @return zona geográfica gestionada */
    public String getZonaGeografica() { return zonaGeografica; }
    /** @param zonaGeografica nueva zona geográfica */
    public void setZonaGeografica(String zonaGeografica) { this.zonaGeografica = zonaGeografica; }

    /** @return fecha de incorporación a la empresa */
    public LocalDate getFechaAlta() { return fechaAlta; }
    /** @param fechaAlta nueva fecha de incorporación */
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
}

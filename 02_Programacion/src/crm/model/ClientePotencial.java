package crm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa a un cliente potencial (lead) captado por el equipo comercial.
 * Implementa {@link Serializable} para permitir su serialización.
 * Extiende {@link ClienteBase} con información específica de prospección.
 *
 * @author Javier
 * @version 1.0
 */
public class ClientePotencial extends ClienteBase implements Serializable {
    private int idPotencial;
    private String empresa;
    private String fuenteCaptacion;
    private LocalDate fechaPrimerContacto;
    /** ID del comercial que gestiona este lead */
    private int idComercialAsignado;

    /**
     * Constructor completo.
     *
     * @param idPersona           identificador único en la tabla Persona
     * @param nombre              nombre del contacto
     * @param email               correo electrónico
     * @param telefono            teléfono de contacto
     * @param fechaRegistro       fecha y hora de alta en el sistema
     * @param idPotencial         identificador único en la tabla ClientePotencial
     * @param empresa             empresa a la que pertenece el lead
     * @param fuenteCaptacion     canal por el que fue captado (web, referido, feria, etc.)
     * @param estado              estado del lead (nuevo, en seguimiento, convertido, perdido)
     * @param fechaPrimerContacto fecha del primer contacto comercial
     * @param idComercialAsignado identificador del comercial responsable
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

    /** Constructor vacío requerido para instanciación vía DAO. */
    public ClientePotencial() {
        super();
    }

    /** @return identificador único en la tabla ClientePotencial */
    public int getIdPotencial() { return idPotencial; }
    /** @param idPotencial nuevo identificador */
    public void setIdPotencial(int idPotencial) { this.idPotencial = idPotencial; }

    /** @return empresa del lead */
    public String getEmpresa() { return empresa; }
    /** @param empresa nombre de la empresa */
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    /** @return canal de captación del lead */
    public String getFuenteCaptacion() { return fuenteCaptacion; }
    /** @param fuenteCaptacion nuevo canal de captación */
    public void setFuenteCaptacion(String fuenteCaptacion) { this.fuenteCaptacion = fuenteCaptacion; }

    /** @return fecha del primer contacto comercial */
    public LocalDate getFechaPrimerContacto() { return fechaPrimerContacto; }
    /** @param fechaPrimerContacto nueva fecha de primer contacto */
    public void setFechaPrimerContacto(LocalDate fechaPrimerContacto) { this.fechaPrimerContacto = fechaPrimerContacto; }

    /** @return ID del comercial responsable del seguimiento */
    public int getIdComercialAsignado() { return idComercialAsignado; }
    /** @param idComercialAsignado ID del nuevo comercial asignado */
    public void setIdComercialAsignado(int idComercialAsignado) { this.idComercialAsignado = idComercialAsignado; }
}

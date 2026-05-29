package crm.model;

import java.time.LocalDateTime;

/**
 * Representa a un cliente formal (empresa o autónomo con contrato activo).
 * Extiende {@link ClienteBase} y añade los datos fiscales y comerciales propios.
 *
 * @author Javier
 * @version 1.0
 */
public class ClienteFormal extends ClienteBase {
    private int idFormal;
    private String codigoCliente;
    private String nifCif;
    private String razonSocial;
    private String direccionFiscal;
    private String condicionesPago;
    private double descuentoHabitual;

    /**
     * Constructor completo.
     *
     * @param idPersona        identificador único en la tabla Persona
     * @param nombre           nombre del contacto principal
     * @param email            correo electrónico
     * @param telefono         teléfono de contacto
     * @param fechaRegistro    fecha y hora de alta en el sistema
     * @param idFormal         identificador único en la tabla ClienteFormal
     * @param codigoCliente    código alfanumérico de cliente
     * @param nifCif           NIF o CIF fiscal
     * @param razonSocial      razón social de la empresa
     * @param direccionFiscal  dirección fiscal completa
     * @param condicionesPago  condiciones de pago acordadas (contado, 30, 60, 90 días)
     * @param descuentoHabitual porcentaje de descuento habitual aplicado
     * @param estado           estado del cliente (activo / inactivo)
     */
    public ClienteFormal(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro,
                         int idFormal, String codigoCliente, String nifCif, String razonSocial,
                         String direccionFiscal, String condicionesPago, double descuentoHabitual, String estado) {

        super(idPersona, nombre, email, telefono, fechaRegistro, estado);

        this.idFormal = idFormal;
        this.codigoCliente = codigoCliente;
        this.nifCif = nifCif;
        this.razonSocial = razonSocial;
        this.direccionFiscal = direccionFiscal;
        this.condicionesPago = condicionesPago;
        this.descuentoHabitual = descuentoHabitual;
    }

    /** Constructor vacío requerido para instanciación vía DAO. */
    public ClienteFormal() {
        super();
    }

    /** @return identificador único en la tabla ClienteFormal */
    public int getIdFormal() { return idFormal; }
    /** @param idFormal nuevo identificador */
    public void setIdFormal(int idFormal) { this.idFormal = idFormal; }

    /** @return código alfanumérico de cliente */
    public String getCodigoCliente() { return codigoCliente; }
    /** @param codigoCliente nuevo código de cliente */
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    /** @return NIF o CIF fiscal */
    public String getNifCif() { return nifCif; }
    /** @param nifCif nuevo NIF/CIF */
    public void setNifCif(String nifCif) { this.nifCif = nifCif; }

    /** @return razón social de la empresa */
    public String getRazonSocial() { return razonSocial; }
    /** @param razonSocial nueva razón social */
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    /** @return dirección fiscal completa */
    public String getDireccionFiscal() { return direccionFiscal; }
    /** @param direccionFiscal nueva dirección fiscal */
    public void setDireccionFiscal(String direccionFiscal) { this.direccionFiscal = direccionFiscal; }

    /** @return condiciones de pago acordadas */
    public String getCondicionesPago() { return condicionesPago; }
    /** @param condicionesPago nuevas condiciones de pago */
    public void setCondicionesPago(String condicionesPago) { this.condicionesPago = condicionesPago; }

    /** @return porcentaje de descuento habitual */
    public double getDescuentoHabitual() { return descuentoHabitual; }
    /** @param descuentoHabitual nuevo porcentaje de descuento */
    public void setDescuentoHabitual(double descuentoHabitual) { this.descuentoHabitual = descuentoHabitual; }
}

package crm.model;

import java.time.LocalDateTime;

/**
 * Representa a un cliente que ya ha formalizado al menos un pedido o contrato.
 * Hereda de {@link ClienteBase} (y por tanto de {@link Persona}), añadiendo
 * los datos fiscales y comerciales propios de un cliente formal.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
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
     * Constructor completo con todos los campos del cliente formal.
     *
     * @param idPersona        ID de la tabla Persona.
     * @param nombre           Nombre del contacto principal.
     * @param email            Correo electrónico único.
     * @param telefono         Teléfono de contacto.
     * @param fechaRegistro    Fecha de alta en el sistema.
     * @param idFormal         ID propio de la tabla ClienteFormal.
     * @param codigoCliente    Código único de cliente (ej: CLI-001).
     * @param nifCif           NIF o CIF de la empresa (9 caracteres, único).
     * @param razonSocial      Nombre legal de la empresa.
     * @param direccionFiscal  Dirección fiscal registrada.
     * @param condicionesPago  Condiciones de pago (contado, 30, 60, 90 días).
     * @param descuentoHabitual Descuento porcentual aplicado por defecto.
     * @param estado           Estado del cliente (activo / inactivo).
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

    /**
     * Constructor vacío para mapeo desde ResultSet.
     */
    public ClienteFormal() {
    }

    /**
     * Devuelve el identificador de la tabla ClienteFormal.
     *
     * @return ID formal.
     */
    public int getIdFormal() {
        return this.idFormal;
    }

    /**
     * Establece el identificador de la tabla ClienteFormal.
     *
     * @param idFormal Nuevo ID formal.
     */
    public void setIdFormal(int idFormal) {
        this.idFormal = idFormal;
    }

    /**
     * Devuelve el código único de cliente.
     *
     * @return Código de cliente.
     */
    public String getCodigoCliente() {
        return this.codigoCliente;
    }

    /**
     * Establece el código único de cliente.
     *
     * @param codigoCliente Nuevo código.
     */
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * Devuelve el NIF o CIF de la empresa.
     *
     * @return NIF/CIF (9 caracteres).
     */
    public String getNifCif() {
        return this.nifCif;
    }

    /**
     * Establece el NIF o CIF de la empresa.
     *
     * @param nifCif Nuevo NIF/CIF.
     */
    public void setNifCif(String nifCif) {
        this.nifCif = nifCif;
    }

    /**
     * Devuelve la razón social (nombre legal) de la empresa.
     *
     * @return Razón social.
     */
    public String getRazonSocial() {
        return this.razonSocial;
    }

    /**
     * Establece la razón social de la empresa.
     *
     * @param razonSocial Nueva razón social.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Devuelve la dirección fiscal registrada.
     *
     * @return Dirección fiscal.
     */
    public String getDireccionFiscal() {
        return this.direccionFiscal;
    }

    /**
     * Establece la dirección fiscal.
     *
     * @param direccionFiscal Nueva dirección fiscal.
     */
    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    /**
     * Devuelve las condiciones de pago del cliente.
     *
     * @return Condiciones de pago (contado, 30, 60, 90 días).
     */
    public String getCondicionesPago() {
        return this.condicionesPago;
    }

    /**
     * Establece las condiciones de pago.
     *
     * @param condicionesPago Nuevas condiciones de pago.
     */
    public void setCondicionesPago(String condicionesPago) {
        this.condicionesPago = condicionesPago;
    }

    /**
     * Devuelve el descuento habitual aplicado al cliente.
     *
     * @return Descuento en porcentaje (0.0 - 100.0).
     */
    public double getDescuentoHabitual() {
        return this.descuentoHabitual;
    }

    /**
     * Establece el descuento habitual del cliente.
     *
     * @param descuentoHabitual Nuevo porcentaje de descuento.
     */
    public void setDescuentoHabitual(double descuentoHabitual) {
        this.descuentoHabitual = descuentoHabitual;
    }
}

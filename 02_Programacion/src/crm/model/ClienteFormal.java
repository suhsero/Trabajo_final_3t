package crm.model;

import java.time.LocalDateTime;

public class ClienteFormal extends ClienteBase {
    private int idFormal;
    private String codigoCliente;
    private String nifCif;
    private String razonSocial;
    private String direccionFiscal;
    private String condicionesPago;
    private double descuentoHabitual;

    public ClienteFormal(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro,
                         int idFormal, String codigoCliente, String nifCif, String razonSocial,
                         String direccionFiscal, String condicionesPago, double descuentoHabitual, String estado) {

        // El estado se pasa al super() porque ahora pertenece a ClienteBase
        super(idPersona, nombre, email, telefono, fechaRegistro, estado);

        this.idFormal = idFormal;
        this.codigoCliente = codigoCliente;
        this.nifCif = nifCif;
        this.razonSocial = razonSocial;
        this.direccionFiscal = direccionFiscal;
        this.condicionesPago = condicionesPago;
        this.descuentoHabitual = descuentoHabitual;
    }

    public ClienteFormal() {
        super();
    }

    // todos los getters y setters pillaos rapido (estado ya no está porque se hereda)
    public int getIdFormal() { return idFormal; }
    public void setIdFormal(int idFormal) { this.idFormal = idFormal; }

    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    public String getNifCif() { return nifCif; }
    public void setNifCif(String nifCif) { this.nifCif = nifCif; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getDireccionFiscal() { return direccionFiscal; }
    public void setDireccionFiscal(String direccionFiscal) { this.direccionFiscal = direccionFiscal; }

    public String getCondicionesPago() { return condicionesPago; }
    public void setCondicionesPago(String condicionesPago) { this.condicionesPago = condicionesPago; }

    public double getDescuentoHabitual() { return descuentoHabitual; }
    public void setDescuentoHabitual(double descuentoHabitual) { this.descuentoHabitual = descuentoHabitual; }
}
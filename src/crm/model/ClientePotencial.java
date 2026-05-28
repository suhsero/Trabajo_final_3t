package crm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientePotencial extends Persona implements Serializable {
    private int idPotencial;
    private String empresa;
    private String fuenteCaptacion;
    private String estado;
    private LocalDate fechaPrimerContacto;
    // guardamos solo el ID del comercial que lo lleva
    private int idComercialAsignado;

    public ClientePotencial(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro,
                            int idPotencial, String empresa, String fuenteCaptacion, String estado,
                            LocalDate fechaPrimerContacto, int idComercialAsignado) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.idPotencial = idPotencial;
        this.empresa = empresa;
        this.fuenteCaptacion = fuenteCaptacion;
        this.estado = estado;
        this.fechaPrimerContacto = fechaPrimerContacto;
        this.idComercialAsignado = idComercialAsignado;
    }

    public ClientePotencial() {
        super();
    }

    public int getIdPotencial() { return idPotencial; }
    public void setIdPotencial(int idPotencial) { this.idPotencial = idPotencial; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getFuenteCaptacion() { return fuenteCaptacion; }
    public void setFuenteCaptacion(String fuenteCaptacion) { this.fuenteCaptacion = fuenteCaptacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaPrimerContacto() { return fechaPrimerContacto; }
    public void setFechaPrimerContacto(LocalDate fechaPrimerContacto) { this.fechaPrimerContacto = fechaPrimerContacto; }

    public int getIdComercialAsignado() { return idComercialAsignado; }
    public void setIdComercialAsignado(int idComercialAsignado) { this.idComercialAsignado = idComercialAsignado; }
}
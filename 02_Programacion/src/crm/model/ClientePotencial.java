package crm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientePotencial extends ClienteBase implements Serializable {
    private int idPotencial;
    private String empresa;
    private String fuenteCaptacion;
    private LocalDate fechaPrimerContacto;
    private int idComercialAsignado;

    public ClientePotencial(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro, int idPotencial, String empresa, String fuenteCaptacion, String estado, LocalDate fechaPrimerContacto, int idComercialAsignado) {
        super(idPersona, nombre, email, telefono, fechaRegistro, estado);
        this.idPotencial = idPotencial;
        this.empresa = empresa;
        this.fuenteCaptacion = fuenteCaptacion;
        this.fechaPrimerContacto = fechaPrimerContacto;
        this.idComercialAsignado = idComercialAsignado;
    }

    public ClientePotencial() {
    }

    public int getIdPotencial() {
        return this.idPotencial;
    }

    public void setIdPotencial(int idPotencial) {
        this.idPotencial = idPotencial;
    }

    public String getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFuenteCaptacion() {
        return this.fuenteCaptacion;
    }

    public void setFuenteCaptacion(String fuenteCaptacion) {
        this.fuenteCaptacion = fuenteCaptacion;
    }

    public LocalDate getFechaPrimerContacto() {
        return this.fechaPrimerContacto;
    }

    public void setFechaPrimerContacto(LocalDate fechaPrimerContacto) {
        this.fechaPrimerContacto = fechaPrimerContacto;
    }

    public int getIdComercialAsignado() {
        return this.idComercialAsignado;
    }

    public void setIdComercialAsignado(int idComercialAsignado) {
        this.idComercialAsignado = idComercialAsignado;
    }
}

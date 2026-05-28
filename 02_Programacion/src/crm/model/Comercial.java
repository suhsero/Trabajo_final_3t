package crm.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Comercial extends Persona {
    private int idComercial;
    private String codigoComercial;
    private String apellidos;
    private String zonaGeografica;
    private LocalDate fechaAlta;

    // usamos super para pasarle los datos al padre
    public Comercial(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro,
                     int idComercial, String codigoComercial, String apellidos, String zonaGeografica, LocalDate fechaAlta) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.idComercial = idComercial;
        this.codigoComercial = codigoComercial;
        this.apellidos = apellidos;
        this.zonaGeografica = zonaGeografica;
        this.fechaAlta = fechaAlta;
    }

    public Comercial() {
        super();
    }

    public int getIdComercial() { return idComercial; }
    public void setIdComercial(int idComercial) { this.idComercial = idComercial; }

    public String getCodigoComercial() { return codigoComercial; }
    public void setCodigoComercial(String codigoComercial) { this.codigoComercial = codigoComercial; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getZonaGeografica() { return zonaGeografica; }
    public void setZonaGeografica(String zonaGeografica) { this.zonaGeografica = zonaGeografica; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
}
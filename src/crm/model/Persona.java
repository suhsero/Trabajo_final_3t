package crm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Persona implements Serializable {
    private int idPersona;
    // lo pongo protected para que los hijos puedan usarlo directo si hace falta
    protected String nombre;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;

    // constructor con todo
    public Persona(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    // constructor vacio por si acaso
    public Persona() {
    }

    // getters y setters tipicos
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
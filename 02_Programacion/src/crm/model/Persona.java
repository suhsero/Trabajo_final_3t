package crm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad base que representa a cualquier persona del sistema (comerciales, clientes).
 * Implementa {@link Serializable} para permitir su serialización.
 *
 * @author Javier
 * @version 1.0
 */
public class Persona implements Serializable {
    private int idPersona;
    // lo pongo protected para que los hijos puedan usarlo directo si hace falta
    protected String nombre;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;

    /**
     * Constructor completo.
     *
     * @param idPersona     identificador único en BD
     * @param nombre        nombre completo de la persona
     * @param email         dirección de correo electrónico
     * @param telefono      número de teléfono de contacto
     * @param fechaRegistro fecha y hora de alta en el sistema
     */
    public Persona(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    /** Constructor vacío requerido para instanciación vía DAO. */
    public Persona() {
    }

    /** @return identificador único de la persona en BD */
    public int getIdPersona() { return idPersona; }
    /** @param idPersona nuevo identificador */
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    /** @return nombre completo de la persona */
    public String getNombre() { return nombre; }
    /** @param nombre nuevo nombre */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return dirección de correo electrónico */
    public String getEmail() { return email; }
    /** @param email nueva dirección de correo */
    public void setEmail(String email) { this.email = email; }

    /** @return número de teléfono */
    public String getTelefono() { return telefono; }
    /** @param telefono nuevo número de teléfono */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** @return fecha y hora de registro en el sistema */
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    /** @param fechaRegistro nueva fecha de registro */
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}

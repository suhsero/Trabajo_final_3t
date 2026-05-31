package crm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clase base que representa a cualquier persona del sistema CRM.
 * Es la raíz de la jerarquía de herencia: ClientePotencial y Comercial
 * heredan directamente de esta clase.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class Persona implements Serializable {
    private int idPersona;
    /** Nombre visible en la interfaz; declarado protected para que las subclases puedan acceder directamente. */
    protected String nombre;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;

    /**
     * Constructor completo con todos los campos.
     *
     * @param idPersona      Identificador único de la persona en la BD.
     * @param nombre         Nombre completo.
     * @param email          Dirección de correo electrónico.
     * @param telefono       Número de teléfono de contacto.
     * @param fechaRegistro  Fecha y hora en que se registró la persona.
     */
    public Persona(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor vacío necesario para instanciar objetos antes de rellenar sus campos
     * (usado habitualmente al mapear filas de ResultSet).
     */
    public Persona() {
    }

    /**
     * Devuelve el identificador único de la persona.
     *
     * @return ID de la persona en la base de datos.
     */
    public int getIdPersona() {
        return this.idPersona;
    }

    /**
     * Establece el identificador único de la persona.
     *
     * @param idPersona Nuevo valor del ID.
     */
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Devuelve el nombre de la persona.
     *
     * @return Nombre completo.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la dirección de correo electrónico.
     *
     * @return Email de la persona.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Establece la dirección de correo electrónico.
     *
     * @param email Nuevo email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el número de teléfono.
     *
     * @return Teléfono de contacto.
     */
    public String getTelefono() {
        return this.telefono;
    }

    /**
     * Establece el número de teléfono.
     *
     * @param telefono Nuevo teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve la fecha y hora de registro en el sistema.
     *
     * @return Fecha y hora de registro.
     */
    public LocalDateTime getFechaRegistro() {
        return this.fechaRegistro;
    }

    /**
     * Establece la fecha y hora de registro.
     *
     * @param fechaRegistro Nueva fecha de registro.
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}

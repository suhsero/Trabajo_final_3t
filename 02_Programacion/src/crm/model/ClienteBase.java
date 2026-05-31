package crm.model;

import java.time.LocalDateTime;

/**
 * Clase abstracta intermedia en la jerarquía de clientes.
 * Extiende {@link Persona} añadiendo el campo estado común a todos los clientes.
 * De esta clase hereda {@link ClienteFormal}.
 *
 * @author Javier Stampa García
 * @version 1.0
 */
public abstract class ClienteBase extends Persona {
    private String estado;

    /**
     * Constructor completo.
     *
     * @param idPersona      Identificador único de la persona.
     * @param nombre         Nombre del contacto principal.
     * @param email          Correo electrónico.
     * @param telefono       Teléfono de contacto.
     * @param fechaRegistro  Fecha de alta en el sistema.
     * @param estado         Estado del cliente (activo, inactivo, etc.).
     */
    public ClienteBase(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro, String estado) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.estado = estado;
    }

    /**
     * Constructor vacío para instanciación sin datos iniciales.
     */
    public ClienteBase() {
    }

    /**
     * Devuelve el estado actual del cliente.
     *
     * @return Estado del cliente.
     */
    public String getEstado() {
        return this.estado;
    }

    /**
     * Establece el estado del cliente.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

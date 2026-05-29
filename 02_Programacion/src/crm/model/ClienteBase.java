package crm.model;

import java.time.LocalDateTime;

/**
 * Clase abstracta que extiende {@link Persona} y añade el atributo {@code estado}
 * compartido por todos los tipos de cliente del CRM.
 *
 * @author Javier
 * @version 1.0
 */
public abstract class ClienteBase extends Persona {

    /** Estado del cliente: activo, inactivo, convertido, etc. */
    private String estado;

    /**
     * Constructor completo.
     *
     * @param idPersona     identificador único en BD
     * @param nombre        nombre completo
     * @param email         correo electrónico
     * @param telefono      teléfono de contacto
     * @param fechaRegistro fecha y hora de alta
     * @param estado        estado actual del cliente
     */
    public ClienteBase(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro, String estado) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.estado = estado;
    }

    /** Constructor vacío requerido para instanciación vía DAO. */
    public ClienteBase() {
        super();
    }

    /** @return estado actual del cliente */
    public String getEstado() { return estado; }
    /** @param estado nuevo estado del cliente */
    public void setEstado(String estado) { this.estado = estado; }
}

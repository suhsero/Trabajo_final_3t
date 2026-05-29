package crm.model;

import java.time.LocalDateTime;

<<<<<<< HEAD
public abstract class ClienteBase extends Persona {

    // Atributo común extraído de ClienteFormal y ClientePotencial
    private String estado;

=======
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
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
    public ClienteBase(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro, String estado) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.estado = estado;
    }

<<<<<<< HEAD
=======
    /** Constructor vacío requerido para instanciación vía DAO. */
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
    public ClienteBase() {
        super();
    }

<<<<<<< HEAD
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
=======
    /** @return estado actual del cliente */
    public String getEstado() { return estado; }
    /** @param estado nuevo estado del cliente */
    public void setEstado(String estado) { this.estado = estado; }
}
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

package crm.exception;

<<<<<<< HEAD
public class EmailInvalidoException extends Exception {
    public EmailInvalidoException(String mensaje) {
        super(mensaje);
    }
}
=======
/**
 * Excepción lanzada cuando la dirección de correo electrónico no tiene un formato válido.
 *
 * @author Javier
 * @version 1.0
 */
public class EmailInvalidoException extends Exception {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param mensaje descripción del error de validación
     */
    public EmailInvalidoException(String mensaje) {
        super(mensaje);
    }
}
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

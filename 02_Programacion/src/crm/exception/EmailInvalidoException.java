package crm.exception;

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

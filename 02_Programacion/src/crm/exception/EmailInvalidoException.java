package crm.exception;

/**
 * Excepción personalizada que se lanza cuando el formato de un correo
 * electrónico no es válido (no contiene '@' o no tiene dominio).
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class EmailInvalidoException extends Exception {

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param mensaje Descripción del motivo por el que el email es inválido.
     */
    public EmailInvalidoException(String mensaje) {
        super(mensaje);
    }
}

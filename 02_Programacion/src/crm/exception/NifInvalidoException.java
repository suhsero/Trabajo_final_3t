package crm.exception;

/**
 * Excepción personalizada que se lanza cuando el NIF o CIF introducido
 * no cumple el formato requerido (exactamente 9 caracteres).
 *
 * @author Javier Stampa García
 * @version 1.0
 */
public class NifInvalidoException extends Exception {

    /**
     * Construye la excepción con un mensaje descriptivo del error.
     *
     * @param mensaje Descripción del motivo por el que el NIF/CIF es inválido.
     */
    public NifInvalidoException(String mensaje) {
        super(mensaje);
    }
}

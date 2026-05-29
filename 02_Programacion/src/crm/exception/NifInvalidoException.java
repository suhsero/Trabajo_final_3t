package crm.exception;

<<<<<<< HEAD
public class NifInvalidoException extends Exception {
    public NifInvalidoException(String mensaje) {
        super(mensaje);
    }
}
=======
/**
 * Excepción lanzada cuando el NIF/CIF proporcionado no supera la validación de formato.
 *
 * @author Javier
 * @version 1.0
 */
public class NifInvalidoException extends Exception {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param mensaje descripción del error de validación
     */
    public NifInvalidoException(String mensaje) {
        super(mensaje);
    }
}
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

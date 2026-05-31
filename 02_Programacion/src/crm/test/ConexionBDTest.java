package crm.test;

import crm.database.ConexionBD;
import java.sql.Connection;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase de pruebas unitarias para verificar la conexión a la base de datos.
 * Utiliza JUnit 4 para comprobar que {@link crm.database.ConexionBD} devuelve
 * una conexión JDBC válida y no nula.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class ConexionBDTest {

    /**
     * Verifica que la conexión a la base de datos se establece correctamente.
     * El test falla si se lanza una excepción o si la conexión devuelta es nula.
     */
    @Test
    public void testConexionExitosa() {
        try {
            Connection con = ConexionBD.getConexion();
            Assert.assertNotNull("La conexión a la base de datos no debería ser nula", con);
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            Assert.fail("Ha fallado la conexión a la base de datos: " + e.getMessage());
        }

    }
}

package crm.test;

import crm.database.ConexionBD;
import org.junit.Test;
import java.sql.Connection;
import static org.junit.Assert.*;

public class ConexionBDTest {

    @Test
    public void testConexionExitosa() {
        try {
            Connection con = ConexionBD.getConexion();
            assertNotNull("La conexión a la base de datos no debería ser nula", con);

            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            fail("Ha fallado la conexión a la base de datos: " + e.getMessage());
        }
    }
}
package crm.test;

import crm.database.ConexionBD;
import java.sql.Connection;
import org.junit.Assert;
import org.junit.Test;

public class ConexionBDTest {
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

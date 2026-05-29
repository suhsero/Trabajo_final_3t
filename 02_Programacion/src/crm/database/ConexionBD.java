
package crm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://172.20.10.10:3306/crm_xtart?useSSL=false&serverTimezone=UTC";
    private static final String USER = "jungleuser";
    private static final String PASS = "JungleP@ss_2026";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://172.20.10.10:3306/crm_xtart?useSSL=false&serverTimezone=UTC", "jungleuser", "JungleP@ss_2026");
        } catch (Exception var1) {
            throw new SQLException("Falla el driver");
        }
    }
}

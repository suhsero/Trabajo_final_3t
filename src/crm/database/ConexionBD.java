package crm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // variables finales como pide el profe en la rubrica
    private static final String URL = "jdbc:mysql://172.20.10.4:3306/crm_xtart?useSSL=false&serverTimezone=UTC";
    private static final String USER = "jungleuser";
    private static final String PASS = "JungleP@ss_2026";

    // engancharse a mysql
    public static Connection getConexion() throws SQLException {
        try {
            // esto carga el driver para que no de fallo
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            // si peta al conectar salta esto
            throw new SQLException("Falla el driver");
        }
    }
}
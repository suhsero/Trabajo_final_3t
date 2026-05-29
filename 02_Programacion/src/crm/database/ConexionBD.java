package crm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para obtener conexiones a la base de datos MariaDB.
 * Los parámetros de conexión están definidos como constantes finales.
 *
 * @author Javier
 * @version 1.0
 */
public class ConexionBD {

    // variables finales como pide el profe en la rubrica
    private static final String URL = "jdbc:mysql://172.20.10.10:3306/crm_xtart?useSSL=false&serverTimezone=UTC";
    private static final String USER = "jungleuser";
    private static final String PASS = "JungleP@ss_2026";

    /**
     * Obtiene una conexión activa a la base de datos.
     * Carga el driver JDBC y devuelve la conexión lista para usar.
     *
     * @return objeto {@link Connection} listo para ejecutar sentencias
     * @throws SQLException si el driver no se encuentra o la conexión falla
     */
    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new SQLException("Error al obtener conexión con la BD: " + e.getMessage());
        }
    }
}
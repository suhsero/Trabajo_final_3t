package crm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión JDBC a la base de datos MySQL.
 * La conexión se establece contra la máquina virtual Windows Server con XAMPP
 * en la IP estática 192.168.56.10, puerto 3306, base de datos crm_xtart.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://192.168.56.10:3306/crm_xtart?useSSL=false&serverTimezone=UTC";
    private static final String USER = "jungleuser";
    private static final String PASS = "JungleP@ss_2026";

    /**
     * Crea y devuelve una nueva conexión a la base de datos MySQL.
     * Carga el driver JDBC de MySQL y establece la conexión con los parámetros
     * definidos en las constantes de la clase. El cierre de la conexión es
     * responsabilidad del código llamante (se recomienda try-with-resources).
     *
     * @return Objeto {@link Connection} listo para ejecutar consultas.
     * @throws SQLException Si el driver no se encuentra o la conexión falla.
     */
    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception var1) {
            throw new SQLException("Falla el driver");
        }
    }
}

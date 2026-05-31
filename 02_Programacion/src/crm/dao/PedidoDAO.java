package crm.dao;

import crm.database.ConexionBD;
import crm.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * DAO (Data Access Object) para la entidad Pedido.
 * Encapsula todas las operaciones CRUD contra la tabla Pedido
 * de la base de datos MySQL, usando JDBC con PreparedStatement.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class PedidoDAO {

    /**
     * Valida que el estado de un pedido sea uno de los valores permitidos.
     * Los estados válidos son: pendiente, en curso, servido, anulado.
     *
     * @param estado Cadena de texto con el estado a validar.
     * @return {@code true} si el estado es válido; {@code false} si es nulo o no reconocido.
     */
    public boolean validarEstadoPedido(String estado) {
        if (estado == null) {
            return false;
        } else {
            String e = estado.toLowerCase().trim();
            return e.equals("pendiente") || e.equals("en curso") || e.equals("servido") || e.equals("anulado");
        }
    }

    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param p Objeto {@link Pedido} con los datos a insertar.
     * @return {@code true} si la inserción fue correcta; {@code false} en caso de error.
     */
    public boolean insertar(Pedido p) {
        String sql = "INSERT INTO Pedido (id_cliente_formal, id_comercial, estado, fecha_pedido) VALUES (?, ?, ?, ?)";

        try {
            boolean var5;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, p.getIdClienteFormal());
                ps.setInt(2, p.getIdComercial());
                ps.setString(3, p.getEstado());
                ps.setTimestamp(4, Timestamp.valueOf(p.getFechaPedido()));
                ps.executeUpdate();
                var5 = true;
            }

            return var5;
        } catch (Exception e) {
            System.out.println("Error al insertar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todos los pedidos de la base de datos.
     *
     * @return Lista con todos los pedidos; lista vacía si no hay registros.
     */
    public ArrayList<Pedido> listarTodos() {
        ArrayList<Pedido> lista = new ArrayList();
        String sql = "SELECT id_pedido, id_cliente_formal, id_comercial, estado, fecha_pedido FROM Pedido";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()) {
                Pedido p = this.extraerPedido(rs);
                lista.add(p);
            }
        } catch (Exception var14) {
            System.out.println("Error al listar pedidos.");
        }

        return lista;
    }

    /**
     * Elimina un pedido por su ID.
     *
     * @param idPedido ID del pedido a eliminar.
     * @return {@code true} si se eliminó el pedido; {@code false} si no se encontró.
     */
    public boolean eliminar(int idPedido) {
        String sql = "DELETE FROM Pedido WHERE id_pedido = ?";

        try {
            boolean var6;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, idPedido);
                int filas = ps.executeUpdate();
                var6 = filas > 0;
            }

            return var6;
        } catch (Exception e) {
            System.out.println("Error al intentar eliminar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un pedido por su ID.
     *
     * @param idPedido ID del pedido a buscar.
     * @return El {@link Pedido} encontrado, o {@code null} si no existe.
     */
    public Pedido buscarPedido(int idPedido) {
        String sql = "SELECT id_pedido, id_cliente_formal, id_comercial, estado, fecha_pedido FROM Pedido WHERE id_pedido = ?";

        try {
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, idPedido);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Pedido var6 = this.extraerPedido(rs);
                        return var6;
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception var14) {
            System.out.println("Error en la búsqueda del pedido.");
            return null;
        }
    }

    /**
     * Actualiza el estado y las referencias de un pedido existente.
     *
     * @param p Objeto {@link Pedido} con los nuevos datos (debe tener idPedido válido).
     * @return {@code true} si la actualización fue correcta; {@code false} en caso de error.
     */
    public boolean actualizar(Pedido p) {
        String sql = "UPDATE Pedido SET id_cliente_formal = ?, id_comercial = ?, estado = ? WHERE id_pedido = ?";

        try {
            boolean var5;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, p.getIdClienteFormal());
                ps.setInt(2, p.getIdComercial());
                ps.setString(3, p.getEstado());
                ps.setInt(4, p.getIdPedido());
                ps.executeUpdate();
                var5 = true;
            }

            return var5;
        } catch (Exception e) {
            System.out.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método privado auxiliar que construye un objeto Pedido a partir
     * de la fila actual de un ResultSet.
     *
     * @param rs ResultSet posicionado en la fila a leer.
     * @return Objeto {@link Pedido} con los datos de la fila.
     * @throws SQLException Si ocurre un error al leer las columnas del ResultSet.
     */
    private Pedido extraerPedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setIdPedido(rs.getInt("id_pedido"));
        p.setIdClienteFormal(rs.getInt("id_cliente_formal"));
        p.setIdComercial(rs.getInt("id_comercial"));
        p.setEstado(rs.getString("estado"));
        Timestamp fecha = rs.getTimestamp("fecha_pedido");
        if (fecha != null) {
            p.setFechaPedido(fecha.toLocalDateTime());
        }

        return p;
    }
}

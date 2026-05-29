package crm.dao;

import crm.database.ConexionBD;
import crm.model.Pedido;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para la entidad Pedido.
 * Proporciona operaciones CRUD completas contra la tabla Pedido.
 *
 * @author Javier
 * @version 1.0
 */
public class PedidoDAO {

    /** Array con los estados válidos para un pedido, usado para validación. */
    public static final String[] ESTADOS_PEDIDO = {"pendiente", "en curso", "servido", "anulado"};

    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param p objeto Pedido con los datos a insertar
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso de error
     */
    public boolean insertar(Pedido p) {
        String sql = "INSERT INTO Pedido (fecha_pedido, id_cliente_formal, id_comercial, estado) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(p.getFechaPedido()));
            ps.setInt(2, p.getIdClienteFormal());
            ps.setInt(3, p.getIdComercial());
            ps.setString(4, p.getEstado());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todos los pedidos de la base de datos.
     *
     * @return lista con todos los pedidos; vacía si no hay ninguno o hay error
     */
    public ArrayList<Pedido> listarTodos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setFechaPedido(rs.getTimestamp("fecha_pedido").toLocalDateTime());
                p.setIdClienteFormal(rs.getInt("id_cliente_formal"));
                p.setIdComercial(rs.getInt("id_comercial"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar los pedidos.");
        }
        return lista;
    }

    /**
     * Actualiza el estado y los datos de un pedido existente.
     *
     * @param p objeto Pedido con los datos nuevos; debe tener {@code idPedido} válido
     * @return {@code true} si la actualización afectó al menos una fila, {@code false} en caso contrario
     */
    public boolean actualizar(Pedido p) {
        String sql = "UPDATE Pedido SET fecha_pedido = ?, id_cliente_formal = ?, id_comercial = ?, estado = ? WHERE id_pedido = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(p.getFechaPedido()));
            ps.setInt(2, p.getIdClienteFormal());
            ps.setInt(3, p.getIdComercial());
            ps.setString(4, p.getEstado());
            ps.setInt(5, p.getIdPedido());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un pedido de la base de datos por su ID.
     *
     * @param idPedido identificador del pedido a eliminar
     * @return {@code true} si se eliminó al menos una fila, {@code false} en caso contrario
     */
    public boolean eliminar(int idPedido) {
        String sql = "DELETE FROM Pedido WHERE id_pedido = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error al intentar eliminar el pedido.");
            return false;
        }
    }

    /**
     * Comprueba si el estado proporcionado es uno de los valores válidos del array {@code ESTADOS_PEDIDO}.
     *
     * @param estado cadena a validar
     * @return {@code true} si el estado es válido, {@code false} en caso contrario
     */
    public boolean validarEstadoPedido(String estado) {
        for (int i = 0; i < ESTADOS_PEDIDO.length; i++) {
            if (ESTADOS_PEDIDO[i].equalsIgnoreCase(estado)) {
                return true;
            }
        }
        return false;
    }
}

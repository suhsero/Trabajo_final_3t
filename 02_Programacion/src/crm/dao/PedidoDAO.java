package crm.dao;

import crm.database.ConexionBD;
import crm.model.Pedido;
import java.sql.*;
import java.util.ArrayList;

<<<<<<< HEAD
public class PedidoDAO {

    public boolean validarEstadoPedido(String estado) {
        if (estado == null) return false;
        String e = estado.toLowerCase().trim();
        return e.equals("pendiente") || e.equals("en curso") || e.equals("servido") || e.equals("anulado");
    }

    public boolean insertar(Pedido p) {
        String sql = "INSERT INTO Pedido (id_cliente_formal, id_comercial, estado, fecha_pedido) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdClienteFormal());
            ps.setInt(2, p.getIdComercial());
            ps.setString(3, p.getEstado());
            ps.setTimestamp(4, Timestamp.valueOf(p.getFechaPedido()));

            ps.executeUpdate();
            return true;
=======
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

>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        } catch (Exception e) {
            System.out.println("Error al insertar el pedido: " + e.getMessage());
            return false;
        }
    }

<<<<<<< HEAD
    public ArrayList<Pedido> listarTodos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id_pedido, id_cliente_formal, id_comercial, estado, fecha_pedido FROM Pedido";
=======
    /**
     * Recupera todos los pedidos de la base de datos.
     *
     * @return lista con todos los pedidos; vacía si no hay ninguno o hay error
     */
    public ArrayList<Pedido> listarTodos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
<<<<<<< HEAD
                Pedido p = extraerPedido(rs);
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error al listar pedidos.");
=======
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
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        }
        return lista;
    }

<<<<<<< HEAD
    // --- NUEVOS MÉTODOS: BORRAR, BUSCAR Y ACTUALIZAR ---

    public boolean eliminar(int idPedido) {
        String sql = "DELETE FROM Pedido WHERE id_pedido = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            System.out.println("Error al intentar eliminar el pedido: " + e.getMessage());
            return false;
        }
    }

    public Pedido buscarPedido(int idPedido) {
        String sql = "SELECT id_pedido, id_cliente_formal, id_comercial, estado, fecha_pedido FROM Pedido WHERE id_pedido = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraerPedido(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la búsqueda del pedido.");
        }
        return null;
    }

    public boolean actualizar(Pedido p) {
        String sql = "UPDATE Pedido SET id_cliente_formal = ?, id_comercial = ?, estado = ? WHERE id_pedido = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdClienteFormal());
            ps.setInt(2, p.getIdComercial());
            ps.setString(3, p.getEstado());
            ps.setInt(4, p.getIdPedido());

            ps.executeUpdate();
            return true;
=======
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

>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        } catch (Exception e) {
            System.out.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

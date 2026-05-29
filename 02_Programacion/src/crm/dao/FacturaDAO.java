package crm.dao;

import crm.database.ConexionBD;
import crm.model.Factura;
import java.sql.*;
import java.util.ArrayList;

<<<<<<< HEAD
public class FacturaDAO {

    public boolean insertar(Factura f) {
        String sql = "INSERT INTO Factura (numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
=======
/**
 * DAO para la entidad Factura.
 * Proporciona operaciones CRUD completas contra la tabla Factura.
 *
 * @author Javier
 * @version 1.0
 */
public class FacturaDAO {

    /**
     * Inserta una nueva factura en la base de datos.
     *
     * @param f objeto Factura con los datos a insertar
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso de error
     */
    public boolean insertar(Factura f) {
        String sql = "INSERT INTO Factura (numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNumeroFactura());
            ps.setDate(2, Date.valueOf(f.getFechaEmision()));
            ps.setDate(3, Date.valueOf(f.getFechaVencimiento()));
            ps.setInt(4, f.getIdClienteFormal());
            ps.setInt(5, f.getIdPedido());
            ps.setDouble(6, f.getBaseImponible());
            ps.setDouble(7, f.getTipoIva());
            ps.setDouble(8, f.getTotal());
            ps.setString(9, f.getEstado());

            ps.executeUpdate();
            return true;
<<<<<<< HEAD
=======

>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        } catch (Exception e) {
            System.out.println("Error al insertar la factura: " + e.getMessage());
            return false;
        }
    }

<<<<<<< HEAD
    public ArrayList<Factura> listarTodos() {
        ArrayList<Factura> lista = new ArrayList<>();
        String sql = "SELECT numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado FROM Factura";
=======
    /**
     * Recupera todas las facturas de la base de datos.
     *
     * @return lista con todas las facturas; vacía si no hay ninguna o hay error
     */
    public ArrayList<Factura> listarTodos() {
        ArrayList<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM Factura";
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
<<<<<<< HEAD
                Factura f = extraerFactura(rs);
                lista.add(f);
            }
        } catch (Exception e) {
            System.out.println("Error al listar facturas.");
=======
                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setNumeroFactura(rs.getString("numero_factura"));
                f.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
                f.setFechaVencimiento(rs.getDate("fecha_vencimiento").toLocalDate());
                f.setIdClienteFormal(rs.getInt("id_cliente_formal"));
                f.setIdPedido(rs.getInt("id_pedido"));
                f.setBaseImponible(rs.getDouble("base_imponible"));
                f.setTipoIva(rs.getDouble("tipo_iva"));
                f.setTotal(rs.getDouble("total"));
                f.setEstado(rs.getString("estado"));

                lista.add(f);
            }

        } catch (Exception e) {
            System.out.println("Error al listar las facturas.");
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        }
        return lista;
    }

<<<<<<< HEAD
    // --- NUEVOS MÉTODOS: BORRAR, BUSCAR Y ACTUALIZAR ---

    public boolean eliminar(String numeroFactura) {
        String sql = "DELETE FROM Factura WHERE numero_factura = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroFactura);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            System.out.println("Error al intentar eliminar la factura: " + e.getMessage());
            return false;
        }
    }

    public Factura buscarFactura(String numeroFactura) {
        String sql = "SELECT numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado FROM Factura WHERE numero_factura = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroFactura);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraerFactura(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la búsqueda de la factura.");
        }
        return null;
    }

    public boolean actualizar(Factura f) {
        String sql = "UPDATE Factura SET id_cliente_formal = ?, id_pedido = ?, base_imponible = ?, tipo_iva = ?, total = ?, estado = ? WHERE numero_factura = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, f.getIdClienteFormal());
            ps.setInt(2, f.getIdPedido());
            ps.setDouble(3, f.getBaseImponible());
            ps.setDouble(4, f.getTipoIva());
            ps.setDouble(5, f.getTotal());
            ps.setString(6, f.getEstado());
            ps.setString(7, f.getNumeroFactura()); // Clave primaria para el WHERE

            ps.executeUpdate();
            return true;
=======
    /**
     * Actualiza los datos de una factura existente.
     *
     * @param f objeto Factura con los datos nuevos; debe tener {@code idFactura} válido
     * @return {@code true} si la actualización afectó al menos una fila, {@code false} en caso contrario
     */
    public boolean actualizar(Factura f) {
        String sql = "UPDATE Factura SET numero_factura = ?, fecha_emision = ?, fecha_vencimiento = ?, " +
                     "id_cliente_formal = ?, id_pedido = ?, base_imponible = ?, tipo_iva = ?, total = ?, " +
                     "estado = ? WHERE id_factura = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNumeroFactura());
            ps.setDate(2, Date.valueOf(f.getFechaEmision()));
            ps.setDate(3, Date.valueOf(f.getFechaVencimiento()));
            ps.setInt(4, f.getIdClienteFormal());
            ps.setInt(5, f.getIdPedido());
            ps.setDouble(6, f.getBaseImponible());
            ps.setDouble(7, f.getTipoIva());
            ps.setDouble(8, f.getTotal());
            ps.setString(9, f.getEstado());
            ps.setInt(10, f.getIdFactura());

            int filas = ps.executeUpdate();
            return filas > 0;

>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        } catch (Exception e) {
            System.out.println("Error al actualizar la factura: " + e.getMessage());
            return false;
        }
    }

<<<<<<< HEAD
    private Factura extraerFactura(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setNumeroFactura(rs.getString("numero_factura"));

        Date emision = rs.getDate("fecha_emision");
        if (emision != null) f.setFechaEmision(emision.toLocalDate());

        Date vencimiento = rs.getDate("fecha_vencimiento");
        if (vencimiento != null) f.setFechaVencimiento(vencimiento.toLocalDate());

        f.setIdClienteFormal(rs.getInt("id_cliente_formal"));
        f.setIdPedido(rs.getInt("id_pedido"));
        f.setBaseImponible(rs.getDouble("base_imponible"));
        f.setTipoIva(rs.getDouble("tipo_iva"));
        f.setTotal(rs.getDouble("total"));
        f.setEstado(rs.getString("estado"));
        return f;
    }
}
=======
    /**
     * Elimina una factura de la base de datos por su ID.
     *
     * @param idFactura identificador de la factura a eliminar
     * @return {@code true} si se eliminó al menos una fila, {@code false} en caso contrario
     */
    public boolean eliminar(int idFactura) {
        String sql = "DELETE FROM Factura WHERE id_factura = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error al intentar eliminar la factura.");
            return false;
        }
    }
}
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

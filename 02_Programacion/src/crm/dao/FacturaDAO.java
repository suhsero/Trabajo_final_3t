package crm.dao;

import crm.database.ConexionBD;
import crm.model.Factura;
import java.sql.*;
import java.util.ArrayList;

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

        } catch (Exception e) {
            System.out.println("Error al insertar la factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todas las facturas de la base de datos.
     *
     * @return lista con todas las facturas; vacía si no hay ninguna o hay error
     */
    public ArrayList<Factura> listarTodos() {
        ArrayList<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM Factura";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
        }
        return lista;
    }

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

        } catch (Exception e) {
            System.out.println("Error al actualizar la factura: " + e.getMessage());
            return false;
        }
    }

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

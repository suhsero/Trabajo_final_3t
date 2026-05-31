package crm.dao;

import crm.database.ConexionBD;
import crm.model.Factura;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO (Data Access Object) para la entidad Factura.
 * Encapsula todas las operaciones CRUD contra la tabla Factura
 * de la base de datos MySQL, usando JDBC con PreparedStatement.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class FacturaDAO {

    /**
     * Inserta una nueva factura en la base de datos.
     *
     * @param f Objeto {@link Factura} con los datos a insertar.
     * @return {@code true} si la inserción fue correcta; {@code false} en caso de error.
     */
    public boolean insertar(Factura f) {
        String sql = "INSERT INTO Factura (numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            boolean var5;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
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
                var5 = true;
            }

            return var5;
        } catch (Exception e) {
            System.out.println("Error al insertar la factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todas las facturas de la base de datos.
     *
     * @return Lista con todas las facturas; lista vacía si no hay registros.
     */
    public ArrayList<Factura> listarTodos() {
        ArrayList<Factura> lista = new ArrayList();
        String sql = "SELECT numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado FROM Factura";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()) {
                Factura f = this.extraerFactura(rs);
                lista.add(f);
            }
        } catch (Exception var14) {
            System.out.println("Error al listar facturas.");
        }

        return lista;
    }

    /**
     * Elimina una factura por su número de factura.
     *
     * @param numeroFactura Número de factura a eliminar (clave única).
     * @return {@code true} si se eliminó la factura; {@code false} si no se encontró.
     */
    public boolean eliminar(String numeroFactura) {
        String sql = "DELETE FROM Factura WHERE numero_factura = ?";

        try {
            boolean var6;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setString(1, numeroFactura);
                int filas = ps.executeUpdate();
                var6 = filas > 0;
            }

            return var6;
        } catch (Exception e) {
            System.out.println("Error al intentar eliminar la factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca una factura por su número de factura.
     *
     * @param numeroFactura Número de factura a buscar.
     * @return La {@link Factura} encontrada, o {@code null} si no existe.
     */
    public Factura buscarFactura(String numeroFactura) {
        String sql = "SELECT numero_factura, fecha_emision, fecha_vencimiento, id_cliente_formal, id_pedido, base_imponible, tipo_iva, total, estado FROM Factura WHERE numero_factura = ?";

        try {
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setString(1, numeroFactura);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Factura var6 = this.extraerFactura(rs);
                        return var6;
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception var14) {
            System.out.println("Error en la búsqueda de la factura.");
            return null;
        }
    }

    /**
     * Actualiza los datos de una factura existente en la base de datos.
     *
     * @param f Objeto {@link Factura} con los nuevos datos (debe tener numeroFactura válido).
     * @return {@code true} si la actualización fue correcta; {@code false} en caso de error.
     */
    public boolean actualizar(Factura f) {
        String sql = "UPDATE Factura SET id_cliente_formal = ?, id_pedido = ?, base_imponible = ?, tipo_iva = ?, total = ?, estado = ? WHERE numero_factura = ?";

        try {
            boolean var5;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, f.getIdClienteFormal());
                ps.setInt(2, f.getIdPedido());
                ps.setDouble(3, f.getBaseImponible());
                ps.setDouble(4, f.getTipoIva());
                ps.setDouble(5, f.getTotal());
                ps.setString(6, f.getEstado());
                ps.setString(7, f.getNumeroFactura());
                ps.executeUpdate();
                var5 = true;
            }

            return var5;
        } catch (Exception e) {
            System.out.println("Error al actualizar la factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método privado auxiliar que construye un objeto Factura a partir
     * de la fila actual de un ResultSet.
     *
     * @param rs ResultSet posicionado en la fila a leer.
     * @return Objeto {@link Factura} con los datos de la fila.
     * @throws SQLException Si ocurre un error al leer las columnas del ResultSet.
     */
    private Factura extraerFactura(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setNumeroFactura(rs.getString("numero_factura"));
        Date emision = rs.getDate("fecha_emision");
        if (emision != null) {
            f.setFechaEmision(emision.toLocalDate());
        }

        Date vencimiento = rs.getDate("fecha_vencimiento");
        if (vencimiento != null) {
            f.setFechaVencimiento(vencimiento.toLocalDate());
        }

        f.setIdClienteFormal(rs.getInt("id_cliente_formal"));
        f.setIdPedido(rs.getInt("id_pedido"));
        f.setBaseImponible(rs.getDouble("base_imponible"));
        f.setTipoIva(rs.getDouble("tipo_iva"));
        f.setTotal(rs.getDouble("total"));
        f.setEstado(rs.getString("estado"));
        return f;
    }
}

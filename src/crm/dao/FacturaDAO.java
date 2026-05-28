package crm.dao;

import crm.database.ConexionBD;
import crm.model.Factura;
import java.sql.*;
import java.util.ArrayList;

public class FacturaDAO {

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
}
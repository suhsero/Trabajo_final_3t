package crm.dao;

import crm.database.ConexionBD;
import crm.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PedidoDAO {
    public boolean validarEstadoPedido(String estado) {
        if (estado == null) {
            return false;
        } else {
            String e = estado.toLowerCase().trim();
            return e.equals("pendiente") || e.equals("en curso") || e.equals("servido") || e.equals("anulado");
        }
    }

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

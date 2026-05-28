package crm.dao;

import crm.database.ConexionBD;
import crm.model.Pedido;
import java.sql.*;
import java.util.ArrayList;

public class PedidoDAO {

    // ARRAY
    public static final String[] ESTADOS_PEDIDO = {"pendiente", "en curso", "servido", "anulado"};

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

    // Método extra para comprobar que el estado es válido usando el array
    public boolean validarEstadoPedido(String estado) {
        for (int i = 0; i < ESTADOS_PEDIDO.length; i++) {
            if (ESTADOS_PEDIDO[i].equalsIgnoreCase(estado)) {
                return true;
            }
        }
        return false;
    }
}
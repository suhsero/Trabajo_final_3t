package crm.dao;

import crm.database.ConexionBD;
import crm.model.ClienteFormal;
import java.sql.*;
import java.util.ArrayList;

public class ClienteFormalDAO {

    public boolean insertar(ClienteFormal c) {
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono) VALUES (?, ?, ?)";
        String sqlFormal = "INSERT INTO ClienteFormal (id_persona, codigo_cliente, nif_cif, razon_social, direccion_fiscal, condiciones_pago, descuento_habitual, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps1 = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {

            // Datos de Persona
            ps1.setString(1, c.getNombre());
            ps1.setString(2, c.getEmail());
            ps1.setString(3, c.getTelefono());
            ps1.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = ps1.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            // Datos de Cliente Formal
            try (PreparedStatement ps2 = con.prepareStatement(sqlFormal)) {
                ps2.setInt(1, idGenerado);
                ps2.setString(2, c.getCodigoCliente());
                ps2.setString(3, c.getNifCif());
                ps2.setString(4, c.getRazonSocial());
                ps2.setString(5, c.getDireccionFiscal());
                ps2.setString(6, c.getCondicionesPago());
                ps2.setDouble(7, c.getDescuentoHabitual());
                ps2.setString(8, c.getEstado());

                ps2.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar el cliente formal: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<ClienteFormal> listarTodos() {
        ArrayList<ClienteFormal> lista = new ArrayList<>();

        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado " +
                "FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClienteFormal c = new ClienteFormal();

                c.setIdPersona(rs.getInt("id_persona"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                c.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());

                c.setIdFormal(rs.getInt("id_formal"));
                c.setCodigoCliente(rs.getString("codigo_cliente"));
                c.setNifCif(rs.getString("nif_cif"));
                c.setRazonSocial(rs.getString("razon_social"));
                c.setDireccionFiscal(rs.getString("direccion_fiscal"));
                c.setCondicionesPago(rs.getString("condiciones_pago"));
                c.setDescuentoHabitual(rs.getDouble("descuento_habitual"));
                c.setEstado(rs.getString("estado"));

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al listar clientes formales.");
        }
        return lista;
    }

    public boolean eliminar(int idPersona) {
        String sql = "DELETE FROM Persona WHERE id_persona = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPersona);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error al intentar eliminar el registro.");
            return false;
        }
    }
}
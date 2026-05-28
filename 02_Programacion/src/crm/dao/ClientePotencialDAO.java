package crm.dao;

import crm.database.ConexionBD;
import crm.model.ClientePotencial;
import java.sql.*;
import java.util.ArrayList;

public class ClientePotencialDAO {

    public boolean insertar(ClientePotencial c) {
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono) VALUES (?, ?, ?)";
        String sqlPotencial = "INSERT INTO ClientePotencial (id_persona, empresa, fuente_captacion, estado, fecha_primer_contacto, id_comercial) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps1 = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {

            ps1.setString(1, c.getNombre());
            ps1.setString(2, c.getEmail());
            ps1.setString(3, c.getTelefono());
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlPotencial)) {
                ps2.setInt(1, idGenerado);
                ps2.setString(2, c.getEmpresa());
                ps2.setString(3, c.getFuenteCaptacion());
                ps2.setString(4, c.getEstado());
                ps2.setDate(5, Date.valueOf(c.getFechaPrimerContacto()));
                ps2.setInt(6, c.getIdComercialAsignado());

                ps2.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar el cliente potencial.");
            return false;
        }
    }

    public ArrayList<ClientePotencial> listarTodos() {
        ArrayList<ClientePotencial> lista = new ArrayList<>();

        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_potencial, c.empresa, c.fuente_captacion, c.estado, c.fecha_primer_contacto, c.id_comercial " +
                "FROM ClientePotencial c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClientePotencial c = new ClientePotencial();

                c.setIdPersona(rs.getInt("id_persona"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                c.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());

                c.setIdPotencial(rs.getInt("id_potencial"));
                c.setEmpresa(rs.getString("empresa"));
                c.setFuenteCaptacion(rs.getString("fuente_captacion"));
                c.setEstado(rs.getString("estado"));
                c.setFechaPrimerContacto(rs.getDate("fecha_primer_contacto").toLocalDate());
                c.setIdComercialAsignado(rs.getInt("id_comercial"));

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al listar clientes potenciales.");
        }
        return lista;
    }
}
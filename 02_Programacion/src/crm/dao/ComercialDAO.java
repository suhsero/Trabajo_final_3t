package crm.dao;

import crm.database.ConexionBD;
import crm.model.Comercial;
import java.sql.*;
import java.util.ArrayList;

public class ComercialDAO {

    public boolean insertar(Comercial c) {
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono) VALUES (?, ?, ?)";
        String sqlComercial = "INSERT INTO Comercial (id_persona, codigo_comercial, zona_geografica) VALUES (?, ?, ?)";

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

            try (PreparedStatement ps2 = con.prepareStatement(sqlComercial)) {
                ps2.setInt(1, idGenerado);
                ps2.setString(2, c.getCodigoComercial());
                ps2.setString(3, c.getZonaGeografica());
                ps2.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar el comercial: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Comercial> listarTodos() {
        ArrayList<Comercial> lista = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_comercial, c.codigo_comercial, c.zona_geografica " +
                "FROM Comercial c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Comercial c = extraerComercial(rs);
                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al listar comerciales.");
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

    // --- MÉTODOS NUEVOS PARA EL REQUISITO "UPDATE" ---

    public Comercial buscarComercial(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_comercial, c.codigo_comercial, c.zona_geografica " +
                "FROM Comercial c INNER JOIN Persona p ON c.id_persona = p.id_persona " +
                "WHERE p.id_persona = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraerComercial(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la búsqueda del comercial.");
        }
        return null;
    }

    public boolean actualizar(Comercial c) {
        String sqlPersona = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlComercial = "UPDATE Comercial SET codigo_comercial = ?, zona_geografica = ? WHERE id_persona = ?";

        try (Connection con = ConexionBD.getConexion()) {
            // Actualizamos la tabla padre (Persona)
            try (PreparedStatement ps1 = con.prepareStatement(sqlPersona)) {
                ps1.setString(1, c.getNombre());
                ps1.setString(2, c.getEmail());
                ps1.setString(3, c.getTelefono());
                ps1.setInt(4, c.getIdPersona());
                ps1.executeUpdate();
            }

            // Actualizamos la tabla hija (Comercial)
            try (PreparedStatement ps2 = con.prepareStatement(sqlComercial)) {
                ps2.setString(1, c.getCodigoComercial());
                ps2.setString(2, c.getZonaGeografica());
                ps2.setInt(3, c.getIdPersona());
                ps2.executeUpdate();
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar el comercial: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para evitar repetir código
    private Comercial extraerComercial(ResultSet rs) throws SQLException {
        Comercial c = new Comercial();
        c.setIdPersona(rs.getInt("id_persona"));
        c.setNombre(rs.getString("nombre"));
        c.setEmail(rs.getString("email"));
        c.setTelefono(rs.getString("telefono"));
        c.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        c.setIdComercial(rs.getInt("id_comercial"));
        c.setCodigoComercial(rs.getString("codigo_comercial"));
        c.setZonaGeografica(rs.getString("zona_geografica"));
        return c;
    }
}
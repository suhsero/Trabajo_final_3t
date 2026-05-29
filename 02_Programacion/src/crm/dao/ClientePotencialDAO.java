package crm.dao;

import crm.database.ConexionBD;
import crm.model.ClientePotencial;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClientePotencialDAO {
    public boolean insertar(ClientePotencial c) {
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";
        String sqlPotencial = "INSERT INTO ClientePotencial (id_persona, empresa, fuente_captacion, estado, fecha_primer_contacto, id_comercial) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            boolean var9;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps1 = con.prepareStatement(sqlPersona, 1);
            ) {
                ps1.setString(1, c.getNombre());
                ps1.setString(2, c.getEmail());
                ps1.setString(3, c.getTelefono());
                ps1.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
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
                    var9 = true;
                }
            }

            return var9;
        } catch (Exception e) {
            System.out.println("Error al insertar el cliente potencial: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<ClientePotencial> listarTodos() {
        ArrayList<ClientePotencial> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_potencial, c.empresa, c.fuente_captacion, c.estado, c.fecha_primer_contacto, c.id_comercial FROM ClientePotencial c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()) {
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
                Date fechaContacto = rs.getDate("fecha_primer_contacto");
                if (fechaContacto != null) {
                    c.setFechaPrimerContacto(fechaContacto.toLocalDate());
                }

                c.setIdComercialAsignado(rs.getInt("id_comercial"));
                lista.add(c);
            }
        } catch (Exception var14) {
            System.out.println("Error al listar potenciales.");
        }

        return lista;
    }

    public boolean eliminar(int idPersona) {
        String sql = "DELETE FROM Persona WHERE id_persona = ?";

        try {
            boolean var6;
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, idPersona);
                int filas = ps.executeUpdate();
                var6 = filas > 0;
            }

            return var6;
        } catch (Exception var11) {
            System.out.println("Error al intentar eliminar el registro.");
            return false;
        }
    }

    public ClientePotencial buscarPotencial(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_potencial, c.empresa, c.fuente_captacion, c.estado, c.fecha_primer_contacto, c.id_comercial FROM ClientePotencial c INNER JOIN Persona p ON c.id_persona = p.id_persona WHERE p.id_persona = ?";

        try {
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, idPersona);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
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
                        Date fechaContacto = rs.getDate("fecha_primer_contacto");
                        if (fechaContacto != null) {
                            c.setFechaPrimerContacto(fechaContacto.toLocalDate());
                        }

                        c.setIdComercialAsignado(rs.getInt("id_comercial"));
                        ClientePotencial var8 = c;
                        return var8;
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception var15) {
            System.out.println("Error en la búsqueda del cliente potencial.");
            return null;
        }
    }

    public boolean actualizar(ClientePotencial c) {
        String sqlPersona = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlPotencial = "UPDATE ClientePotencial SET empresa = ?, fuente_captacion = ?, estado = ?, id_comercial = ? WHERE id_persona = ?";

        try {
            boolean var16;
            try (Connection con = ConexionBD.getConexion()) {
                try (PreparedStatement ps1 = con.prepareStatement(sqlPersona)) {
                    ps1.setString(1, c.getNombre());
                    ps1.setString(2, c.getEmail());
                    ps1.setString(3, c.getTelefono());
                    ps1.setInt(4, c.getIdPersona());
                    ps1.executeUpdate();
                }

                try (PreparedStatement ps2 = con.prepareStatement(sqlPotencial)) {
                    ps2.setString(1, c.getEmpresa());
                    ps2.setString(2, c.getFuenteCaptacion());
                    ps2.setString(3, c.getEstado());
                    ps2.setInt(4, c.getIdComercialAsignado());
                    ps2.setInt(5, c.getIdPersona());
                    ps2.executeUpdate();
                }

                var16 = true;
            }

            return var16;
        } catch (Exception e) {
            System.out.println("Error al actualizar el cliente potencial: " + e.getMessage());
            return false;
        }
    }
}
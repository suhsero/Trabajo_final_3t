package crm.dao;

import crm.database.ConexionBD;
import crm.model.Comercial;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ComercialDAO {
    public boolean insertar(Comercial c) {
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";
        String sqlComercial = "INSERT INTO Comercial (id_persona, codigo_comercial, apellidos, zona_geografica, fecha_alta) VALUES (?, ?, ?, ?, ?)";

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

                try (PreparedStatement ps2 = con.prepareStatement(sqlComercial)) {
                    ps2.setInt(1, idGenerado);
                    ps2.setString(2, c.getCodigoComercial());
                    ps2.setString(3, c.getApellidos());
                    ps2.setString(4, c.getZonaGeografica());
                    ps2.setDate(5, Date.valueOf(c.getFechaAlta()));
                    ps2.executeUpdate();
                    var9 = true;
                }
            }

            return var9;
        } catch (Exception e) {
            System.out.println("Error al insertar el comercial: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Comercial> listarTodos() {
        ArrayList<Comercial> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_comercial, c.codigo_comercial, c.apellidos, c.zona_geografica, c.fecha_alta FROM Comercial c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()) {
                Comercial c = this.extraerComercial(rs);
                lista.add(c);
            }
        } catch (Exception var14) {
            System.out.println("Error al listar comerciales.");
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

    public Comercial buscarComercial(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_comercial, c.codigo_comercial, c.apellidos, c.zona_geografica, c.fecha_alta FROM Comercial c INNER JOIN Persona p ON c.id_persona = p.id_persona WHERE p.id_persona = ?";

        try {
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                ps.setInt(1, idPersona);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Comercial var6 = this.extraerComercial(rs);
                        return var6;
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception var14) {
            System.out.println("Error en la búsqueda del comercial.");
            return null;
        }
    }

    public boolean actualizar(Comercial c) {
        String sqlPersona = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlComercial = "UPDATE Comercial SET codigo_comercial = ?, apellidos = ?, zona_geografica = ? WHERE id_persona = ?";

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

                try (PreparedStatement ps2 = con.prepareStatement(sqlComercial)) {
                    ps2.setString(1, c.getCodigoComercial());
                    ps2.setString(2, c.getApellidos());
                    ps2.setString(3, c.getZonaGeografica());
                    ps2.setInt(4, c.getIdPersona());
                    ps2.executeUpdate();
                }

                var16 = true;
            }

            return var16;
        } catch (Exception e) {
            System.out.println("Error al actualizar el comercial: " + e.getMessage());
            return false;
        }
    }

    private Comercial extraerComercial(ResultSet rs) throws SQLException {
        Comercial c = new Comercial();
        c.setIdPersona(rs.getInt("id_persona"));
        c.setNombre(rs.getString("nombre"));
        c.setEmail(rs.getString("email"));
        c.setTelefono(rs.getString("telefono"));
        c.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        c.setIdComercial(rs.getInt("id_comercial"));
        c.setCodigoComercial(rs.getString("codigo_comercial"));
        c.setApellidos(rs.getString("apellidos"));
        c.setZonaGeografica(rs.getString("zona_geografica"));
        Date fechaAlta = rs.getDate("fecha_alta");
        if (fechaAlta != null) {
            c.setFechaAlta(fechaAlta.toLocalDate());
        }

        return c;
    }
}

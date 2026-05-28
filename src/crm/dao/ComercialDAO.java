package crm.dao;

import crm.database.ConexionBD;
import crm.model.Comercial;
import java.sql.*;
import java.util.ArrayList;

public class ComercialDAO {

    // meter comercial nuevo en la bd
    public boolean insertar(Comercial c) {
        String sql1 = "INSERT INTO Persona (nombre, email, telefono) VALUES (?, ?, ?)";
        String sql2 = "INSERT INTO Comercial (id_persona, codigo_comercial, apellidos, zona_geografica, fecha_alta) VALUES (?, ?, ?, ?, ?)";

        // uso try with resources y asi se cierra solo el connection
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps1 = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {

            // meto los datos de persona primero
            ps1.setString(1, c.getNombre());
            ps1.setString(2, c.getEmail());
            ps1.setString(3, c.getTelefono());
            ps1.executeUpdate();

            // pillo el id que le dio mysql a la persona que acabo de crear
            ResultSet rs = ps1.getGeneratedKeys();
            int elId = 0;
            if (rs.next()) {
                elId = rs.getInt(1);
            }

            // ahora meto el comercial usando el id de arriba
            try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
                ps2.setInt(1, elId);
                ps2.setString(2, c.getCodigoComercial());
                ps2.setString(3, c.getApellidos());
                ps2.setString(4, c.getZonaGeografica());
                // hay que pasar la fecha normal a fecha sql
                ps2.setDate(5, Date.valueOf(c.getFechaAlta()));

                ps2.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error metiendo al comercial: " + e.getMessage());
            return false;
        }
    }

    // sacar todos los comerciales para listarlos luego en la pantalla
    public ArrayList<Comercial> listarTodos() {
        ArrayList<Comercial> lista = new ArrayList<>();
        // el join para juntar las dos tablas porque los datos estan separados
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_comercial, c.codigo_comercial, c.apellidos, c.zona_geografica, c.fecha_alta " +
                "FROM Comercial c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // bucle para ir pillando cada comercial que devuelve la select
            while (rs.next()) {
                Comercial c = new Comercial();
                // seteamos lo de persona
                c.setIdPersona(rs.getInt("id_persona"));
                c.setNombre(rs.getString("nombre"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                c.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());

                // seteamos lo de comercial
                c.setIdComercial(rs.getInt("id_comercial"));
                c.setCodigoComercial(rs.getString("codigo_comercial"));
                c.setApellidos(rs.getString("apellidos"));
                c.setZonaGeografica(rs.getString("zona_geografica"));
                c.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());

                // lo meto en la lista
                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Falló el select");
        }
        return lista; // devuelvo la lista llena (o vacia si ha habido fallo)
    }

    // modificar los datos
    public boolean modificar(Comercial c) {
        String sql1 = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sql2 = "UPDATE Comercial SET codigo_comercial = ?, apellidos = ?, zona_geografica = ?, fecha_alta = ? WHERE id_comercial = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps1 = con.prepareStatement(sql1);
             PreparedStatement ps2 = con.prepareStatement(sql2)) {

            // update de la tabla persona
            ps1.setString(1, c.getNombre());
            ps1.setString(2, c.getEmail());
            ps1.setString(3, c.getTelefono());
            ps1.setInt(4, c.getIdPersona());
            ps1.executeUpdate();

            // update de la tabla comercial
            ps2.setString(1, c.getCodigoComercial());
            ps2.setString(2, c.getApellidos());
            ps2.setString(3, c.getZonaGeografica());
            ps2.setDate(4, Date.valueOf(c.getFechaAlta()));
            ps2.setInt(5, c.getIdComercial());
            ps2.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("No se pudo modificar");
            return false;
        }
    }

    // borrar
    public boolean eliminar(int idPersona) {
        // borramos a la persona. en mysql pusimos cascade asique se borra el comercial solo
        String sql = "DELETE FROM Persona WHERE id_persona = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPersona);
            int filas = ps.executeUpdate();

            if (filas > 0){
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Falló el borrar");
            return false;
        }
    }
}
package crm.dao;

import crm.database.ConexionBD;
import crm.model.Comercial;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para la entidad Comercial.
 * Proporciona operaciones CRUD completas contra las tablas Persona y Comercial.
 *
 * @author Javier
 * @version 1.0
 */
public class ComercialDAO {

    /**
     * Inserta un nuevo comercial en la base de datos.
     * Primero inserta en la tabla padre Persona y después en Comercial.
     *
     * @param c objeto Comercial con los datos a insertar
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso de error
     */
    public boolean insertar(Comercial c) {
<<<<<<< HEAD
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";
        // SOLUCIÓN: Añadido 'fecha_alta' al INSERT
        String sqlComercial = "INSERT INTO Comercial (id_persona, codigo_comercial, apellidos, zona_geografica, fecha_alta) VALUES (?, ?, ?, ?, ?)";
=======
        String sqlPersona   = "INSERT INTO Persona (nombre, email, telefono) VALUES (?, ?, ?)";
        String sqlComercial = "INSERT INTO Comercial (id_persona, codigo_comercial, zona_geografica) VALUES (?, ?, ?)";
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps1 = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {

            ps1.setString(1, c.getNombre());
            ps1.setString(2, c.getEmail());
            ps1.setString(3, c.getTelefono());
            ps1.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
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
                ps2.setDate(5, Date.valueOf(c.getFechaAlta())); // Pasamos la fecha de alta a la BD
                ps2.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al insertar el comercial: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todos los comerciales de la base de datos.
     *
     * @return lista con todos los comerciales; vacía si no hay ninguno o hay error
     */
    public ArrayList<Comercial> listarTodos() {
        ArrayList<Comercial> lista = new ArrayList<>();
        // SOLUCIÓN: Añadido 'c.fecha_alta' a la SELECT
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_comercial, c.codigo_comercial, c.apellidos, c.zona_geografica, c.fecha_alta " +
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

<<<<<<< HEAD
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

=======
    /**
     * Busca un comercial por su ID de persona.
     *
     * @param idPersona identificador único en la tabla Persona
     * @return objeto Comercial encontrado, o {@code null} si no existe
     */
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
    public Comercial buscarComercial(int idPersona) {
        // SOLUCIÓN: Añadido 'c.fecha_alta' a la SELECT
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_comercial, c.codigo_comercial, c.apellidos, c.zona_geografica, c.fecha_alta " +
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

    /**
     * Actualiza los datos de un comercial existente.
     * Actualiza tanto la tabla Persona (nombre, email, teléfono) como
     * la tabla Comercial (código, zona geográfica).
     *
     * @param c objeto Comercial con los datos nuevos; debe tener {@code idPersona} válido
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error
     */
    public boolean actualizar(Comercial c) {
<<<<<<< HEAD
        String sqlPersona = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlComercial = "UPDATE Comercial SET codigo_comercial = ?, apellidos = ?, zona_geografica = ? WHERE id_persona = ?";
=======
        String sqlPersona   = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlComercial = "UPDATE Comercial SET codigo_comercial = ?, zona_geografica = ? WHERE id_persona = ?";
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

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
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar el comercial: " + e.getMessage());
            return false;
        }
    }

<<<<<<< HEAD
=======
    /**
     * Elimina un comercial de la base de datos borrando su registro en Persona.
     * La FK con ON DELETE CASCADE elimina automáticamente el registro de Comercial.
     *
     * @param idPersona identificador de la persona a eliminar
     * @return {@code true} si se eliminó al menos una fila, {@code false} en caso contrario
     */
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

    /**
     * Mapea una fila del ResultSet a un objeto Comercial.
     *
     * @param rs ResultSet posicionado en la fila a leer
     * @return objeto Comercial con los datos de la fila
     * @throws SQLException si hay un error al leer las columnas
     */
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
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

        // Rescatamos la fecha de alta de la base de datos
        Date fechaAlta = rs.getDate("fecha_alta");
        if (fechaAlta != null) {
            c.setFechaAlta(fechaAlta.toLocalDate());
        }

        return c;
    }
}

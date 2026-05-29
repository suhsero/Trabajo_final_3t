package crm.dao;

import crm.database.ConexionBD;
import crm.model.ClientePotencial;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para la entidad ClientePotencial.
 * Proporciona operaciones CRUD completas contra las tablas Persona y ClientePotencial.
 *
 * @author Javier
 * @version 1.0
 */
public class ClientePotencialDAO {

    /**
     * Inserta un nuevo cliente potencial en la base de datos.
     * Primero inserta en la tabla padre Persona y después en ClientePotencial.
     *
     * @param c objeto ClientePotencial con los datos a insertar
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso de error
     */
    public boolean insertar(ClientePotencial c) {
        String sqlPersona   = "INSERT INTO Persona (nombre, email, telefono) VALUES (?, ?, ?)";
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

    /**
     * Recupera todos los clientes potenciales de la base de datos.
     *
     * @return lista con todos los clientes potenciales; vacía si no hay ninguno o hay error
     */
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

    /**
     * Actualiza los datos de un cliente potencial existente.
     * Actualiza tanto la tabla Persona como la tabla ClientePotencial.
     *
     * @param c objeto ClientePotencial con los datos nuevos; debe tener {@code idPersona} válido
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error
     */
    public boolean actualizar(ClientePotencial c) {
        String sqlPersona   = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlPotencial = "UPDATE ClientePotencial SET empresa = ?, fuente_captacion = ?, " +
                              "estado = ?, fecha_primer_contacto = ?, id_comercial = ? WHERE id_persona = ?";

        try (Connection con = ConexionBD.getConexion()) {
            // Actualizamos la tabla padre (Persona)
            try (PreparedStatement ps1 = con.prepareStatement(sqlPersona)) {
                ps1.setString(1, c.getNombre());
                ps1.setString(2, c.getEmail());
                ps1.setString(3, c.getTelefono());
                ps1.setInt(4, c.getIdPersona());
                ps1.executeUpdate();
            }

            // Actualizamos la tabla hija (ClientePotencial)
            try (PreparedStatement ps2 = con.prepareStatement(sqlPotencial)) {
                ps2.setString(1, c.getEmpresa());
                ps2.setString(2, c.getFuenteCaptacion());
                ps2.setString(3, c.getEstado());
                ps2.setDate(4, Date.valueOf(c.getFechaPrimerContacto()));
                ps2.setInt(5, c.getIdComercialAsignado());
                ps2.setInt(6, c.getIdPersona());
                ps2.executeUpdate();
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar el cliente potencial: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente potencial de la base de datos borrando su registro en Persona.
     * La FK con ON DELETE CASCADE elimina automáticamente el registro de ClientePotencial.
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
}

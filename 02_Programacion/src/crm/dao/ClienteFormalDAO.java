package crm.dao;

import crm.database.ConexionBD;
import crm.model.ClienteFormal;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para la entidad ClienteFormal.
 * Proporciona operaciones CRUD completas contra las tablas Persona y ClienteFormal.
 *
 * @author Javier
 * @version 1.0
 */
public class ClienteFormalDAO {

    /**
     * Inserta un nuevo cliente formal en la base de datos.
     * Primero inserta en la tabla padre Persona y después en ClienteFormal.
     *
     * @param c objeto ClienteFormal con los datos a insertar
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso de error
     */
    public boolean insertar(ClienteFormal c) {
        // SOLUCIÓN: Añadido fecha_registro al INSERT
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";
        String sqlFormal = "INSERT INTO ClienteFormal (id_persona, codigo_cliente, nif_cif, razon_social, direccion_fiscal, condiciones_pago, descuento_habitual, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps1 = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {

            ps1.setString(1, c.getNombre());
            ps1.setString(2, c.getEmail());
            ps1.setString(3, c.getTelefono());
            ps1.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now())); // Se envía la fecha actual
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

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

    /**
     * Recupera todos los clientes formales de la base de datos.
     *
     * @return lista con todos los clientes formales; vacía si no hay ninguno o hay error
     */
    public ArrayList<ClienteFormal> listarTodos() {
        ArrayList<ClienteFormal> lista = new ArrayList<>();

        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado " +
                "FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClienteFormal c = extraerCliente(rs);
                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al listar clientes formales.");
        }
        return lista;
    }

    /**
     * Actualiza los datos de un cliente formal existente.
     * Actualiza tanto la tabla Persona (nombre, email, teléfono) como
     * la tabla ClienteFormal (razon social, NIF, condiciones, etc.).
     *
     * @param c objeto ClienteFormal con los datos nuevos; debe tener {@code idPersona} válido
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso de error
     */
    public boolean actualizar(ClienteFormal c) {
        String sqlPersona  = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlFormal   = "UPDATE ClienteFormal SET codigo_cliente = ?, nif_cif = ?, razon_social = ?, " +
                             "direccion_fiscal = ?, condiciones_pago = ?, descuento_habitual = ?, estado = ? " +
                             "WHERE id_persona = ?";

        try (Connection con = ConexionBD.getConexion()) {
            // Actualizamos la tabla padre (Persona)
            try (PreparedStatement ps1 = con.prepareStatement(sqlPersona)) {
                ps1.setString(1, c.getNombre());
                ps1.setString(2, c.getEmail());
                ps1.setString(3, c.getTelefono());
                ps1.setInt(4, c.getIdPersona());
                ps1.executeUpdate();
            }

            // Actualizamos la tabla hija (ClienteFormal)
            try (PreparedStatement ps2 = con.prepareStatement(sqlFormal)) {
                ps2.setString(1, c.getCodigoCliente());
                ps2.setString(2, c.getNifCif());
                ps2.setString(3, c.getRazonSocial());
                ps2.setString(4, c.getDireccionFiscal());
                ps2.setString(5, c.getCondicionesPago());
                ps2.setDouble(6, c.getDescuentoHabitual());
                ps2.setString(7, c.getEstado());
                ps2.setInt(8, c.getIdPersona());
                ps2.executeUpdate();
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar el cliente formal: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente formal de la base de datos borrando su registro en Persona.
     * La FK con ON DELETE CASCADE elimina automáticamente el registro de ClienteFormal.
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

<<<<<<< HEAD
=======
    // --- REQUISITO DE LA RÚBRICA: SOBRECARGA DE MÉTODOS ---

    /**
     * Busca un cliente formal por su ID de persona.
     *
     * @param idPersona identificador único en la tabla Persona
     * @return objeto ClienteFormal encontrado, o {@code null} si no existe
     */
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
    public ClienteFormal buscarCliente(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado " +
                "FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona " +
                "WHERE p.id_persona = ?";
        return ejecutarBusquedaUnica(sql, idPersona, null);
    }

<<<<<<< HEAD
=======
    /**
     * Busca un cliente formal por su NIF/CIF.
     *
     * @param nifCif NIF o CIF del cliente
     * @return objeto ClienteFormal encontrado, o {@code null} si no existe
     */
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
    public ClienteFormal buscarCliente(String nifCif) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, " +
                "c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado " +
                "FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona " +
                "WHERE c.nif_cif = ?";
        return ejecutarBusquedaUnica(sql, 0, nifCif);
    }

<<<<<<< HEAD
=======
    /**
     * Método auxiliar privado que ejecuta una consulta que devuelve un único ClienteFormal.
     *
     * @param sql       sentencia SQL preparada
     * @param idPersona parámetro entero (se usa cuando nifCif es null)
     * @param nifCif    parámetro String (cuando no es null se usa en lugar de idPersona)
     * @return objeto ClienteFormal o {@code null} si no hay resultado
     */
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
    private ClienteFormal ejecutarBusquedaUnica(String sql, int idPersona, String nifCif) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (nifCif == null) {
                ps.setInt(1, idPersona);
            } else {
                ps.setString(1, nifCif);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraerCliente(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la búsqueda del cliente formal.");
        }
        return null;
    }

<<<<<<< HEAD
=======
    /**
     * Mapea una fila del ResultSet a un objeto ClienteFormal.
     *
     * @param rs ResultSet posicionado en la fila a leer
     * @return objeto ClienteFormal con los datos de la fila
     * @throws SQLException si hay un error al leer las columnas
     */
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
    private ClienteFormal extraerCliente(ResultSet rs) throws SQLException {
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
        return c;
    }
}

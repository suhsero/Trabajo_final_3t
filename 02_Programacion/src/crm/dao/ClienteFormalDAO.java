package crm.dao;

import crm.database.ConexionBD;
import crm.model.ClienteFormal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * DAO (Data Access Object) para la entidad ClienteFormal.
 * Encapsula todas las operaciones CRUD contra las tablas Persona y ClienteFormal
 * de la base de datos MySQL, usando JDBC con PreparedStatement.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class ClienteFormalDAO {

    /**
     * Inserta un nuevo cliente formal en la base de datos.
     * Realiza dos inserciones: primero en la tabla Persona y después en ClienteFormal,
     * usando el ID generado automáticamente por la primera inserción.
     *
     * @param c Objeto {@link ClienteFormal} con los datos a insertar.
     * @return {@code true} si la inserción fue correcta; {@code false} en caso de error.
     */
    public boolean insertar(ClienteFormal c) {
        String sqlPersona = "INSERT INTO Persona (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";
        String sqlFormal = "INSERT INTO ClienteFormal (id_persona, codigo_cliente, nif_cif, razon_social, direccion_fiscal, condiciones_pago, descuento_habitual, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
                    var9 = true;
                }
            }

            return var9;
        } catch (Exception e) {
            System.out.println("Error al insertar el cliente formal: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todos los clientes formales de la base de datos.
     * Realiza un JOIN entre las tablas Persona y ClienteFormal.
     *
     * @return Lista con todos los clientes formales; lista vacía si no hay registros.
     */
    public ArrayList<ClienteFormal> listarTodos() {
        ArrayList<ClienteFormal> lista = new ArrayList();
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()) {
                ClienteFormal c = this.extraerCliente(rs);
                lista.add(c);
            }
        } catch (Exception var14) {
            System.out.println("Error al listar clientes formales.");
        }

        return lista;
    }

    /**
     * Elimina un cliente formal por su ID de persona.
     * Al existir ON DELETE CASCADE en la BD, la eliminación de Persona
     * también borra el registro de ClienteFormal.
     *
     * @param idPersona ID de la persona a eliminar.
     * @return {@code true} si se eliminó al menos un registro; {@code false} si no se encontró.
     */
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

    /**
     * Actualiza los datos de un cliente formal existente en la base de datos.
     * Modifica tanto la tabla Persona como la tabla ClienteFormal.
     *
     * @param c Objeto {@link ClienteFormal} con los datos actualizados.
     * @return {@code true} si la actualización fue correcta; {@code false} en caso de error.
     */
    public boolean actualizar(ClienteFormal c) {
        String sqlPersona = "UPDATE Persona SET nombre = ?, email = ?, telefono = ? WHERE id_persona = ?";
        String sqlFormal = "UPDATE ClienteFormal SET codigo_cliente = ?, nif_cif = ?, razon_social = ?, direccion_fiscal = ?, condiciones_pago = ?, descuento_habitual = ?, estado = ? WHERE id_persona = ?";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps1 = con.prepareStatement(sqlPersona);
                PreparedStatement ps2 = con.prepareStatement(sqlFormal)
        ) {
            ps1.setString(1, c.getNombre());
            ps1.setString(2, c.getEmail());
            ps1.setString(3, c.getTelefono());
            ps1.setInt(4, c.getIdPersona());
            ps1.executeUpdate();

            ps2.setString(1, c.getCodigoCliente());
            ps2.setString(2, c.getNifCif());
            ps2.setString(3, c.getRazonSocial());
            ps2.setString(4, c.getDireccionFiscal());
            ps2.setString(5, c.getCondicionesPago());
            ps2.setDouble(6, c.getDescuentoHabitual());
            ps2.setString(7, c.getEstado());
            ps2.setInt(8, c.getIdPersona());
            ps2.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar el cliente formal: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un cliente formal por su ID de persona.
     * Sobrecarga del método buscarCliente para búsqueda por entero.
     *
     * @param idPersona ID de la persona a buscar.
     * @return El {@link ClienteFormal} encontrado, o {@code null} si no existe.
     */
    public ClienteFormal buscarCliente(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona WHERE p.id_persona = ?";
        return this.ejecutarBusquedaUnica(sql, idPersona, null);
    }

    /**
     * Busca un cliente formal por su NIF/CIF.
     * Sobrecarga del método buscarCliente para búsqueda por cadena de texto.
     *
     * @param nifCif NIF o CIF del cliente a buscar.
     * @return El {@link ClienteFormal} encontrado, o {@code null} si no existe.
     */
    public ClienteFormal buscarCliente(String nifCif) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona WHERE c.nif_cif = ?";
        return this.ejecutarBusquedaUnica(sql, 0, nifCif);
    }

    /**
     * Método privado auxiliar que ejecuta una búsqueda de un único cliente formal.
     * Centraliza la lógica de búsqueda para las dos sobrecargas de buscarCliente,
     * evitando duplicación de código (principio DRY).
     *
     * @param sql       Sentencia SQL con un parámetro de búsqueda.
     * @param idPersona ID a usar como parámetro (si nifCif es null).
     * @param nifCif    NIF/CIF a usar como parámetro (si idPersona es 0).
     * @return El {@link ClienteFormal} encontrado, o {@code null} si no existe.
     */
    private ClienteFormal ejecutarBusquedaUnica(String sql, int idPersona, String nifCif) {
        try {
            try (
                    Connection con = ConexionBD.getConexion();
                    PreparedStatement ps = con.prepareStatement(sql);
            ) {
                if (nifCif == null) {
                    ps.setInt(1, idPersona);
                } else {
                    ps.setString(1, nifCif);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ClienteFormal var7 = this.extraerCliente(rs);
                        return var7;
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception var15) {
            System.out.println("Error en la búsqueda del cliente formal.");
            return null;
        }
    }

    /**
     * Método privado auxiliar que construye un objeto ClienteFormal a partir
     * de la fila actual de un ResultSet.
     *
     * @param rs ResultSet posicionado en la fila a leer.
     * @return Objeto {@link ClienteFormal} con los datos de la fila.
     * @throws SQLException Si ocurre un error al leer las columnas del ResultSet.
     */
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
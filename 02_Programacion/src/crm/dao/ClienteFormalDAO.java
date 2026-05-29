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

public class ClienteFormalDAO {
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

    public ClienteFormal buscarCliente(int idPersona) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona WHERE p.id_persona = ?";
        return this.ejecutarBusquedaUnica(sql, idPersona, (String)null);
    }

    public ClienteFormal buscarCliente(String nifCif) {
        String sql = "SELECT p.id_persona, p.nombre, p.email, p.telefono, p.fecha_registro, c.id_formal, c.codigo_cliente, c.nif_cif, c.razon_social, c.direccion_fiscal, c.condiciones_pago, c.descuento_habitual, c.estado FROM ClienteFormal c INNER JOIN Persona p ON c.id_persona = p.id_persona WHERE c.nif_cif = ?";
        return this.ejecutarBusquedaUnica(sql, 0, nifCif);
    }

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import restaurante.config.ConexionDB;
import restaurante.modelo.Cliente;
import restaurante.repositorio.ClienteRepositorio;

public class ClienteDAOMySQL implements ClienteRepositorio {

    @Override
    public void guardar(Cliente c) {
        String sql = "INSERT INTO clientes (id_cliente, nombre, apellido, telefono, correo, direccion) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            cn.setAutoCommit(false);
            try {

                ps.setInt(1, c.getId());
                ps.setString(2, c.getNombre());
                ps.setString(3, c.getApellido());
                ps.setString(4, c.getTelefono());
                ps.setString(5, c.getCorreo());
                ps.setString(6, c.getDireccion());
                int afectados = ps.executeUpdate();
                int idAuditoria = c.getId();
                if (idAuditoria == 0) {
                    try (ResultSet claves = ps.getGeneratedKeys()) {
                        if (!claves.next()) throw new SQLException("No se obtuvo el ID generado");
                        idAuditoria = claves.getInt(1);
                    }
                }

                if (afectados > 0) AuditoriaDAO.registrar(cn, "clientes", String.valueOf(idAuditoria), "CREAR", DetalleAuditoria.creacion("clientes", DetalleAuditoria.cliente(c)));
                cn.commit();
                c.setId(idAuditoria);
            } catch (SQLException | RuntimeException ex) {
                try { cn.rollback(); } catch (SQLException r) { ex.addSuppressed(r); }
                throw ex;
            }

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al guardar cliente: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, telefono=?, correo=?, direccion=? "
                + "WHERE id_cliente=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            try {
                java.util.Map<String, String> anterior = DetalleAuditoria.leerAnterior(cn, "clientes", c.getId());

                ps.setString(1, c.getNombre());
                ps.setString(2, c.getApellido());
                ps.setString(3, c.getTelefono());
                ps.setString(4, c.getCorreo());
                ps.setString(5, c.getDireccion());
                ps.setInt(6, c.getId());
                int afectados = ps.executeUpdate();

                if (afectados > 0) AuditoriaDAO.registrar(cn, "clientes", String.valueOf(c.getId()), "ACTUALIZAR", DetalleAuditoria.cambio("clientes", anterior, DetalleAuditoria.cliente(c)));
                cn.commit();
            } catch (SQLException | RuntimeException ex) {
                try { cn.rollback(); } catch (SQLException r) { ex.addSuppressed(r); }
                throw ex;
            }

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al actualizar cliente: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            cn.setAutoCommit(false);
            try {
                java.util.Map<String, String> anterior = DetalleAuditoria.leerAnterior(cn, "clientes", id);

                ps.setInt(1, id);
                int afectados = ps.executeUpdate();

                if (afectados > 0) AuditoriaDAO.registrar(cn, "clientes", String.valueOf(id), "ELIMINAR", DetalleAuditoria.eliminacion("clientes", anterior));
                cn.commit();
            } catch (SQLException | RuntimeException ex) {
                try { cn.rollback(); } catch (SQLException r) { ex.addSuppressed(r); }
                throw ex;
            }

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al eliminar cliente: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al buscar cliente: " + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id_cliente";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar clientes: " + ex.getMessage(), ex);
        }
        return lista;
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id_cliente"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("direccion")
        );
    }
}

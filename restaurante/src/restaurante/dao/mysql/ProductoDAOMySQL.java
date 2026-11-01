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
import restaurante.modelo.Categoria;
import restaurante.modelo.Producto;
import restaurante.repositorio.ProductoRepositorio;

public class ProductoDAOMySQL implements ProductoRepositorio {

    private static final String SELECT_BASE
            = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.estado, "
            + "       c.id_categoria, c.nombre AS cat_nombre, c.descripcion AS cat_descripcion "
            + "FROM productos p "
            + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria ";

    @Override
    public void guardar(Producto p) {
        String sql = "INSERT INTO productos (id_producto, id_categoria, nombre, descripcion, precio, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setInt(2, p.getCategoria().getId());
            ps.setString(3, p.getNombre());
            ps.setString(4, p.getDescripcion());
            ps.setDouble(5, p.getPrecio());
            ps.setString(6, p.isDisponible() ? "Disponible" : "No disponible");
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al guardar producto: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void actualizar(Producto p) {
        String sql = "UPDATE productos SET id_categoria=?, nombre=?, descripcion=?, precio=?, estado=? "
                + "WHERE id_producto=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, p.getCategoria().getId());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setString(5, p.isDisponible() ? "Disponible" : "No disponible");
            ps.setInt(6, p.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al actualizar producto: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_producto=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al eliminar producto: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Producto buscarPorId(int id) {
        String sql = SELECT_BASE + "WHERE p.id_producto=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al buscar producto: " + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY p.id_producto";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar productos: " + ex.getMessage(), ex);
        }
        return lista;
    }

    @Override
    public List<Producto> listarDisponibles() {
        List<Producto> lista = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE p.estado='Disponible' ORDER BY p.id_producto";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar productos disponibles: " + ex.getMessage(), ex);
        }
        return lista;
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Categoria cat = new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("cat_nombre"),
                rs.getString("cat_descripcion"));

        return new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                "Disponible".equalsIgnoreCase(rs.getString("estado")),
                cat
        );
    }
}

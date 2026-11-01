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
import restaurante.repositorio.CategoriaRepositorio;

public class CategoriaDAOMySQL implements CategoriaRepositorio {

    @Override
    public void guardar(Categoria c) {
        String sql = "INSERT INTO categorias (id_categoria, nombre, descripcion) VALUES (?, ?, ?)";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, c.getId());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDescripcion());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al guardar categoría: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void actualizar(Categoria c) {
        String sql = "UPDATE categorias SET nombre=?, descripcion=? WHERE id_categoria=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al actualizar categoría: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id_categoria=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al eliminar categoría: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id_categoria=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                            rs.getInt("id_categoria"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"));
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al buscar categoría: " + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY id_categoria";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar categorías: " + ex.getMessage(), ex);
        }
        return lista;
    }
}

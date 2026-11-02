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
import restaurante.modelo.Mesa;

public class MesaDAOMySQL {

    public List<Mesa> listar() {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT id_mesa, numero_mesa, capacidad, estado FROM mesas ORDER BY numero_mesa";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Mesa(
                        rs.getInt("id_mesa"),
                        rs.getInt("numero_mesa"),
                        rs.getInt("capacidad"),
                        rs.getString("estado")));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar mesas: " + ex.getMessage(), ex);
        }
        return lista;
    }

    public Mesa buscarPorId(int id) {
        String sql = "SELECT id_mesa, numero_mesa, capacidad, estado FROM mesas WHERE id_mesa=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Mesa(
                            rs.getInt("id_mesa"),
                            rs.getInt("numero_mesa"),
                            rs.getInt("capacidad"),
                            rs.getString("estado"));
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al buscar mesa: " + ex.getMessage(), ex);
        }
        return null;
    }
}

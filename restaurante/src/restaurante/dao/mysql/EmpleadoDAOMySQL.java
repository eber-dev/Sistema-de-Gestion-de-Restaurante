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
import restaurante.modelo.Empleado;

public class EmpleadoDAOMySQL {

    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT id_empleado, nombre, apellido, cargo FROM empleados "
                + "WHERE estado='Activo' ORDER BY id_empleado";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cargo")));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar empleados: " + ex.getMessage(), ex);
        }
        return lista;
    }

    public Empleado buscarPorId(int id) {
        String sql = "SELECT id_empleado, nombre, apellido, cargo FROM empleados WHERE id_empleado=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id_empleado"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("cargo"));
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al buscar empleado: " + ex.getMessage(), ex);
        }
        return null;
    }
}

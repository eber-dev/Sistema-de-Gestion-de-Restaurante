/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.controlador;

import java.util.List;
import restaurante.app.Aplicacion;
import restaurante.modelo.Cliente;

/**
 *
 * @author EBER
 */
public class ClienteControlador {

    public void guardar(int id, String nombre, String apellido,
            String telefono, String correo, String direccion) {
        if (Aplicacion.cliente().buscarPorId(id) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con el ID " + id);
        }
        Aplicacion.cliente().guardar(new Cliente(
                id, nombre, apellido, telefono, correo, direccion));
    }

    public void actualizar(int id, String nombre, String apellido,
            String telefono, String correo, String direccion) {
        if (Aplicacion.cliente().buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "No existe un cliente con el ID " + id);
        }
        Aplicacion.cliente().actualizar(new Cliente(
                id, nombre, apellido, telefono, correo, direccion));
    }

    public void eliminar(int id) {
        Aplicacion.cliente().eliminar(id);
    }

    public List<Cliente> listar() {
        return Aplicacion.cliente().listar();
    }

    public Cliente buscar(int id) {
        return Aplicacion.cliente().buscarPorId(id);
    }
}

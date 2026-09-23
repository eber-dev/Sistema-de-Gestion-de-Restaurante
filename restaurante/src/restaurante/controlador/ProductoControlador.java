/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.controlador;

import java.util.List;
import restaurante.app.Aplicacion;
import restaurante.modelo.Categoria;
import restaurante.modelo.Producto;

/**
 *
 * @author EBER
 */
public class ProductoControlador {

    public void guardar(int id, String nombre, String descripcion,
            double precio, boolean disponible, Categoria categoria) {
        if (Aplicacion.producto().buscarPorId(id) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el ID " + id);
        }
        Aplicacion.producto().guardar(new Producto(
                id, nombre, descripcion, precio, disponible, categoria));
    }

    public void actualizar(int id, String nombre, String descripcion,
            double precio, boolean disponible, Categoria categoria) {
        if (Aplicacion.producto().buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "No existe un producto con el ID " + id);
        }
        Aplicacion.producto().actualizar(new Producto(
                id, nombre, descripcion, precio, disponible, categoria));
    }

    public void eliminar(int id) {
        Aplicacion.producto().eliminar(id);
    }

    public List<Producto> listar() {
        return Aplicacion.producto().listar();
    }

    public Producto buscar(int id) {
        return Aplicacion.producto().buscarPorId(id);
    }
}

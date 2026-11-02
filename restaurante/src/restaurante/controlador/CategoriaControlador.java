/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.controlador;

import java.util.List;
import restaurante.app.Aplicacion;
import restaurante.modelo.Categoria;

/**
 *
 * @author EBER
 */
public class CategoriaControlador {

    public void guardar(int id, String nombre, String descripcion) {
        if (Aplicacion.categoria().buscarPorId(id) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una categoría con el ID " + id);
        }
        Aplicacion.categoria().guardar(
                new Categoria(id, nombre, descripcion));
    }

    public void actualizar(int id, String nombre, String descripcion) {
        if (Aplicacion.categoria().buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "No existe una categoría con el ID " + id);
        }
        Aplicacion.categoria().actualizar(
                new Categoria(id, nombre, descripcion));
    }

    public void eliminar(int id) {
        Aplicacion.categoria().eliminar(id);
    }

    public List<Categoria> listar() {
        return Aplicacion.categoria().listar();
    }

    public Categoria buscar(int id) {
        return Aplicacion.categoria().buscarPorId(id);
    }
}

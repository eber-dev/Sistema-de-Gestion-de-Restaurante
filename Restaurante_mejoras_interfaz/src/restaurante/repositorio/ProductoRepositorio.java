/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.repositorio;

import java.util.List;
import restaurante.modelo.Producto;

/**
 *
 * @author EBER
 */
public interface ProductoRepositorio {

    void guardar(Producto producto);

    void actualizar(Producto producto);

    void eliminar(int id);

    Producto buscarPorId(int id);

    List<Producto> listar();

    List<Producto> listarDisponibles();
}

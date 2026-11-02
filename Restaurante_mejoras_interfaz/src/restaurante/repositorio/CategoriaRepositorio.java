/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.repositorio;

import java.util.List;
import restaurante.modelo.Categoria;

/**
 *
 * @author EBER
 */

public interface CategoriaRepositorio {

    void guardar(Categoria categoria);

    void actualizar(Categoria categoria);

    void eliminar(int id);

    Categoria buscarPorId(int id);

    List<Categoria> listar();
}

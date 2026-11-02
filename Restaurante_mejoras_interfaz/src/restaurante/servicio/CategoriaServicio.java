/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.servicio;

import java.util.List;
import restaurante.modelo.Categoria;
import restaurante.repositorio.CategoriaRepositorio;

/**
 *
 * @author EBER
 */

public class CategoriaServicio {

    private final CategoriaRepositorio repositorio;

    public CategoriaServicio(CategoriaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void guardar(Categoria categoria) {

        validarCategoria(categoria);

        repositorio.guardar(categoria);
    }

    public void actualizar(Categoria categoria) {

        validarCategoria(categoria);

        repositorio.actualizar(categoria);
    }

    public void eliminar(int id) {

        Categoria categoria
                = repositorio.buscarPorId(id);

        if (categoria == null) {

            throw new IllegalArgumentException(
                    "La categoría no existe."
            );
        }

        repositorio.eliminar(id);
    }

    public Categoria buscarPorId(int id) {

        return repositorio.buscarPorId(id);
    }

    public List<Categoria> listar() {

        return repositorio.listar();
    }

    private void validarCategoria(Categoria categoria) {

        if (categoria == null) {

            throw new IllegalArgumentException(
                    "La categoría no puede ser nula."
            );
        }

        if (categoria.getNombre() == null
                || categoria.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre de la categoría es obligatorio."
            );
        }
    }
}

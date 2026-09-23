/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.implementacion;

import java.util.ArrayList;
import java.util.List;
import restaurante.modelo.Categoria;
import restaurante.repositorio.CategoriaRepositorio;

/**
 *
 * @author EBER
 */

public class CategoriaRepositorioMemoria
        implements CategoriaRepositorio {

    private final List<Categoria> categorias
            = new ArrayList<>();

    @Override
    public void guardar(Categoria categoria) {

        categorias.add(categoria);
    }

    @Override
    public void actualizar(Categoria categoria) {

        for (int i = 0; i < categorias.size(); i++) {

            if (categorias.get(i).getId()
                    == categoria.getId()) {

                categorias.set(i, categoria);
                return;
            }
        }
    }

    @Override
    public void eliminar(int id) {

        categorias.removeIf(
                categoria -> categoria.getId() == id
        );
    }

    @Override
    public Categoria buscarPorId(int id) {

        for (Categoria categoria : categorias) {

            if (categoria.getId() == id) {
                return categoria;
            }
        }

        return null;
    }

    @Override
    public List<Categoria> listar() {

        return new ArrayList<>(categorias);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.implementacion;

import java.util.ArrayList;
import java.util.List;
import restaurante.modelo.Producto;
import restaurante.repositorio.ProductoRepositorio;

/**
 *
 * @author EBER
 */
public class ProductoRepositorioMemoria
        implements ProductoRepositorio {

    private final List<Producto> productos = new ArrayList<>();

    @Override
    public void guardar(Producto producto) {
        productos.add(producto);
    }

    @Override
    public void actualizar(Producto producto) {

        for (int i = 0; i < productos.size(); i++) {

            if (productos.get(i).getId() == producto.getId()) {

                productos.set(i, producto);
                return;
            }
        }
    }

    @Override
    public void eliminar(int id) {

        productos.removeIf(
                producto -> producto.getId() == id
        );
    }

    @Override
    public Producto buscarPorId(int id) {

        for (Producto producto : productos) {

            if (producto.getId() == id) {
                return producto;
            }
        }

        return null;
    }

    @Override
    public List<Producto> listar() {

        return new ArrayList<>(productos);
    }

    @Override
    public List<Producto> listarDisponibles() {

        List<Producto> disponibles = new ArrayList<>();

        for (Producto producto : productos) {

            if (producto.isDisponible()) {
                disponibles.add(producto);
            }
        }

        return disponibles;
    }
}

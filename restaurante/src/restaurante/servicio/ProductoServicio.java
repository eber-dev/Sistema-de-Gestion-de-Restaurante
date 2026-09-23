/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.servicio;

import java.util.List;
import restaurante.modelo.Producto;
import restaurante.repositorio.ProductoRepositorio;

/**
 *
 * @author EBER
 */

public class ProductoServicio {

    private final ProductoRepositorio repositorio;

    public ProductoServicio(ProductoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void guardar(Producto producto) {

        validarProducto(producto);

        repositorio.guardar(producto);
    }

    public void actualizar(Producto producto) {

        validarProducto(producto);

        repositorio.actualizar(producto);
    }

    public void eliminar(int id) {

        repositorio.eliminar(id);
    }

    public Producto buscarPorId(int id) {

        return repositorio.buscarPorId(id);
    }

    public List<Producto> listar() {

        return repositorio.listar();
    }

    public List<Producto> listarDisponibles() {

        return repositorio.listarDisponibles();
    }

    public void cambiarDisponibilidad(int id, boolean disponible) {

        Producto producto = repositorio.buscarPorId(id);

        if (producto == null) {
            throw new IllegalArgumentException(
                    "Producto no encontrado."
            );
        }

        producto.setDisponible(disponible);

        repositorio.actualizar(producto);
    }

    private void validarProducto(Producto producto) {

        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre es obligatorio."
            );
        }

        if (producto.getPrecio() <= 0) {

            throw new IllegalArgumentException(
                    "El precio debe ser mayor que 0."
            );
        }

        if (producto.getCategoria() == null) {

            throw new IllegalArgumentException(
                    "Debe seleccionar una categoría."
            );
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.app;

import restaurante.implementacion.CategoriaRepositorioMemoria;
import restaurante.implementacion.ClienteRepositorioMemoria;
import restaurante.implementacion.ProductoRepositorioMemoria;
import restaurante.servicio.CategoriaServicio;
import restaurante.servicio.ClienteServicio;
import restaurante.servicio.ProductoServicio;

/**
 *
 * @author EBER
 */
public class Aplicacion {

    private static final CategoriaServicio categoriaServicio
            = new CategoriaServicio(new CategoriaRepositorioMemoria());

    private static final ClienteServicio clienteServicio
            = new ClienteServicio(new ClienteRepositorioMemoria());

    private static final ProductoServicio productoServicio
            = new ProductoServicio(new ProductoRepositorioMemoria());

    private static boolean inicializado = false;

    private Aplicacion() {
    }

    public static void inicializar() {
        if (inicializado) {
            return;
        }
        DatosDemo.cargar();
        inicializado = true;
    }

    public static CategoriaServicio categoria() {
        return categoriaServicio;
    }

    public static ClienteServicio cliente() {
        return clienteServicio;
    }

    public static ProductoServicio producto() {
        return productoServicio;
    }
}

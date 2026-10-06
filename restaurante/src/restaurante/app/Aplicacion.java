/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.app;

import patrones.facade.VentaFacade;
import restaurante.implementacion.CategoriaRepositorioMemoria;
import restaurante.implementacion.ClienteRepositorioMemoria;
import restaurante.implementacion.PedidoRepositorioMemoria;
import restaurante.implementacion.ProductoRepositorioMemoria;
import restaurante.implementacion.VentaRepositorioMemoria;
import restaurante.repositorio.PedidoRepositorio;
import restaurante.repositorio.VentaRepositorio;
import restaurante.servicio.BoletaServicio;
import restaurante.servicio.CategoriaServicio;
import restaurante.servicio.ClienteServicio;
import restaurante.servicio.PedidoServicio;
import restaurante.servicio.ProductoServicio;
import restaurante.servicio.VentaServicio;

public class Aplicacion {

    private static final CategoriaServicio categoriaServicio
            = new CategoriaServicio(new CategoriaRepositorioMemoria());

    private static final ClienteServicio clienteServicio
            = new ClienteServicio(new ClienteRepositorioMemoria());

    private static final ProductoServicio productoServicio
            = new ProductoServicio(new ProductoRepositorioMemoria());

    private static final PedidoRepositorio pedidoRepositorio
            = patrones.singleton.PedidoRepositorio.getInstancia();
    private static final PedidoServicio pedidoServicio
            = new PedidoServicio(pedidoRepositorio);

    private static final VentaRepositorio ventaRepositorio
            = new VentaRepositorioMemoria();
    private static final BoletaServicio boletaServicio
            = new BoletaServicio();
    private static final VentaServicio ventaServicio
            = new VentaServicio(ventaRepositorio);
    private static final VentaFacade ventaFacade
            = new VentaFacade(ventaServicio, boletaServicio);

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

    public static PedidoServicio pedido() {
        return pedidoServicio;
    }

    public static VentaServicio venta() {
        return ventaServicio;
    }

    public static VentaFacade ventaFacade() {
        return ventaFacade;
    }
}

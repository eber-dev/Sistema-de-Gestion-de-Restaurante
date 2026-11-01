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

    private static final restaurante.dao.DAOFactory DAO
            = restaurante.dao.DAOFactory.getInstancia();

    private static final CategoriaServicio categoriaServicio
            = new CategoriaServicio(DAO.crearCategoriaRepositorio());

    private static final ClienteServicio clienteServicio
            = new ClienteServicio(DAO.crearClienteRepositorio());

    private static final ProductoServicio productoServicio
            = new ProductoServicio(DAO.crearProductoRepositorio());

    private static final PedidoRepositorio pedidoRepositorio
            = DAO.crearPedidoRepositorio();
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

        try {
            restaurante.config.DatabaseInitializer.inicializar();
        } catch (Exception ex) {
            System.err.println("[App] No se pudo inicializar la base de datos: " + ex.getMessage());
        }

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

    public static restaurante.dao.mysql.EmpleadoDAOMySQL empleados() {
        return DAO.crearEmpleadoDAO();
    }

    public static restaurante.dao.mysql.MesaDAOMySQL mesas() {
        return DAO.crearMesaDAO();
    }

    public static restaurante.repositorio.PedidoRepositorio pedidoDAO() {
        return pedidoRepositorio;
    }
}

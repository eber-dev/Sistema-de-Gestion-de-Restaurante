/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.controlador;

/**
 *
 * @author Loreley
 */
import java.util.List;
import restaurante.modelo.Pedido;
import patrones.abstractfactory.AbstractFactoryPedido;
import patrones.Builder.AbstractPedidoBuilder;
import patrones.factory.PedidoFactory;
import patrones.singleton.PedidoRepositorio;

public class PedidoControlador {

    private final PedidoRepositorio repositorio;

    public PedidoControlador() {
        repositorio = PedidoRepositorio.getInstancia();
    }

    public Pedido crearPedido(
            AbstractFactoryPedido fabrica,
            String cliente,
            String sucursal,
            String canal) {

        AbstractPedidoBuilder builder = fabrica.crearBuilder();

        return builder
                .establecerCliente(cliente)
                .establecerSucursal(sucursal)
                .establecerCanal(canal)
                .construir();
    }

    public Pedido crearPedidoConFactory(
            String cliente, String sucursal, String canal) {

        PedidoFactory factory
                = new patrones.factory.PedidoFactoryConcreto();

        Pedido pedido = factory.crearPedido();
        pedido.setCliente(cliente);
        pedido.setSucursal(sucursal);
        pedido.setCanal(canal);

        return pedido;
    }

    public void confirmarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("No existe un pedido.");
        }

        if (pedido.getCliente() == null
                || pedido.getCliente().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un cliente.");
        }

        if (pedido.getSucursal() == null
                || pedido.getSucursal().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una sucursal.");
        }

        if (!pedido.tieneDetalles()) {
            throw new IllegalArgumentException(
                    "Debe agregar al menos un producto.");
        }

        pedido.setEstado("Confirmado");
        repositorio.guardar(pedido);
    }

    public Pedido clonarPedido(Pedido original) {
        if (original == null) {
            throw new IllegalArgumentException(
                    "No existe un pedido para clonar.");
        }

        return original.clonar();
    }

    public List<Pedido> listarPedidos() {
        return repositorio.listar();
    }

    public boolean eliminarPedido(String codigo) {
        return repositorio.eliminar(codigo);
    }
}

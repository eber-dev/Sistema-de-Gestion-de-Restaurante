/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.Builder;

/**
 *
 * @author Loreley
 */

import restaurante.modelo.Pedido;
import patrones.factory.PedidoFactory;

public class PedidoBuilderConcreto implements AbstractPedidoBuilder {

    private final Pedido pedido;

    public PedidoBuilderConcreto(PedidoFactory factory) {
        this.pedido = factory.crearPedido();
    }

    @Override
    public AbstractPedidoBuilder establecerCliente(String cliente) {
        pedido.setCliente(cliente);
        return this;
    }

    @Override
    public AbstractPedidoBuilder establecerSucursal(String sucursal) {
        pedido.setSucursal(sucursal);
        return this;
    }

    @Override
    public AbstractPedidoBuilder establecerCanal(String canal) {
        pedido.setCanal(canal);
        return this;
    }

    @Override
    public AbstractPedidoBuilder agregarProducto(
            String producto, int cantidad, double precio) {

        pedido.agregarDetalle(producto, cantidad, precio);
        return this;
    }

    @Override
    public Pedido construir() {
        return pedido;
    }
}
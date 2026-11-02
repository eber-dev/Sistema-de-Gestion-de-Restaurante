/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package patrones.Builder;

/**
 *
 * @author Loreley
 */

import restaurante.modelo.Pedido;

public interface AbstractPedidoBuilder {

    AbstractPedidoBuilder establecerCliente(String cliente);

    AbstractPedidoBuilder establecerSucursal(String sucursal);

    AbstractPedidoBuilder establecerCanal(String canal);

    AbstractPedidoBuilder agregarProducto(
            String producto, int cantidad, double precio);

    Pedido construir();
}

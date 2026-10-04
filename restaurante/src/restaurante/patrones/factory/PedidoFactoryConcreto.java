/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.factory;

/**
 *
 * @author Loreley
 */


import restaurante.modelo.Pedido;

public class PedidoFactoryConcreto implements PedidoFactory {

    private static int contador = 1;

    @Override
    public Pedido crearPedido() {
        String codigo = String.format("PED-%03d", contador++);
        return new Pedido(codigo);
    }
}

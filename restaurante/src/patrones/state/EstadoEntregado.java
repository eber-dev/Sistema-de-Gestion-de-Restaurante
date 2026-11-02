/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.state;

import restaurante.modelo.Pedido;

public class EstadoEntregado implements EstadoPedido {

    @Override
    public String getNombre() {
        return "Entregado";
    }

    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue entregado.");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue entregado.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("No se puede cancelar un pedido ya entregado.");
    }
}

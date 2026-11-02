/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.state;

import restaurante.modelo.Pedido;

public class EstadoCancelado implements EstadoPedido {

    @Override
    public String getNombre() {
        return "Cancelado";
    }

    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("El pedido está cancelado.");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("El pedido está cancelado.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está cancelado.");
    }
}

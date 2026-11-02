/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.state;

import restaurante.modelo.Pedido;

public class EstadoEnPreparacion implements EstadoPedido {

    @Override
    public String getNombre() {
        return "En preparacion";
    }

    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está en preparación.");
    }

    @Override
    public void entregar(Pedido pedido) {
        pedido.setEstadoObj(new EstadoEntregado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstadoObj(new EstadoCancelado());
    }
}

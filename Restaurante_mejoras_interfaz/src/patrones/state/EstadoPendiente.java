/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.state;

import restaurante.modelo.Pedido;

public class EstadoPendiente implements EstadoPedido {

    @Override
    public String getNombre() {
        return "Pendiente";
    }

    @Override
    public void confirmar(Pedido pedido) {
        pedido.setEstadoObj(new EstadoEnPreparacion());
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("El pedido debe ser confirmado primero.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstadoObj(new EstadoCancelado());
    }
}

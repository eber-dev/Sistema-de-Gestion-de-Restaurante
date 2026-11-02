/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.decorator;

import restaurante.modelo.Pedido;

public class PedidoBase implements Calculable {

    private final Pedido pedido;

    public PedidoBase(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public double calcularTotal() {
        return pedido.calcularTotal();
    }

    @Override
    public String getDescripcion() {
        return "Pedido Base";
    }
}

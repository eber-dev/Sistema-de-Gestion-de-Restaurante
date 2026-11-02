/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.command;

import restaurante.modelo.Pedido;
import restaurante.repositorio.PedidoRepositorio;

public class ConfirmarPedidoComando implements Comando {

    private final Pedido pedido;
    private final PedidoRepositorio repositorio;

    public ConfirmarPedidoComando(Pedido pedido, PedidoRepositorio repositorio) {
        this.pedido = pedido;
        this.repositorio = repositorio;
    }

    @Override
    public void ejecutar() {
        pedido.confirmar();
        repositorio.guardar(pedido);
    }
}

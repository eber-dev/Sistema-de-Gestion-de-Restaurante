/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.state;

import restaurante.modelo.Pedido;

public interface EstadoPedido {

    String getNombre();

    void confirmar(Pedido pedido);

    void entregar(Pedido pedido);

    void cancelar(Pedido pedido);
}

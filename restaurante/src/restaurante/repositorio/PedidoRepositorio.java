/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.repositorio;

import java.util.List;
import restaurante.modelo.Pedido;

public interface PedidoRepositorio {

    void guardar(Pedido pedido);

    Pedido buscarPorCodigo(String codigo);

    List<Pedido> listar();
}

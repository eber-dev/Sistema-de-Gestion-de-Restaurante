/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.implementacion;

import java.util.ArrayList;
import java.util.List;
import restaurante.modelo.Pedido;
import restaurante.repositorio.PedidoRepositorio;

public class PedidoRepositorioMemoria implements PedidoRepositorio {

    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void guardar(Pedido p) {
        pedidos.add(p);
    }

    @Override
    public Pedido buscarPorCodigo(String codigo) {
        for (Pedido p : pedidos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Pedido> listar() {
        return new ArrayList<>(pedidos);
    }
}

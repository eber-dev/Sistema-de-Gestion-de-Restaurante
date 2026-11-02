/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.servicio;

import java.util.List;
import restaurante.modelo.Pedido;
import restaurante.repositorio.PedidoRepositorio;

public class PedidoServicio {

    private final PedidoRepositorio repositorio;

    public PedidoServicio(PedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void guardar(Pedido p) {
        if (p == null) {
            throw new IllegalArgumentException("Pedido nulo.");
        }
        if (repositorio.buscarPorCodigo(p.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe el pedido " + p.getCodigo());
        }
        repositorio.guardar(p);
    }

    public Pedido buscarPorCodigo(String codigo) {
        return repositorio.buscarPorCodigo(codigo);
    }

    public List<Pedido> listar() {
        return repositorio.listar();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.singleton;

/**
 *
 * @author Loreley
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import restaurante.modelo.Pedido;

public class PedidoRepositorio {

    private static final PedidoRepositorio INSTANCIA
            = new PedidoRepositorio();

    private final List<Pedido> pedidos = new ArrayList<>();

    private PedidoRepositorio() {
    }

    public static PedidoRepositorio getInstancia() {
        return INSTANCIA;
    }

    public void guardar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> listar() {
        return Collections.unmodifiableList(
                new ArrayList<>(pedidos));
    }

    public boolean eliminar(String codigo) {
        return pedidos.removeIf(p -> p.getCodigo().equals(codigo));
    }

    public Pedido buscarPorCodigo(String codigo) {
        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }

        return null;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.controlador;

import java.util.List;
import restaurante.modelo.Pedido;
import patrones.abstractfactory.AbstractFactoryPedido;
import patrones.Builder.AbstractPedidoBuilder;
import restaurante.app.Aplicacion;

public class PedidoControlador {

    public Pedido crearPedido(
            AbstractFactoryPedido fabrica,
            int idCliente,
            String nombreCliente,
            int idEmpleado,
            int idMesa,
            String tipoPedido) {

        AbstractPedidoBuilder builder = fabrica.crearBuilder();

        Pedido p = builder
                .establecerCliente(nombreCliente)
                .establecerSucursal(tipoPedido)
                .establecerCanal("Delivery".equalsIgnoreCase(tipoPedido) ? "Virtual" : "Presencial")
                .construir();

        p.setIdCliente(idCliente);
        p.setIdEmpleado(idEmpleado);
        p.setIdMesa(idMesa);
        p.setTipoPedido(tipoPedido);
        p.setEstado("Pendiente");
        return p;
    }

    public void confirmarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("No existe un pedido para confirmar.");
        }

        // Si ya tiene ID, es porque ya fue guardado en la BD
        if (pedido.getIdPedido() > 0) {
            throw new IllegalArgumentException("Este pedido ya fue confirmado y guardado en la base de datos.");
        }

        // Verificamos el estado usando el patrón State
        if (!pedido.getEstado().equalsIgnoreCase("Pendiente")) {
            throw new IllegalArgumentException("Solo se pueden confirmar pedidos en estado 'Pendiente'.");
        }

        if (pedido.getCliente() == null || pedido.getCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un cliente.");
        }
        if (pedido.getIdEmpleado() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un empleado.");
        }
        if (!pedido.tieneDetalles()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto.");
        }

        // Ejecutamos el patrón Command
        patrones.command.Comando cmd = new patrones.command.ConfirmarPedidoComando(pedido, Aplicacion.pedidoDAO());
        cmd.ejecutar();
    }

    public Pedido clonarPedido(Pedido original) {
        if (original == null) {
            throw new IllegalArgumentException("No existe un pedido para clonar.");
        }
        return original.clonar();
    }

    public List<Pedido> listarPedidos() {
        return Aplicacion.pedido().listar();
    }

    public boolean eliminarPedido(String codigo) {
        return Aplicacion.pedido().buscarPorCodigo(codigo) != null
                && ((restaurante.dao.mysql.PedidoDAOMySQL) Aplicacion.pedidoDAO()).eliminar(codigo);
    }
}

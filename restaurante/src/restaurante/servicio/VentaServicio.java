/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.servicio;

import java.util.List;
import restaurante.modelo.DetallePedido;
import restaurante.modelo.MetodoPago;
import restaurante.modelo.Pedido;
import restaurante.modelo.Venta;
import restaurante.repositorio.VentaRepositorio;

public class VentaServicio {

    private static final double IGV = 0.18;
    private final VentaRepositorio repositorio;

    public VentaServicio(VentaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void guardar(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser nula.");
        }

        // 1. Guardamos el pago en la BD (o memoria)
        repositorio.guardar(venta);

        // 2. Actualizamos el estado del pedido
        venta.getPedido().entregar();
        restaurante.app.Aplicacion.pedidoDAO().actualizarEstado(
                venta.getPedido().getIdPedido(),
                venta.getPedido().getEstado() // Devuelve "Entregado"
        );
    }

    public Venta buscarPorID(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Venta> listar() {
        return repositorio.listar();
    }

    public Venta crearVenta(Pedido pedido, MetodoPago metodo) {
        if (pedido == null || metodo == null) {
            throw new IllegalArgumentException("Pedido y método de pago son obligatorios.");
        }
        if (!pedido.estaConfirmado()) {
            throw new IllegalArgumentException("El pedido no está confirmado.");
        }

        // 1. Envolvemos el pedido en el componente base
        patrones.decorator.Calculable calculo = new patrones.decorator.PedidoBase(pedido);

        // 2. Lo decoramos con el descuento según el método de pago
        calculo = new patrones.decorator.DescuentoDecorator(calculo, metodo.getDescuento());

        // 3. Calculamos los totales
        double opv = pedido.calcularTotal();
        double totalConDescuento = calculo.calcularTotal();
        double descuentoAplicado = opv - totalConDescuento;

        // El IGV (18%) se suma sobre el total ya descontado
        double igv = totalConDescuento * IGV;
        double totalFinal = totalConDescuento + igv;

        return new Venta(0, pedido, metodo, opv, descuentoAplicado, igv, totalFinal);
    }

    private double calcularOPV(Pedido pedido) {
        double opv = 0;
        for (DetallePedido dp : pedido.getDetalles()) {
            opv += dp.calcularSubtotal();
        }
        return opv;
    }

    private int siguienteId() {
        return repositorio.listar().size() + 1;
    }
}

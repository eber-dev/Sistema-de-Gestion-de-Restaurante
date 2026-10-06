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
        if (repositorio.buscarPorId(venta.getIdVenta()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una venta con ID " + venta.getIdVenta());
        }
        repositorio.guardar(venta);
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

        double opv = calcularOPV(pedido);
        double descuento = opv * metodo.getDescuento();
        double igv = (opv - descuento) * IGV;
        double total = (opv - descuento) + igv;

        return new Venta(siguienteId(), pedido, metodo, opv, descuento, igv, total);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.controlador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import restaurante.app.Aplicacion;
import restaurante.modelo.MetodoPago;
import restaurante.modelo.Pedido;
import restaurante.modelo.Venta;
import restaurante.vista.FrmVenta;

public class VentaControlador {

    private final FrmVenta vista;

    public VentaControlador(FrmVenta vista) {
        this.vista = vista;
    }

    public void inicializar() {
        cargarPedidos();
        vista.cargarMetodosPago(MetodoPago.values());
        actualizarHistorial();
    }

    public void cargarPedidos() {
        List<Pedido> disponibles = new ArrayList<>();
        for (Pedido p : Aplicacion.pedido().listar()) {
            if (p.estaConfirmado()) {
                disponibles.add(p);
            }
        }
        vista.cargarPedidos(disponibles);
    }

    public void cobrar() {
        Pedido pedido = vista.getPedidoSeleccionado();
        MetodoPago metodo = vista.getMetodoPagoSeleccionado();

        if (pedido == null) {
            vista.mostrarError("Seleccione un pedido confirmado.");
            return;
        }
        if (metodo == null) {
            vista.mostrarError("Seleccione un método de pago.");
            return;
        }

        int ok = JOptionPane.showConfirmDialog(vista,
                "¿Cobrar el pedido #" + pedido.getCodigo()
                + " por S/ " + String.format("%.2f", pedido.calcularTotal())
                + " usando " + metodo + "?",
                "Confirmar cobro", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            String boleta = Aplicacion.ventaFacade().cobrar(pedido, metodo);
            vista.mostrarBoleta(boleta);
            actualizarHistorial();
            cargarPedidos();
            vista.mostrarMensaje("Venta registrada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            vista.mostrarError("Error inesperado: " + ex.getMessage());
        }
    }

    public void actualizarHistorial() {
        vista.mostrarHistorial(Aplicacion.venta().listar());
    }

    public void buscarVenta(int id) {
        Venta v = Aplicacion.venta().buscarPorID(id);
        if (v == null) {
            vista.mostrarError("No existe la venta #" + id);
            return;
        }
        vista.mostrarDetalleVenta(v);
    }
}

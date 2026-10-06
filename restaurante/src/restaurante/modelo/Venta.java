/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.modelo;

import java.time.LocalDateTime;

public class Venta {

    private final int idVenta;
    private final Pedido pedido;
    private final MetodoPago metodoPago;
    private final double opvGravado;
    private final double descuento;
    private final double igv;
    private final double total;
    private final LocalDateTime fecha;

    public Venta(int idVenta, Pedido pedido, MetodoPago metodoPago,
            double opvGravado, double descuento, double igv, double total) {
        this.idVenta = idVenta;
        this.pedido = pedido;
        this.metodoPago = metodoPago;
        this.opvGravado = opvGravado;
        this.descuento = descuento;
        this.igv = igv;
        this.total = total;
        this.fecha = LocalDateTime.now();
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public double getOpvGravado() {
        return opvGravado;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getIgv() {
        return igv;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Venta #" + idVenta + " | Total: S/ " + String.format("%.2f", total)
                + " | Pago: " + metodoPago;
    }
}

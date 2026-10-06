/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.modelo;

public enum MetodoPago {
    EFECTIVO(0.00, "Efectivo"),
    TARJETA(0.01, "Tarjeta"),
    BILLETERA_DIGITAL(0.02, "Billetera Digital");

    private final double descuento;
    private final String etiqueta;

    MetodoPago(double descuento, String etiqueta) {
        this.descuento = descuento;
        this.etiqueta = etiqueta;
    }

    public double getDescuento() {
        return descuento;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.decorator;

public class DescuentoDecorator implements Calculable {

    private final Calculable envuelto;
    private final double porcentajeDescuento;

    public DescuentoDecorator(Calculable envuelto, double porcentajeDescuento) {
        this.envuelto = envuelto;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularTotal() {
        return envuelto.calcularTotal() * (1.0 - porcentajeDescuento);
    }

    @Override
    public String getDescripcion() {
        return envuelto.getDescripcion() + " con Descuento del " + (porcentajeDescuento * 100) + "%";
    }
}

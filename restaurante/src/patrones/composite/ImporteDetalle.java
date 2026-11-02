package patrones.composite;

import patrones.decorator.Calculable;
import restaurante.modelo.DetallePedido;

public final class ImporteDetalle implements Calculable {
    private final DetallePedido detalle;
    public ImporteDetalle(DetallePedido detalle) { this.detalle=detalle; }
    @Override public String getDescripcion() { return detalle.getProducto(); }
    @Override public double calcularTotal() { return detalle.calcularSubtotal(); }
}


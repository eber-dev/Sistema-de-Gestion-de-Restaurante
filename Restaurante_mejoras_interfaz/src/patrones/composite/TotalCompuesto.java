package patrones.composite;

import java.util.ArrayList;
import java.util.List;
import patrones.decorator.Calculable;

/** Compuesto de importes que respeta la interfaz de cálculo ya utilizada. */
public final class TotalCompuesto implements Calculable {
    private final List<Calculable> componentes = new ArrayList<>();
    public void agregar(Calculable componente) { if(componente == null) throw new IllegalArgumentException("Componente vacío"); componentes.add(componente); }
    @Override public String getDescripcion() { return "Total del pedido"; }
    @Override public double calcularTotal() { double total=0; for(Calculable c:componentes) total+=c.calcularTotal(); return total; }
}


import java.math.BigDecimal;
import restaurante.controlador.VentaControlador;
import restaurante.modelo.MetodoPago;
import restaurante.modelo.Pedido;
import restaurante.modelo.Venta;
import restaurante.servicio.VentaServicio;

/** Comprueba las reglas de precio y la misma cifra consultada por la interfaz. */
public class VerificaImportesVenta {
    private static void igual(double actual, String esperado) {
        if (BigDecimal.valueOf(actual).compareTo(new BigDecimal(esperado)) != 0)
            throw new AssertionError("Esperado " + esperado + ", recibido " + actual);
    }
    public static void main(String[] args) {
        Pedido pedido = new Pedido("prueba");
        pedido.setEstado("En preparacion");
        pedido.agregarDetalle(1, "Prueba", 1, 10.00);
        VentaServicio servicio = new VentaServicio(null);
        VentaControlador controlador = new VentaControlador(null);
        String[] esperados = {"11.80", "11.68", "11.56"};
        MetodoPago[] metodos = MetodoPago.values();
        for (int i = 0; i < metodos.length; i++) {
            Venta venta = servicio.crearVenta(pedido, metodos[i]);
            igual(venta.getTotal(), esperados[i]);
            igual(controlador.calcularTotal(pedido, metodos[i]), esperados[i]);
            BigDecimal suma = BigDecimal.valueOf(venta.getOpvGravado())
                    .subtract(BigDecimal.valueOf(venta.getDescuento()))
                    .add(BigDecimal.valueOf(venta.getIgv()));
            if (suma.compareTo(new BigDecimal(esperados[i])) != 0) throw new AssertionError("Desglose incoherente");
            String boleta = new restaurante.servicio.BoletaServicio().generar(venta);
            if (!boleta.contains("SIMULADA") || !boleta.contains(String.format("%.2f", venta.getTotal())))
                throw new AssertionError("Boleta o total incorrecto");
        }
        System.out.println("OK: importes de los tres métodos coinciden con el total mostrado y la boleta");
    }
}

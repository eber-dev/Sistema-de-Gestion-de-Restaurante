/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.servicio;

import java.time.format.DateTimeFormatter;
import restaurante.modelo.DetallePedido;
import restaurante.modelo.Venta;

public class BoletaServicio {

    private static final DateTimeFormatter FORMATO
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public String generar(Venta venta) {
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("      BOLETA DE VENTA ELECTRONICA\n");
        sb.append("==========================================\n");
        sb.append(String.format("N B001-%08d%n", venta.getIdVenta()));
        sb.append("Fecha  : ").append(venta.getFecha().format(FORMATO)).append("\n");
        // getCliente() ya devuelve un String, no un objeto Cliente
        sb.append("Cliente: ").append(venta.getPedido().getCliente()).append("\n");
        sb.append("------------------------------------------\n");
        sb.append(String.format("%-20s %3s %8s %8s%n",
                "DESCRIPCION", "CANT", "P.UNIT", "TOTAL"));
        sb.append("------------------------------------------\n");

        for (DetallePedido dp : venta.getPedido().getDetalles()) {
            // dp.getProducto() devuelve String; dp.getPrecio() y calcularSubtotal() son los métodos reales
            sb.append(String.format("%-20s %3d %8.2f %8.2f%n",
                    dp.getProducto(),
                    dp.getCantidad(),
                    dp.getPrecio(),
                    dp.calcularSubtotal()));
        }

        sb.append("------------------------------------------\n");
        sb.append(String.format("OP. GRAVADA : S/ %8.2f%n", venta.getOpvGravado()));
        sb.append(String.format("DESCUENTO   : S/ %8.2f%n", venta.getDescuento()));
        sb.append(String.format("IGV (18%%)  : S/ %8.2f%n", venta.getIgv()));
        sb.append(String.format("TOTAL       : S/ %8.2f%n", venta.getTotal()));
        sb.append("Pago        : ").append(venta.getMetodoPago()).append("\n");
        sb.append("==========================================\n");
        return sb.toString();
    }
}

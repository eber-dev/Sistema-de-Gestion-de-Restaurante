/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.facade;

import patrones.adapter.PasarelaEfectivo;
import patrones.adapter.PasarelaPago;
import patrones.adapter.VisaAdapter;
import patrones.adapter.YapeAdapter;
import patrones.adapter.externo.VisaApi;
import patrones.adapter.externo.YapeApi;
import restaurante.modelo.MetodoPago;
import restaurante.modelo.Pedido;
import restaurante.modelo.Venta;
import restaurante.servicio.BoletaServicio;
import restaurante.servicio.VentaServicio;

public class VentaFacade {

    private final VentaServicio ventaServicio;
    private final BoletaServicio boletaServicio;

    public VentaFacade(VentaServicio ventaServicio, BoletaServicio boletaServicio) {
        this.ventaServicio = ventaServicio;
        this.boletaServicio = boletaServicio;
    }

    /**
     * Orquesta todo el flujo de una venta.
     */
    public String cobrar(Pedido pedido, MetodoPago metodo) {
        Venta venta = ventaServicio.crearVenta(pedido, metodo);
        PasarelaPago pasarela = elegirPasarela(metodo);

        if (!pasarela.cobrar(venta.getTotal())) {
            throw new IllegalStateException("Pago rechazado por la pasarela.");
        }

        ventaServicio.guardar(venta);
        return boletaServicio.generar(venta);
    }

    private PasarelaPago elegirPasarela(MetodoPago metodo) {
        switch (metodo) {
            case EFECTIVO:
                return new PasarelaEfectivo();
            case TARJETA:
                return new VisaAdapter(new VisaApi());
            case BILLETERA_DIGITAL:
                return new YapeAdapter(new YapeApi());
            default:
                throw new IllegalArgumentException("Método no soportado: " + metodo);
        }
    }
}

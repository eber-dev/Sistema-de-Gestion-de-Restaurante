package patrones.proxy;
import java.sql.Date;
import net.sf.jasperreports.engine.JasperPrint;
import restaurante.reportes.ReporteVentas;
/** Proxy de inicialización diferida; no implementa control de acceso. */
public final class ProxyReporteVentas extends ReporteVentas {
    private ReporteVentas real;
    @Override public JasperPrint generar(Date desde, Date hasta) throws Exception {
        if (real == null) real = new ReporteVentas();
        return real.generar(desde,hasta);
    }
}

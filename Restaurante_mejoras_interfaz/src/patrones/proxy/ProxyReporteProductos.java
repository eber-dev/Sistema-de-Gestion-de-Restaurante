package patrones.proxy;
import java.sql.Date;
import net.sf.jasperreports.engine.JasperPrint;
import restaurante.reportes.ReporteProductos;
public final class ProxyReporteProductos extends ReporteProductos {
    private ReporteProductos real;
    @Override public JasperPrint generar(Date desde, Date hasta)throws Exception {if(real==null)real=new ReporteProductos();return real.generar(desde,hasta);}
}

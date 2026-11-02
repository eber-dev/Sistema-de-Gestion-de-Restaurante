package restaurante.reportes;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import restaurante.config.ConexionDB;
public class ReporteProductos extends ReporteVentas {
    @Override public JasperPrint generar(Date desde, Date hasta) throws Exception {
        if(desde==null || hasta==null || desde.after(hasta))throw new IllegalArgumentException("Revise el periodo de fechas");
        try(InputStream in=getClass().getResourceAsStream("productos.jrxml")) {
            if(in==null)throw new IllegalStateException("No se encontró productos.jrxml");
            JasperReport informe=JasperCompileManager.compileReport(in); Map<String,Object> p=new HashMap<>(); p.put("DESDE",desde);p.put("HASTA",hasta);
            try(Connection c=ConexionDB.getInstancia().conectar()){return JasperFillManager.fillReport(informe,p,c);}
        }
    }
}

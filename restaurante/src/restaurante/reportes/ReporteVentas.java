package restaurante.reportes;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import restaurante.config.ConexionDB;

/** Genera el reporte desde el JRXML empaquetado y una conexión de corta duración. */
public class ReporteVentas {
    public JasperPrint generar(Date desde, Date hasta) throws Exception {
        if (desde == null || hasta == null || desde.after(hasta)) throw new IllegalArgumentException("Revise el periodo de fechas");
        try (InputStream in = getClass().getResourceAsStream("ventas.jrxml")) {
            if (in == null) throw new IllegalStateException("No se encontró ventas.jrxml");
            JasperReport plantilla = JasperCompileManager.compileReport(in);
            Map<String,Object> parametros = new HashMap<>();
            parametros.put("DESDE", desde); parametros.put("HASTA", hasta);
            try (Connection c = ConexionDB.getInstancia().conectar()) { return JasperFillManager.fillReport(plantilla, parametros, c); }
        }
    }
}

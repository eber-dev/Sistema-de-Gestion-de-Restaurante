package patrones.bridge;
import net.sf.jasperreports.engine.JasperPrint;
public interface SalidaInforme { void presentar(JasperPrint informe) throws Exception; }

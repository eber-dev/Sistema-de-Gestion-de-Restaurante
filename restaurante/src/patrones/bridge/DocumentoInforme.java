package patrones.bridge;
import net.sf.jasperreports.engine.JasperPrint;
public final class DocumentoInforme {
    private final JasperPrint informe;
    private final SalidaInforme salida;
    public DocumentoInforme(JasperPrint informe, SalidaInforme salida) { if(informe==null || salida==null) throw new IllegalArgumentException("Informe o salida vacíos"); this.informe=informe; this.salida=salida; }
    public void presentar() throws Exception { salida.presentar(informe); }
}

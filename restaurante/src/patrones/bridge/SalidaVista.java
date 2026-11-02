package patrones.bridge;
import javax.swing.JTabbedPane;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
public final class SalidaVista implements SalidaInforme {
    private final JTabbedPane destino;
    public SalidaVista(JTabbedPane destino){this.destino=destino;}
    @Override public void presentar(JasperPrint informe){destino.addTab("Reporte / exportar PDF",new JRViewer(informe));}
}

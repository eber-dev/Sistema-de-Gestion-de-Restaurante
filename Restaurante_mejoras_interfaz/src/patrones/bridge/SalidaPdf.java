package patrones.bridge;
import java.io.File;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
public final class SalidaPdf implements SalidaInforme {
    private final File archivo;
    public SalidaPdf(File archivo){this.archivo=archivo;}
    @Override public void presentar(JasperPrint informe) throws Exception {JasperExportManager.exportReportToPdfFile(informe,archivo.getAbsolutePath());}
}

package restaurante.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import net.sf.jasperreports.swing.JRViewer;
import org.jfree.chart.ChartPanel;
import restaurante.reportes.GraficoVentas;
import restaurante.reportes.ReporteVentas;

public class FrmReportes extends JFrame {
    private final JTextField desde = new JTextField(LocalDate.now().minusDays(30).toString(),10);
    private final JTextField hasta = new JTextField(LocalDate.now().toString(),10);
    private final JTabbedPane pestanas = new JTabbedPane();
    private final ReporteVentas servicio = new patrones.proxy.ProxyReporteVentas();
    private final ReporteVentas productos = new patrones.proxy.ProxyReporteProductos();
    private final javax.swing.JComboBox<String> tipo = new javax.swing.JComboBox<>(new String[]{"Ventas por fecha", "Productos vendidos"});
    private net.sf.jasperreports.engine.JasperPrint ultimoReporte;
    public FrmReportes() {
        super("Reportes del restaurante"); setDefaultCloseOperation(DISPOSE_ON_CLOSE); setSize(1000,700); setLocationRelativeTo(null);
        JPanel filtros=new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.add(new JLabel("Reporte:")); filtros.add(tipo);
        filtros.add(new JLabel("Desde (AAAA-MM-DD):")); filtros.add(desde); filtros.add(new JLabel("Hasta:")); filtros.add(hasta);
        JButton generar=new JButton("Generar reporte"); generar.addActionListener(e->generar()); filtros.add(generar);
        JButton pdf=new JButton("Guardar PDF"); pdf.addActionListener(e->guardarPdf()); filtros.add(pdf);
        add(filtros,BorderLayout.NORTH); add(pestanas,BorderLayout.CENTER);
    }
    private void guardarPdf() {
        if(ultimoReporte==null) { JOptionPane.showMessageDialog(this,"Genere primero el reporte."); return; }
        javax.swing.JFileChooser selector=new javax.swing.JFileChooser(); selector.setDialogTitle("Guardar reporte en PDF"); selector.setSelectedFile(new java.io.File("ventas.pdf"));
        if(selector.showSaveDialog(this)!=javax.swing.JFileChooser.APPROVE_OPTION) return;
        java.io.File archivo=selector.getSelectedFile(); if(!archivo.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".pdf")) archivo=new java.io.File(archivo.getParentFile(),archivo.getName()+".pdf");
        if(archivo.exists() && JOptionPane.showConfirmDialog(this,"El archivo ya existe. ¿Reemplazarlo?","Guardar PDF",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;
        try { new patrones.bridge.DocumentoInforme(ultimoReporte,new patrones.bridge.SalidaPdf(archivo)).presentar(); JOptionPane.showMessageDialog(this,"PDF guardado correctamente."); } catch(Exception ex) { JOptionPane.showMessageDialog(this,"No se pudo guardar el PDF: "+ex.getMessage()); }
    }
    private void generar() {
        try {
            Date inicio=Date.valueOf(LocalDate.parse(desde.getText().trim())); Date fin=Date.valueOf(LocalDate.parse(hasta.getText().trim()));
            if(inicio.after(fin)) throw new IllegalArgumentException("La fecha inicial debe ser anterior o igual a la final.");
            net.sf.jasperreports.engine.JasperPrint reporte=(tipo.getSelectedIndex()==0 ? servicio : productos).generar(inicio,fin);
            org.jfree.chart.JFreeChart grafico=tipo.getSelectedIndex()==0 ? new GraficoVentas().generar(inicio,fin) : new restaurante.reportes.GraficoProductos().generar(inicio,fin);
            pestanas.removeAll(); ultimoReporte=reporte; new patrones.bridge.DocumentoInforme(reporte,new patrones.bridge.SalidaVista(pestanas)).presentar();
            JPanel panelGrafico = new JPanel(new BorderLayout(8,8));
            JLabel explicacion = new JLabel(tipo.getSelectedIndex()==0 ? "Cada barra muestra el importe total cobrado por método de pago." : "Cada barra muestra cuántas unidades se vendieron de cada producto.");
            explicacion.setBorder(javax.swing.BorderFactory.createEmptyBorder(10,12,4,12));
            panelGrafico.add(explicacion,BorderLayout.NORTH);
            panelGrafico.add(new ChartPanel(grafico),BorderLayout.CENTER);
            pestanas.addTab("Gráfico",panelGrafico);
        } catch(Exception ex) { JOptionPane.showMessageDialog(this,"No se pudo generar el reporte: "+ex.getMessage(),"Reportes",JOptionPane.ERROR_MESSAGE); }
    }
}

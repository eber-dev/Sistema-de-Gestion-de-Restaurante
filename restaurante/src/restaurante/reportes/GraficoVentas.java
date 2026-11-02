package restaurante.reportes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import restaurante.config.ConexionDB;

public class GraficoVentas {
    public JFreeChart generar(Date desde, Date hasta) throws Exception {
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        String sql="SELECT metodo_pago, COALESCE(SUM(monto),0) total FROM pagos WHERE estado='Pagado' AND DATE(fecha_pago) BETWEEN ? AND ? GROUP BY metodo_pago ORDER BY metodo_pago";
        try(Connection c=ConexionDB.getInstancia().conectar(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setDate(1,desde); p.setDate(2,hasta);
            try(ResultSet r=p.executeQuery()) { while(r.next()) datos.addValue(r.getBigDecimal("total"), "Ventas", r.getString("metodo_pago")); }
        }
        JFreeChart grafico = ChartFactory.createBarChart("Ingresos por método de pago", "Método de pago", "Importe cobrado (S/)", datos, PlotOrientation.VERTICAL, false, true, false);
        grafico.addSubtitle(new org.jfree.chart.title.TextTitle("Ventas pagadas del " + desde + " al " + hasta));
        grafico.setBackgroundPaint(java.awt.Color.WHITE);
        org.jfree.chart.plot.CategoryPlot plot = grafico.getCategoryPlot();
        plot.setBackgroundPaint(new java.awt.Color(248, 250, 252));
        plot.getRenderer().setSeriesPaint(0, new java.awt.Color(37, 99, 235));
        plot.getRenderer().setBaseItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
        plot.getRenderer().setBaseItemLabelsVisible(true);
        plot.setNoDataMessage("No hay ventas pagadas en el periodo seleccionado.");
        return grafico;
    }
}

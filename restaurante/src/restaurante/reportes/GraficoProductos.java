package restaurante.reportes;
import java.sql.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import restaurante.config.ConexionDB;
public class GraficoProductos {
    public JFreeChart generar(Date desde, Date hasta)throws Exception {
        DefaultCategoryDataset datos=new DefaultCategoryDataset();
        String sql="SELECT pr.id_producto, pr.nombre, SUM(d.cantidad) unidades FROM detalle_pedido d JOIN productos pr ON pr.id_producto=d.id_producto WHERE EXISTS (SELECT 1 FROM pagos p WHERE p.id_pedido=d.id_pedido AND p.estado='Pagado' AND DATE(p.fecha_pago) BETWEEN ? AND ?) GROUP BY pr.id_producto,pr.nombre ORDER BY unidades DESC,pr.nombre LIMIT 10";
        try(Connection c=ConexionDB.getInstancia().conectar();PreparedStatement ps=c.prepareStatement(sql)){ps.setDate(1,desde);ps.setDate(2,hasta);try(ResultSet r=ps.executeQuery()){while(r.next())datos.addValue(r.getBigDecimal("unidades"),"Unidades",r.getString("nombre") + " (ID " + r.getInt("id_producto") + ")");}}
        JFreeChart grafico=ChartFactory.createBarChart("Productos más vendidos", "Producto", "Cantidad vendida (unidades)",datos,PlotOrientation.HORIZONTAL,false,true,false);
        grafico.addSubtitle(new org.jfree.chart.title.TextTitle("Hasta 10 productos · Ventas pagadas del " + desde + " al " + hasta));
        grafico.setBackgroundPaint(java.awt.Color.WHITE);
        org.jfree.chart.plot.CategoryPlot plot=grafico.getCategoryPlot();
        plot.setBackgroundPaint(new java.awt.Color(248,250,252));
        plot.getRenderer().setSeriesPaint(0,new java.awt.Color(22,163,74));
        plot.getRenderer().setBaseItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
        plot.getRenderer().setBaseItemLabelsVisible(true);
        plot.setNoDataMessage("No hay productos vendidos en el periodo seleccionado.");
        return grafico;
    }
}

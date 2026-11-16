package restaurante.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import restaurante.config.ConexionDB;

/** Consulta de solo lectura de las últimas operaciones registradas. */
public class FrmAuditoria extends JFrame {
    private final JTextArea detalleSeleccionado = new JTextArea(3, 40);
    private final DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"Fecha y hora", "Entidad", "ID", "Acción", "Detalle", "Usuario MySQL"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    public FrmAuditoria() {
        super("Auditoría de operaciones");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 560);
        setLocationRelativeTo(null);
        JPanel contenido = new JPanel(new BorderLayout(0, 12));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        contenido.setBackground(Tema.CREMA);
        JLabel titulo = new JLabel("Auditoría de operaciones — últimos 500 registros");
        titulo.setFont(new Font(Tema.FUENTE_TITULO, Font.BOLD, 20));
        contenido.add(titulo, BorderLayout.NORTH);
        JTable tabla = new JTable(modelo);
        tabla.setAutoCreateRowSorter(true);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(320);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                detalleSeleccionado.setText(fila < 0 ? "Seleccione una operación para ver su detalle."
                    : String.valueOf(modelo.getValueAt(tabla.convertRowIndexToModel(fila), 4)));
                detalleSeleccionado.setCaretPosition(0);
            }
        });
        contenido.add(new JScrollPane(tabla), BorderLayout.CENTER);
        JPanel inferior = new JPanel(new BorderLayout(0, 8));
        inferior.setOpaque(false);
        detalleSeleccionado.setEditable(false);
        detalleSeleccionado.setLineWrap(true);
        detalleSeleccionado.setWrapStyleWord(true);
        detalleSeleccionado.setText("Seleccione una operación para ver su detalle.");
        detalleSeleccionado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JPanel detallePanel = new JPanel(new BorderLayout(0, 4));
        detallePanel.setOpaque(false);
        detallePanel.add(new JLabel("Detalle de la operación seleccionada"), BorderLayout.NORTH);
        detallePanel.add(new JScrollPane(detalleSeleccionado), BorderLayout.CENTER);
        inferior.add(detallePanel, BorderLayout.CENTER);
        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.add(new JLabel("El usuario mostrado corresponde a la cuenta de MySQL."), BorderLayout.CENTER);
        JButton actualizar = new JButton("Actualizar");
        Tema.estilizarBoton(actualizar);
        actualizar.addActionListener(e -> cargar());
        pie.add(actualizar, BorderLayout.EAST);
        inferior.add(pie, BorderLayout.SOUTH);
        contenido.add(inferior, BorderLayout.SOUTH);
        setContentPane(contenido);
        cargar();
    }
    private void cargar() {
        String sql = "SELECT fecha_hora, entidad, id_registro, accion, detalle, usuario_bd FROM auditoria ORDER BY id_auditoria DESC LIMIT 500";
        try (Connection cn = ConexionDB.getInstancia().conectar();
             PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            modelo.setRowCount(0);
            while (rs.next()) {
                String detalle = rs.getString(5);
                if (detalle == null || detalle.isBlank()) detalle = "Sin detalle registrado";
                modelo.addRow(new Object[]{rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4), detalle, rs.getString(6)});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo consultar la auditoría. Verifique la conexión y la instalación de la tabla.", "Auditoría", JOptionPane.ERROR_MESSAGE);
        }
    }
}

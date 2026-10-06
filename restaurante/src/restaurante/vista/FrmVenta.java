/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import restaurante.app.Aplicacion;
import restaurante.controlador.VentaControlador;
import restaurante.modelo.DetallePedido;
import restaurante.modelo.MetodoPago;
import restaurante.modelo.Pedido;
import restaurante.modelo.Venta;

public class FrmVenta extends JFrame {

    private static final Color FONDO = new Color(240, 242, 245);
    private static final Color OSCURO = new Color(31, 41, 55);
    private static final Color AZUL = new Color(37, 99, 235);
    private static final Color GRIS = new Color(226, 232, 240);

    private final JComboBox<Pedido> cboPedidos = new JComboBox<>();
    private final JComboBox<MetodoPago> cboMetodoPago = new JComboBox<>();
    private final JTextArea txtDetalle = new JTextArea();
    private final JTextField txtBuscar = new JTextField(8);
    private final JLabel lblTotal = new JLabel("S/ 0.00");
    private final JTable tblHistorial;
    private final DefaultTableModel modeloTabla;
    private final VentaControlador controlador;

    public FrmVenta() {
        super("Restaurante | Gestión de Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 680);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(18, 18));

        modeloTabla = new DefaultTableModel(
                new Object[]{"Venta", "Pedido", "Cliente", "Pago", "Total", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblHistorial = new JTable(modeloTabla);
        tblHistorial.setRowHeight(26);
        tblHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);

        controlador = new VentaControlador(this);
        configurarEventos();
        controlador.inicializar();
    }

    public static void main(String[] args) {
        Aplicacion.inicializar();
        javax.swing.SwingUtilities.invokeLater(() -> new FrmVenta().setVisible(true));
    }

    private JPanel crearEncabezado() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel titulo = new JLabel("Gestión de Ventas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel sub = new JLabel("Registra cobros y consulta el historial");
        sub.setForeground(new Color(203, 213, 225));
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel txt = new JPanel(new GridLayout(2, 1));
        txt.setOpaque(false);
        txt.add(titulo);
        txt.add(sub);
        p.add(txt, BorderLayout.WEST);
        return p;
    }

    private JPanel crearContenido() {
        JPanel cont = new JPanel(new BorderLayout(18, 0));
        cont.setOpaque(false);
        cont.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        cont.add(crearPanelIzquierdo(), BorderLayout.WEST);
        cont.add(crearPanelDerecho(), BorderLayout.CENTER);
        return cont;
    }

    private JPanel crearPanelIzquierdo() {
        JPanel p = new JPanel(new BorderLayout(10, 12));
        p.setBackground(Color.WHITE);
        p.setPreferredSize(new Dimension(390, 450));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel t = new JLabel("Registrar venta");
        t.setFont(new Font("Segoe UI", Font.BOLD, 17));

        JPanel form = new JPanel(new GridLayout(2, 1, 0, 8));
        form.setOpaque(false);
        form.add(etiqueta("Pedido confirmado", cboPedidos));
        form.add(etiqueta("Método de pago", cboMetodoPago));

        txtDetalle.setEditable(false);
        txtDetalle.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);
        txtDetalle.setText("Selecciona un pedido para ver su detalle.");

        JScrollPane scroll = new JScrollPane(txtDetalle);
        scroll.setBorder(BorderFactory.createTitledBorder("Detalle del pedido"));

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setOpaque(false);
        JLabel lbl = new JLabel("TOTAL:");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTotal.setForeground(AZUL);
        totalPanel.add(lbl, BorderLayout.WEST);
        totalPanel.add(lblTotal, BorderLayout.EAST);

        JPanel sur = new JPanel(new BorderLayout(0, 10));
        sur.setOpaque(false);
        sur.add(scroll, BorderLayout.CENTER);
        sur.add(totalPanel, BorderLayout.SOUTH);

        p.add(t, BorderLayout.NORTH);
        p.add(form, BorderLayout.CENTER);
        p.add(sur, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearPanelDerecho() {
        JPanel p = new JPanel(new BorderLayout(8, 12));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel t = new JLabel("Historial de ventas");
        t.setFont(new Font("Segoe UI", Font.BOLD, 17));

        JPanel busqueda = new JPanel();
        busqueda.setOpaque(false);
        busqueda.add(new JLabel("Código:"));
        busqueda.add(txtBuscar);
        JButton btnBuscar = boton("Buscar", new Color(71, 85, 105));
        btnBuscar.addActionListener(e -> buscar());
        busqueda.add(btnBuscar);

        JPanel norte = new JPanel(new BorderLayout(0, 10));
        norte.setOpaque(false);
        norte.add(t, BorderLayout.NORTH);
        norte.add(busqueda, BorderLayout.SOUTH);

        p.add(norte, BorderLayout.NORTH);
        p.add(new JScrollPane(tblHistorial), BorderLayout.CENTER);
        return p;
    }

    private JPanel crearPie() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(0, 20, 18, 20));

        JLabel nota = new JLabel("Selecciona un pedido y método de pago para cobrar.");
        nota.setForeground(new Color(100, 116, 139));

        JButton btn = boton("COBRAR Y GENERAR BOLETA", AZUL);
        btn.addActionListener(e -> controlador.cobrar());

        p.add(nota, BorderLayout.WEST);
        p.add(btn, BorderLayout.EAST);
        return p;
    }

    private JPanel etiqueta(String texto, java.awt.Component c) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        p.add(l, BorderLayout.NORTH);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private JButton boton(String txt, Color color) {
        JButton b = new JButton(txt);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        return b;
    }

    private void configurarEventos() {
        cboPedidos.addActionListener(e -> mostrarPedidoSeleccionado());
    }

    // ----------- ADAPTADO A TU Pedido/DetallePedido -----------
    private void mostrarPedidoSeleccionado() {
        Pedido pedido = getPedidoSeleccionado();
        if (pedido == null) {
            txtDetalle.setText("Sin pedidos confirmados disponibles.");
            lblTotal.setText("S/ 0.00");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(pedido.getCodigo()).append("\n");
        sb.append("Cliente : ").append(pedido.getCliente()).append("\n");
        sb.append("Sucursal: ").append(pedido.getSucursal()).append("\n");
        sb.append("Canal   : ").append(pedido.getCanal()).append("\n");
        sb.append("Estado  : ").append(pedido.getEstado()).append("\n\n");

        for (DetallePedido d : pedido.getDetalles()) {
            sb.append("- ").append(d.getProducto()) // String
                    .append(" x ").append(d.getCantidad())
                    .append(" = S/ ").append(String.format("%.2f", d.calcularSubtotal()))
                    .append("\n");
        }
        txtDetalle.setText(sb.toString());
        lblTotal.setText(String.format("S/ %.2f", pedido.calcularTotal()));
    }

    private void buscar() {
        try {
            int id = Integer.parseInt(txtBuscar.getText().trim());
            controlador.buscarVenta(id);
        } catch (NumberFormatException ex) {
            mostrarError("Ingresa un código de venta válido.");
        }
    }

    public Pedido getPedidoSeleccionado() {
        return (Pedido) cboPedidos.getSelectedItem();
    }

    public MetodoPago getMetodoPagoSeleccionado() {
        return (MetodoPago) cboMetodoPago.getSelectedItem();
    }

    public void cargarPedidos(List<Pedido> pedidos) {
        DefaultComboBoxModel<Pedido> m = new DefaultComboBoxModel<>();
        for (Pedido p : pedidos) {
            m.addElement(p);
        }
        cboPedidos.setModel(m);
        mostrarPedidoSeleccionado();
    }

    public void cargarMetodosPago(MetodoPago[] metodos) {
        cboMetodoPago.setModel(new DefaultComboBoxModel<>(metodos));
    }

    public void mostrarHistorial(List<Venta> ventas) {
        modeloTabla.setRowCount(0);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Venta v : ventas) {
            modeloTabla.addRow(new Object[]{
                v.getIdVenta(),
                v.getPedido().getCodigo(),
                v.getPedido().getCliente(),
                v.getMetodoPago(),
                String.format("S/ %.2f", v.getTotal()),
                v.getFecha().format(f)
            });
        }
    }

    public void mostrarBoleta(String boleta) {
        JTextArea area = new JTextArea(boleta, 18, 46);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(area),
                "Boleta generada", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarDetalleVenta(Venta v) {
        String txt = "Venta #" + v.getIdVenta()
                + "\nPedido : #" + v.getPedido().getCodigo()
                + "\nCliente: " + v.getPedido().getCliente()
                + "\nPago   : " + v.getMetodoPago()
                + "\nOPV    : S/ " + String.format("%.2f", v.getOpvGravado())
                + "\nDesc.  : S/ " + String.format("%.2f", v.getDescuento())
                + "\nIGV    : S/ " + String.format("%.2f", v.getIgv())
                + "\nTOTAL  : S/ " + String.format("%.2f", v.getTotal())
                + "\nFecha  : " + v.getFecha();
        JOptionPane.showMessageDialog(this, txt, "Detalle de venta",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarMensaje(String m) {
        setTitle("Restaurante | Ventas - " + m);
    }

    public void mostrarError(String m) {
        JOptionPane.showMessageDialog(this, m, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}

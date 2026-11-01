/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import restaurante.app.Aplicacion;
import restaurante.controlador.PedidoControlador;
import restaurante.modelo.Cliente;
import restaurante.modelo.DetallePedido;
import restaurante.modelo.Empleado;
import restaurante.modelo.Mesa;
import restaurante.modelo.Pedido;
import restaurante.modelo.Producto;

public class FrmPedidos extends JFrame {

    // ====== Paleta ======
    private static final Color OSCURO = new Color(31, 45, 55);
    private static final Color FONDO = new Color(245, 247, 250);
    private static final Color VERDE = new Color(0, 153, 0);
    private static final Color NARANJA = new Color(204, 102, 0);
    private static final Color AZUL_OSC = new Color(0, 0, 51);
    private static final Color ROJO = new Color(204, 0, 51);
    private static final Color GRIS = new Color(180, 180, 180);

    // ====== Componentes ======
    private JComboBox<Cliente> cbxCliente;
    private JComboBox<Empleado> cbxEmpleado;
    private JComboBox<Mesa> cbxMesa;
    private JComboBox<String> cbxTipoPedido;
    private JComboBox<Producto> cbxProducto;

    private JTextField txtCodigo;
    private JTextField txtEstado;
    private JTextField txtCantidad;
    private JTextField txtPrecio;

    private JTable tblDetalles;
    private JTable tblPedidos;
    private DefaultTableModel modeloDetalles;
    private DefaultTableModel modeloPedidos;

    private JButton btnCrearPedido;
    private JButton btnLimpiar;
    private JButton btnAgregar;
    private JButton btnConfirmar;
    private JButton btnClonar;
    private JButton btnEliminar;
    private JButton btnIrVentas;

    // ====== Estado ======
    private final PedidoControlador controlador = new PedidoControlador();
    private Pedido pedidoActual;

    // ====== Constructor ======
    public FrmPedidos() {
        setTitle("Pedidos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1120, 780);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(0, 0));

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCentro(), BorderLayout.CENTER);
        add(crearSur(), BorderLayout.SOUTH);

        modeloDetalles = (DefaultTableModel) tblDetalles.getModel();
        modeloPedidos = (DefaultTableModel) tblPedidos.getModel();

        txtEstado.setEditable(false);
        txtCodigo.setEditable(false);
        txtPrecio.setEditable(false);

        cargarCombos();
        actualizarTablaPedidos();
    }

    // ====== UI: Header ======
    private JPanel crearHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        p.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("Restaurante");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel sub = new JLabel("Gestión de pedidos");
        sub.setForeground(new Color(200, 210, 220));
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(sub);

        p.add(textos, BorderLayout.WEST);
        return p;
    }

    // ====== UI: Centro (datos + detalle) ======
    private JPanel crearCentro() {
        JPanel centro = new JPanel(new GridLayout(1, 2, 14, 14));
        centro.setBackground(FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        centro.add(crearPanelDatos());
        centro.add(crearPanelDetalle());
        return centro;
    }

    private JPanel crearPanelDatos() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));

        JLabel titulo = new JLabel("DATOS DEL PEDIDO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        txtCodigo = new JTextField();
        cbxCliente = new JComboBox<>();
        cbxEmpleado = new JComboBox<>();
        cbxMesa = new JComboBox<>();
        cbxTipoPedido = new JComboBox<>();
        txtEstado = new JTextField();

        int fila = 0;
        agregarFila(form, fila++, "Código:", txtCodigo);
        agregarFila(form, fila++, "Cliente:", cbxCliente);
        agregarFila(form, fila++, "Empleado:", cbxEmpleado);
        agregarFila(form, fila++, "Mesa:", cbxMesa);
        agregarFila(form, fila++, "Tipo:", cbxTipoPedido);
        agregarFila(form, fila++, "Estado:", txtEstado);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botones.setOpaque(false);
        btnCrearPedido = boton("Crear Pedido", VERDE);
        btnLimpiar = boton("Limpiar", NARANJA);
        btnCrearPedido.addActionListener(e -> onCrearPedido());
        btnLimpiar.addActionListener(e -> limpiarFormularioPedido());
        botones.add(btnCrearPedido);
        botones.add(btnLimpiar);

        p.add(titulo, BorderLayout.NORTH);
        p.add(form, BorderLayout.CENTER);
        p.add(botones, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearPanelDetalle() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));

        JLabel titulo = new JLabel("DETALLE DEL PEDIDO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // --- Formulario (Producto, Cantidad, Precio) ---
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        cbxProducto = new JComboBox<>();
        txtCantidad = new JTextField();
        txtPrecio = new JTextField();

        int fila = 0;
        agregarFila(form, fila++, "Producto:", cbxProducto);
        agregarFila(form, fila++, "Cantidad:", txtCantidad);
        agregarFila(form, fila++, "Precio:", txtPrecio);

        btnAgregar = boton("Agregar Producto", new Color(70, 130, 180));
        btnAgregar.addActionListener(e -> onAgregar());

        JPanel botonera = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        botonera.setOpaque(false);
        botonera.add(btnAgregar);

        JPanel superior = new JPanel(new BorderLayout(0, 10));
        superior.setOpaque(false);
        superior.add(form, BorderLayout.CENTER);
        superior.add(botonera, BorderLayout.SOUTH);

        // --- Tabla de detalles ---
        tblDetalles = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Producto", "Cantidad", "Precio", "Subtotal"}) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });
        tblDetalles.setRowHeight(24);
        tblDetalles.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        JScrollPane scroll = new JScrollPane(tblDetalles);

        // --- Ensamblamos todo ---
        JPanel norte = new JPanel(new BorderLayout(0, 10));
        norte.setOpaque(false);
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(superior, BorderLayout.CENTER);

        p.add(norte, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER); // La tabla ocupa el centro, no el sur

        return p;
    }

    // ====== UI: Sur (acciones + tabla pedidos) ======
    private JPanel crearSur() {
        JPanel sur = new JPanel(new BorderLayout(0, 10));
        sur.setBackground(FONDO);
        sur.setBorder(BorderFactory.createEmptyBorder(0, 14, 14, 14));

        // Acciones
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        acciones.setBackground(Color.WHITE);
        acciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        JLabel lblAcc = new JLabel("ACCIONES:");
        lblAcc.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnConfirmar = boton("Confirmar Pedido", new Color(0, 102, 51));
        btnClonar = boton("Clonar Pedido", AZUL_OSC);
        btnEliminar = boton("Eliminar Pedido", ROJO);
        btnIrVentas = boton("Ir a Ventas", new Color(37, 99, 235));

        btnConfirmar.addActionListener(e -> onConfirmar());
        btnClonar.addActionListener(e -> onClonar());
        btnEliminar.addActionListener(e -> onEliminar());
        btnIrVentas.addActionListener(e -> onIrVentas());

        acciones.add(lblAcc);
        acciones.add(btnConfirmar);
        acciones.add(btnClonar);
        acciones.add(btnEliminar);
        acciones.add(btnIrVentas);

        // Tabla pedidos
        JPanel pedidos = new JPanel(new BorderLayout(0, 6));
        pedidos.setBackground(Color.WHITE);
        pedidos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lblPed = new JLabel("PEDIDOS REGISTRADOS");
        lblPed.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tblPedidos = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Cliente", "Sucursal", "Canal", "Estado", "Total"}) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });
        tblPedidos.setRowHeight(24);
        tblPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPed = new JScrollPane(tblPedidos);
        scrollPed.setPreferredSize(new Dimension(0, 180));

        pedidos.add(lblPed, BorderLayout.NORTH);
        pedidos.add(scrollPed, BorderLayout.CENTER);

        sur.add(acciones, BorderLayout.NORTH);
        sur.add(pedidos, BorderLayout.CENTER);
        return sur;
    }

    // ====== Helper de layout ======
    private void agregarFila(JPanel panel, int fila, String etiqueta, java.awt.Component campo) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(4, 4, 4, 8);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 4);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(campo, gbc);
    }

    private JButton boton(String txt, Color color) {
        JButton b = new JButton(txt);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return b;
    }

    // ====== Carga de combos ======
    private void cargarCombos() {
        cbxCliente.removeAllItems();
        for (Cliente c : Aplicacion.cliente().listar()) {
            cbxCliente.addItem(c);
        }

        cbxEmpleado.removeAllItems();
        for (Empleado e : Aplicacion.empleados().listar()) {
            cbxEmpleado.addItem(e);
        }

        cbxMesa.removeAllItems();
        for (Mesa m : Aplicacion.mesas().listar()) {
            cbxMesa.addItem(m);
        }

        cbxProducto.removeAllItems();
        for (Producto p : Aplicacion.producto().listarDisponibles()) {
            cbxProducto.addItem(p);
        }

        cbxTipoPedido.removeAllItems();
        cbxTipoPedido.addItem("Mesa");
        cbxTipoPedido.addItem("Para llevar");
        cbxTipoPedido.addItem("Delivery");

        cbxProducto.addActionListener(e -> actualizarPrecio());
        actualizarPrecio();
    }

    private void actualizarPrecio() {
        Producto p = (Producto) cbxProducto.getSelectedItem();
        if (p != null) {
            txtPrecio.setText(String.format("%.2f", p.getPrecio()));
        } else {
            txtPrecio.setText("");
        }
    }

    // ====== Eventos ======
    private void onCrearPedido() {
        Cliente cli = (Cliente) cbxCliente.getSelectedItem();
        Empleado emp = (Empleado) cbxEmpleado.getSelectedItem();
        Mesa mesa = (Mesa) cbxMesa.getSelectedItem();
        String tipo = (String) cbxTipoPedido.getSelectedItem();

        if (cli == null || emp == null || tipo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione cliente y empleado.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            patrones.abstractfactory.AbstractFactoryPedido fabrica
                    = "Delivery".equalsIgnoreCase(tipo)
                    ? new patrones.abstractfactory.FactoryPedidoVirtual()
                    : new patrones.abstractfactory.FactoryPedidoPresencial();

            int idMesa = "Mesa".equalsIgnoreCase(tipo) && mesa != null ? mesa.getId() : 0;

            pedidoActual = controlador.crearPedido(
                    fabrica,
                    cli.getId(),
                    cli.getNombre() + " " + cli.getApellido(),
                    emp.getId(),
                    idMesa,
                    tipo);

            txtCodigo.setText(pedidoActual.getCodigo());
            txtEstado.setText(pedidoActual.getEstado());
            JOptionPane.showMessageDialog(this, "Pedido creado. Ahora agregue productos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAgregar() {
        if (pedidoActual == null) {
            JOptionPane.showMessageDialog(this, "Primero cree un pedido.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Producto prod = (Producto) cbxProducto.getSelectedItem();
        String cantStr = txtCantidad.getText().trim();

        if (prod == null || cantStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione producto y cantidad.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantStr);
            pedidoActual.agregarDetalle(prod.getId(), prod.getNombre(),
                    cantidad, prod.getPrecio());
            actualizarTablaDetalles();
            txtCantidad.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onConfirmar() {
        try {
            controlador.confirmarPedido(pedidoActual);
            actualizarTablaPedidos();
            JOptionPane.showMessageDialog(this, "Pedido confirmado y guardado en MySQL.");
            limpiarFormularioPedido();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onClonar() {
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para clonar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String codigoSel = (String) tblPedidos.getValueAt(fila, 0);
        Pedido original = null;
        for (Pedido p : controlador.listarPedidos()) {
            if (p.getCodigo().equals(codigoSel)) {
                original = p;
                break;
            }
        }
        if (original != null) {
            pedidoActual = controlador.clonarPedido(original);
            txtCodigo.setText(pedidoActual.getCodigo());
            txtEstado.setText(pedidoActual.getEstado());
            actualizarTablaDetalles();
            JOptionPane.showMessageDialog(this,
                    "Pedido clonado. Modifíquelo y confirme cuando esté listo.");
        }
    }

    private void onEliminar() {
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el pedido seleccionado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }

        String codigo = (String) tblPedidos.getValueAt(fila, 0);
        if (controlador.eliminarPedido(codigo)) {
            actualizarTablaPedidos();
            JOptionPane.showMessageDialog(this, "Pedido eliminado.");
        }
    }

    private void onIrVentas() {
        new FrmVenta(this).setVisible(true);
        this.setVisible(false);
    }

    // ====== Tablas y limpieza ======
    private void actualizarTablaDetalles() {
        modeloDetalles.setRowCount(0);
        if (pedidoActual != null) {
            for (DetallePedido d : pedidoActual.getDetalles()) {
                modeloDetalles.addRow(new Object[]{
                    d.getProducto(),
                    d.getCantidad(),
                    d.getPrecio(),
                    d.calcularSubtotal()
                });
            }
        }
    }

    private void actualizarTablaPedidos() {
        modeloPedidos.setRowCount(0);
        for (Pedido p : controlador.listarPedidos()) {
            modeloPedidos.addRow(new Object[]{
                p.getCodigo(),
                p.getCliente(),
                p.getSucursal(),
                p.getCanal(),
                p.getEstado(),
                p.calcularTotal()
            });
        }
    }

    private void limpiarFormularioPedido() {
        pedidoActual = null;
        txtCodigo.setText("");
        txtEstado.setText("Pendiente");
        txtCantidad.setText("");
        modeloDetalles.setRowCount(0);
    }

    public static void main(String[] args) {
        Aplicacion.inicializar();
        java.awt.EventQueue.invokeLater(() -> new FrmPedidos().setVisible(true));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 *
 * @author EBER
 */
public class FrmMenuPrincipal extends JFrame {

    public FrmMenuPrincipal() {
        initComponents();
        setTitle("Restaurante - Menú Principal");
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 480);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(240, 242, 245));

        // encabezado
        JPanel header = new JPanel();
        header.setBackground(new Color(31, 41, 55));
        header.setBounds(0, 0, 500, 100);
        header.setLayout(null);
        getContentPane().add(header);

        JLabel lblTitulo = new JLabel("Restaurante");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 15, 500, 35);
        header.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Sistema de Gestión");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(214, 217, 222));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 55, 500, 25);
        header.add(lblSubtitulo);

        // botones
        JButton btnClientes = crearBoton("Clientes",
                UIManager.getDefaults().getColor("Actions.Blue"));
        btnClientes.setBounds(60, 140, 170, 70);
        btnClientes.addActionListener((ActionEvent e) -> new FrmClientes().setVisible(true));
        getContentPane().add(btnClientes);

        JButton btnCategorias = crearBoton("Categorías",
                UIManager.getDefaults().getColor("Actions.Green"));
        btnCategorias.setBounds(260, 140, 170, 70);
        btnCategorias.addActionListener((ActionEvent e) -> new FrmCategorias().setVisible(true));
        getContentPane().add(btnCategorias);

        JButton btnProductos = crearBoton("Productos",
                UIManager.getDefaults().getColor("Actions.Yellow"));
        btnProductos.setBounds(60, 230, 170, 70);
        btnProductos.addActionListener((ActionEvent e) -> new FrmProductos().setVisible(true));
        getContentPane().add(btnProductos);

        JButton btnPedidos = crearBoton("Pedidos",
                UIManager.getDefaults().getColor("Actions.Red"));
        btnPedidos.setBounds(260, 230, 170, 70);
        btnPedidos.addActionListener((ActionEvent e) -> new FrmPedidos().setVisible(true));
        getContentPane().add(btnPedidos);

        JButton btnVentas = crearBoton("Ventas",
                UIManager.getDefaults().getColor("Actions.Blue"));
        btnVentas.setBounds(60, 310, 370, 55);
        btnVentas.addActionListener((ActionEvent e) -> new FrmVenta().setVisible(true));
        getContentPane().add(btnVentas);

        JButton btnSalir = new JButton("Salir del Sistema");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setBounds(150, 375, 200, 45);
        btnSalir.setBackground(new Color(60, 60, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener((ActionEvent e) -> System.exit(0));
        getContentPane().add(btnSalir);
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        return btn;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FrmMenuPrincipal().setVisible(true));
    }
}

package restaurante.vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import restaurante.controlador.CategoriaControlador;
import restaurante.controlador.ClienteControlador;
import restaurante.controlador.ProductoControlador;

/**
 * Menú principal: encabezado con resumen y tarjetas de acceso a cada módulo.
 *
 * @author EBER
 */
public class FrmMenuPrincipal extends JFrame {

    private final JLabel lblClientes = new JLabel("–");
    private final JLabel lblProductos = new JLabel("–");
    private final JLabel lblCategorias = new JLabel("–");

    public FrmMenuPrincipal() {
        setTitle("Restaurante - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(940, 640);
        setMinimumSize(new Dimension(860, 600));
        setLocationRelativeTo(null);
        initComponents();
        // al volver de otra sección se actualizan los contadores
        addComponentListener(new ComponentAdapter() {
            @Override public void componentShown(ComponentEvent e) { actualizarResumen(); }
        });
        actualizarResumen();
    }

    private void initComponents() {
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.CREMA);
        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(crearTarjetas(), BorderLayout.CENTER);
        raiz.add(crearPie(), BorderLayout.SOUTH);
        setContentPane(raiz);
    }

    // ---------- Encabezado ----------

    private JComponent crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, Tema.ESPRESSO, getWidth(), getHeight(), Tema.ESPRESSO_2));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Tema.ORO);
                g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                g2.dispose();
            }
        };
        header.setBorder(BorderFactory.createEmptyBorder(26, 36, 28, 36));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel etiqueta = new JLabel("SISTEMA DE GESTIÓN");
        etiqueta.setFont(new Font(Tema.FUENTE, Font.BOLD, 11));
        etiqueta.setForeground(Tema.ORO);

        JLabel titulo = new JLabel("Restaurante");
        titulo.setFont(new Font(Tema.FUENTE_TITULO, Font.BOLD, 34));
        titulo.setForeground(Color.WHITE);

        String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", Locale.of("es", "PE")));
        JLabel fecha = new JLabel(Character.toUpperCase(hoy.charAt(0)) + hoy.substring(1));
        fecha.setFont(new Font(Tema.FUENTE, Font.PLAIN, 13));
        fecha.setForeground(Tema.ORO_SUAVE);

        textos.add(etiqueta);
        textos.add(Box.createVerticalStrut(4));
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(2));
        textos.add(fecha);
        textos.add(anchoMinimo(fecha.getPreferredSize().width + 12));

        JPanel resumen = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        resumen.setOpaque(false);
        resumen.add(crearIndicador(lblClientes, "Clientes"));
        resumen.add(crearIndicador(lblProductos, "Productos"));
        resumen.add(crearIndicador(lblCategorias, "Categorías"));

        JPanel derecha = new JPanel(new BorderLayout());
        derecha.setOpaque(false);
        derecha.add(resumen, BorderLayout.SOUTH);

        header.add(textos, BorderLayout.WEST);
        header.add(derecha, BorderLayout.EAST);
        return header;
    }

    private JComponent crearIndicador(JLabel valor, String nombre) {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.setColor(new Color(201, 161, 91, 90));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 20));
        p.add(anchoMinimo(64)); // para que no se corten los textos
        valor.setFont(new Font(Tema.FUENTE_TITULO, Font.BOLD, 22));
        valor.setForeground(Color.WHITE);
        JLabel n = new JLabel(nombre);
        n.setFont(new Font(Tema.FUENTE, Font.PLAIN, 11));
        n.setForeground(Tema.ORO_SUAVE);
        p.add(valor);
        p.add(n);
        return p;
    }

    private static JComponent anchoMinimo(int ancho) {
        JComponent espacio = (JComponent) Box.createHorizontalStrut(ancho);
        espacio.setAlignmentX(LEFT_ALIGNMENT);
        return espacio;
    }

    private void actualizarResumen() {
        lblClientes.setText(contar(() -> new ClienteControlador().listar().size()));
        lblProductos.setText(contar(() -> new ProductoControlador().listar().size()));
        lblCategorias.setText(contar(() -> new CategoriaControlador().listar().size()));
    }

    private static String contar(Supplier<Integer> s) {
        try {
            return String.valueOf(s.get());
        } catch (RuntimeException ex) {
            return "–";
        }
    }

    // ---------- Tarjetas ----------

    private JComponent crearTarjetas() {
        JPanel grid = new JPanel(new GridLayout(2, 3, 18, 18));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(26, 36, 10, 36));

        grid.add(new Tarjeta("", 'C', "Clientes", "Registra y edita los datos de tus clientes",
                new Color(0x35557F), () -> Navegacion.abrir(this, new FrmClientes())));
        grid.add(new Tarjeta("", 'G', "Categorías", "Organiza la carta por tipo de plato",
                new Color(0x2E6B50), () -> Navegacion.abrir(this, new FrmCategorias())));
        grid.add(new Tarjeta("", 'P', "Productos", "Precios, descripción y disponibilidad",
                new Color(0xA8742E), () -> Navegacion.abrir(this, new FrmProductos())));
        grid.add(new Tarjeta("", 'D', "Pedidos", "Toma pedidos y agrega productos",
                new Color(0xB44A3C), () -> Navegacion.abrir(this, new FrmPedidos())));
        grid.add(new Tarjeta("", 'V', "Ventas", "Cobra pedidos y revisa el historial",
                new Color(0x6B4E8A), () -> Navegacion.abrir(this, new FrmVenta())));
        grid.add(new Tarjeta("", 'R', "Reportes", "Informes en PDF y gráficos por fechas",
                new Color(0x3B2E25), () -> Navegacion.abrir(this, new FrmReportes())));
        return grid;
    }

    /** Tarjeta clicable con icono, título y descripción. */
    private static final class Tarjeta extends JPanel {
        private final String glifo;
        private final char inicial;
        private final Color color;
        private boolean encima;

        Tarjeta(String glifo, char inicial, String titulo, String descripcion, Color color, Runnable accion) {
            this.glifo = glifo;
            this.inicial = inicial;
            this.color = color;
            setOpaque(false);
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(86, 22, 18, 22));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel textos = new JPanel();
            textos.setOpaque(false);
            textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
            JLabel t = new JLabel(titulo);
            t.setFont(new Font(Tema.FUENTE_TITULO, Font.BOLD, 18));
            t.setForeground(Tema.TEXTO);
            JLabel d = new JLabel("<html>" + descripcion + "</html>");
            d.setFont(new Font(Tema.FUENTE, Font.PLAIN, 12));
            d.setForeground(Tema.TEXTO_SUAVE);
            textos.add(t);
            textos.add(Box.createVerticalStrut(4));
            textos.add(d);
            add(textos, BorderLayout.CENTER);

            JLabel abrir = new JLabel("Abrir  →");
            abrir.setFont(new Font(Tema.FUENTE, Font.BOLD, 12));
            abrir.setForeground(Tema.mezclar(color, Color.BLACK, 0.1f));
            abrir.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
            add(abrir, BorderLayout.SOUTH);

            getAccessibleContext().setAccessibleName(titulo);
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { encima = true; repaint(); }
                @Override public void mouseExited(MouseEvent e) { encima = false; repaint(); }
                @Override public void mouseClicked(MouseEvent e) { accion.run(); }
            });
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), arc = 18;
            int dy = encima ? 0 : 2;

            // sombra suave
            for (int i = 0; i < 6; i++) {
                g2.setColor(new Color(60, 40, 20, encima ? 10 : 6));
                g2.fillRoundRect(i / 2, 4 + i / 2 + dy, w - i, h - 4 - i - dy, arc, arc);
            }
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(2, dy, w - 5, h - 7, arc, arc);
            g2.setStroke(new BasicStroke(encima ? 1.6f : 1f));
            g2.setColor(encima ? color : Tema.BORDE);
            g2.drawRoundRect(2, dy, w - 5, h - 7, arc, arc);

            // franja superior de color
            g2.setColor(color);
            g2.fillRoundRect(22, dy + 22, 48, 48, 14, 14);
            g2.setColor(Color.WHITE);
            if (Tema.FUENTE_ICONOS != null) {
                g2.setFont(new Font(Tema.FUENTE_ICONOS, Font.PLAIN, 22));
                dibujarCentrado(g2, glifo, 22, dy + 22, 48);
            } else {
                g2.setFont(new Font(Tema.FUENTE_TITULO, Font.BOLD, 22));
                dibujarCentrado(g2, String.valueOf(inicial), 22, dy + 22, 48);
            }
            g2.dispose();
        }

        private static void dibujarCentrado(Graphics2D g2, String s, int x, int y, int lado) {
            FontMetrics fm = g2.getFontMetrics();
            int tx = x + (lado - fm.stringWidth(s)) / 2;
            int ty = y + (lado - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(s, tx, ty);
        }
    }

    // ---------- Pie ----------

    private JComponent crearPie() {
        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.setBorder(BorderFactory.createEmptyBorder(6, 36, 22, 36));

        JLabel info = new JLabel("Seleccione un módulo para comenzar");
        info.setFont(new Font(Tema.FUENTE, Font.PLAIN, 12));
        info.setForeground(Tema.TEXTO_SUAVE);

        JButton btnSalir = new JButton("Salir del sistema");
        Tema.estilizarBoton(btnSalir);
        btnSalir.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this, "¿Desea cerrar el sistema?", "Salir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        pie.add(info, BorderLayout.WEST);
        pie.add(btnSalir, BorderLayout.EAST);
        return pie;
    }

    public static void main(String[] args) {
        Tema.instalar();
        java.awt.EventQueue.invokeLater(() -> new FrmMenuPrincipal().setVisible(true));
    }
}

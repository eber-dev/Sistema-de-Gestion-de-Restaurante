package restaurante.vista;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

/**
 * Tema visual del sistema. Instala FlatLaf con una paleta cálida y aplica
 * el estilo a cualquier ventana sin modificar el código generado por NetBeans.
 */
public final class Tema {

    // Paleta
    public static final Color ESPRESSO = new Color(0x231C17);
    public static final Color ESPRESSO_2 = new Color(0x3B2E25);
    public static final Color ORO = new Color(0xC9A15B);
    public static final Color ORO_SUAVE = new Color(0xE8D5AE);
    public static final Color CREMA = new Color(0xF7F3EC);
    public static final Color BLANCO = Color.WHITE;
    public static final Color BORDE = new Color(0xE6DED1);
    public static final Color TEXTO = new Color(0x2B2420);
    public static final Color TEXTO_SUAVE = new Color(0x7A6E64);
    public static final Color ETIQUETA = new Color(0x5E534B);
    public static final Color VERDE = new Color(0x2E6B50);
    public static final Color AZUL = new Color(0x35557F);
    public static final Color ROJO = new Color(0xB44A3C);
    public static final Color NEUTRO = new Color(0xEFE8DD);
    public static final Color SELECCION = new Color(0xF2E2C2);

    public static final String FUENTE = "Segoe UI";
    public static final String FUENTE_TITULO = elegirFuente("Georgia", "Cambria", FUENTE);
    public static final String FUENTE_ICONOS = elegirFuente("Segoe Fluent Icons", "Segoe MDL2 Assets", null);

    private static final String YA_ESTILIZADO = "tema.aplicado";

    private Tema() { }

    /** Debe llamarse una sola vez, antes de crear cualquier ventana. */
    public static void instalar() {
        Map<String, String> extra = new HashMap<>();
        extra.put("@accentColor", hex(ORO));
        FlatLightLaf.setGlobalExtraDefaults(extra);
        FlatLightLaf.setup();

        UIManager.put("defaultFont", new Font(FUENTE, Font.PLAIN, 13));
        UIManager.put("Panel.background", CREMA);
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 10);
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("Component.borderColor", BORDE);
        UIManager.put("Component.focusColor", ORO);
        UIManager.put("Component.focusedBorderColor", ORO);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.width", 10);
        UIManager.put("Table.alternateRowColor", new Color(0xFBF8F3));
        UIManager.put("Table.selectionBackground", SELECCION);
        UIManager.put("Table.selectionForeground", TEXTO);
        UIManager.put("Table.selectionInactiveBackground", SELECCION);
        UIManager.put("Table.selectionInactiveForeground", TEXTO);
        UIManager.put("Table.gridColor", BORDE);
        UIManager.put("TableHeader.height", 34);
        UIManager.put("TableHeader.background", NEUTRO);
        UIManager.put("TableHeader.separatorColor", BORDE);
        UIManager.put("TableHeader.bottomSeparatorColor", BORDE);
        UIManager.put("TabbedPane.underlineColor", ORO);
        UIManager.put("TabbedPane.selectedBackground", BLANCO);
        UIManager.put("TitlePane.background", CREMA);
        UIManager.put("TitlePane.inactiveBackground", CREMA);
        UIManager.put("TitlePane.foreground", TEXTO);
        UIManager.put("TitlePane.inactiveForeground", TEXTO_SUAVE);
        UIManager.put("TitlePane.embeddedForeground", TEXTO_SUAVE);
        UIManager.put("MenuBar.background", CREMA);
        UIManager.put("TabbedPane.contentAreaColor", BORDE);
        UIManager.put("OptionPane.buttonAreaBorder", BorderFactory.createEmptyBorder(12, 0, 0, 0));
    }

    /** Aplica el tema a una ventana completa y a todo lo que contiene. */
    public static void aplicar(JFrame ventana) {
        if (ventana.getRootPane().getClientProperty(YA_ESTILIZADO) != null) {
            return;
        }
        ventana.getRootPane().putClientProperty(YA_ESTILIZADO, Boolean.TRUE);
        Container contenido = ventana.getContentPane();
        if (esClaro(contenido.getBackground()) || !(contenido instanceof JPanel)) {
            contenido.setBackground(CREMA);
        }
        recorrer(contenido);
        ventana.revalidate();
        ventana.repaint();
    }

    private static void recorrer(Component c) {
        if (c instanceof JTable t) {
            estilizarTabla(t);
        } else if (c instanceof JScrollPane s) {
            estilizarScroll(s);
        } else if (c instanceof JButton b) {
            estilizarBoton(b);
        } else if (c instanceof JLabel l) {
            estilizarEtiqueta(l);
        } else if (c instanceof JPanel p) {
            estilizarPanel(p);
        }
        if (c instanceof Container k && !(c instanceof JButton)) {
            for (Component hijo : k.getComponents()) {
                recorrer(hijo);
            }
        }
    }

    // ---------- Paneles ----------

    private static void estilizarPanel(JPanel p) {
        Color bg = p.getBackground();
        if (!p.isOpaque() || bg == null) {
            return;
        }
        if (esOscuro(bg)) {
            p.setBackground(ESPRESSO);
            Border actual = p.getBorder();
            Border linea = BorderFactory.createMatteBorder(0, 0, 3, 0, ORO);
            p.setBorder(actual == null ? linea : BorderFactory.createCompoundBorder(linea, actual));
        } else if (bg.equals(Color.WHITE)) {
            Container padre = p.getParent();
            boolean dentroDeTarjeta = padre != null && Color.WHITE.equals(padre.getBackground());
            if (!dentroDeTarjeta) {
                Insets in = p.getBorder() != null ? p.getBorder().getBorderInsets(p) : new Insets(12, 14, 12, 14);
                // el estilo va primero: FlatLaf reinicia el borde al aplicarlo
                p.putClientProperty(FlatClientProperties.STYLE, "arc:16");
                p.setBorder(new FlatLineBorder(new Insets(Math.max(in.top, 12), Math.max(in.left, 14),
                        Math.max(in.bottom, 12), Math.max(in.right, 14)), BORDE, 1f, 16));
            }
        } else if (esClaro(bg)) {
            p.setBackground(CREMA);
        }
    }

    /** Convierte un panel cualquiera en una tarjeta blanca redondeada. */
    public static void tarjeta(JComponent c, int relleno) {
        c.setOpaque(true);
        c.setBackground(BLANCO);
        c.putClientProperty(FlatClientProperties.STYLE, "arc:16");
        c.setBorder(new FlatLineBorder(new Insets(relleno, relleno, relleno, relleno), BORDE, 1f, 16));
    }

    // ---------- Etiquetas ----------

    private static void estilizarEtiqueta(JLabel l) {
        Font f = l.getFont();
        if (f == null) {
            return;
        }
        String texto = l.getText() == null ? "" : l.getText().strip();
        if (!texto.equals(l.getText())) {
            l.setText(texto); // algunos textos del diseñador traen saltos de línea
        }
        if (fondoEfectivoOscuro(l)) {
            if (f.isBold() && f.getSize() >= 18) {
                l.setForeground(BLANCO);
                l.setFont(new Font(FUENTE_TITULO, Font.BOLD, f.getSize() + 2));
            } else {
                l.setForeground(ORO_SUAVE);
                l.setFont(new Font(FUENTE, Font.PLAIN, f.getSize()));
            }
        } else if (f.isBold() && f.getSize() >= 14 && texto.equals(texto.toUpperCase(Locale.ROOT))
                && texto.matches("[^0-9]*\\p{Lu}{4,}[^0-9]*")) {
            // títulos de sección en mayúsculas: "DATOS DEL PRODUCTO"
            l.setForeground(ESPRESSO_2);
            l.setFont(new Font(FUENTE, Font.BOLD, 13));
            l.setText(corregirTitulo(texto));
            l.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 3, 0, 0, ORO),
                    BorderFactory.createEmptyBorder(0, 8, 0, 0)));
        } else if (!f.isBold() && esNeutro(l.getForeground())) {
            // etiquetas de campos; las que tienen color propio (avisos, totales) se respetan
            l.setForeground(ETIQUETA);
        }
    }

    private static boolean esNeutro(Color c) {
        return c == null || (Math.abs(c.getRed() - c.getGreen()) < 12 && Math.abs(c.getGreen() - c.getBlue()) < 12
                && c.getRed() < 120);
    }

    private static boolean fondoEfectivoOscuro(Component c) {
        for (Component p = c.getParent(); p != null; p = p.getParent()) {
            if (p.isOpaque() || p instanceof JFrame) {
                Color bg = p.getBackground();
                return bg != null && (esOscuro(bg) || bg.equals(ESPRESSO));
            }
        }
        return false;
    }

    // ---------- Botones ----------

    public static void estilizarBoton(JButton b) {
        String t = normalizar(b.getText());
        Color fondo;
        Color texto = BLANCO;
        if (contiene(t, "eliminar", "cancelar", "quitar", "anular", "borrar")) {
            fondo = ROJO;
        } else if (contiene(t, "limpiar", "volver", "nuevo", "salir", "cerrar")) {
            fondo = NEUTRO;
            texto = ESPRESSO_2;
        } else if (contiene(t, "editar", "actualizar", "buscar", "pdf", "ir a", "ver ", "clonar", "imprimir")) {
            fondo = AZUL;
        } else if (contiene(t, "guardar", "agregar", "crear", "confirmar", "registrar", "cobrar", "generar", "pagar", "anadir", "procesar")) {
            fondo = VERDE;
        } else {
            fondo = ESPRESSO_2;
        }
        b.setBorder(UIManager.getBorder("Button.border"));
        b.setBackground(fondo);
        b.setForeground(texto);
        b.setFocusPainted(false);
        b.setFont(new Font(FUENTE, Font.BOLD, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.putClientProperty(FlatClientProperties.STYLE,
                "arc:10; borderWidth:0; focusWidth:0; innerFocusWidth:0; margin:7,16,7,16;"
                + " hoverBackground:" + hex(mezclar(fondo, texto, 0.12f)) + ";"
                + " pressedBackground:" + hex(mezclar(fondo, Color.BLACK, 0.15f)));
    }

    // ---------- Tablas ----------

    public static void estilizarTabla(JTable t) {
        t.setRowHeight(32);
        t.setFont(new Font(FUENTE, Font.PLAIN, 13));
        t.setForeground(TEXTO);
        t.setShowVerticalLines(false);
        t.setShowHorizontalLines(true);
        t.setIntercellSpacing(new Dimension(0, 1));
        t.setGridColor(BORDE);
        t.setSelectionBackground(SELECCION);
        t.setSelectionForeground(TEXTO);
        t.setFillsViewportHeight(true);
        t.setBackground(BLANCO);
        JTableHeader h = t.getTableHeader();
        if (h != null) {
            h.setFont(new Font(FUENTE, Font.BOLD, 12));
            h.setBackground(NEUTRO);
            h.setForeground(ESPRESSO_2);
            h.setReorderingAllowed(false);
        }
    }

    private static void estilizarScroll(JScrollPane s) {
        s.setBorder(new FlatLineBorder(new Insets(1, 1, 1, 1), BORDE, 1f, 0));
        s.getViewport().setBackground(BLANCO);
    }

    // ---------- Utilidades ----------

    public static Color mezclar(Color a, Color b, float t) {
        return new Color(
                Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }

    public static String hex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    private static boolean esOscuro(Color c) {
        return c.getRed() + c.getGreen() + c.getBlue() < 240;
    }

    private static boolean esClaro(Color c) {
        return c == null || (c.getRed() >= 225 && c.getGreen() >= 225 && c.getBlue() >= 225);
    }

    private static boolean contiene(String texto, String... palabras) {
        return Arrays.stream(palabras).anyMatch(texto::contains);
    }

    private static String normalizar(String s) {
        if (s == null) {
            return "";
        }
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT);
    }

    private static String corregirTitulo(String s) {
        return s.replace("DATOS DEL CLIENTES", "DATOS DEL CLIENTE");
    }

    private static String elegirFuente(String a, String b, String porDefecto) {
        String[] familias = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String f : familias) {
            if (f.equalsIgnoreCase(a)) {
                return a;
            }
        }
        for (String f : familias) {
            if (f.equalsIgnoreCase(b)) {
                return b;
            }
        }
        return porDefecto;
    }
}

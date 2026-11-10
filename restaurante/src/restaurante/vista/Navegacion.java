package restaurante.vista;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

/** Mantiene una sola ventana visible y permite regresar incluso con la X. */
final class Navegacion {
    private static final Map<JFrame, JFrame> padres = new IdentityHashMap<>();
    private Navegacion() { }

    static void abrir(JFrame origen, JFrame destino) {
        padres.put(destino, origen);
        destino.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Tema.aplicar(destino);

        JMenuBar barra = new JMenuBar();
        barra.setOpaque(true);
        barra.setBackground(Tema.CREMA);
        barra.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Tema.BORDE),
                BorderFactory.createEmptyBorder(6, 10, 6, 14)));
        JButton volver = new JButton("←  Volver al menú principal");
        Tema.estilizarBoton(volver);
        volver.addActionListener(e -> volverAlMenu(destino));
        barra.add(volver);
        barra.add(Box.createHorizontalGlue());
        JLabel ruta = new JLabel("Inicio  /  " + destino.getTitle());
        ruta.setFont(new Font(Tema.FUENTE, Font.PLAIN, 12));
        ruta.setForeground(Tema.TEXTO_SUAVE);
        barra.add(ruta);
        destino.setJMenuBar(barra);

        destino.setSize(destino.getWidth(), destino.getHeight() + barra.getPreferredSize().height);
        destino.setLocationRelativeTo(null);
        destino.addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                JFrame anterior = padres.remove(destino);
                if (anterior != null && anterior.isDisplayable()) {
                    anterior.setVisible(true);
                    anterior.toFront();
                }
            }
        });
        origen.setVisible(false);
        destino.setVisible(true);
    }

    private static void volverAlMenu(JFrame actual) {
        JFrame ventana = actual;
        JFrame padre;
        while ((padre = padres.remove(ventana)) != null) {
            ventana.dispose();
            ventana = padre;
        }
        if (ventana.isDisplayable()) {
            ventana.setVisible(true);
            ventana.toFront();
        }
    }
}

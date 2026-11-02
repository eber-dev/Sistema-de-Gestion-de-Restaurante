package restaurante.vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

/** Mantiene una sola ventana visible y permite regresar incluso con la X. */
final class Navegacion {
    private static final Map<JFrame, JFrame> padres = new IdentityHashMap<>();
    private Navegacion() { }

    static void abrir(JFrame origen, JFrame destino) {
        padres.put(destino, origen);
        destino.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JMenuBar barra = new JMenuBar();
        JButton volver = new JButton("Volver al menú principal");
        volver.addActionListener(e -> volverAlMenu(destino));
        barra.add(volver);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante;

import javax.swing.SwingUtilities;
import restaurante.app.Aplicacion;
import restaurante.vista.FrmMenuPrincipal;

/**
 *
 * @author EBER
 */
public class Main {

    public static void main(String[] args) {
        restaurante.vista.Tema.instalar();
        try {
            Aplicacion.inicializar();
        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> javax.swing.JOptionPane.showMessageDialog(null,
                "No se pudo preparar la base de datos. Revise usuario, contraseña, servidor y db.autoinit.\n" + ex.getMessage(),
                "Conexión MySQL", javax.swing.JOptionPane.ERROR_MESSAGE));
            return;
        }

        SwingUtilities.invokeLater(() -> new FrmMenuPrincipal().setVisible(true));
    }
}

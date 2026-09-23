/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante;

import javax.swing.SwingUtilities;
import restaurante.app.Aplicacion;
import restaurante.vista.FrmCategorias;

/**
 *
 * @author EBER
 */
public class Main {

    public static void main(String[] args) {
        Aplicacion.inicializar();
        SwingUtilities.invokeLater(()
                -> new FrmCategorias().setVisible(true));
    }
}

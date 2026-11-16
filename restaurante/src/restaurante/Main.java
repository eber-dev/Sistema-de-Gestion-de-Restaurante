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
                diagnosticoConexion(ex),
                "Conexión MySQL", javax.swing.JOptionPane.ERROR_MESSAGE));
            return;
        }

        SwingUtilities.invokeLater(() -> new FrmMenuPrincipal().setVisible(true));
    }

    private static String diagnosticoConexion(Throwable error) {
        Throwable causa = error;
        while (causa != null) {
            if (causa instanceof java.sql.SQLException) {
                java.sql.SQLException sql = (java.sql.SQLException) causa;
                int codigo = sql.getErrorCode();
                if (codigo == 1045) return "MySQL rechazó el usuario o la contraseña. Revisa db.local.properties.";
                if (codigo == 1049) return "La base de datos configurada no existe. Revisa db.name y db.autoinit.";
                if (codigo == 1044 || codigo == 1142) return "El usuario de MySQL no tiene los permisos necesarios para esta operación.";
                if (sql.getSQLState() != null && sql.getSQLState().startsWith("08")) return "No se pudo contactar a MySQL. Revisa que el servidor esté iniciado y verifica host y puerto.";
                return "No se pudo preparar la base de datos. Código MySQL: " + codigo + "; estado SQL: " + sql.getSQLState();
            }
            causa = causa.getCause();
        }
        return "No se pudo preparar la aplicación. Revisa la configuración y la consola de NetBeans.";
    }
}

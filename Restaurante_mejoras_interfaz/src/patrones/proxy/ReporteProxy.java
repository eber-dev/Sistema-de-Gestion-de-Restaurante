/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.proxy;

public class ReporteProxy implements Reporte {

    private final String nombre;
    private final String rolUsuario;

    public ReporteProxy(String nombre, String rolUsuario) {
        this.nombre = nombre;
        this.rolUsuario = rolUsuario;
    }

    @Override
    public void generar() {
        if ("Administrador".equalsIgnoreCase(rolUsuario)) {
            // Si es admin, crea el reporte real y lo ejecuta
            ReporteReal real = new ReporteReal(nombre);
            real.generar();
        } else {
            throw new SecurityException("Acceso denegado. Solo administradores pueden generar reportes.");
        }
    }
}

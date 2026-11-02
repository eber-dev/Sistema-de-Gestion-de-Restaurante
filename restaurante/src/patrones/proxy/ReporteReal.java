/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.proxy;

public class ReporteReal implements Reporte {

    private final String nombre;

    public ReporteReal(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void generar() {
        System.out.println("Generando reporte JasperReports: " + nombre + "...");
        // Aquí irá la lógica de JasperReports
    }
}

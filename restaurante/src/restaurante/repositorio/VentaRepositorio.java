/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.repositorio;

import java.util.List;
import restaurante.modelo.Venta;

public interface VentaRepositorio {

    void guardar(Venta venta);

    Venta buscarPorId(int id);

    List<Venta> listar();
}

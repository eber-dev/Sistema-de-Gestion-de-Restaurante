/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.implementacion;

import java.util.ArrayList;
import java.util.List;
import restaurante.modelo.Venta;
import restaurante.repositorio.VentaRepositorio;

public class VentaRepositorioMemoria implements VentaRepositorio {

    private final List<Venta> ventas = new ArrayList<>();

    @Override
    public void guardar(Venta venta) {
        ventas.add(venta);
    }

    @Override
    public Venta buscarPorId(int id) {
        for (Venta v : ventas) {
            if (v.getIdVenta() == id) {
                return v;
            }
        }
        return null;
    }

    @Override
    public List<Venta> listar() {
        return new ArrayList<>(ventas);
    }
}

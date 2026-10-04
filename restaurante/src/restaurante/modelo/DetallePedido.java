/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.modelo;

/**
 *
 * @author Loreley
 */
public class DetallePedido implements Cloneable {

    private String producto;
    private int cantidad;
    private double precio;

    public DetallePedido(String producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public double calcularSubtotal() {
        return cantidad * precio;
    }

    @Override
    public DetallePedido clone() {
        return new DetallePedido(producto, cantidad, precio);
    }
}

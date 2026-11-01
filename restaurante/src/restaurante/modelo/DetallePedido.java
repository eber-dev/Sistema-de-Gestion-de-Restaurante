/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.modelo;

public class DetallePedido implements Cloneable {

    private int idDetalle;         // id_detalle en BD (0 si aún no persiste)
    private int idProducto;        // id_producto en BD
    private String producto;       // nombre visible
    private int cantidad;
    private double precio;

    public DetallePedido(String producto, int cantidad, double precio) {
        this(0, 0, producto, cantidad, precio);
    }

    public DetallePedido(int idProducto, String producto, int cantidad, double precio) {
        this(0, idProducto, producto, cantidad, precio);
    }

    public DetallePedido(int idDetalle, int idProducto, String producto, int cantidad, double precio) {
        this.idDetalle = idDetalle;
        this.idProducto = idProducto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
        return new DetallePedido(idDetalle, idProducto, producto, cantidad, precio);
    }
}

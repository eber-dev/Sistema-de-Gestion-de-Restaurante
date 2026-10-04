/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.modelo;

/**
 *
 * @author Loreley
 */
import java.util.ArrayList;
import java.util.List;
import patrones.prototype.PedidoPrototype;

public class Pedido implements PedidoPrototype {

    private String codigo;
    private String cliente;
    private String sucursal;
    private String canal;
    private String estado;
    private final List<DetallePedido> detalles;

    public Pedido(String codigo) {
        this.codigo = codigo;
        this.cliente = "";
        this.sucursal = "";
        this.canal = "";
        this.estado = "En preparación";
        this.detalles = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getSucursal() {
        return sucursal;
    }

    public String getCanal() {
        return canal;
    }

    public String getEstado() {
        return estado;
    }

    public List<DetallePedido> getDetalles() {
        return new ArrayList<>(detalles);
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void agregarDetalle(String producto, int cantidad, double precio) {
        if (cantidad <= 0 || precio < 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser positiva y el precio no puede ser negativo.");
        }

        detalles.add(new DetallePedido(producto, cantidad, precio));
    }

    public boolean tieneDetalles() {
        return !detalles.isEmpty();
    }

    public double calcularTotal() {
        double total = 0;

        for (DetallePedido detalle : detalles) {
            total += detalle.calcularSubtotal();
        }

        return total;
    }

    @Override
    public Pedido clonar() {
        Pedido copia = new Pedido(this.codigo + "-COPIA");

        copia.setCliente(this.cliente);
        copia.setSucursal(this.sucursal);
        copia.setCanal(this.canal);
        copia.setEstado("En preparación");

        for (DetallePedido detalle : this.detalles) {
            copia.detalles.add(detalle.clone());
        }

        return copia;
    }
}

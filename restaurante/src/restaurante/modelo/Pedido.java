/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.modelo;

import java.util.ArrayList;
import java.util.List;
import patrones.prototype.PedidoPrototype;

public class Pedido implements PedidoPrototype {

    // PK en BD
    private int idPedido;

    // Relaciones FK
    private int idCliente;
    private int idEmpleado;
    private int idMesa;   // 0 si no aplica
    private String tipoPedido;  // "Mesa" | "Para llevar" | "Delivery"

    // Campos de negocio / visualización
    private String codigo;
    private String cliente;      // nombre visible
    private String sucursal;     // cosmético: "Mesa 3", "Para llevar", etc.
    private String canal;        // cosmético: derivado de tipoPedido
    private patrones.state.EstadoPedido estadoObj;
    private final List<DetallePedido> detalles;

    public Pedido(String codigo) {
        this.idPedido = 0;
        this.codigo = codigo;
        this.cliente = "";
        this.sucursal = "";
        this.canal = "";
        this.tipoPedido = "Mesa";
        this.estadoObj = new patrones.state.EstadoPendiente();
        this.detalles = new ArrayList<>();
    }

    // Getters / Setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
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
        return estadoObj.getNombre();
    }

    public void setEstado(String estado) {
        switch (estado) {
            case "Pendiente":
                this.estadoObj = new patrones.state.EstadoPendiente();
                break;
            case "En preparacion":
                this.estadoObj = new patrones.state.EstadoEnPreparacion();
                break;
            case "Listo":
                this.estadoObj = new patrones.state.EstadoListo();
                break;
            case "Entregado":
                this.estadoObj = new patrones.state.EstadoEntregado();
                break;
            case "Cancelado":
                this.estadoObj = new patrones.state.EstadoCancelado();
                break;
            default:
                this.estadoObj = new patrones.state.EstadoPendiente();
        }
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

    // Detalles — dos sobrecargas
    public void agregarDetalle(String producto, int cantidad, double precio) {
        agregarDetalle(0, producto, cantidad, precio);
    }

    public void agregarDetalle(int idProducto, String producto, int cantidad, double precio) {
        if (cantidad <= 0 || precio < 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser positiva y el precio no puede ser negativo.");
        }
        detalles.add(new DetallePedido(idProducto, producto, cantidad, precio));
    }

    // Para uso del DAO al reconstruir desde BD
    public void agregarDetallePersistido(DetallePedido d) {
        detalles.add(d);
    }

    public boolean tieneDetalles() {
        return !detalles.isEmpty();
    }

    public double calcularTotal() {
        patrones.composite.TotalCompuesto total = new patrones.composite.TotalCompuesto();
        for (DetallePedido d : detalles) total.agregar(new patrones.composite.ImporteDetalle(d));
        return total.calcularTotal();
    }

    public patrones.memento.PedidoMemento crearMemento() { return new patrones.memento.PedidoMemento(getEstado()); }
    public void restaurar(patrones.memento.PedidoMemento m) { if(m == null) throw new IllegalArgumentException("Memento vacío"); setEstado(m.getEstado()); }

    public boolean estaConfirmado() {
        if (estadoObj == null) {
            return false;
        }
        String e = estadoObj.getNombre().trim().toLowerCase();
        return e.equals("en preparacion")
                || e.equals("en preparación")
                || e.equals("entregado")
                || e.equals("listo")
                || e.equals("confirmado");
    }

    @Override
    public Pedido clonar() {
        Pedido copia = new Pedido(patrones.factory.PedidoFactoryConcreto.generarCodigo());
        copia.setIdCliente(this.idCliente);
        copia.setIdEmpleado(this.idEmpleado);
        copia.setIdMesa(this.idMesa);
        copia.setTipoPedido(this.tipoPedido);
        copia.setCliente(this.cliente);
        copia.setSucursal(this.sucursal);
        copia.setCanal(this.canal);
        copia.setEstado("Pendiente");
        for (DetallePedido d : this.detalles) {
            copia.detalles.add(d.clone());
        }
        return copia;
    }

    @Override
    public String toString() {
        return codigo + " - " + cliente;
    }

    public void setEstadoObj(patrones.state.EstadoPedido estado) {
        this.estadoObj = estado;
    }

    public void confirmar() {
        estadoObj.confirmar(this);
    }

    public void entregar() {
        estadoObj.entregar(this);
    }

    public void cancelar() {
        estadoObj.cancelar(this);
    }
}

package patrones.memento;

/** Instantánea inmutable del estado de un pedido durante una operación. */
public final class PedidoMemento {
    private final String estado;
    public PedidoMemento(String estado) { this.estado=estado; }
    public String getEstado() { return estado; }
}

package patrones.state;
import restaurante.modelo.Pedido;
/** Pedido listo para entrega y cobro. */
public final class EstadoListo implements EstadoPedido {
    @Override public String getNombre(){return "Listo";}
    @Override public void confirmar(Pedido pedido){throw new IllegalStateException("El pedido ya está confirmado.");}
    @Override public void entregar(Pedido pedido){pedido.setEstadoObj(new EstadoEntregado());}
    @Override public void cancelar(Pedido pedido){pedido.setEstadoObj(new EstadoCancelado());}
}

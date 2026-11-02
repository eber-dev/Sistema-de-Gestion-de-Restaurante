package patrones.factory;
import java.util.Locale;
import java.util.UUID;
import restaurante.modelo.Pedido;

/** Crea códigos compatibles con VARCHAR(20) sin reiniciar una secuencia al abrir la aplicación. */
public class PedidoFactoryConcreto implements PedidoFactory {
    public static String generarCodigo() { return "PED-" + UUID.randomUUID().toString().replace("-", "").substring(0,16).toUpperCase(Locale.ROOT); }
    @Override public Pedido crearPedido() { return new Pedido(generarCodigo()); }
}

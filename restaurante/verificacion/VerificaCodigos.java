import java.util.*;
import patrones.factory.PedidoFactoryConcreto;
import restaurante.modelo.Pedido;
public class VerificaCodigos {
 public static void main(String[]args){Set<String> ids=new HashSet<>();PedidoFactoryConcreto f=new PedidoFactoryConcreto();for(int i=0;i<10000;i++){String id=f.crearPedido().getCodigo();if(id.length()>20||!ids.add(id))throw new AssertionError("Código repetido o fuera de longitud");}Pedido original=f.crearPedido();original.agregarDetalle(1,"Plato",2,5);Pedido a=original.clonar(),b=original.clonar();if(a.getCodigo().equals(b.getCodigo())||a.getCodigo().length()>20||a.getDetalles().size()!=1||a.getIdPedido()!=0)throw new AssertionError("Clonación incorrecta");System.out.println("OK: 10 000 códigos distintos, longitud válida y clones nuevos con detalle");}
}

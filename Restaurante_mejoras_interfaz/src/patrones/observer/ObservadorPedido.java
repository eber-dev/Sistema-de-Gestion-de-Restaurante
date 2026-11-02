package patrones.observer;

import java.sql.Connection;
import java.sql.SQLException;

/** Observador sincrónico: usa la misma conexión de la operación que origina el evento. */
public interface ObservadorPedido {
    void alCambiarEstado(Connection conexion, int pedido, String anterior, String nuevo) throws SQLException;
}

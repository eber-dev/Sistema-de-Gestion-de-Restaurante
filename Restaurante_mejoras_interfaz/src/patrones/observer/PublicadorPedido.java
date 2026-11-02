package patrones.observer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PublicadorPedido {
    private final List<ObservadorPedido> observadores = new ArrayList<>();
    public void agregar(ObservadorPedido o) { observadores.add(o); }
    public void publicar(Connection c, int id, String antes, String despues) throws SQLException {
        for (ObservadorPedido o : observadores) o.alCambiarEstado(c, id, antes, despues);
    }
}

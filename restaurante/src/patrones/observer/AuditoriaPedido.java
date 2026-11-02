package patrones.observer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class AuditoriaPedido implements ObservadorPedido {
    @Override public void alCambiarEstado(Connection conexion, int pedido, String anterior, String nuevo) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO pedido_eventos(id_pedido, estado_anterior, estado_nuevo, usuario) VALUES (?,?,?,?)")) {
            ps.setInt(1, pedido); ps.setString(2, anterior); ps.setString(3, nuevo); ps.setString(4, "sistema"); ps.executeUpdate();
        }
    }
}

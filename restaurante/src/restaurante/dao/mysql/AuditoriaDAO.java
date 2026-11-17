package restaurante.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Registra la acción en la misma transacción que la operación de negocio. */
public final class AuditoriaDAO {
    private AuditoriaDAO() { }
    public static void registrar(Connection cn, String entidad, String id, String accion, String detalle) throws SQLException {
        String sql = "INSERT INTO auditoria (entidad, id_registro, accion, detalle, usuario_bd) VALUES (?, ?, ?, ?, CURRENT_USER())";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, entidad);
            ps.setString(2, id);
            ps.setString(3, accion);
            ps.setString(4, detalle);
            ps.executeUpdate();
        }
    }
}

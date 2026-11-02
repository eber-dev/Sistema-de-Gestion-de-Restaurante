/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import restaurante.config.ConexionDB;
import restaurante.modelo.MetodoPago;
import restaurante.modelo.Pedido;
import restaurante.modelo.Venta;
import restaurante.repositorio.VentaRepositorio;

public class VentaDAOMySQL implements VentaRepositorio {

    @Override
    public void guardar(Venta v) {
        if (v == null || v.getPedido() == null || v.getPedido().getIdPedido() <= 0)
            throw new IllegalArgumentException("Pedido no válido");
        int id = v.getPedido().getIdPedido();
        try (Connection cn = ConexionDB.getInstancia().conectar()) {
            cn.setAutoCommit(false);
            try {
                String estado; int mesa;
                try (PreparedStatement ps = cn.prepareStatement("SELECT estado,id_mesa FROM pedidos WHERE id_pedido=? FOR UPDATE")) {
                    ps.setInt(1,id);
                    try (ResultSet r=ps.executeQuery()) {
                        if (!r.next()) throw new SQLException("Pedido inexistente");
                        estado=r.getString(1); mesa=r.getInt(2);
                    }
                }
                if (!"En preparacion".equalsIgnoreCase(estado) && !"Listo".equalsIgnoreCase(estado))
                    throw new SQLException("El pedido no está disponible para cobrar");
                try (PreparedStatement ps=cn.prepareStatement("SELECT id_pago FROM pagos WHERE id_pedido=? AND estado='Pagado' FOR UPDATE")) {
                    ps.setInt(1,id); try(ResultSet r=ps.executeQuery()) { if(r.next()) throw new SQLException("El pedido ya fue pagado"); }
                }
                // Validar el detalle y consultar productos en la misma transacción.
                int detalles=0;
                try (PreparedStatement ps=cn.prepareStatement("SELECT d.id_detalle, p.id_producto FROM detalle_pedido d JOIN productos p ON p.id_producto=d.id_producto WHERE d.id_pedido=? FOR UPDATE")) {
                    ps.setInt(1,id); try(ResultSet r=ps.executeQuery()){while(r.next()) detalles++;}
                }
                if(detalles==0) throw new SQLException("El pedido no tiene detalles");
                int idPago;
                try (PreparedStatement ps=cn.prepareStatement("INSERT INTO pagos(id_pedido,metodo_pago,monto,estado) VALUES(?,?,?,'Pagado')",Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1,id); ps.setString(2,mapearMetodoPago(v.getMetodoPago())); ps.setBigDecimal(3,java.math.BigDecimal.valueOf(v.getTotal()).setScale(2,java.math.RoundingMode.HALF_UP));
                    ps.executeUpdate(); try(ResultSet r=ps.getGeneratedKeys()){if(!r.next()) throw new SQLException("No se obtuvo el identificador del pago"); idPago=r.getInt(1);}
                }
                try (PreparedStatement ps=cn.prepareStatement("UPDATE pedidos SET estado='Entregado' WHERE id_pedido=?")) {ps.setInt(1,id); if(ps.executeUpdate()!=1)throw new SQLException("No se actualizó el pedido");}
                patrones.observer.PublicadorPedido publicador=new patrones.observer.PublicadorPedido();
                publicador.agregar(new patrones.observer.AuditoriaPedido());
                publicador.publicar(cn,id,estado,"Entregado");
                if(mesa>0) {
                    try (PreparedStatement ps=cn.prepareStatement("UPDATE mesas SET estado='Libre' WHERE id_mesa=? AND estado='Ocupada' AND NOT EXISTS (SELECT 1 FROM pedidos WHERE id_mesa=? AND id_pedido<>? AND estado IN ('Pendiente','En preparacion','Listo'))")) {
                        ps.setInt(1,mesa); ps.setInt(2,mesa); ps.setInt(3,id); ps.executeUpdate();
                    }
                }
                cn.commit();
                v.setIdVenta(idPago);
                v.getPedido().setEstado("Entregado");
            } catch (Exception ex) {
                try { cn.rollback(); } catch (SQLException rollback) { ex.addSuppressed(rollback); }
                throw ex;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error al guardar pago: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Venta buscarPorId(int id) {
        String sql = "SELECT p.id_pago, p.metodo_pago, p.monto, p.fecha_pago, "
                + "       pe.id_pedido, pe.codigo, pe.total AS total_pedido "
                + "FROM pagos p "
                + "INNER JOIN pedidos pe ON p.id_pedido = pe.id_pedido "
                + "WHERE p.id_pago = ?";

        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearVenta(cn, rs);
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al buscar pago: " + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT p.id_pago, p.metodo_pago, p.monto, p.fecha_pago, "
                + "       pe.id_pedido, pe.codigo, pe.total AS total_pedido "
                + "FROM pagos p "
                + "INNER JOIN pedidos pe ON p.id_pedido = pe.id_pedido "
                + "ORDER BY p.id_pago DESC";

        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearVenta(cn, rs));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar pagos: " + ex.getMessage(), ex);
        }
        return lista;
    }

    private Venta mapearVenta(Connection cn, ResultSet rs) throws SQLException {
        // Reconstruimos un pedido "ligero" solo con el ID y Código para el historial
        Pedido p = new Pedido(rs.getString("codigo"));
        p.setIdPedido(rs.getInt("id_pedido"));

        // Buscamos el nombre del cliente para que se vea bonito en la tabla
        String cliente = "";
        try (PreparedStatement ps = cn.prepareStatement(
                "SELECT c.nombre, c.apellido FROM pedidos pe "
                + "INNER JOIN clientes c ON pe.id_cliente = c.id_cliente "
                + "WHERE pe.id_pedido = ?")) {
            ps.setInt(1, p.getIdPedido());
            try (ResultSet rsC = ps.executeQuery()) {
                if (rsC.next()) {
                    cliente = rsC.getString("nombre") + " " + rsC.getString("apellido");
                }
            }
        }
        p.setCliente(cliente);

        double montoTotal = rs.getDouble("monto");

        // Calculamos inversamente para la boleta (IGV incluido en el monto)
        double opv = montoTotal / 1.18;
        double igv = montoTotal - opv;

        Venta v = new Venta(
                rs.getInt("id_pago"),
                p,
                mapearMetodoPagoInverso(rs.getString("metodo_pago")),
                opv,
                0.0, // descuento
                igv,
                montoTotal
        );

        return v;
    }

    // Mapeos entre Java y MySQL
    private String mapearMetodoPago(MetodoPago m) {
        switch (m) {
            case EFECTIVO:
                return "Efectivo";
            case TARJETA:
                return "Tarjeta";
            case BILLETERA_DIGITAL:
                return "Yape";
            default:
                return "Efectivo";
        }
    }

    private MetodoPago mapearMetodoPagoInverso(String db) {
        if ("Tarjeta".equalsIgnoreCase(db)) {
            return MetodoPago.TARJETA;
        }
        if ("Yape".equalsIgnoreCase(db) || "Plin".equalsIgnoreCase(db)
                || "Transferencia".equalsIgnoreCase(db)) {
            return MetodoPago.BILLETERA_DIGITAL;
        }
        return MetodoPago.EFECTIVO;
    }
}

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
import restaurante.modelo.DetallePedido;
import restaurante.modelo.Pedido;
import restaurante.repositorio.PedidoRepositorio;

public class PedidoDAOMySQL implements PedidoRepositorio {

    @Override
    public void guardar(Pedido p) {
        String sqlPedido = "INSERT INTO pedidos "
                + "(codigo, id_cliente, id_empleado, id_mesa, tipo_pedido, estado, total) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedido "
                + "(id_pedido, id_producto, cantidad, precio_unitario, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = ConexionDB.getInstancia().conectar()) {
            cn.setAutoCommit(false);
            try {
            if(p == null || p.getIdEmpleado() <= 0 || p.getDetalles().isEmpty()) throw new SQLException("Pedido, empleado y detalles son obligatorios");
            // Se comprueban empleados y clientes antes de crear el encabezado.
            try (PreparedStatement ps=cn.prepareStatement("SELECT e.id_empleado,c.id_cliente FROM empleados e LEFT JOIN clientes c ON c.id_cliente=? WHERE e.id_empleado=? FOR UPDATE")) {
                if(p.getIdCliente()>0) ps.setInt(1,p.getIdCliente()); else ps.setNull(1,java.sql.Types.INTEGER);
                ps.setInt(2,p.getIdEmpleado()); try(ResultSet r=ps.executeQuery()) {
                    if(!r.next() || (p.getIdCliente()>0 && r.getObject(2)==null)) throw new SQLException("Cliente o empleado inexistente");
                }
            }
            if(p.getIdMesa()>0) try(PreparedStatement ps=cn.prepareStatement("SELECT id_mesa FROM mesas WHERE id_mesa=? FOR UPDATE")) {
                ps.setInt(1,p.getIdMesa());try(ResultSet r=ps.executeQuery()){if(!r.next())throw new SQLException("Mesa inexistente");}
            }
            try(PreparedStatement ps=cn.prepareStatement("SELECT id_producto FROM productos WHERE id_producto=? FOR UPDATE")) {
                for(DetallePedido d:p.getDetalles()) { if(d.getIdProducto()<=0 || d.getCantidad()<=0)throw new SQLException("Detalle inválido"); ps.setInt(1,d.getIdProducto()); try(ResultSet r=ps.executeQuery()){if(!r.next())throw new SQLException("Producto inexistente");} }
            }

            int idPedidoGenerado;
            try (PreparedStatement ps = cn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, p.getCodigo());
                if (p.getIdCliente() > 0) {
                    ps.setInt(2, p.getIdCliente());
                } else {
                    ps.setNull(2, java.sql.Types.INTEGER);
                }
                ps.setInt(3, p.getIdEmpleado());
                if (p.getIdMesa() > 0) {
                    ps.setInt(4, p.getIdMesa());
                } else {
                    ps.setNull(4, java.sql.Types.INTEGER);
                }
                ps.setString(5, p.getTipoPedido());
                ps.setString(6, p.getEstado());
                ps.setDouble(7, p.calcularTotal());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPedidoGenerado = rs.getInt(1);
                    } else {
                        throw new SQLException("No se obtuvo id_pedido generado.");
                    }
                }
            }

            try (PreparedStatement ps = cn.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS)) {
                for (DetallePedido d : p.getDetalles()) {
                    ps.setInt(1, idPedidoGenerado);
                    ps.setInt(2, d.getIdProducto());
                    ps.setInt(3, d.getCantidad());
                    ps.setDouble(4, d.getPrecio());
                    ps.setDouble(5, d.calcularSubtotal());
                    ps.executeUpdate();
                }
            }

            cn.commit();
            p.setIdPedido(idPedidoGenerado);
            } catch (SQLException | RuntimeException ex) {
                try { cn.rollback(); } catch (SQLException rollback) { ex.addSuppressed(rollback); }
                throw ex;
            }

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al guardar pedido: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Pedido buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM pedidos WHERE codigo=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return reconstruir(cn, rs);
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al buscar pedido: " + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos ORDER BY id_pedido";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(reconstruir(cn, rs));
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al listar pedidos: " + ex.getMessage(), ex);
        }
        return lista;
    }

    public boolean eliminar(String codigo) {
        try (Connection cn = ConexionDB.getInstancia().conectar()) {
            cn.setAutoCommit(false); // Iniciamos transacción
            try {

            // 1. Buscar el id del pedido
            int idPedido = -1;
            try (PreparedStatement ps = cn.prepareStatement("SELECT id_pedido FROM pedidos WHERE codigo=? FOR UPDATE")) {
                ps.setString(1, codigo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idPedido = rs.getInt(1);
                    }
                }
            }

            if (idPedido == -1) {
                cn.rollback();
                return false;
            }

            // Bloquea productos relacionados antes de retirar sus detalles.
            try (PreparedStatement ps = cn.prepareStatement("SELECT p.id_producto FROM detalle_pedido d JOIN productos p ON p.id_producto=d.id_producto WHERE d.id_pedido=? FOR UPDATE")) {
                ps.setInt(1,idPedido); try(ResultSet r=ps.executeQuery()){while(r.next()) { /* Mantener bloqueos hasta commit. */ }}
            }

            try (PreparedStatement ps = cn.prepareStatement("DELETE FROM pagos WHERE id_pedido=?")) {
                ps.setInt(1, idPedido);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = cn.prepareStatement("DELETE FROM pedido_eventos WHERE id_pedido=?")) {
                ps.setInt(1, idPedido);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = cn.prepareStatement("DELETE FROM detalle_pedido WHERE id_pedido=?")) {
                ps.setInt(1, idPedido);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = cn.prepareStatement("DELETE FROM pedidos WHERE id_pedido=?")) {
                ps.setInt(1, idPedido);
                ps.executeUpdate();
            }

            cn.commit();
            return true;
            } catch (SQLException | RuntimeException ex) {
                try { cn.rollback(); } catch (SQLException rollback) { ex.addSuppressed(rollback); }
                throw ex;
            }

        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al eliminar pedido: " + ex.getMessage(), ex);
        }
    }

    private Pedido reconstruir(Connection cn, ResultSet rs) throws SQLException {
        Pedido p = new Pedido(rs.getString("codigo"));
        p.setIdPedido(rs.getInt("id_pedido"));
        p.setIdCliente(rs.getInt("id_cliente"));
        p.setIdEmpleado(rs.getInt("id_empleado"));
        p.setIdMesa(rs.getInt("id_mesa"));
        p.setTipoPedido(rs.getString("tipo_pedido"));
        p.setEstado(rs.getString("estado"));

        p.setCliente(obtenerNombreCliente(cn, p.getIdCliente()));
        p.setSucursal(obtenerSucursal(cn, p.getIdMesa(), p.getTipoPedido()));
        p.setCanal("Delivery".equalsIgnoreCase(p.getTipoPedido()) ? "Virtual" : "Presencial");

        String sqlDet = "SELECT d.id_detalle, d.id_producto, d.cantidad, d.precio_unitario, pr.nombre "
                + "FROM detalle_pedido d "
                + "INNER JOIN productos pr ON d.id_producto = pr.id_producto "
                + "WHERE d.id_pedido=?";
        try (PreparedStatement ps = cn.prepareStatement(sqlDet)) {
            ps.setInt(1, p.getIdPedido());
            try (ResultSet dr = ps.executeQuery()) {
                while (dr.next()) {
                    p.agregarDetallePersistido(new DetallePedido(
                            dr.getInt("id_detalle"),
                            dr.getInt("id_producto"),
                            dr.getString("nombre"),
                            dr.getInt("cantidad"),
                            dr.getDouble("precio_unitario")));
                }
            }
        }
        return p;
    }

    private String obtenerNombreCliente(Connection cn, int id) throws SQLException {
        if (id <= 0) {
            return "";
        }
        try (PreparedStatement ps = cn.prepareStatement(
                "SELECT nombre, apellido FROM clientes WHERE id_cliente=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre") + " " + rs.getString("apellido");
                }
            }
        }
        return "";
    }

    private String obtenerSucursal(Connection cn, int idMesa, String tipo) throws SQLException {
        if ("Delivery".equalsIgnoreCase(tipo)) {
            return "Delivery";
        }
        if ("Para llevar".equalsIgnoreCase(tipo)) {
            return "Para llevar";
        }
        if (idMesa <= 0) {
            return "-";
        }
        try (PreparedStatement ps = cn.prepareStatement(
                "SELECT numero_mesa FROM mesas WHERE id_mesa=?")) {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return "Mesa " + rs.getInt("numero_mesa");
                }
            }
        }
        return "-";
    }

    public void actualizarEstado(int idPedido, String nuevoEstado) {
        String sql = "UPDATE pedidos SET estado=? WHERE id_pedido=?";
        try (Connection cn = ConexionDB.getInstancia().conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error al actualizar estado: " + ex.getMessage(), ex);
        }
    }
}

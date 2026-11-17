package restaurante.dao.mysql;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import restaurante.modelo.Categoria;
import restaurante.modelo.Cliente;
import restaurante.modelo.Producto;

/** Descripciones legibles con los valores anteriores y nuevos de cada operación. */
final class DetalleAuditoria {
    private DetalleAuditoria() { }

    static Map<String, String> producto(Producto p) {
        Map<String, String> v = new LinkedHashMap<>();
        v.put("nombre", p.getNombre());
        v.put("precio", BigDecimal.valueOf(p.getPrecio()).setScale(2, RoundingMode.HALF_UP).toPlainString());
        v.put("estado", p.isDisponible() ? "Disponible" : "No disponible");
        v.put("categoría (ID)", String.valueOf(p.getCategoria().getId()));
        v.put("descripción", p.getDescripcion());
        return v;
    }

    static Map<String, String> categoria(Categoria c) {
        Map<String, String> v = new LinkedHashMap<>();
        v.put("nombre", c.getNombre());
        v.put("descripción", c.getDescripcion());
        return v;
    }

    static Map<String, String> cliente(Cliente c) {
        Map<String, String> v = new LinkedHashMap<>();
        v.put("nombre", c.getNombre());
        v.put("apellido", c.getApellido());
        v.put("teléfono", c.getTelefono());
        v.put("correo", c.getCorreo());
        v.put("dirección", c.getDireccion());
        return v;
    }

    static Map<String, String> leerAnterior(Connection cn, String entidad, int id) throws SQLException {
        String sql;
        String[] campos;
        switch (entidad) {
            case "productos":
                sql = "SELECT nombre, precio, estado, id_categoria, descripcion FROM productos WHERE id_producto=? FOR UPDATE";
                campos = new String[]{"nombre", "precio", "estado", "categoría (ID)", "descripción"};
                break;
            case "categorias":
                sql = "SELECT nombre, descripcion FROM categorias WHERE id_categoria=? FOR UPDATE";
                campos = new String[]{"nombre", "descripción"};
                break;
            case "clientes":
                sql = "SELECT nombre, apellido, telefono, correo, direccion FROM clientes WHERE id_cliente=? FOR UPDATE";
                campos = new String[]{"nombre", "apellido", "teléfono", "correo", "dirección"};
                break;
            default: throw new IllegalArgumentException("Entidad de auditoría no soportada");
        }
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Map<String, String> valores = new LinkedHashMap<>();
                for (int i = 0; i < campos.length; i++) valores.put(campos[i], rs.getString(i + 1));
                return valores;
            }
        }
    }

    static String creacion(String entidad, Map<String, String> valores) {
        return limitar(etiqueta(entidad) + ": " + nombre(valores));
    }

    static String eliminacion(String entidad, Map<String, String> anterior) {
        return limitar(etiqueta(entidad) + ": " + nombre(anterior));
    }

    static String cambio(String entidad, Map<String, String> anterior, Map<String, String> nuevo) {
        if (anterior == null) return limitar(etiqueta(entidad) + ": " + nombre(nuevo) + "; valores anteriores no disponibles");
        StringBuilder detalle = new StringBuilder(etiqueta(entidad)).append(": ").append(nombre(anterior));
        boolean huboCambios = false;
        for (String campo : nuevo.keySet()) {
            if (!Objects.equals(normalizar(anterior.get(campo)), normalizar(nuevo.get(campo)))) {
                huboCambios = true;
                detalle.append("; ").append(campo);
                if (entidad.equals("clientes") && (campo.equals("teléfono") || campo.equals("correo") || campo.equals("dirección"))) {
                    detalle.append(" modificado");
                } else {
                    detalle.append(": ").append(valor(anterior.get(campo))).append(" → ").append(valor(nuevo.get(campo)));
                }
            }
        }
        if (!huboCambios) detalle.append("; sin cambios en los campos registrados");
        return limitar(detalle.toString());
    }

    private static String etiqueta(String entidad) {
        if (entidad.equals("productos")) return "Producto";
        if (entidad.equals("clientes")) return "Cliente";
        return "Categoría";
    }
    private static String nombre(Map<String, String> valores) {
        if (valores == null) return "no disponible";
        String nombre = valor(valores.get("nombre"));
        String apellido = valores.get("apellido");
        return apellido == null || apellido.isBlank() ? nombre : nombre + " " + valor(apellido);
    }
    private static String normalizar(String v) { return v == null ? "" : v; }
    private static String valor(String v) {
        if (v == null || v.isBlank()) return "(vacío)";
        String limpio = v.replace('\n', ' ').replace('\r', ' ');
        return recortar(limpio, 55);
    }
    static String limitar(String s) { return recortar(s, 255); }
    private static String recortar(String s, int max) {
        if (s.codePointCount(0, s.length()) <= max) return s;
        return s.substring(0, s.offsetByCodePoints(0, max - 1)) + "…";
    }
}

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import restaurante.dao.mysql.ProductoDAOMySQL;
import restaurante.modelo.Categoria;
import restaurante.modelo.Producto;

/** Prueba con JDBC simulado; no conecta con una base real. */
public class VerificaIdProducto {
    static Object idAsignado;
    static Object valor(Class<?> t) {
        if (!t.isPrimitive()) return null;
        if (t == boolean.class) return false;
        if (t == int.class) return 0;
        if (t == long.class) return 0L;
        return null;
    }
    @SuppressWarnings("unchecked") static <T> T proxy(Class<T> t, InvocationHandler h) {
        return (T) Proxy.newProxyInstance(t.getClassLoader(), new Class<?>[]{t}, h);
    }
    static ResultSet clave() {
        int[] fila={0};
        return proxy(ResultSet.class,(o,m,a)-> {
            if (m.getName().equals("next")) return fila[0]++==0;
            if (m.getName().equals("getInt")) return 42;
            return valor(m.getReturnType());
        });
    }
    static Connection conexion() {
        return proxy(Connection.class,(o,m,a)-> {
            if (!m.getName().equals("prepareStatement")) return valor(m.getReturnType());
            return proxy(PreparedStatement.class,(x,met,par)-> {
                if (met.getName().equals("setNull") && ((Integer)par[0])==1) idAsignado=null;
                if (met.getName().equals("setInt") && ((Integer)par[0])==1) idAsignado=par[1];
                if (met.getName().equals("executeUpdate")) return 1;
                if (met.getName().equals("getGeneratedKeys")) return clave();
                return valor(met.getReturnType());
            });
        });
    }
    static class DriverSimulado implements Driver {
        public Connection connect(String u, Properties p){return conexion();}
        public boolean acceptsURL(String u){return u.startsWith("jdbc:mysql:");}
        public DriverPropertyInfo[] getPropertyInfo(String u,Properties p){return new DriverPropertyInfo[0];}
        public int getMajorVersion(){return 1;} public int getMinorVersion(){return 0;}
        public boolean jdbcCompliant(){return false;}
        public java.util.logging.Logger getParentLogger(){return java.util.logging.Logger.getGlobal();}
    }
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        for (Enumeration<Driver> d=DriverManager.getDrivers(); d.hasMoreElements();) DriverManager.deregisterDriver(d.nextElement());
        DriverManager.registerDriver(new DriverSimulado());
        Categoria c=new Categoria(1,"Prueba","");
        Producto automatico=new Producto(0,"Prueba","",10,true,c);
        new ProductoDAOMySQL().guardar(automatico);
        if (idAsignado!=null || automatico.getId()!=42) throw new AssertionError("Falla ID automático");
        Producto manual=new Producto(7,"Prueba","",10,true,c);
        new ProductoDAOMySQL().guardar(manual);
        if (!Integer.valueOf(7).equals(idAsignado) || manual.getId()!=7) throw new AssertionError("Falla ID manual");
        System.out.println("OK: ID automático y manual con JDBC simulado");
    }
}

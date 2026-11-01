/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static ConexionDB instancia;

    private final String url;
    private final String user;
    private final String password;

    private ConexionDB(Properties props) {
        String host = props.getProperty("db.host", "localhost");
        String port = props.getProperty("db.port", "3306");
        String name = props.getProperty("db.name", "restaurante_intermedio");
        this.user = props.getProperty("db.user", "root");
        this.password = props.getProperty("db.password", "");

        this.url = "jdbc:mysql://" + host + ":" + port + "/" + name
                + "?useSSL=false&serverTimezone=America/Lima"
                + "&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    }

    public static synchronized ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB(cargarProps());
        }
        return instancia;
    }

    private static Properties cargarProps() {
        Properties p = new Properties();
        try (InputStream is = ConexionDB.class.getResourceAsStream("/restaurante/config/db.properties")) {
            if (is == null) {
                throw new IllegalStateException("No se encontró db.properties en resources.");
            }
            p.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando db.properties", e);
        }
        return p;
    }

    /**
     * Abre una nueva conexión a la base de datos del sistema. El usuario es
     * responsable de cerrarla (try-with-resources).
     */
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Conexión al servidor MySQL SIN base de datos. Útil para CREATE DATABASE.
     */
    public Connection conectarServidor() throws SQLException {
        String base = url.substring(0, url.indexOf("/", "jdbc:mysql://".length()));
        String sinDb = base + "/?useSSL=false&serverTimezone=America/Lima"
                + "&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
        return DriverManager.getConnection(sinDb, user, password);
    }

    public String getDatabaseName() {
        int q = url.indexOf('?');
        String sinQuery = (q == -1) ? url : url.substring(0, q);
        int slash = sinQuery.lastIndexOf('/');
        return sinQuery.substring(slash + 1);
    }
}

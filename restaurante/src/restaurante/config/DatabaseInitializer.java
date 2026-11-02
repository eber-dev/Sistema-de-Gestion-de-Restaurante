/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    public static void inicializar() {
        try {
            crearBaseDatosSiNoExiste();
            boolean baseVacia = baseSinTablas();
            ejecutarScript("01_schema.sql");
            if (baseVacia) { ejecutarScript("02_seed.sql"); }
            else { System.out.println("[DB] Esquema existente: se omiten datos de ejemplo."); }
            System.out.println("[DB] Base de datos lista.");
        } catch (Exception e) {
            System.err.println("[DB] Error inicializando: " + e.getMessage());
            throw new RuntimeException("No se pudo inicializar la base de datos", e);
        }
    }

    private static void crearBaseDatosSiNoExiste() throws Exception {
        ConexionDB db = ConexionDB.getInstancia();
        String nombre = db.getDatabaseName();

        try (Connection cn = db.conectarServidor(); Statement st = cn.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS `" + nombre + "` "
                    + "CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
            st.executeUpdate(sql);
        }
    }

    private static boolean baseSinTablas() throws Exception {
        try (Connection cn = ConexionDB.getInstancia().conectar();
             java.sql.ResultSet rs = cn.getMetaData().getTables(cn.getCatalog(), null, "%", new String[]{"TABLE"})) {
            return !rs.next();
        }
    }

    private static void ejecutarScript(String archivo) throws Exception {
        String path = "/restaurante/config/sql/" + archivo;
        InputStream is = DatabaseInitializer.class.getResourceAsStream(path);
        if (is == null) {
            throw new IllegalStateException("No se encontró script: " + path);
        }

        List<String> sentencias = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String limpia = linea.trim();
                if (limpia.isEmpty() || limpia.startsWith("--")) {
                    continue;
                }
                buffer.append(linea).append("\n");
                if (limpia.endsWith(";")) {
                    String sql = buffer.toString().trim();
                    sql = sql.substring(0, sql.length() - 1);
                    sentencias.add(sql);
                    buffer.setLength(0);
                }
            }
        }

        try (Connection cn = ConexionDB.getInstancia().conectar(); Statement st = cn.createStatement()) {
            for (String sql : sentencias) {
                st.execute(sql);
            }
        }
    }
}

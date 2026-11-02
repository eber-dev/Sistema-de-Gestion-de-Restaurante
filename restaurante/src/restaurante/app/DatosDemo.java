/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.app;

import restaurante.modelo.Categoria;
import restaurante.modelo.Cliente;
import restaurante.modelo.Producto;

/**
 *
 * @author EBER
 */
//Datos de prueba
public final class DatosDemo {

    private DatosDemo() {
    }

    public static void cargar() {

        Categoria bebidas = new Categoria(1, "Bebidas", "Bebidas frías y calientes");
        Categoria entradas = new Categoria(2, "Entradas", "Platos de entrada");
        Categoria platos = new Categoria(3, "Platos Fuertes", "Platos principales");
        Categoria postres = new Categoria(4, "Postres", "Postres y dulces");

        Aplicacion.categoria().guardar(bebidas);
        Aplicacion.categoria().guardar(entradas);
        Aplicacion.categoria().guardar(platos);
        Aplicacion.categoria().guardar(postres);

        Aplicacion.cliente().guardar(new Cliente(
                1, "Juan", "Pérez", "987654321",
                "juan.perez@mail.com", "Av. Los Álamos 123"));

        Aplicacion.cliente().guardar(new Cliente(
                2, "María", "Gómez", "912345678",
                "maria.gomez@mail.com", "Jr. Las Flores 456"));

        Aplicacion.cliente().guardar(new Cliente(
                3, "Carlos", "Ramírez", "999888777",
                "carlos.ramirez@mail.com", "Calle Sol 789"));

        Aplicacion.producto().guardar(new Producto(
                1, "Ceviche Clásico",
                "Pescado fresco en leche de tigre",
                25.50, true, entradas));

        Aplicacion.producto().guardar(new Producto(
                2, "Lomo Saltado",
                "Carne salteada con papas y arroz",
                32.00, true, platos));

        Aplicacion.producto().guardar(new Producto(
                3, "Inca Kola 500ml",
                "Gaseosa peruana",
                5.00, true, bebidas));

        Aplicacion.producto().guardar(new Producto(
                4, "Suspiro Limeño",
                "Postre tradicional",
                12.00, true, postres));

        Aplicacion.producto().guardar(new Producto(
                5, "Anticucho",
                "Brochetas de corazón",
                18.00, false, entradas));
    }
}

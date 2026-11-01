/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.dao;

import restaurante.dao.mysql.CategoriaDAOMySQL;
import restaurante.dao.mysql.ClienteDAOMySQL;
import restaurante.dao.mysql.ProductoDAOMySQL;
import restaurante.implementacion.CategoriaRepositorioMemoria;
import restaurante.implementacion.ClienteRepositorioMemoria;
import restaurante.implementacion.ProductoRepositorioMemoria;
import restaurante.repositorio.CategoriaRepositorio;
import restaurante.repositorio.ClienteRepositorio;
import restaurante.repositorio.ProductoRepositorio;

/**
 * Abstract Factory + Singleton. Decide si devuelve DAOs MySQL o repositorios en
 * memoria.
 */
public class DAOFactory {

    private static DAOFactory instancia;

    private final boolean usarMySQL;

    private DAOFactory(boolean usarMySQL) {
        this.usarMySQL = usarMySQL;
    }

    public static synchronized DAOFactory getInstancia() {
        if (instancia == null) {
            instancia = new DAOFactory(true); // true = MySQL
        }
        return instancia;
    }

    public ClienteRepositorio crearClienteRepositorio() {
        return usarMySQL ? new ClienteDAOMySQL() : new ClienteRepositorioMemoria();
    }

    public CategoriaRepositorio crearCategoriaRepositorio() {
        return usarMySQL ? new CategoriaDAOMySQL() : new CategoriaRepositorioMemoria();
    }

    public ProductoRepositorio crearProductoRepositorio() {
        return usarMySQL ? new ProductoDAOMySQL() : new ProductoRepositorioMemoria();
    }
}

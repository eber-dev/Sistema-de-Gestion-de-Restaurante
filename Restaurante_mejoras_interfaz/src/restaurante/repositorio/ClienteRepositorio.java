/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.repositorio;

import java.util.List;
import restaurante.modelo.Cliente;

/**
 *
 * @author EBER
 */
public interface ClienteRepositorio {

    void guardar(Cliente cliente);

    void actualizar(Cliente cliente);

    void eliminar(int id);

    Cliente buscarPorId(int id);

    List<Cliente> listar();
}

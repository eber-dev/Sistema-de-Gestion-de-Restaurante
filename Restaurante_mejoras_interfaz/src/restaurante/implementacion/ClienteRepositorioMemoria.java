/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.implementacion;

import java.util.ArrayList;
import java.util.List;
import restaurante.modelo.Cliente;
import restaurante.repositorio.ClienteRepositorio;

/**
 *
 * @author EBER
 */
public class ClienteRepositorioMemoria implements ClienteRepositorio {

    private final List<Cliente> clientes = new ArrayList<>();

    @Override
    public void guardar(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public void actualizar(Cliente cliente) {

        for (int i = 0; i < clientes.size(); i++) {

            if (clientes.get(i).getId() == cliente.getId()) {
                clientes.set(i, cliente);
                return;
            }
        }
    }

    @Override
    public void eliminar(int id) {

        clientes.removeIf(cliente -> cliente.getId() == id);
    }

    @Override
    public Cliente buscarPorId(int id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId() == id) {
                return cliente;
            }
        }

        return null;
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }
}

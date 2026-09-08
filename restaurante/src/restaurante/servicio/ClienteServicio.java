/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.servicio;

import java.util.List;
import restaurante.modelo.Cliente;
import restaurante.repositorio.ClienteRepositorio;

/**
 *
 * @author EBER
 */
public class ClienteServicio {

    private final ClienteRepositorio repositorio;

    public ClienteServicio(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void guardar(Cliente cliente) {

        validarCliente(cliente);

        repositorio.guardar(cliente);
    }

    public void actualizar(Cliente cliente) {

        validarCliente(cliente);

        repositorio.actualizar(cliente);
    }

    public void eliminar(int id) {

        repositorio.eliminar(id);
    }

    public Cliente buscarPorId(int id) {

        return repositorio.buscarPorId(id);
    }

    public List<Cliente> listar() {

        return repositorio.listar();
    }

    private void validarCliente(Cliente cliente) {

        if (cliente.getNombre() == null
                || cliente.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio.");
        }

        if (cliente.getTelefono() == null
                || cliente.getTelefono().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El teléfono es obligatorio.");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package patrones.abstractfactory;

/**
 *
 * @author Loreley
 */

import patrones.factory.PedidoFactory;
import patrones.Builder.AbstractPedidoBuilder;

public interface AbstractFactoryPedido {

    PedidoFactory crearFactory();

    AbstractPedidoBuilder crearBuilder();
}

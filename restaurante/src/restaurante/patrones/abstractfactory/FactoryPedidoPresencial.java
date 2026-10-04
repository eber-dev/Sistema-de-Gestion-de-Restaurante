/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.abstractfactory;

/**
 *
 * @author Loreley
 */

import patrones.factory.PedidoFactory;
import patrones.factory.PedidoFactoryConcreto;
import patrones.Builder.AbstractPedidoBuilder;
import patrones.Builder.PedidoBuilderConcreto;

public class FactoryPedidoPresencial implements AbstractFactoryPedido {

    @Override
    public PedidoFactory crearFactory() {
        return new PedidoFactoryConcreto();
    }

    @Override
    public AbstractPedidoBuilder crearBuilder() {
        return new PedidoBuilderConcreto(crearFactory());
    }
}
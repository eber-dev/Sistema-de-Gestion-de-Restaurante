/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.adapter;

public class PasarelaEfectivo implements PasarelaPago {

    @Override
    public boolean cobrar(double monto) {
        return monto >= 0;
    }
}

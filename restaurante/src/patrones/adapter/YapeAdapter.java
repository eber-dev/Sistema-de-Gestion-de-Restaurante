/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.adapter;

import patrones.adapter.externo.YapeApi;

public class YapeAdapter implements PasarelaPago {

    private static final String CELULAR_PRUEBA = "999999999";

    private final YapeApi yape;

    public YapeAdapter(YapeApi yape) {
        this.yape = yape;
    }

    @Override
    public boolean cobrar(double monto) {
        int centimos = (int) Math.round(monto * 100);
        return yape.enviarPago(CELULAR_PRUEBA, centimos).startsWith("OK");
    }
}

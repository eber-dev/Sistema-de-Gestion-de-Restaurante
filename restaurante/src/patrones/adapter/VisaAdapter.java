/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones.adapter;

import patrones.adapter.externo.VisaApi;

public class VisaAdapter implements PasarelaPago {

    private static final String TARJETA_PRUEBA = "4111-1111-1111-1111";

    private final VisaApi visa;

    public VisaAdapter(VisaApi visa) {
        this.visa = visa;
    }

    @Override
    public boolean cobrar(double monto) {
        return visa.autorizarTransaccion(TARJETA_PRUEBA, monto, "PEN") == 0;
    }
}

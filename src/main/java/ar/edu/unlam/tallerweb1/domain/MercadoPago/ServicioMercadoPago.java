package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import com.mercadopago.resources.preference.Preference;

public interface ServicioMercadoPago {

    Preference generarPago(Pago sandPagar);

}
package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import com.mercadopago.resources.preference.Preference;

public interface ServicioMercadoPago {

    Preference generarPago(Long alquilerId);
}

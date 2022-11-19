package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import com.mercadopago.resources.preference.Preference;

import java.util.List;

public interface ServicioMercadoPago {

    Preference generarPago(Pago sandPagar);

}
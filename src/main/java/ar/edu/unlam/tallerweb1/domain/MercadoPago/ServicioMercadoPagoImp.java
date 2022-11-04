package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.Sandwich.RepositorioSandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("servicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImp implements ServicioMercadoPago {
    private RepositorioSandwich repositorioSandwich;


    @Autowired
    public ServicioMercadoPagoImp(RepositorioSandwich repositorioSandwich) {
        this.repositorioSandwich = repositorioSandwich;
    }
    @Override
    public Preference generarPago(Long sandId) {
        // Ac� va la clave privada(Access Token) que se genera en la cuenta de MercadoPago del vendedor
        MercadoPagoConfig.setAccessToken("APP_USR-7774580668311517-110115-cdcfe731c5f7fae554a7073a2fbe2f54-97526199");
        Sandwich sandwich = this.repositorioSandwich.obtenerSandwichPorId(sandId);
        // Crea datos del cliente
        PreferenceClient client = new PreferenceClient();

        // Crea un �tem en la preferencia para el pago
        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title(sandwich.getNombre())
                        .quantity(1)
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal(this.obtenerMonto(sandwich.getIngrediente())))
                        .build();

        items.add(item);
		/* Urls propias de mi app en spring a las que va a
		redireccionar despues del pago si es exitoso o no */
        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success("http://localhost:8080/proyecto-limpio-spring_war_exploded/alerta_exitosa")
                        .failure("http://localhost:8080/proyecto-limpio-spring_war_exploded/alerta_exitosa")
                        .build();

        // Genera la petici�n para la preferencia
        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .externalReference("sandId")
                .build();

        Preference preference = null;

        try {
			/* Creo que esto hace una petici�n post a un endpoint de mercadopago
			 para generar el pago en la cuenta del vendedor y devuelve el id y datos
			 de la preferencia. Despu�s desde el frontend se genera el bot�n del pago
			 con ese id de preferencia. Con eso redirigo al usuario a un sitio de mercado
			 pago para que pague con su tarjeta el item solicitado */

            preference = client.create(request);

        } catch (MPException | MPApiException e) {
            e.printStackTrace();
        }
        return preference;
    }

    private Float obtenerMonto(Set<Ingrediente> lista){
        Float imp = 0F;
        for (Ingrediente ing: lista) {
            imp += ing.getPrecio();
        }
        return imp;
    }
}

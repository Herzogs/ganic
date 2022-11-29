package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.Excepciones.ErrorAlRealizarCompraException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.RepositorioSandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.PaymentRefund;
import com.mercadopago.resources.preference.Preference;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service("servicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImp implements ServicioMercadoPago {

    private final Dotenv dotEnv = Dotenv.load();

    @Autowired
    public ServicioMercadoPagoImp(RepositorioSandwich repo) {

    MercadoPagoConfig.setAccessToken(dotEnv.get("MPACCESSTOKEN"));
        MercadoPagoConfig.setConnectionRequestTimeout(2000);
        MercadoPagoConfig.setSocketTimeout(2000);
        MercadoPagoConfig.setLoggingLevel(Level.FINEST);
    }

    @Override
    public Preference generarPago(Pago sandPagar) throws ErrorAlRealizarCompraException {
        // Crea datos del cliente
        PreferenceClient client = new PreferenceClient();

        // Crea un �tem en la preferencia para el pago
        List<PreferenceItemRequest> items = new ArrayList<>();
        sandPagar.getListaCobrar().forEach(pago -> {
            items.add(PreferenceItemRequest.builder()
                            .id(pago.getSandwich().getIdSandwich().toString())
                            .title(pago.getSandwich().getNombre())
                            .quantity(pago.getCantidad())
                            .description(pago.getSandwich().getDescripcion())
                            .currencyId("ARS")
                            .unitPrice(BigDecimal.valueOf(pago.getSandwich().obtenerMonto()))
                            .build());
        });

        items.add(PreferenceItemRequest.builder()
                        .id("123")
                        .title("Recargo")
                        .quantity(1)
                        .description("Recargo De Envio")
                        .currencyId("ARS")
                        .unitPrice(BigDecimal.valueOf(sandPagar.getRecargo()))
                        .build());

		/* Urls propias de mi app en spring a las que va a
		redireccionar despues del pago si es exitoso o no */
        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success("http://localhost:8080/proyecto_limpio_spring_war_exploded/alerta_exitosa")
                        .failure("http://localhost:8080/proyecto_limpio_spring_war_exploded/alerta_fallo")
                        .build();

        // Genera la peticion para la preferencia
        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .binaryMode(true)
                .backUrls(backUrls)
                .externalReference("default")
                .build();

        Preference preference = null;

        try {
			/* Creo que esto hace una petici�n post a un endpoint de mercadopago
			 para generar el pago en la cuenta del vendedor y devuelve el id y datos
			 de la preferencia. Despu�s desde el frontend se genera el bot�n del pago
			 con ese id de preferencia. Con eso redirigo al usuario a un sitio de mercado
			 pago para que pague con su tarjeta el item solicitado */


            preference = client.create(request);
            items.clear();

        } catch (MPException | MPApiException e) {
            throw new ErrorAlRealizarCompraException("No se pudo realizar la compra");
        }
        return preference;
    }

    @Override
    public String reembolso(Compra compraAReembolsar){
        PaymentRefundClient client = new PaymentRefundClient();
        PaymentRefund paymentRefund = null;
        try {
            paymentRefund = client.refund(compraAReembolsar.getPayment());
        } catch (MPException e) {
            System.err.println(e.getMessage());
        } catch (MPApiException e){
            System.err.println(e.getApiResponse().getContent() + " " + e.getApiResponse().getStatusCode()  );
        }

        return paymentRefund.getStatus();
    }
}

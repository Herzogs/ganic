package ar.edu.unlam.tallerweb1.delivery.Pago;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPagoTest extends SpringTest {

    private ServicioEmail servicioEmail;
    private ServicioLogin servicioLogin;
    private ServicioMercadoPago servicioMercadoPago;
    private HttpSession session;
    private HttpServletRequest request;

    @Before
    public void init(){
        this.servicioEmail = new ServicioEmailImp();
        this.servicioLogin = mock(ServicioLogin.class);
        this.servicioMercadoPago = mock(ServicioMercadoPago.class);
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(this.session);
    }

    @Test
    public void prueboQueAlQuererPagarUnSandwichEsteMeEnvieALaPaginadePago(){
        Sandwich sandwich = dadoQueTengoUnSandwich();
    }

    private Sandwich dadoQueTengoUnSandwich() {
        Sandwich sandwich = new Sandwich();
        sandwich.setNombre("Test");
        sandwich.setDescripcion("test");
        sandwich.setEnPromocion(false);
        return sandwich;
    }

}

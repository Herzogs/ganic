package ar.edu.unlam.tallerweb1.delivery.detalleCarro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorDetalleCarro;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.carro.ServicioCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.ServicioDetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorDetalleCarroTest extends SpringTest {

    private ControladorDetalleCarro controlador;

    private ServicioDetalleCarro servicioDetalle;
    private HttpServletRequest request;
    private ServicioLogin servicioLogin;
    private ServicioSandwich servicioSandwich;
    private ServicioCarro servicioCarro;

    @Before
public void init(){
        this.servicioDetalle= mock(ServicioDetalleCarro.class);
        this.request = mock(HttpServletRequest.class);
        this.servicioSandwich=mock(ServicioSandwich.class);
        this.servicioCarro= mock(ServicioCarro.class);
        this.controlador= new ControladorDetalleCarro(servicioCarro,servicioSandwich,servicioDetalle,servicioLogin);
        HttpSession session = mock(HttpSession.class);
        this.servicioLogin = mock(ServicioLogin.class);
        when(this.request.getSession()).thenReturn(session);

    }
    @Test
    public void test(){

    }
}

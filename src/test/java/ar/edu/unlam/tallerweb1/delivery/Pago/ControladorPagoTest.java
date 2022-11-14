package ar.edu.unlam.tallerweb1.delivery.Pago;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorPago;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPagoTest extends SpringTest {

    private ServicioEmail servicioEmail;

    private ServicioCompra servicioCompra;
    private ServicioLogin servicioLogin;
    private ServicioMercadoPago servicioMercadoPago;

    private ControladorPago controladorPago;
    private HttpSession session;
    private HttpServletRequest request;

    @Before
    public void init(){
        this.servicioEmail = new ServicioEmailImp();
        this.servicioCompra = mock(ServicioCompra.class);
        this.servicioLogin = mock(ServicioLogin.class);
        this.servicioMercadoPago = mock(ServicioMercadoPago.class);
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        this.controladorPago = new ControladorPago(servicioLogin,servicioMercadoPago,servicioCompra);
        when(this.request.getSession()).thenReturn(this.session);
    }

    @Test
    public void prueboQueAlQuererPagarUnSandwichEsteMeEnvieALaPaginadePago(){
        Sandwich sandwich = dadoQueTengoUnSandwich();
        when(this.request.getSession().getAttribute("SANDWICH_ELEGIDO")).thenReturn(sandwich);
        when(this.request.getSession().getAttribute("RECARGO")).thenReturn(0F);
        Pago pago = dadoQueTengoUnPago(sandwich);
        ModelAndView model = this.controladorPago.pagarSandwich(this.request);
        assertThat(model.getModel().get("nombre")).isEqualTo(sandwich.getNombre());
    }

    @Test
    public void prueboQueAlPagarCorrectamenteMeMuestraUnMsgExito() throws UsuarioInvalidoException {
        Usuario user = dadoQueTengoUnUsuario();
        when(this.request.getSession().getAttribute("id")).thenReturn(user.getId());
        when(this.request.getSession().getAttribute("DESTINO")).thenReturn("test,test,test,test,test");
        when(this.servicioLogin.consultarPorID(user.getId())).thenReturn(user);
        this.controladorPago.setPago(dadoQueTengoUnPago(dadoQueTengoUnSandwich()));
        when(this.request.getSession().getAttribute("RECARGO")).thenReturn(2F);
        ModelAndView model = this.controladorPago.pagoCorrecto("Tarjeta",this.request);
        assertThat(model.getModel().get("msg")).isEqualTo("Se ha enviado el email de confirmación");
    }

    @Test
    public void prueboQueAlPagarCorrectamentePeroNoHayaUsuarioMeMuestraUnMsgERROR() throws UsuarioInvalidoException {
        Usuario user = dadoQueTengoUnUsuario();
        when(this.request.getSession().getAttribute("id")).thenReturn(user.getId());
        when(this.request.getSession().getAttribute("DESTINO")).thenReturn("test,test,test,test,test");
        when(this.servicioLogin.consultarPorID(user.getId())).thenThrow(new UsuarioInvalidoException("No existe usuario"));
        this.controladorPago.setPago(dadoQueTengoUnPago(dadoQueTengoUnSandwich()));
        when(this.request.getSession().getAttribute("RECARGO")).thenReturn(2F);
        ModelAndView model = this.controladorPago.pagoCorrecto("Tarjeta",this.request);
        assertThat(model.getModel().get("error")).isEqualTo("a ocurrido un error en el proceso de envio");
    }









    private Usuario dadoQueTengoUnUsuario() {
        Usuario user = new Usuario();
        user.setId(1L);
        user.setNombre("Damian");
        user.setApellido("Spizzini");
        user.setDireccion("xxxxxxxxxx");
        user.setEmail("crisefeld@gmail.com");
        user.setPreferencia("SinRestriccion");
        user.setPassword("123");
        return user;
    }

    private Pago dadoQueTengoUnPago(Sandwich sandwich) {
        Pago pago = new Pago();
        pago.setSandwich(sandwich);
        pago.setImpTot(sandwich.obtenerMonto());
        return pago;
    }

    private List<Ingrediente> dadoQueTengoUnaListaDeIngredientesSinRestriccion(){
        List<Ingrediente> list = new ArrayList<>();
        list.add(new Ingrediente(1L,"test",10F,1,"test","SinRestriccion"));
        list.add(new Ingrediente(2L,"test",10F,2,"test","SinRestriccion"));
        list.add(new Ingrediente(3L,"test",10F,3,"test","SinRestriccion"));
        return list;
    }

    private Sandwich dadoQueTengoUnSandwich() {
        Sandwich sandwich = new Sandwich();
        sandwich.setIdSandwich(1L);
        sandwich.setNombre("Test");
        sandwich.setDescripcion("test");
        sandwich.setEnPromocion(false);
        sandwich.setEsApto("SinRestriccion");
        dadoQueTengoUnaListaDeIngredientesSinRestriccion().forEach(ingrediente -> sandwich.agregarIngrediente(ingrediente));
        return sandwich;
    }

}

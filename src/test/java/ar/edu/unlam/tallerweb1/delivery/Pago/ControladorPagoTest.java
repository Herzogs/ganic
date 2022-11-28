package ar.edu.unlam.tallerweb1.delivery.Pago;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorPago;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.MpEntidad;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.ServicioDetalleCarro;
import ar.edu.unlam.tallerweb1.domain.factura.ServicioFactura;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPagoTest extends SpringTest {

    private ServicioEmail servicioEmail;

    private ServicioCompra servicioCompra;
    private ServicioLogin servicioLogin;
    private ServicioMercadoPago servicioMercadoPago;
    private ServicioDetalleCarro servicioDetalleCarro;

    private ServicioFactura servicioFactura;

    private ControladorPago controladorPago;
    private HttpServletRequest request;

    @Before
    public void init(){
        this.servicioEmail = new ServicioEmailImp();
        this.servicioFactura = mock(ServicioFactura.class);
        this.servicioCompra = mock(ServicioCompra.class);
        this.servicioLogin = mock(ServicioLogin.class);
        this.servicioMercadoPago = mock(ServicioMercadoPago.class);
        this.servicioDetalleCarro = mock(ServicioDetalleCarro.class);
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        this.controladorPago = new ControladorPago(servicioLogin,servicioMercadoPago,servicioCompra,servicioDetalleCarro,this.servicioFactura);
        when(this.request.getSession()).thenReturn(session);
    }

    @Test
    public void prueboQueAlQuererPagarUnSandwichEsteMeEnvieALaPaginadePago(){
        Sandwich sandwich = dadoQueTengoUnSandwich();
        cuandoLePidoAlServletQueMeDevuelvaElSandwichElegido(sandwich);
        cuandoLePidaAlServLetQueMeDevuelvaDe("DESTINO","test,test,test,test,test");
        cuandoLePidaAlServLetQueMeDevuelvaElRecargo("RECARGO", 0F);
        cuandoLLamoAlControladorParaQueMePrepareElPagoDel(sandwich);
        ModelAndView model = cuandoLLamoAlControladorDePagoParaPagasElSandwich();
        entoncesVerificoQueElMontoFinalSeaElMismoDelQueSeleccione(sandwich, model);
    }


    @Test
    public void prueboQueAlPagarCorrectamenteMeMuestraUnMsgExito() throws UsuarioInvalidoException {
        Usuario user = dadoQueTengoUnUsuario();
        Sandwich sandwich = dadoQueTengoUnSandwich();
        cuandoLePidaAlSerLetQueMeDevuelvaElIdGuardado(user);
        cuandoLePidaAlServLetQueMeDevuelvaDe("DESTINO", "test,test,test,test,test");
        cuandoLeSoliciteAlServicioLoginPorElUsuario(user);
        cargoLaClasePagoPAraQuePuedaOperarConMP();
        cuandoLePidaAlServLetQueMeDevuelvaDe("DONDE_VENGO", "NORMAL");
        cuandoLePidaAlServLetQueMeDevuelvaElRecargo("RECARGO", 2F);
        cuandoLLamoAlControladorParaQueMePrepareElPagoDel(sandwich);
        cuandoLePidaAlServicioDeFacturaQueMeGenereLaFacturaDelPedido();
        ModelAndView model = cuandoElControladorDePAgoVerificoMiPago();
        entoncesVerificoQueElMensajeDevueltoSeaElEsperado(model, "msg", "Se ha enviado el email de confirmación");
    }

    @Test
    public void prueboQueAlPagarCorrectamentePeroNoHayaUsuarioMeMuestraUnMsgERROR() throws UsuarioInvalidoException {
        Usuario user = dadoQueTengoUnUsuario();
        cuandoLePidaAlSerLetQueMeDevuelvaElIdGuardado(user);
        cuandoLePidaAlServLetQueMeDevuelvaDe("DESTINO", "test,test,test,test,test");
        when(this.servicioLogin.consultarPorID(user.getId())).thenThrow(new UsuarioInvalidoException("No existe usuario"));
        cargoLaClasePagoPAraQuePuedaOperarConMP();
        cuandoLePidaAlServLetQueMeDevuelvaElRecargo("RECARGO", 2F);
        ModelAndView model = cuandoElControladorDePAgoVerificoMiPago();
        entoncesVerificoQueElMensajeDevueltoSeaElEsperado(model, "error", "a ocurrido un error en el proceso de envio");
    }


    private void cuandoLePidaAlServLetQueMeDevuelvaDe(String opcionGuardado, String opcionADevolver) {
        when(this.request.getSession().getAttribute(opcionGuardado)).thenReturn(opcionADevolver);
    }

    private void cuandoLePidoAlServletQueMeDevuelvaElSandwichElegido(Sandwich sandwich) {
        when(this.request.getSession().getAttribute("SANDWICH_ELEGIDO")).thenReturn(sandwich);
    }

    private void cuandoLePidaAlServLetQueMeDevuelvaElRecargo(String RECARGO, float t) {
        when(this.request.getSession().getAttribute(RECARGO)).thenReturn(t);
    }

    private static void entoncesVerificoQueElMontoFinalSeaElMismoDelQueSeleccione(Sandwich sandwich, ModelAndView model) {
        assertThat(model.getModel().get("montoFinal")).isEqualTo(sandwich.obtenerMonto());
    }

    private ModelAndView cuandoLLamoAlControladorDePagoParaPagasElSandwich() {
        ModelAndView model = this.controladorPago.pagarSandwich(this.request);
        return model;
    }

    private void cuandoLLamoAlControladorParaQueMePrepareElPagoDel(Sandwich sandwich) {
        this.controladorPago.setPago(dadoQueTengoUnPago(sandwich));
    }

    private ModelAndView cuandoElControladorDePAgoVerificoMiPago() {
        return this.controladorPago.pagoCorrecto("Tarjeta",1L,this.request);
    }

    private void cuandoLePidaAlServicioDeFacturaQueMeGenereLaFacturaDelPedido() {
        when(this.servicioFactura.generarFactura(any(Pago.class),any())).thenReturn("test");
    }

    private void cargoLaClasePagoPAraQuePuedaOperarConMP() {
        this.controladorPago.setPago(dadoQueTengoUnPago(dadoQueTengoUnSandwich()));
    }

    private void cuandoLeSoliciteAlServicioLoginPorElUsuario(Usuario user) throws UsuarioInvalidoException {
        when(this.servicioLogin.consultarPorID(user.getId())).thenReturn(user);
    }

    private void cuandoLePidaAlSerLetQueMeDevuelvaElIdGuardado(Usuario user) {
        when(this.request.getSession().getAttribute("id")).thenReturn(user.getId());
    }

    private static void entoncesVerificoQueElMensajeDevueltoSeaElEsperado(ModelAndView model, String msg, String expected) {
        assertThat(model.getModel().get(msg)).isEqualTo(expected);
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
        MpEntidad entidad = new MpEntidad();
        entidad.setCantidad(1);
        entidad.setSandwich(sandwich);
        pago.getListaCobrar().add(entidad);
        pago.setImpTot(sandwich.obtenerMonto());
        pago.setRecargo(30F);
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

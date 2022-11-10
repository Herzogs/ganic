package ar.edu.unlam.tallerweb1.delivery.Pago;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorPago;
import ar.edu.unlam.tallerweb1.delivery.FormularioDePago;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPagoTest extends SpringTest {

    private ServicioEmail servicioEmail;
    private ServicioLogin servicioLogin;

    private ServicioSandwich servicioSandwich;
    private ServicioCompra servicioCompra;

    private ControladorPago controladorPago;
    private HttpServletRequest request;

    @Before
    public void init(){
        this.servicioEmail = new ServicioEmailImp();
        this.servicioLogin = mock(ServicioLogin.class);
        this.servicioSandwich = mock(ServicioSandwich.class);
        this.servicioCompra = mock(ServicioCompra.class);
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(session);
        this.controladorPago = new ControladorPago(servicioLogin,servicioCompra,servicioSandwich);
    }

    @Test
    public void prueboQueAlPagarUnSandwichMeMuestreUnResumenDeSusDatos(){
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Usuario user = dadoQueTengoUnCliente();
        cuandoLeDigoAlServletQueDevuelvaUnSandwichQueDevuelva(sandwich);
        cuandoLePidaAlServletQueDevuelvaElRecargoDevuelva(100F);
        ModelAndView model = cuandoSolicitoAlControladorQueMeMuestreLosDatosDelSandwichSeleccionado();
        entoncesVerficoQueSeaElSandwichSolicitado(sandwich, model);
    }

    @Test
    public void prueboQueSiHayErroresEnLaTarjetaIngresadaMeRedirijeALaVistaDePagoYMuestraMsgError(){
        FormularioDePago fp = dadoQueTengoUnaTarjetaIngresadaConErrores();
        Sandwich sandwich = dadoQueTengoUnSandwich();
        cuandoLeDigoAlServletQueDevuelvaUnSandwichQueDevuelva(sandwich);
        cuandoLePidaAlServletQueDevuelvaElRecargoDevuelva(100F);
        ModelAndView model = cuandoLeEnvioAlControladorParaQueVerifiqueLosDatosDeLaTarjeta(fp,this.request);
        entoncesVerificoQueMeDevuelvaElMensajeDeError(model);
    }

    @Test
    public void prueboQueSiNoHayErroresEnLaTarjetaIngresadaMeRedirijeALaVistaDePagoCorrecto(){
        FormularioDePago fp = dadoQueTengoUnaTarjetaIngresadaSinErrores();
        Sandwich sandwich = dadoQueTengoUnSandwich();
        cuandoLeDigoAlServletQueDevuelvaUnSandwichQueDevuelva(sandwich);
        cuandoLePidaAlServletQueDevuelvaElRecargoDevuelva(100F);
        ModelAndView model = cuandoLeEnvioAlControladorParaQueVerifiqueLosDatosDeLaTarjeta(fp, this.request);
        entoncesVerificoQueMeRedirijaALaVistaDe(model,"pago");
    }

    private void entoncesVerificoQueMeRedirijaALaVistaDe(ModelAndView model, String alerta_exitosa) {
        assertThat(model.getViewName()).isEqualTo(alerta_exitosa);
    }

    private void entoncesVerificoQueMeDevuelvaElMensajeDeError(ModelAndView model) {
        assertThat(model.getModel().get("msg")).isEqualTo("valores incorrectos en el ingreso de la tarjeta");
    }

    private ModelAndView cuandoLeEnvioAlControladorParaQueVerifiqueLosDatosDeLaTarjeta(FormularioDePago fp, HttpServletRequest request) {
        return this.controladorPago.validarPago(fp, request);
    }

    private FormularioDePago dadoQueTengoUnaTarjetaIngresadaConErrores() {
        FormularioDePago fp = new FormularioDePago();
        fp.setNroTarjeta("111111111");
        fp.setVencTarjeta("2022-11-07");
        fp.setCodSeguridad("123");
        return fp;
    }

    private FormularioDePago dadoQueTengoUnaTarjetaIngresadaSinErrores() {
        FormularioDePago fp = new FormularioDePago();
        fp.setNroTarjeta("1111111111111111");
        fp.setVencTarjeta("2022-11-08");
        fp.setCodSeguridad("123");
        return fp;
    }


    private static void entoncesVerficoQueSeaElSandwichSolicitado(Sandwich sandwich, ModelAndView model) {
        assertThat(model.getModel().get("nombre")).isEqualTo(sandwich.getNombre());
    }

    private ModelAndView cuandoSolicitoAlControladorQueMeMuestreLosDatosDelSandwichSeleccionado() {
        ModelAndView model = this.controladorPago.pagarSandwich(this.request);
        return model;
    }

    private void cuandoLePidaAlServletQueDevuelvaElRecargoDevuelva(Float recargo) {
        when(this.request.getSession().getAttribute("RECARGO")).thenReturn(recargo);
    }

    private void cuandoLeDigoAlServletQueDevuelvaUnSandwichQueDevuelva(Sandwich sandwich) {
        when(this.request.getSession().getAttribute("SANDWICH_ELEGIDO")).thenReturn(sandwich);
    }

    private Usuario dadoQueTengoUnCliente() {
        Usuario us = new Usuario();
        us.setEmail("test@test.com");
        us.setPassword("123");
        return us;
    }

    private Sandwich dadoQueTengoUnSandwich() {
        Sandwich sandwich = new Sandwich();
        sandwich.setNombre("test");
        sandwich.setDescripcion("test");
        sandwich.setEnPromocion(false);
        sandwich.setEsApto("SinRestriccion");
        Ingrediente ing = new Ingrediente(1L,"test",1F,1,"test","SinRestricciones");
        sandwich.agregarIngrediente(ing);
        return sandwich;
    }
}

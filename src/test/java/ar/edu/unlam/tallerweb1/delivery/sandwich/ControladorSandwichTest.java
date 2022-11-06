package ar.edu.unlam.tallerweb1.delivery.sandwich;


import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorSandwich;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Excepciones.NoHaySandwichEnPromocionException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorSandwichTest extends SpringTest {

    private ServicioSandwich servicioSandwich;

    private ServicioLogin servicioLogin;

    private ControladorSandwich controladorSandwich;
    private HttpSession session;
    private HttpServletRequest request;

    private ServicioEmail servicioEmail;

    @Before
    public void init() {
        this.servicioSandwich = mock(ServicioSandwich.class);
        this.servicioLogin = mock(ServicioLogin.class);
        this.session = mock(HttpSession.class);
        this.request = mock(HttpServletRequest.class);
        this.controladorSandwich = new ControladorSandwich(this.servicioSandwich,this.servicioLogin);
        this.servicioEmail = mock(ServicioEmail.class);
        when(this.request.getSession()).thenReturn(this.session);
    }

    @Test
    public void queSePuedanMostrtarTodosLosSandwichesEnPromocionConUsuarioNoLoguado() throws NoHaySandwichEnPromocionException {
        List<Sandwich> sandwichList = dadoQueExistenVariosSandwiches();
        cuandoLLamoAlServicioOptengoUnaLIstaDeSandwich(sandwichList);
        ModelAndView modelAndView = cuandoLLamoAlControladorObtengoUnaListaDeSandwichEnPromocion(request);
        comparoLista(sandwichList, (List<Sandwich>) modelAndView.getModel().get("listaEnPromocion"));
    }

    //TODO: modificar COntrolador de sandwich
    @Test
    public void queINoHaySandwichesEnPromocionMeDevuelvaLaExepcionConUSuarioNoLogueado() throws NoHaySandwichEnPromocionException {
        List<Sandwich> sandwichList = new ArrayList<>();
        String msjError = "no hay sandwich disponibles";
        cuandoLLamoAlServicioYNoExistaSandwichEnPromocion(sandwichList);
        ModelAndView modelAndView = cuandoLLamoAlControladorObtengoUnaListaDeSandwichEnPromocion(request);
        ahoraEsperoElMensaje((String) modelAndView.getModel().get("msj"), msjError);
    }

    @Test
    public void queAlSeleccionarUnapreferenciaMeMuestreLosSandwichDeMiPreferencia() throws SandwichNoExistenteException {
        List<Sandwich> sandwichList = dadoQueExistenVariosSandwichesDeUnTipo();
        String preferencia = "Vegano";
        cuandoLLamoAlServicioYFiltroPorPreferencia(preferencia);
        ModelAndView modelAndView = cuandoLLamoAlControladoryFiltroPorPreferencia(preferencia);
        comparoLista(sandwichList, (List<Sandwich>) modelAndView.getModel().get("listaEnPromocion"));
    }
    @Test
    public void queSiFiltroPorPreferenciaYNoHayaSandwichMemUestreUnMensajeDeError() throws SandwichNoExistenteException {
        String msjError = "no hay sandwich disponibles";
        String preferencia = "sin_TACC";
        cuandoLlamoAlServicioYNoHayaListaPorPreferencia(preferencia);
        ModelAndView modelAndView = cuandoLLamoAlControladoryFiltroPorPreferencia(preferencia);
        ahoraEsperoElMensaje((String) modelAndView.getModel().get("msj"), msjError);
    }

    @Test
    public void queSePuedaComprarUnSandwich()throws SandwichNoExistenteException {
        Sandwich sandwich = dadoQueTengoUnSandwichConIngredientes();
        cuandoLLamoAlServicioParaBuscarUnSandwichPor(sandwich.getIdSandwich(),sandwich);
        when((Long)this.request.getSession().getAttribute("id")).thenReturn(1L);
        ModelAndView mav = cuandoLLamoAlControladorParaQueMeEnvieALaConfirmacion(sandwich.getIdSandwich(),request);
        assertThat(mav.getModel().get("IngredientesDelSandwich")).isNotNull();
        assertThat(mav.getModel().get("nombre")).isEqualTo(sandwich.getNombre());
    }

    @Test
    public void queDeMeEnvieUnEmailConLosDatosDelSandwichComprado() throws UsuarioInvalidoException {
        Sandwich sandwichComprado = dadoQueTengoUnSandwichConIngredientes();
        Usuario usuarioRegistrado = dadoQueTengoUnUsuarioRegistrado();
        when((Long)this.request.getSession().getAttribute("id")).thenReturn(usuarioRegistrado.getId());
        cuandoLLamoAlServicioDeUsuarioParaBuscarUnUsuarioPor(usuarioRegistrado.getId(),usuarioRegistrado);
        ModelAndView mav = cuandoLLamoAlControladorDeExitoDeCompraDeSandwich(sandwichComprado.getIdSandwich(),this.request);
        assertThat(mav.getModel().get("msg")).isEqualTo("Se ha enviado el email de confirmación");
    }

    private void cuandoLLamoAlServicioDeUsuarioParaBuscarUnUsuarioPor(Long id, Usuario usuarioRegistrado) throws UsuarioInvalidoException {
        when(this.servicioLogin.consultarPorID(id)).thenReturn(usuarioRegistrado);
    }

    private Usuario dadoQueTengoUnUsuarioRegistrado() {
        Usuario cliente = new Usuario();
        cliente.setId(1L);
        cliente.setNombre("Pepe");
        cliente.setApellido("Argento");
        cliente.setDireccion("dire");
        cliente.setEmail("pepe@racinguista.com");
        return cliente;
    }


    private ModelAndView cuandoLLamoAlControladorDeExitoDeCompraDeSandwich(Long idSandwich, HttpServletRequest request) {
        return this.controladorSandwich.envioDeConfirmacion(idSandwich,request);
    }

    private ModelAndView cuandoLLamoAlControladorParaQueMeEnvieALaConfirmacion(Long idSandwich, HttpServletRequest request) {
        return this.controladorSandwich.confirmarSandwich(idSandwich, request);
    }

    private void cuandoLLamoAlServicioParaBuscarUnSandwichPor(Long idSandwich,Sandwich sandwichEsperado) throws SandwichNoExistenteException {
        when(this.servicioSandwich.obtenerSandwichPorId(idSandwich)).thenReturn(sandwichEsperado);
    }

    private void cuandoLlamoAlServicioYNoHayaListaPorPreferencia(String preferencia) throws SandwichNoExistenteException {
        when(this.servicioSandwich.obtenerTodosLosSandwichesDeUnTipo("sin_TACC")).thenThrow(new SandwichNoExistenteException("no hay sandwich disponibles"));
    }

    private ModelAndView cuandoLLamoAlControladoryFiltroPorPreferencia(String preferencia) {
        return controladorSandwich.mostrarSandwichFiltradosPorPreferencia(preferencia);
    }

    private void cuandoLLamoAlServicioYFiltroPorPreferencia(String preferencia) throws SandwichNoExistenteException {
        when(this.servicioSandwich.obtenerTodosLosSandwichesDeUnTipo(preferencia)).thenReturn(dadoQueExistenVariosSandwichesDeUnTipo());
    }

    private void ahoraEsperoElMensaje(String msj, String msjError) {
        assertThat(msj).isEqualTo(msjError);
    }

    private void cuandoLLamoAlServicioYNoExistaSandwichEnPromocion(List<Sandwich> sandwichList) throws NoHaySandwichEnPromocionException {
        when(servicioSandwich.obtenerTodosLosSandwichesEnPromocion()).thenThrow(new NoHaySandwichEnPromocionException("no hay sandwich"));
    }

    private void comparoLista(List<Sandwich> sandwichList, List<Sandwich> listaEnPromocion) {
        assertThat(sandwichList.size()).isEqualTo(listaEnPromocion.size());
    }

    private ModelAndView cuandoLLamoAlControladorObtengoUnaListaDeSandwichEnPromocion(HttpServletRequest request) {
        return this.controladorSandwich.listarSandwichPorPromocion(request);
    }

    private void cuandoLLamoAlServicioOptengoUnaLIstaDeSandwich(List<Sandwich> sandwichList) throws NoHaySandwichEnPromocionException {
        when(servicioSandwich.obtenerTodosLosSandwichesEnPromocion()).thenReturn(sandwichList);

    }


    private Sandwich dadoQueTengoUnSandwichConIngredientes() {
        Ingrediente ing1 = new Ingrediente(1l, "test", 1F, 1, "test", "Vegano");
        Ingrediente ing2 = new Ingrediente(2l, "test", 1F, 1, "test", "Vegano");
        Set<Ingrediente> listaIngredientes = new LinkedHashSet<>();
        listaIngredientes.add(ing1);
        listaIngredientes.add(ing2);
        Sandwich sandwich = new Sandwich(1l, "sand1", "sand1", true, "Vegano", listaIngredientes);
        return sandwich;
    }

    private List<Sandwich> dadoQueExistenVariosSandwichesDeUnTipo() {
        List<Sandwich> lista = new ArrayList<>();
        Sandwich s1 = new Sandwich(1L, "sandwich1", "sandwich1");
        s1.setEsApto("Vegano");
        Sandwich s2 = new Sandwich(2L, "sandwich2", "sandwich2");
        s1.setEsApto("Vegano");
        lista.add(s1);
        lista.add(s2);

        return lista;
    }

    private List<Sandwich> dadoQueExistenVariosSandwiches() {
        List<Sandwich> lista = new ArrayList<>();
        Sandwich s1 = new Sandwich(1L, "sandwich1", "sandwich1");
        s1.setEsApto("Vegano");
        Sandwich s2 = new Sandwich(2L, "sandwich2", "sandwich2");
        s1.setEsApto("sin_TACC");
        lista.add(s1);
        lista.add(s2);
        return lista;
    }


}

package ar.edu.unlam.tallerweb1.delivery.detalleCarro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorDetalleCarro;
import ar.edu.unlam.tallerweb1.domain.Excepciones.DetalleInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.NoSePudoQuitarException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.carro.ServicioCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.ServicioDetalleCarro;
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
import static org.mockito.Mockito.*;

public class ControladorDetalleCarroTest extends SpringTest {

    private ControladorDetalleCarro controlador;

    private ServicioDetalleCarro servicioDetalle;
    private ServicioLogin servicioLogin;
    private ServicioSandwich servicioSandwich;
    private ServicioCarro servicioCarro;

    private HttpServletRequest request;

    @Before
    public void init() {
        this.servicioDetalle = mock(ServicioDetalleCarro.class);
        this.servicioLogin = mock(ServicioLogin.class);
        this.servicioSandwich = mock(ServicioSandwich.class);
        this.servicioCarro = mock(ServicioCarro.class);
        this.controlador = new ControladorDetalleCarro(servicioCarro, servicioSandwich, servicioDetalle, servicioLogin);
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

    }

    @Test
    public void LuegoDeTenerUnosSandwichSeleccionadosElCarritoMemuestreLosDetalles() throws UsuarioInvalidoException, DetalleInexistenteExeption {
        Usuario user = dadoQueTengoUnUsuario();
        Carro carro = dadoQueTengoUnCarro(1L, user);
        Sandwich sand1 = dadoQueTengoUnSandwich();
        DetalleCarro detalleCarro = dadoQueTengoUnDetalleCarro(carro, sand1);
        List<DetalleCarro> list = dadoQueTengoUnaListaDeDetalles(detalleCarro);
        cuandoLePidaAlServletPorID(user);
        cuandoLePidaAlServicioLoginPorUnID(user);
        cuandoLePidaAlServicioDetalle(list, user);
        ModelAndView mav = cuandoLLamoAlControladorParVerElCarrito(this.request);
        entoncesVerificoQueMeDevuelvaUnaListaDeDetalles(mav, list);
    }

    @Test
    public void probarQueMeTireUnMensajeDeCarritoVacioCuandoNoHubieraDetalles() throws UsuarioInvalidoException, DetalleInexistenteExeption {
        Usuario user = dadoQueTengoUnUsuario();
        cuandoLePidaAlServletPorID(user);
        cuandoLePidaAlServicioLoginPorUnID(user);
        cuandoLePidaAlServicioDetalleQueMeTireLaExcepcion(user);
        ModelAndView mav = cuandoLLamoAlControladorParVerElCarrito(this.request);
        String mensaje = "Carrito vacio";
        entoncesVerificoQueMeDevuelvaMensajeDeError(mav, mensaje);
    }

    @Test
    public void prueboQueAlAgregarALCarritoYExistaSeIncrementeCantidad() throws UsuarioInvalidoException, DetalleInexistenteExeption {
        Usuario user = dadoQueTengoUnUsuario();
        Carro carro = dadoQueTengoUnCarro(1L, user);
        Sandwich sand1 = dadoQueTengoUnSandwich();
        DetalleCarro detalleCarro = dadoQueTengoUnDetalleCarro(carro, sand1);
        List<DetalleCarro> list = dadoQueTengoUnaListaDeDetalles(detalleCarro);
        cuandoLePidaAlServletPorID(user);
        cuandoLePidaAlServicioLoginPorUnID(user);
        cuandoLePidaAlServicioDeCarro(user,carro);
        cuandoLePidaAlServicioDeDetalleQueIncrementeCantidad(1,user,sand1,true);
        ModelAndView mav = cuandoLLamoAlControladorParaQueAgregueAlCarrito(this.request,sand1,1,true);
        entoncesVerificoQueMeMandeALaVistaDeVerCarrito(mav,"redirect:/verCarrito");
        entoncesVerificoQueSeInvocoElMetodoActualizar();
    }

    @Test
    public void prueboQueAlAgregarALCarritoYNoExistaMeLoGuarde() throws UsuarioInvalidoException, DetalleInexistenteExeption {
        Usuario user = dadoQueTengoUnUsuario();
        Carro carro = dadoQueTengoUnCarro(1L, user);
        Sandwich sand1 = dadoQueTengoUnSandwich();
        DetalleCarro detalleCarro = dadoQueTengoUnDetalleCarro(carro, sand1);
        List<DetalleCarro> list = dadoQueTengoUnaListaDeDetalles(detalleCarro);
        cuandoLePidaAlServletPorID(user);
        cuandoLePidaAlServicioLoginPorUnID(user);
        cuandoLePidaAlServicioDetalle(list, user);
        cuandoLePidaAlServicioDeCarro(user,carro);
        cuandoLePidaAlServicioDeDetalleQueIncrementeCantidad(1,user,sand1, false);
        ModelAndView mav = cuandoLLamoAlControladorParaQueAgregueAlCarrito(this.request,sand1,1,true);
        entoncesVerificoQueSeIncovoElMetodoGuardar();
    }


    @Test
    public void LuegoDeTenerUnosCantidadDeSandwixhSelecionadoPuedaBajarLaCantidadDeAlguno() throws UsuarioInvalidoException, SandwichNoExistenteException, NoSePudoQuitarException {
        Usuario user = dadoQueTengoUnUsuario();
        Sandwich sand1 = dadoQueTengoUnSandwich();
        cuandoLePidaAlServletPorID(user);
        cuandoLePidaAlServicioLoginPorUnID(user);
        cuandoLePidaAlServicioDeSandwichObtenerSandwichPorID(sand1);
        cuandoLePidoALControladorQueLeQuiteDelCarrito(this.request,sand1,1);
        entoncesVerificoQueSeInvoqueAlMetodoDecrementarCantidad();
    }

    @Test
    public void siLaCantidadDeSandwichEs1MeTireUnMensajeQueNoSePuedaDecrementar() throws UsuarioInvalidoException, SandwichNoExistenteException, NoSePudoQuitarException {
        Usuario user = dadoQueTengoUnUsuario();
        Sandwich sand1 = dadoQueTengoUnSandwich();
        cuandoLePidaAlServletPorID(user);
        cuandoLePidaAlServicioLoginPorUnID(user);
        cuandoLePidaAlServicioDeSandwichObtenerSandwichPorID(sand1);
        cuandoInvoquenAlMetodoDecrementarCantidadYNoSePuedaTireExcepcion();
        ModelAndView mav = cuandoLePidoALControladorQueLeQuiteDelCarrito(this.request,sand1,1);
        String mensaje = "No se puede quitar, debe eliminar el detalle";
        entoncesVerificoQueMeDevuelvaMensajeDeError(mav, mensaje);
    }

    @Test
    public void cuandoQuieraPagarYTEngaUnaListaDeSAndwichSeleccionadoMeRedirijaADestino() throws UsuarioInvalidoException, DetalleInexistenteExeption {
        String vista = "redirect:/destino";
        Usuario user = dadoQueTengoUnUsuario();
        Carro carro = dadoQueTengoUnCarro(1L, user);
        Sandwich sand1 = dadoQueTengoUnSandwich();
        DetalleCarro detalleCarro = dadoQueTengoUnDetalleCarro(carro, sand1);
        List<DetalleCarro> list = dadoQueTengoUnaListaDeDetalles(detalleCarro);
        cuandoLePidaAlServletPorID(user);
        cuandoLePidaAlServicioLoginPorUnID(user);
        cuandoLePidaAlServicioDetalle(list, user);
        cuandoLePidaAlServicioDeCarro(user,carro);
        cuandoAgregoLaListaDeDetallesAlServLet(list,this.request);
        cuandoAgregoElOrigenDeDondeSeInvocaALServLet("CARRO",this.request);
        ModelAndView mav = cuandoLLamoAlControladorPAraQueGuardeEnElServLetElCarro(this.request);
        entoncesVerificoQueMeRedirijaALaVistaDestino(mav,vista);
    }



    private void entoncesVerificoQueMeRedirijaALaVistaDestino(ModelAndView mav, String vista) {
        assertThat(mav.getViewName()).isEqualTo(vista);
    }

    private ModelAndView cuandoLLamoAlControladorPAraQueGuardeEnElServLetElCarro(HttpServletRequest request) {
        return this.controlador.salvarCarroEnServlet(request);
    }

    private void cuandoAgregoElOrigenDeDondeSeInvocaALServLet(String carro, HttpServletRequest request) {
        request.getSession().setAttribute("DONDE_VENGO","CARRO");
    }

    private void cuandoAgregoLaListaDeDetallesAlServLet(List<DetalleCarro> list, HttpServletRequest request) {
        request.getSession().setAttribute("LISTA_DETALLE",list);
    }

    private void cuandoInvoquenAlMetodoDecrementarCantidadYNoSePuedaTireExcepcion() throws NoSePudoQuitarException {
        when(this.servicioDetalle.decrementarCantidad(any(),any(),any())).thenThrow(new NoSePudoQuitarException("No se pudoquitar"));
    }


    private void entoncesVerificoQueSeInvoqueAlMetodoDecrementarCantidad() throws NoSePudoQuitarException {
        verify(this.servicioDetalle).decrementarCantidad(any(),any(),any());
    }

    private ModelAndView cuandoLePidoALControladorQueLeQuiteDelCarrito(HttpServletRequest request, Sandwich sand1, int i) {
        return this.controlador.quitarDelCarrito(request,sand1.getIdSandwich(),1);
    }

    private void cuandoLePidaAlServicioDeSandwichObtenerSandwichPorID(Sandwich sand1) throws SandwichNoExistenteException {
        when(this.servicioSandwich.obtenerSandwichPorId(sand1.getIdSandwich())).thenReturn(sand1);
    }


    private void entoncesVerificoQueSeInvocoElMetodoActualizar() {
        verify(this.servicioDetalle).incrementarCntidad(anyInt(),any(Usuario.class),any());
    }

    private void entoncesVerificoQueSeIncovoElMetodoGuardar() {
        verify(this.servicioDetalle).guardarDetalle(any(DetalleCarro.class));
    }

    private void entoncesVerificoQueMeMandeALaVistaDeVerCarrito(ModelAndView mav, String s) {
        assertThat(mav.getViewName()).isEqualTo(s);
    }

    private ModelAndView cuandoLLamoAlControladorParaQueAgregueAlCarrito(HttpServletRequest request, Sandwich sand1, int i, boolean b) {
        return this.controlador.agregarAlCarrito(request,sand1.getIdSandwich(),i,b);
    }

    private void cuandoLePidaAlServicioDeDetalleQueIncrementeCantidad(int i, Usuario user, Sandwich sand1, boolean b) {
        when(this.servicioDetalle.incrementarCntidad(i,user,sand1)).thenReturn(b);
    }

    private void cuandoLePidaAlServicioDeCarro(Usuario user, Carro carro) {
        when(this.servicioCarro.obtenerCarroDeCLiente(user)).thenReturn(carro);
    }

    private void entoncesVerificoQueMeDevuelvaMensajeDeError(ModelAndView mav, String mensaje) {
        assertThat((mav.getModel().get("msg"))).isEqualTo(mensaje);
    }

    private void cuandoLePidaAlServicioDetalleQueMeTireLaExcepcion(Usuario user) throws DetalleInexistenteExeption {
        when(this.servicioDetalle.obtenerDetalleDeCarroDeUsuario(user)).thenThrow(new DetalleInexistenteExeption("no hay detalle"));
    }

    private void entoncesVerificoQueMeDevuelvaUnaListaDeDetalles(ModelAndView mav, List<DetalleCarro> list) {
        assertThat(mav.getModel().get("listaDetalle")).isNotNull();
        assertThat((mav.getModel().get("listaDetalle"))).isEqualTo(list);
    }

    private ModelAndView cuandoLLamoAlControladorParVerElCarrito(HttpServletRequest request) {
        return this.controlador.listarCarrito(request);
    }

    private void cuandoLePidaAlServicioDetalle(List<DetalleCarro> list, Usuario user) throws DetalleInexistenteExeption {
        when(this.servicioDetalle.obtenerDetalleDeCarroDeUsuario(user)).thenReturn(list);
    }

    private void cuandoLePidaAlServicioLoginPorUnID(Usuario user) throws UsuarioInvalidoException {
        when(this.servicioLogin.consultarPorID(user.getId())).thenReturn(user);
    }

    private void cuandoLePidaAlServletPorID(Usuario user) {
        when(this.request.getSession().getAttribute("id")).thenReturn(user.getId());
    }

    private List<DetalleCarro> dadoQueTengoUnaListaDeDetalles(DetalleCarro detalleCarro) {
        List<DetalleCarro> list = new ArrayList<>();
        list.add(detalleCarro);
        return list;
    }

    private static DetalleCarro dadoQueTengoUnDetalleCarro(Carro carro, Sandwich sand1) {
        return new DetalleCarro(1L, carro, sand1, 1);
    }

    private Sandwich dadoQueTengoUnSandwich() {
        Ingrediente ing1 = new Ingrediente(1l, "test", 1F, 1, "test", "Vegano");
        Ingrediente ing2 = new Ingrediente(2l, "test", 1F, 1, "test", "Vegano");
        Set<Ingrediente> listaIngredientes = new LinkedHashSet<>();
        listaIngredientes.add(ing1);
        listaIngredientes.add(ing2);
        Sandwich sandwich = new Sandwich(1l, "sand1", "sand1", true, "Vegano", listaIngredientes);
        return sandwich;
    }

    private Carro dadoQueTengoUnCarro(long l, Usuario user) {
        return new Carro(l, user);
    }

    private Usuario dadoQueTengoUnUsuario() {
        Usuario user = new Usuario("diego@gmail.com", "123");
        user.setId(1L);
        return user;
    }
}

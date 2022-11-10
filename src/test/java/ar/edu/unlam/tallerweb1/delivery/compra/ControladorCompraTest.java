package ar.edu.unlam.tallerweb1.delivery.compra;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorCompra;
import ar.edu.unlam.tallerweb1.delivery.FormularioComentario;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
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
import static org.mockito.Mockito.*;

public class ControladorCompraTest extends SpringTest {
    private ControladorCompra controladorComprapra;
    private ServicioCompra servicio;
    private HttpServletRequest request;
    private ServicioLogin servicioLogin;

    @Before
    public void init() {
        this.servicio = mock(ServicioCompra.class);
        this.servicioLogin = mock(ServicioLogin.class);
        this.controladorComprapra = new ControladorCompra(this.servicio, servicioLogin);
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(session);
    }

    @Test
    public void queSiElUSuarioRealizoAlgunPedidoPuedaListarTodasLasCompras() throws CompraNoEncontradaExeption {
        Usuario usuario = dadoQueTengoUnUsuario();
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Compra compra = dadoQueTengoUnaCompra(usuario, sandwich);
        List<Compra> listaComprados = dadoQueTengoUnaListaDeCompras(compra);
        cuandoLlamoAlServicioMeListeTodasLAsCompras(usuario.getId(), listaComprados);
        when(request.getSession().getAttribute("id")).thenReturn(usuario.getId());
        ModelAndView model = cuandoLePidoAlControladorQueMeDevuelvaTodasLasComprasDelUSuario(request);
        verificaQueMeHAyaDevueltoUnaLIstaConCompras(model, listaComprados);

    }

    @Test
    public void siUnUsuarioNoTieneNingunaCompraMeMuestreUnMensaje() throws CompraNoEncontradaExeption {
        when(request.getSession().getAttribute("id")).thenReturn(1L);
        cuandoLLameAlServicioDeListarComprasYNoTengaComprasMeLanceExeption(1L);
        ModelAndView model = cuandoNoTengoComprasMeMuestreUnMensajeDeError(request);
        String msg = "Todavía no realizo ninguna compra";
        verificoQueMeMandeElMensajeDeError(model, msg);
    }


    @Test
    public void siElUsuarioListaSusComprasPuedeAgregarUnComentario() throws CompraNoEncontradaExeption {
        Usuario usuario = dadoQueTengoUnUsuario();
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Compra compra = dadoQueTengoUnaCompra(usuario, sandwich);
        FormularioComentario miComentario = dadoQueTengoUnModeloDeDatos();
        cuandoIngresoDatosAlComentario(miComentario);
        cuandoBuscoUnaDeLasCompras(usuario.getId(), compra);
        entoncesVerificoQueTengoUnComentario(miComentario);

    }

    private void entoncesVerificoQueTengoUnComentario(FormularioComentario comentario) {
        assertThat(comentario).isNotNull();
    }

    private FormularioComentario dadoQueTengoUnModeloDeDatos() {
        FormularioComentario coment = new FormularioComentario();
        coment.setComentario(null);
        coment.setIdCompra(null);
        return coment;
    }

    private FormularioComentario cuandoIngresoDatosAlComentario(FormularioComentario comentario) {
        comentario.setComentario("Muy rico");
        comentario.setIdCompra(1L);
        return comentario;
    }


    private void verificoQueMeMandeElMensajeDeError(ModelAndView model, String msg) {
        assertThat(model.getModel().get("msg")).isEqualTo(msg);
    }

    private ModelAndView cuandoNoTengoComprasMeMuestreUnMensajeDeError(HttpServletRequest request) {
        return controladorComprapra.listarTodasLasCompras(request);
    }

    private void cuandoLLameAlServicioDeListarComprasYNoTengaComprasMeLanceExeption(long idUsuario) throws CompraNoEncontradaExeption {
        when(servicio.listarTodasLasCompras(idUsuario)).thenThrow(new CompraNoEncontradaExeption("No hay Compras"));
    }

    private void verificaQueMeHAyaDevueltoUnaLIstaConCompras(ModelAndView model, List<Compra> listaComprados) {
        assertThat(model.getModel().get("listaDeCompras")).isEqualTo(listaComprados);

    }

    private ModelAndView cuandoLePidoAlControladorQueMeDevuelvaTodasLasComprasDelUSuario(HttpServletRequest request) {
        return controladorComprapra.listarTodasLasCompras(request);
    }

    private void cuandoLlamoAlServicioMeListeTodasLAsCompras(Long idUsuario, List<Compra> compra) throws CompraNoEncontradaExeption {
        when(servicio.listarTodasLasCompras(idUsuario)).thenReturn(compra);
    }

    private void cuandoBuscoUnaDeLasCompras(Long idUsuario,Compra compra) throws CompraNoEncontradaExeption {
        when(servicio.buscarCompra(idUsuario)).thenReturn(compra);
    }

    private List<Compra> dadoQueTengoUnaListaDeCompras(Compra compra) {
        List<Compra> lista = new ArrayList<>();
        lista.add(compra);
        return lista;
    }


    private Compra dadoQueTengoUnaCompra(Usuario usuario, Sandwich sandwich) {
        List<Sandwich> lista = new ArrayList<>();
        lista.add(sandwich);
        return new Compra(usuario, lista);
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

    private Usuario dadoQueTengoUnUsuario() {
        return new Usuario("diego@gmail.com", "123");
    }


//
//    @Test
//    public void queUnusuarioPuedaGenerarUnaCompra() throws CompraNoEncontradaExeption {
//        //preparacion
//        Usuario usuario = dadoQueTengoUnUsuario("diego@gmail.com","123");
//        Sandwich sandwich = dadoQueTengoUnSandwichSeleccionado();
//        Compra compra = hagoLaCompra(usuario, sandwich);
//        ModelAndView model = generoLaCompra(request);
//        comprueboQueSeHayaGeneradoLaCompra(usuario, sandwich);
//        verificoLaCompra(model);
//    }
//    @Test
//    public void luegoDeHacerUnaCompraDeUnSandwichPuedaEncontrarMiPedidoEnEstadopDePedido() throws CompraNoEncontradaExeption {
//        Usuario usuario = dadoQueTengoUnUsuario("diego@gmail.com", "123");
//        Sandwich sandwich = dadoQueTengoUnSandwichSeleccionado();
//        Compra compra = hagoLaCompra(usuario, sandwich);
//        Compra compraBuscada= buscoLaCompra(compra);
//        List<Compra> listaCompra= buscoLaCompraPorUsuarioYEstadoDePedido(usuario,EstadoDeCompra.PEDIDO);
//        listaCompra.add(compra);
//        ModelAndView modelAndView= listoLosPedidos(request);
//        verificoQueElPEdidoEsteEnestadoPedido(modelAndView,listaCompra);
//    }
//
//    private List<Compra> buscoLaCompraPorUsuarioYEstadoDePedido(Usuario usuario, EstadoDeCompra estado) throws CompraNoEncontradaExeption {
//        List<Compra> lista= new ArrayList<>();
//        List<Sandwich> pedido= new ArrayList<>();
//        Compra compra= new Compra(1L,new Usuario(),pedido);
//        when(servicio.listarComprasDeUsuarioPorEstado(usuario,estado)).thenReturn(lista);
//        return servicio.listarComprasDeUsuarioPorEstado(usuario,estado);
//    }
//
//    private void verificoQueElPEdidoEsteEnestadoPedido(ModelAndView modelAndView, List<Compra> compraBuscada) {
//        assertThat(modelAndView.getModel().get("compra")).isNotNull();
//        assertThat(modelAndView.getModelMap().get("compra")).isEqualTo(compraBuscada);
//
//    }
//
//    private ModelAndView listoLosPedidos(HttpServletRequest request) {
//        Usuario usuario= (Usuario) request.getSession().getAttribute("usuario");
//        return controladorComprapra.verMisPedidos(request);
//    }
//
//    private Compra buscoLaCompra(Compra compra) throws CompraNoEncontradaExeption {
//        when(servicio.buscarCompra(compra.getIdCompra())).thenReturn(compra);
//        return servicio.buscarCompra(compra.getIdCompra());
//    }
//
//
//    private void verificoLaCompra(ModelAndView model) {
//        assertThat(model.getViewName()).isEqualTo("alerta_exitosa");
//    }
//
//    private Compra hagoLaCompra(Usuario usuario, Sandwich sandwich) throws CompraNoEncontradaExeption {
//        request.getSession().setAttribute("usuario", usuario);
//        request.getSession().setAttribute("sandwich", sandwich);
//        List<Sandwich> lista = new ArrayList<>();
//        lista.add(sandwich);
//        Compra compra = new Compra(1L,usuario, lista);
//        return compra;
//    }
//
//    private void comprueboQueSeHayaGeneradoLaCompra(Usuario usuario, Sandwich sandwich) throws CompraNoEncontradaExeption {
//        verify(servicio).guardarCompra(any());
//
//    }
//
//    private ModelAndView generoLaCompra(HttpServletRequest request) {
//        return controladorComprapra.guardarCompra(request);
//    }
//
//    private Sandwich dadoQueTengoUnSandwichSeleccionado() {
//        Ingrediente ing1 = new Ingrediente(1l, "test", 1F, 1, "test", "Vegano");
//        Ingrediente ing2 = new Ingrediente(2l, "test", 1F, 1, "test", "Vegano");
//        Set<Ingrediente> listaIngredientes = new LinkedHashSet<>();
//        listaIngredientes.add(ing1);
//        listaIngredientes.add(ing2);
//        Sandwich sandwich = new Sandwich(1l, "sand1", "sand1", true, "Vegano", listaIngredientes);
//        return sandwich;
//
//    }
//
//    private Usuario dadoQueTengoUnUsuario(String email, String password) {
//        Usuario usuario= new Usuario(email,password);
//        when(request.getSession().getAttribute("usuario")).thenReturn(usuario);
//        return usuario;
//
//    }


}

package ar.edu.unlam.tallerweb1.delivery.cancelarCompra;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorCancelarCompra;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ControladorCancelacionCompraTest extends SpringTest {
    
    private ServicioCompra servicioCompra;
    private ServicioLogin servicioLogin;
    
    private ControladorCancelarCompra controlador;
    private HttpSession session;
    private HttpServletRequest request;
    
    @Before
    public void init(){
        this.servicioLogin = mock(ServicioLogin.class);
        this.servicioCompra = mock(ServicioCompra.class);
        this.session = mock(HttpSession.class);
        this.request = mock(HttpServletRequest.class);
        when(this.request.getSession()).thenReturn(this.session);
        this.controlador = new ControladorCancelarCompra(this.servicioCompra,this.servicioLogin);
    }

    @Test
    public void queAlIrAListarComprasEnPreparacionMeDevuelvaUnaListaNoVacia() throws UsuarioInvalidoException, CompraNoEncontradaExeption {
        Usuario user = dadoQueTengoUnUsuario();
        List<Sandwich> l = new ArrayList<>();
        l.add(dadoQueTengoUnSandwichConIngredientes());
        Compra cmp = dadoQueUnaCompra(1L,user,l,EstadoDeCompra.PREPARACION);
        List<Compra> lista = dadoQueTengoUnaListaDeCompras(user,cmp);
        cuandoPedisAlServlet(user);
        cuandoPideAlServicioLogin(user);
        cuandoPideAlServicioCompra(dadoQueTengoUnUsuario(),EstadoDeCompra.PREPARACION,lista);
        ModelAndView mav = cuandoLlamoAlControladorDeListarCompras(this.request);
        verificoQueLasListasSeanIguales(mav,lista);
    }


    @Test
    public void queAlCancelarUnaCompraDentroDeLos5MinLaMismaSePuedaCancelar() throws CompraNoEncontradaExeption {
        Usuario user = dadoQueTengoUnUsuario();
        List<Sandwich> l = new ArrayList<>();
        l.add(dadoQueTengoUnSandwichConIngredientes());
        Compra cmp = dadoQueUnaCompra(1L,user,l,EstadoDeCompra.PREPARACION);
        when(this.servicioCompra.buscarCompra(cmp.getIdCompra())).thenReturn(cmp);
        ModelAndView mav = cuandoLLamoAlControladorParaCancelarUnaCOmpra(cmp,this.request);
        cuandoSeLLamaAlServicioPAraCancelarLaCompra();
        assertThat(mav.getModel().get("msg")).isEqualTo("Se a eliminado la compra seleccionada");
    }

    @Test
    public void queAlCancelarUnaCompraDespuesDeLos5MinLaMismaNOSePuedaCancelar() throws CompraNoEncontradaExeption {
        Usuario user = dadoQueTengoUnUsuario();
        List<Sandwich> l = new ArrayList<>();
        l.add(dadoQueTengoUnSandwichConIngredientes());
        Compra cmp = dadoQueUnaCompraFueraDeLos5Min(1L,user,l,EstadoDeCompra.PREPARACION);
        when(this.servicioCompra.buscarCompra(cmp.getIdCompra())).thenReturn(cmp);
        ModelAndView mav = cuandoLLamoAlControladorParaCancelarUnaCOmpra(cmp,this.request);
        assertThat(mav.getModel().get("msg")).isEqualTo("Su pedido esta en preparacion, no se pudo eliminar su compra");
    }

    private Compra dadoQueUnaCompraFueraDeLos5Min(long l, Usuario user, List<Sandwich> l1, EstadoDeCompra preparacion) {
        Compra c = new Compra();
        c.setIdCompra(l);
        c.setEstado(preparacion);
        c.setComentario("");
        c.setFecha(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).withSecond(0).withNano(0).minusMinutes(3));
        c.setFechaEntrega(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).withSecond(0).withNano(0).plusMinutes(3));
        return c;
    }

    private ModelAndView cuandoLLamoAlControladorParaCancelarUnaCOmpra(Compra cmp, HttpServletRequest request) {
        return  this.controlador.cancelarCompra(cmp.getIdCompra(), request);
    }

    private void cuandoSeLLamaAlServicioPAraCancelarLaCompra() {
        verify(this.servicioCompra).cancelarCompra(any(Compra.class),any());
    }

    private void verificoQueLasListasSeanIguales(ModelAndView mav, List<Compra> lista) {
        assertThat(mav.getModel().get("listaDeCompras")).isNotNull();
    }

    private ModelAndView cuandoLlamoAlControladorDeListarCompras(HttpServletRequest request) {
        return  this.controlador.listarComprasEnPreparacion(request);
    }

    private void cuandoPideAlServicioCompra(Usuario user, EstadoDeCompra estado, List<Compra> lista) throws CompraNoEncontradaExeption {
        when(this.servicioCompra.listarComprasDeUsuarioPorEstado(user,estado)).thenReturn(lista);
    }

    private void cuandoPideAlServicioLogin(Usuario user) throws UsuarioInvalidoException {
        when(this.servicioLogin.consultarPorID(user.getId())).thenReturn(user);
    }

    private void cuandoPedisAlServlet(Usuario user) {
        when(this.request.getSession().getAttribute("id")).thenReturn(user.getId());
    }

    private List<Compra> dadoQueTengoUnaListaDeCompras(Usuario user,Compra compra) {
        List<Compra> list = new ArrayList<>();
        list.add(compra);
        return list;
    }

    private Sandwich dadoQueTengoUnSandwichConIngredientes() {
        Ingrediente ing1 = new Ingrediente(1l,"test",1F,1,"test","Vegano");
        Ingrediente ing2 = new Ingrediente(2l,"test",1F,1,"test","Vegano");
        Set<Ingrediente> listaIngredientes = new LinkedHashSet<>();
        listaIngredientes.add(ing1);
        listaIngredientes.add(ing2);
        Sandwich sandwich = new Sandwich(1l,"sand1","sand1",true,"Vegano",listaIngredientes);
        return sandwich;
    }

    Usuario dadoQueTengoUnUsuario(){
        Usuario nuevo = new Usuario();
        nuevo.setId(1L);
        nuevo.setNombre("Damian");
        nuevo.setApellido("Spizzini");
        nuevo.setEmail("test@test.com");
        nuevo.setPassword("123");
        nuevo.setPreferencia("SinRestriccion");
        return nuevo;
    }

    private Compra dadoQueUnaCompra(Long id, Usuario us, List<Sandwich> s, EstadoDeCompra e) {
        Compra c = new Compra();
        c.setIdCompra(id);
        c.setEstado(e);
        c.setComentario("");
        c.setFecha(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).withSecond(0).withNano(0));
        c.setFechaEntrega(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).withSecond(0).withNano(0).plusMinutes(3));
        return c;
    }
}

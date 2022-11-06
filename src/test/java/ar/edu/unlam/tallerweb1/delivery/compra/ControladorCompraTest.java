package ar.edu.unlam.tallerweb1.delivery.compra;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorCompra;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
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
    private HttpSession session;
    private HttpServletRequest request;

    @Before
    public void init() {
        this.servicio = mock(ServicioCompra.class);
        this.controladorComprapra = new ControladorCompra(this.servicio);
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(this.session);
    }

    @Test
    public void queUnusuarioPuedaGenerarUnaCompra() throws CompraNoEncontradaExeption {
        //preparacion
        Usuario usuario = dadoQueTengoUnUsuario("diego@gmail.com","123");
        Sandwich sandwich = dadoQueTengoUnSandwichSeleccionado();
        Compra compra = hagoLaCompra(usuario, sandwich);
        ModelAndView model = generoLaCompra(request);
        comprueboQueSeHayaGeneradoLaCompra(usuario, sandwich);
        verificoLaCompra(model);
    }
    @Test
    public void luegoDeHacerUnaCompraDeUnSandwichPuedaEncontrarMiPedidoEnEstadopDePedido() throws CompraNoEncontradaExeption {
        Usuario usuario = dadoQueTengoUnUsuario("diego@gmail.com", "123");
        Sandwich sandwich = dadoQueTengoUnSandwichSeleccionado();
        Compra compra = hagoLaCompra(usuario, sandwich);
        Compra compraBuscada= buscoLaCompra(compra);
        List<Compra> listaCompra= buscoLaCompraPorUsuarioYEstadoDePedido(usuario,EstadoDeCompra.PEDIDO);
        listaCompra.add(compra);
        ModelAndView modelAndView= listoLosPedidos(request);
        verificoQueElPEdidoEsteEnestadoPedido(modelAndView,listaCompra);
    }

    private List<Compra> buscoLaCompraPorUsuarioYEstadoDePedido(Usuario usuario, EstadoDeCompra estado) throws CompraNoEncontradaExeption {
        List<Compra> lista= new ArrayList<>();
        List<Sandwich> pedido= new ArrayList<>();
        Compra compra= new Compra(1L,new Usuario(),pedido);
        when(servicio.listarComprasDeUsuarioPorEstado(usuario,estado)).thenReturn(lista);
        return servicio.listarComprasDeUsuarioPorEstado(usuario,estado);
    }

    private void verificoQueElPEdidoEsteEnestadoPedido(ModelAndView modelAndView, List<Compra> compraBuscada) {
        assertThat(modelAndView.getModel().get("compra")).isNotNull();
        assertThat(modelAndView.getModelMap().get("compra")).isEqualTo(compraBuscada);

    }

    private ModelAndView listoLosPedidos(HttpServletRequest request) {
        Usuario usuario= (Usuario) request.getSession().getAttribute("usuario");
        return controladorComprapra.verMisPedidos(request);
    }

    private Compra buscoLaCompra(Compra compra) throws CompraNoEncontradaExeption {
        when(servicio.buscarCompra(compra.getIdCompra())).thenReturn(compra);
        return servicio.buscarCompra(compra.getIdCompra());
    }


    private void verificoLaCompra(ModelAndView model) {
        assertThat(model.getViewName()).isEqualTo("guardarCompra");
    }

    private Compra hagoLaCompra(Usuario usuario, Sandwich sandwich) throws CompraNoEncontradaExeption {
        request.getSession().setAttribute("usuario", usuario);
        request.getSession().setAttribute("sandwich", sandwich);
        List<Sandwich> lista = new ArrayList<>();
        lista.add(sandwich);
        Compra compra = new Compra(1L,usuario, lista);
        return compra;
    }

    private void comprueboQueSeHayaGeneradoLaCompra(Usuario usuario, Sandwich sandwich) throws CompraNoEncontradaExeption {
        verify(servicio).guardarCompra(any());

    }

    private ModelAndView generoLaCompra(HttpServletRequest request) {
        return controladorComprapra.guardarCompra(request);
    }

    private Sandwich dadoQueTengoUnSandwichSeleccionado() {
        Ingrediente ing1 = new Ingrediente(1l, "test", 1F, 1, "test", "Vegano");
        Ingrediente ing2 = new Ingrediente(2l, "test", 1F, 1, "test", "Vegano");
        Set<Ingrediente> listaIngredientes = new LinkedHashSet<>();
        listaIngredientes.add(ing1);
        listaIngredientes.add(ing2);
        Sandwich sandwich = new Sandwich(1l, "sand1", "sand1", true, "Vegano", listaIngredientes);
        return sandwich;

    }

    private Usuario dadoQueTengoUnUsuario(String email, String password) {
        Usuario usuario= new Usuario(email,password);
        when(request.getSession().getAttribute("usuario")).thenReturn(usuario);
        return usuario;

    }


}

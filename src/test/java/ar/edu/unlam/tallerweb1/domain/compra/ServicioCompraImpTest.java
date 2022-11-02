package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ServicioCompraImpTest extends SpringTest {
    private RepositorioCompra repo;
    private ServicioCompra servicioCompra;

    @Before
    public void init() {
        this.repo = mock(RepositorioCompra.class);
        this.servicioCompra = new ServicioCompraImp(repo);
    }

    @Test
    public void luegoDeGenerarUnaCompraLaPuedaObtenerPorIdCompra() throws CompraNoEncontradaExeption {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(usuario, sandwich);
        guardoLaCompra(compra);
        Compra compraBuscada = buscoLaCompraPorId(compra.getIdCompra());
        comparoQueLasComprasSeanIguales(compra, compraBuscada);
        verificoQueSeUseElRepo();
    }

    @Test(expected = CompraNoEncontradaExeption.class)
    public void siBuscoUnaCompraQueNoExistaMeLanceUnaExeption() throws CompraNoEncontradaExeption {
        Long idCompraInexistente = 88L;
        Compra inexistente = buscoUnaCompraQueNOExiste(idCompraInexistente);
        verificoLoObtenido(inexistente.getIdCompra());
    }


    @Test
    public void luegoDeHaberHechoVariasComprasPuedaBuscarLasQueHizoUnCliente() throws CompraNoEncontradaExeption {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Usuario usuario2 = dadoQueTengoUnUsuario(2L, "messi@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra1 = generoLaCompra(usuario, sandwich);
        Compra compra2 = generoLaCompra(usuario2, sandwich);
        Compra compra3 = generoLaCompra(usuario, sandwich);
        guardoLaCompra(compra1);
        guardoLaCompra(compra2);
        guardoLaCompra(compra3);
        usoDeMokitoParaLaLIstaPorUsuario(usuario);
        List<Compra> historial = buscoTodasLasComprasPorUsuario(usuario);
        cuentoLasComprasObtenidas(historial, 2);
    }

    @Test(expected = CompraNoEncontradaExeption.class)
    public void enCasoDeBuscarCompraPorUsuarioQueNoTengaComprasDevuelvaUnaExepcion() throws CompraNoEncontradaExeption {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Usuario usuario2 = dadoQueTengoUnUsuario(2L, "messi@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra1 = generoLaCompra(usuario, sandwich);
        Compra compra2 = generoLaCompra(usuario, sandwich);
        Compra compra3 = generoLaCompra(usuario, sandwich);
        List<Compra> esperado = buscoCompraPorUsuarioInexistente(usuario2);
    }
    @Test
    public void luegoDeRealizarUnPedidoSePuedaObtenerLasComprasEsEstadoPedido() throws CompraNoEncontradaExeption {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(usuario, sandwich);
        List<Compra> pedido= buscarComprasEnEstadoPedidoPorCliente(usuario,EstadoDeCompra.PEDIDO);
        verificoQueElPedidoSeaIgualALOComprado(compra,pedido);
    }

    private void verificoQueElPedidoSeaIgualALOComprado(Compra compra, List<Compra> pedido) {
        assertThat(compra.getCliente().getEmail()).isEqualTo(pedido.get(0).getCliente().getEmail());
        assertThat(compra.getDetalle().size()).isEqualTo(pedido.get(0).getDetalle().size());
        assertThat(compra.getEstado()).isEqualTo(pedido.get(0).getEstado());
    }

    private List<Compra> buscarComprasEnEstadoPedidoPorCliente(Usuario usuario, EstadoDeCompra estado) throws CompraNoEncontradaExeption {
        Usuario usu = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(usu, sandwich);
        List<Compra> historial= new ArrayList<>();
        historial.add(compra);
        when(repo.buscarPorEstado(usuario,EstadoDeCompra.PEDIDO)).thenReturn(historial);
        return servicioCompra.listarComprasDeUsuarioPorEstado(usuario,estado);
    }


    private List<Compra> buscoCompraPorUsuarioInexistente(Usuario usuario) throws CompraNoEncontradaExeption {

        return servicioCompra.buscarComprasPorUsuario(usuario);
    }


    private void verificoLoObtenido(Long idCompra) {
        assertThat(idCompra).isEqualTo(28L);
    }

    private Compra buscoUnaCompraQueNOExiste(Long idCompraInexistente) throws CompraNoEncontradaExeption {
        return servicioCompra.buscarCompra(idCompraInexistente);
    }

    private void usoDeMokitoParaLaLIstaPorUsuario(Usuario usuario) {
        List<Compra> historial = new ArrayList<>();
        historial.add(new Compra());
        historial.add(new Compra());
        when(repo.buscarCompraPorCliente(usuario)).thenReturn(historial);
    }

    private void cuentoLasComprasObtenidas(List<Compra> historial, int cantidadDeCompras) {
        assertThat(historial).hasSize(cantidadDeCompras);
    }

    private List<Compra> buscoTodasLasComprasPorUsuario(Usuario usuario) throws CompraNoEncontradaExeption {
        return servicioCompra.buscarComprasPorUsuario(usuario);

    }

    private void guardoLaCompra(Compra compra) {
        servicioCompra.guardarCompra(compra);
    }

    private void verificoQueSeUseElRepo() {
        verify(repo).guardarCompra(any());
    }

    private void comparoQueLasComprasSeanIguales(Compra compra, Compra compraBuscada) {
        assertThat(compra.getIdCompra()).isEqualTo(compraBuscada.getIdCompra());
        assertThat(compra.getCliente().getEmail()).isEqualTo(compraBuscada.getCliente().getEmail());
    }

    private Compra buscoLaCompraPorId(Long idCompra) throws CompraNoEncontradaExeption {
        return servicioCompra.buscarCompra(idCompra);
    }

    private Compra generoLaCompra(Usuario usuario, List<Sandwich> sandwich) {
        Compra nuevo = new Compra(usuario, sandwich);
        when(repo.buscarCompra(nuevo.getIdCompra())).thenReturn(nuevo);
        return nuevo;
    }

    private List<Sandwich> dadoQueTengoSandwichsSeleccionados() {
        List<Sandwich> pedido = new ArrayList<>();
        Sandwich sandwich = new Sandwich();
        sandwich.setIdSandwich(1L);
        Sandwich sandwich2 = new Sandwich();
        sandwich2.setIdSandwich(2L);
        pedido.add(sandwich);
        pedido.add(sandwich2);
        return pedido;
    }

    private Usuario dadoQueTengoUnUsuario(long id, String email, String password) {
        Usuario nuevo = new Usuario();
        nuevo.setId(id);
        nuevo.setEmail(email);
        nuevo.setPassword(password);
        return nuevo;
    }

}

package ar.edu.unlam.tallerweb1.infrastructure.compra;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.RepositorioCompra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RepositorioCompraImpTest extends SpringTest {
    @Autowired
    private RepositorioCompra repo;

    @Test
    @Transactional
    public void luegoDeGenerarUnaCompraSePuedaBuscarlaPorIdCompra() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, sandwich);
        Compra buscado = buscoLaCompra(compra);
        comprueboQueMeDevuelvaLaCompra(buscado, compra);
    }

    @Test
    @Transactional
    public void queSePuedaObtenerElListadoDeSandwichQueComponenLaCompra() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, sandwich);
        Compra buscada = buscoLaCompra(compra);
        comprueboQueMeDevuelvaLaDosSandwich(buscada.getDetalle(), 2);
    }

    @Test
    @Transactional
    public void queSePuedanObtenerLasComprasDeUnCLienteListadoPorIdUsuario() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, sandwich);
        Compra compra2 = generoLaCompra(2L, usuario, sandwich);
        System.out.println(compra.getEstado() + "VEREMOS verewmos ");
        List<Compra> listaCompra = obtengoLasComprasDeUnCliente(usuario);
        comparoLaCantidadDeComprasDeUnCliente(listaCompra, 2);
    }

    @Test
    @Transactional
    public void luegoDeHacerUnaCompraYCancelarlaSeLaPuedaObtenerPorEstadoDeCompra() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, sandwich);
        Compra compra2 = generoLaCompra(2L, usuario, sandwich);
        canceloLaCompra(usuario, compra, EstadoDeCompra.CANCELADO);
        verificoQueSeHayaCanceladolaCompra(compra, EstadoDeCompra.CANCELADO);

    }

    @Test
    @Transactional
    public void dadoQueRealiceUnPedidoPuedaVerLosQueEstanEnEstadoDePEDIDO() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, sandwich);
        List<Compra> miPedido = buscoLaCompraPorUsuarioYEstado(usuario, EstadoDeCompra.PREPARACION);
        verificoQueElPedidoSeaCorrecto(miPedido, sandwich);
    }

    private void verificoQueElPedidoSeaCorrecto(List<Compra> miPedido, List<Sandwich> sandwich) {
        assertThat(miPedido.get(0).getDetalle().size()).isEqualTo(sandwich.size());
        assertThat(miPedido.get(0).getDetalle().get(0)).isEqualTo(sandwich.get(0));
    }

    private List<Compra> buscoLaCompraPorUsuarioYEstado(Usuario usuario, EstadoDeCompra estado) {
        List<Compra> lista = repo.buscarPorEstado(usuario, estado);
        return lista;
    }

    private void verificoQueSeHayaCanceladolaCompra(Compra compra, EstadoDeCompra estado) {
        assertThat(compra.getEstado()).isEqualTo(estado);
    }

    private void canceloLaCompra(Usuario usuario, Compra compra, EstadoDeCompra cancelado) {
        compra.setEstado(cancelado);
        repo.actualizoLaCompra(compra);
    }


    private void comparoLaCantidadDeComprasDeUnCliente(List<Compra> listaCompra, int cantidadCompras) {
        assertThat(listaCompra).hasSize(cantidadCompras);
    }

    private List<Compra> obtengoLasComprasDeUnCliente(Usuario usuario) {
        return repo.buscarCompraPorCliente(usuario);
    }


    private void comprueboQueMeDevuelvaLaDosSandwich(List<Sandwich> detalle, int cantidad) {
        assertThat(detalle).hasSize(cantidad);
    }


    private void comprueboQueMeDevuelvaLaCompra(Compra buscado, Compra compra) {
        assertThat(buscado).isNotNull();
        assertThat(buscado.getIdCompra()).isEqualTo(compra.getIdCompra());

    }

    private Compra buscoLaCompra(Compra compra) {
        Compra compra1 = repo.buscarCompra(compra.getIdCompra());
        return compra1;
    }

    private Compra generoLaCompra(Long idCompra, Usuario usuario, List<Sandwich> sandwich) {
        Compra compra = new Compra(usuario, sandwich);
        session().save(compra);
        return compra;
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

    private Usuario dadoQueTengoUnUsuario(Long id, String email, String password) {
        Usuario nuevo = new Usuario();
        nuevo.setId(id);
        nuevo.setEmail(email);
        nuevo.setPassword(password);
        session().save(nuevo);
        return nuevo;
    }
}

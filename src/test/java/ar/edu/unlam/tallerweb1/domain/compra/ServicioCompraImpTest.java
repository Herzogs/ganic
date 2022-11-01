package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.SpringTest;
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
    public void luegoDeGenerarUnaCompraLaPuedaObtenerPorIdCompra() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        List<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(usuario, sandwich);
        guardoLaCompra(compra);
        Compra compraBuscada = buscoLaCompraPorId(compra.getIdCompra());
        comparoQueLasComprasSeanIguales(compra, compraBuscada);
        verificoQueSeUseElRepo();
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

    private Compra buscoLaCompraPorId(Long idCompra) {
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

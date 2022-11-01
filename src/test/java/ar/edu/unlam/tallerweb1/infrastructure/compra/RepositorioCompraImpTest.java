package ar.edu.unlam.tallerweb1.infrastructure.compra;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import ar.edu.unlam.tallerweb1.infrastructure.RepositorioCompraImp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RepositorioCompraImpTest extends SpringTest {
    @Autowired
    RepositorioCompraImp repo;

    @Test
    @Transactional
    public void luegoDeGenerarUnaCompraSePuedaBuscarlaPorIdCompra() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Set<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, sandwich);
        Compra buscado = buscoLaCompra(compra);
        comprueboQueMeDevuelvaLaCompra(buscado, compra);
    }

    @Test
    @Transactional
    public void queSePuedaObtenerElListadoDeSandwichQueComponenLaCompra() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Set<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, (Set<Sandwich>) sandwich);
        Compra buscada = buscoLaCompra(compra);
        comprueboQueMeDevuelvaLaDosSandwich(buscada.getDetalle(), 2);
    }
    @Test
    @Transactional
    public void queSePuedanObtenerLasComprasDeUnCLiente(){
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Set<Sandwich> sandwich = dadoQueTengoSandwichsSeleccionados();
        Compra compra = generoLaCompra(1L, usuario, (Set<Sandwich>) sandwich);
        Compra compra2 = generoLaCompra(2L, usuario, (Set<Sandwich>) sandwich);
        List<Compra> listaCompra= obtengoLasComprasDeUnCliente(usuario);
        comparoLaCantidadDeComprasDeUnCliente(listaCompra, 2);
    }

    private void comparoLaCantidadDeComprasDeUnCliente(List<Compra> listaCompra, int cantidadCompras) {
        assertThat(listaCompra).hasSize(cantidadCompras);
    }

    private List<Compra> obtengoLasComprasDeUnCliente(Usuario usuario) {
        return repo.buscarCompraPorCliente(usuario);
    }


    private void comprueboQueMeDevuelvaLaDosSandwich(Set<Sandwich> detalle, int cantidad) {
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

    private Compra generoLaCompra(Long idCompra, Usuario usuario, Set<Sandwich> sandwich) {
        Compra compra = new Compra();
        compra.setIdCompra(idCompra);
        compra.setCliente(usuario);
        System.out.println(usuario.getId() + "El nombre del cliente");
        compra.setDetalle(sandwich);
        repo.guardarCompra(compra);
        session().save(compra);
        return compra;
    }

    private Set<Sandwich> dadoQueTengoSandwichsSeleccionados() {
        Set<Sandwich> pedido = new HashSet<>();
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

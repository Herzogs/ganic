package ar.edu.unlam.tallerweb1.infrastructure.detalleCarro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.RepositorioDetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ReposistorioDetalleCarroImpTest extends SpringTest {
    @Autowired
    private RepositorioDetalleCarro repo;


    @Test
    @Transactional
    @Rollback
    public void queSiTengoSandwichSeleccionadosPuedaObtenerElDetalleDeCarro() {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Carro carro = dadoQueTengoUnCarro(usuario, 1L);
        Sandwich sandwich = dadoQueTengoUnSandwich(1L, "sandwichDePrube", "miPedido");
        List<DetalleCarro> detalleCarro = agregoElSandwichAlDetalleCarro(carro, sandwich, 1);
        guardoElDetalleDeCarro(detalleCarro.get(0));
        List<DetalleCarro> detalleObtenido = buscoElDetalleCarro(usuario);
        verificarQueDetalleObtenidoSeaIgualAlEsperado(detalleCarro.get(0), detalleObtenido.get(0));
    }

    @Test
    @Transactional
    @Rollback
    public void siTengoVariosSandwichDistintosEnMiDetalleCarroLosPuedaVer() {
        Usuario usuario = dadoQueTengoUnUsuario(2L, "diego@ganic.com", "123");
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        Sandwich sandwich = dadoQueTengoUnSandwich(1L, "sandwichDePruba", "miPedido");
        Sandwich sandwich2 = dadoQueTengoUnSandwich(2L, "sandwichDePruba", "miPedido");
        DetalleCarro detalleCarro = dadoQueTengoUnDetalleDeCarro(carro, sandwich, 1);
        DetalleCarro detalleCarro2 = dadoQueTengoUnDetalleDeCarro(carro, sandwich2, 3);
        guardoLosDetalleDeCarro(detalleCarro);
        guardoLosDetalleDeCarro(detalleCarro2);
        List<DetalleCarro> detalles = buscoElDetalleCarro(usuario);
        verificoQueEstenMisSandwich(detalles, detalleCarro, detalleCarro2);

    }

    private void verificoQueEstenMisSandwich(List<DetalleCarro> detalles, DetalleCarro detalleCarro, DetalleCarro detalleCarro2) {
        assertThat(detalles).hasSize(2);
        assertThat(detalles.get(0)).isEqualTo(detalleCarro);
        assertThat(detalles.get(1)).isEqualTo(detalleCarro2);
    }

    private void guardoLosDetalleDeCarro(DetalleCarro detalleCarro) {
        repo.guardarDetalleCarro(detalleCarro);
    }

    private DetalleCarro dadoQueTengoUnDetalleDeCarro(Carro carro, Sandwich sandwich, int cantidad) {
        DetalleCarro detalleCarro = new DetalleCarro();
        detalleCarro.setCarro(carro);
        detalleCarro.setSandwich(sandwich);
        detalleCarro.setCantidad(cantidad);
        session().save(detalleCarro);
        return detalleCarro;
    }


    private void verificarQueDetalleObtenidoSeaIgualAlEsperado(DetalleCarro detalleCarro, DetalleCarro detalleObtenido) {
        assertThat(detalleCarro).isNotNull();
        assertThat(detalleObtenido).isNotNull();
        assertThat(detalleCarro).isEqualTo(detalleObtenido);
        assertThat(detalleCarro.getSandwich()).isEqualTo(detalleObtenido.getSandwich());
    }

    private List<DetalleCarro> buscoElDetalleCarro(Usuario usuario) {
        return repo.obtenerDetalleDeCarrPorUsuario(usuario);
    }

    private void guardoElDetalleDeCarro(DetalleCarro detalleCarro) {
        repo.guardarDetalleCarro(detalleCarro);
    }

    private List<DetalleCarro> agregoElSandwichAlDetalleCarro(Carro carro, Sandwich sandwich, int cantidad) {
        List<DetalleCarro> detalles = new ArrayList<>();

        DetalleCarro detalleCarro = new DetalleCarro();
        detalleCarro.setCarro(carro);
        detalleCarro.setSandwich(sandwich);
        detalleCarro.setCantidad(cantidad);
        detalles.add(detalleCarro);
        session().save(detalleCarro);
        return detalles;
    }

    private Sandwich dadoQueTengoUnSandwich(long idSandwich, String sandwichDePrube, String miPedido) {
        Sandwich sandwich = new Sandwich();
        sandwich.setIdSandwich(idSandwich);
        sandwich.setDescripcion(miPedido);
        sandwich.setNombre(miPedido);
        sandwich.setIngrediente(new HashSet<>());
        sandwich.setEsApto("Vegano");
        sandwich.setEnPromocion(false);
        session().save(sandwich);
        return sandwich;
    }


    private Carro dadoQueTengoUnCarro(Usuario usuario, Long idCarro) {
        Carro carro = new Carro();
        carro.setUsuario(usuario);
        carro.setIdCarro(idCarro);
        session().save(carro);
        return carro;
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

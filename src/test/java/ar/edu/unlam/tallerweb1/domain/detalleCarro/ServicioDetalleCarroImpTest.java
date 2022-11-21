package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.DetalleInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ServicioDetalleCarroImpTest extends SpringTest {
    private RepositorioDetalleCarro repo;
    private ServicioDetalleCarro servicio;

    @Before
    public void init() {
        this.repo = mock(RepositorioDetalleCarro.class);
        this.servicio = new ServicioDetalleCarroImp(repo);
    }

    @Test
    public void queSiElijoUnSandwichSePuedaGuardarElDetalleDeCarroYLoPuedaObtener() throws DetalleInexistenteExeption {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Carro carro = dadoQueTengoUnCarro(usuario, 1L);
        Sandwich sandwich = dadoQueTengoUnSandwich(1L, "sandwichDePrube", "miPedido");
        DetalleCarro detalleCarro = dadoQueTengoUnDetalleDeCarro(1L, carro, sandwich, 1);
        guardoElDetalleDeCarro(detalleCarro);
        buscoConMoquito(detalleCarro);
        DetalleCarro detalleBuscado = buscoElDetalleDeCarro(detalleCarro.getIdDetalleCarro());
        verificoQueSeGuaro(detalleCarro);
        verificoQueElDetalleBuscadoSeaElMismoQueElEsperado(detalleBuscado, detalleCarro);
    }

    @Test(expected = DetalleInexistenteExeption.class)
    public void queSiBuscoUnDetalleDeCArroQueNoExistaMeLanceDetalleInexistenteExeption() throws DetalleInexistenteExeption {
        Long idInexistente = 558L;
        lanzoExeptionConMoquito(idInexistente);
        DetalleCarro detalleBuscado = buscoElDetalleDeCarro(idInexistente);

    }

    @Test
    public void luegoDeSeleccionarUnosSandwichSePuedaObtenerTodaLaListaDeSandWichSeleccionadaPorUsuario() throws DetalleInexistenteExeption {
        Usuario usuario = dadoQueTengoUnUsuario(2L, "diego@ganic.com", "123");
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        Sandwich sandwich2 = dadoQueTengoUnSandwich(2L, "sandwichDePrube", "miPedido");
        Sandwich sandwich3 = dadoQueTengoUnSandwich(3L, "sandwichDePrube", "miPedido");
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(2L, carro, sandwich2, 1);
        DetalleCarro detalleCarro2 = dadoQueTengoUnDetalleDeCarro(2L, carro, sandwich3, 1);
        buscoConMoquito(detalleCarro1, detalleCarro2, usuario);
        List<DetalleCarro> detalles = cuandoBuscoLaListaDeDetallesPoeUsuario(usuario);
        verificoQueMeHayaDevueltoLosSandwichs(detalles, detalleCarro1, detalleCarro2);
    }

    @Test(expected = DetalleInexistenteExeption.class)
    public void siNoTengoSandwichSeleccionadosYpidaListarLosDetallesDeCarroMeLanceDetalleInexistenteExeption() throws DetalleInexistenteExeption {
        Usuario usuario = dadoQueTengoUnUsuario(2L, "diego@ganic.com", "123");
        lanzoLaExepcionCuandoNoTengaSandwichSeleccionadosConMockito(usuario);
        buacoLaListaDeDetalleDelUsuario(usuario);
    }
    @Test
    public void luegoDeHaberElegidoAlgunSandwichPuedaEliminarAlgunDetalle(){
        Usuario usuario = dadoQueTengoUnUsuario(2L, "diego@ganic.com", "123");
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        Sandwich sandwich2 = dadoQueTengoUnSandwich(2L, "sandwichDePrube", "miPedido");
        Sandwich sandwich3 = dadoQueTengoUnSandwich(3L, "sandwichDePrube", "miPedido");
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(2L, carro, sandwich2, 8);
        DetalleCarro detalleCarro2 = dadoQueTengoUnDetalleDeCarro(2L, carro, sandwich3, 5);
        eliminamosUnDetalleDeCarro(detalleCarro1);
       verificarQueSeLLamoAlMEtodoEliminarDelRepo(detalleCarro1);
    }
    @Test
    public void queLuegoDeHaberSeleccionadoVariasVecesElMismoSandwichPuedaModificarLaCantidad() throws DetalleInexistenteExeption {
        Usuario usuario = dadoQueTengoUnUsuario(2L, "diego@ganic.com", "123");
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        Sandwich sandwich2 = dadoQueTengoUnSandwich(2L, "sandwichDePrube", "miPedido");
        Sandwich sandwich3 = dadoQueTengoUnSandwich(3L, "sandwichDePrube", "miPedido");
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(2L, carro, sandwich2, 8);
        Integer cantidad= 25;
        DetalleCarro esperado=  actualizarCantidad(detalleCarro1,cantidad);
        obtengoElDetalleModificadoConMockito(esperado);
        DetalleCarro detalleCarroModificado= buscoEsteDetalleDeCarro(detalleCarro1);
        verificoQueLaCantidadSeaLaSeleccionada(detalleCarroModificado,cantidad);
    }

    private void obtengoElDetalleModificadoConMockito(DetalleCarro detalleCarroModificado) {
        when(repo.obtnerDetalleCarro(detalleCarroModificado.getIdDetalleCarro())).thenReturn(detalleCarroModificado);
    }

    private void verificoQueLaCantidadSeaLaSeleccionada(DetalleCarro detalleCarroModificado, Integer cantidad) {
        assertThat(detalleCarroModificado.getCantidad()).isEqualTo(cantidad);
    }

    private DetalleCarro buscoEsteDetalleDeCarro(DetalleCarro detalleCarro1) throws DetalleInexistenteExeption {
     return  servicio.obtenerDetalle(detalleCarro1.getIdDetalleCarro());
    }

    private DetalleCarro actualizarCantidad(DetalleCarro detalleCarro1, Integer cantidad) {
   detalleCarro1.setCantidad(cantidad);
   servicio.actualizarDetalle(detalleCarro1);
        return detalleCarro1;
    }


    private void verificarQueSeLLamoAlMEtodoEliminarDelRepo(DetalleCarro detalleCarro1) {
    verify(repo).borrarDetalleDeCarro(any(DetalleCarro.class));
    }

    private void eliminamosUnDetalleDeCarro(DetalleCarro detalleCarro1) {
    servicio.eliminarDetalle(detalleCarro1);
    }


    private void buacoLaListaDeDetalleDelUsuario(Usuario usuario) throws DetalleInexistenteExeption {
        servicio.obtenerDetalleDeCarroDeUsuario(usuario);
    }

    private void lanzoLaExepcionCuandoNoTengaSandwichSeleccionadosConMockito(Usuario usuario) {
        when(repo.obtenerDetalleDeCarrPorUsuario(usuario)).thenReturn(new ArrayList<>());
    }


    private void buscoConMoquito(DetalleCarro detalleCarro1, DetalleCarro detalleCarro2, Usuario usuario) {
        List<DetalleCarro> buscado = new ArrayList<>();
        buscado.add(detalleCarro1);
        buscado.add(detalleCarro2);
        when(repo.obtenerDetalleDeCarrPorUsuario(usuario)).thenReturn(buscado);
    }

    private void verificoQueMeHayaDevueltoLosSandwichs(List<DetalleCarro> detalles, DetalleCarro detalleCarro1, DetalleCarro detalleCarro2) {
        assertThat(detalles).isNotEmpty();
        assertThat(detalles).hasSize(2);
        assertThat(detalles.get(0)).isEqualTo(detalleCarro1);
        assertThat(detalles.get(1)).isEqualTo(detalleCarro2);
    }

    private List<DetalleCarro> cuandoBuscoLaListaDeDetallesPoeUsuario(Usuario usuario) throws DetalleInexistenteExeption {
        return servicio.obtenerDetalleDeCarroDeUsuario(usuario);
    }

    private void lanzoExeptionConMoquito(Long idInexistente) {
        when(repo.obtnerDetalleCarro(idInexistente)).thenReturn(null);
    }

    private void verificoQueSeGuaro(DetalleCarro detalleCarro) {
        verify(repo).guardarDetalleCarro(any());
    }

    private void buscoConMoquito(DetalleCarro detalleCarro) {
        when(repo.obtnerDetalleCarro(detalleCarro.getIdDetalleCarro())).thenReturn(detalleCarro);
    }

    private void verificoQueElDetalleBuscadoSeaElMismoQueElEsperado(DetalleCarro detalleBuscado, DetalleCarro detalleCarro) {
        assertThat(detalleBuscado).isNotNull();
        assertThat(detalleBuscado).isEqualTo(detalleCarro);
        assertThat(detalleBuscado.getSandwich()).isEqualTo(detalleCarro.getSandwich());
    }

    private DetalleCarro buscoElDetalleDeCarro(Long idDetalleCarro) throws DetalleInexistenteExeption {
        return servicio.obtenerDetalle(idDetalleCarro);
    }

    private void guardoElDetalleDeCarro(DetalleCarro detalleCarro) {
        servicio.guardarDetalle(detalleCarro);
    }

    private DetalleCarro dadoQueTengoUnDetalleDeCarro(long idDetalleDeCarro, Carro carro, Sandwich sandwich, int cantidad) {
        DetalleCarro detalleCarro = new DetalleCarro();
        detalleCarro.setCarro(carro);
        detalleCarro.setSandwich(sandwich);
        detalleCarro.setCantidad(cantidad);
        detalleCarro.setIdDetalleCarro(idDetalleDeCarro);
        return detalleCarro;
    }

    private Sandwich dadoQueTengoUnSandwich(long idSandwich, String sandwichDePrube, String miPedido) {
        Sandwich sandwich = new Sandwich();
        sandwich.setIdSandwich(idSandwich);
        sandwich.setDescripcion(miPedido);
        sandwich.setNombre(miPedido);
        sandwich.setIngrediente(new HashSet<>());
        sandwich.setEsApto("Vegano");
        sandwich.setEnPromocion(false);
        return sandwich;
    }


    private Carro dadoQueTengoUnCarro(Usuario usuario, Long idCarro) {
        Carro carro = new Carro();
        carro.setUsuario(usuario);
        carro.setIdCarro(idCarro);
        return carro;
    }

    private Usuario dadoQueTengoUnUsuario(long id, String email, String password) {
        Usuario nuevo = new Usuario();
        nuevo.setId(id);
        nuevo.setEmail(email);
        nuevo.setPassword(password);
        return nuevo;
    }

}

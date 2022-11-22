package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.DetalleInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.NoSePudoQuitarException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        buscoElDetalleDeCarro(idInexistente);

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

    @Test
    public void SiYaTengoUnSandwcihSeleccionadoYElijoElMismoSeAumenteLACantidadSeleccionada(){
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
       /* Sandwich sandwich2 = dadoQueTengoUnSandwich(2L, "sandwichDePrube", "miPedido");
        Sandwich sandwich3 = dadoQueTengoUnSandwich(3L, "sandwichDePrube", "miPedido");*/
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(1L, carro, sandwich, 8);
        //DetalleCarro detalleCarro2 = dadoQueTengoUnDetalleDeCarro(2L, carro, sandwich3, 1);
        buscoConMoquito2(detalleCarro1, usuario);
        Boolean respuesta = cuandoLePasoElMismoSandwich(1,usuario,sandwich);
        entoncesVerificoQueSeInvocoElMetodoActualizarDetalle();
        entoncesVerificoQueLaRespuestaSeaVerdadera(respuesta);
    }

    @Test
    public void SiYaTengoUnSandwcihSeleccionadoYNoElijoElMismoMeDevuelvaFalsa(){
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Sandwich sandwichPrimeraVez = dadoQueUnSandwichPorPrimeraVez();
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(1L, carro, sandwich, 8);
        buscoConMoquito2(detalleCarro1, usuario);
        Boolean respuesta = cuandoLePasoElMismoSandwich(1,usuario,sandwichPrimeraVez);
        entoncesVerificoQueLaRespuestaSeaFalsa(respuesta);
    }

    @Test
    public void SiYaTengoUnSandwichSeleccionadoDosVecesPuedaBajarCantidad() throws NoSePudoQuitarException {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(1L, carro, sandwich, 2);
        buscoConMoquito2(detalleCarro1, usuario);
        Boolean respuesta = cuandoLePasoElMismoSandwichYDecremento(1,usuario,sandwich);
        entoncesVerificoQueSeInvocoElMetodoActualizarDetalle();
        entoncesVerificoQueLaRespuestaSeaVerdadera(respuesta);
    }

    @Test (expected = NoSePudoQuitarException.class)
    public void siYaTengoUnSandwichSeleccionadoUnaVezMeLanzeExcepcion() throws NoSePudoQuitarException {
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(1L, carro, sandwich, 1);
        buscoConMoquito2(detalleCarro1, usuario);
        cuandoLePasoElMismoSandwichYDecremento(1,usuario,sandwich);
    }

    @Test
    public void queSePuedaVaciarUnCarro(){
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Sandwich sandwich = dadoQueTengoUnSandwich();
        Carro carro = dadoQueTengoUnCarro(usuario, 2L);
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(1L, carro, sandwich, 1);
        buscoConMoquito2(detalleCarro1,usuario);
        cuandoVacioElCarro(usuario);
        entoncesVerificoQueSeInvoqueALMetodoBorrarDetalleDeCarro();
    }

    private void entoncesVerificoQueSeInvoqueALMetodoBorrarDetalleDeCarro() {
        verify(this.repo,times(1)).borrarDetalleDeCarro(any(DetalleCarro.class));
    }

    private void cuandoVacioElCarro(Usuario usuario) {
        this.servicio.vaciarCarro(usuario);
    }

    private Boolean cuandoLePasoElMismoSandwichYDecremento(int i, Usuario usuario, Sandwich sandwich) throws NoSePudoQuitarException {
        return this.servicio.decrementarCantidad(i,usuario,sandwich);
    }

    private void entoncesVerificoQueLaRespuestaSeaFalsa(Boolean respuesta) {
        assertThat(respuesta).isFalse();
    }

    private void buscoConMoquito2(DetalleCarro detalleCarro1, Usuario usuario) {
        List<DetalleCarro> buscado = new ArrayList<>();
        buscado.add(detalleCarro1);
        when(repo.obtenerDetalleDeCarrPorUsuario(usuario)).thenReturn(buscado);
    }

    private Sandwich dadoQueUnSandwichPorPrimeraVez(){
        Ingrediente ing1 = new Ingrediente(1l, "test", 1F, 1, "test", "SinRestriccion");
        Ingrediente ing2 = new Ingrediente(2l, "test", 1F, 1, "test", "SinRestriccion");
        Set<Ingrediente> listaIngredientes = new LinkedHashSet<>();
        listaIngredientes.add(ing1);
        listaIngredientes.add(ing2);
        Sandwich sandwich = new Sandwich(1l, "sand1", "sand1", false, "SinRestriccion", listaIngredientes);
        return sandwich;
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

    private void entoncesVerificoQueLaRespuestaSeaVerdadera(Boolean respuesta) {
        assertThat(respuesta).isTrue();
    }

    private void entoncesVerificoQueSeInvocoElMetodoActualizarDetalle() {
        verify(this.repo).actualizarDetalleCarro(any());
    }

    private Boolean cuandoLePasoElMismoSandwich(int i, Usuario usuario, Sandwich sandwich2) {
        return this.servicio.incrementarCntidad(i,usuario,sandwich2);
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

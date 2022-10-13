package ar.edu.unlam.tallerweb1.domain.Ingredientes;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.*;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.RepositorioIngredientes;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngredienteImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ServicioIngredienteTest extends SpringTest {

    private ServicioDeIngrediente servicio;
    private RepositorioIngredientes repo;


    @Before
    public void init() {
        this.repo = mock(RepositorioIngredientes.class);
        this.servicio = new ServicioDeIngredienteImpl(this.repo);
    }

    @Test
    public void alPedirUnIngredienteQueLoDevuelta() throws IngredienteInvalidoException {
        cuandoLeSoliciteAlRepositorioBuscarUnIngredientePorId(1L);
        Ingrediente valorEsperado = dadoQueExisteUnIngrediente(1L, "Pan Centeno", 123F, 1, "detalle", "Vegano");
        Ingrediente valorObtenido = cuandoLlamoAlServicioParaQueDevuelvaUnIngredientePorID(valorEsperado);
        entoncesComparoQueAmbosIngredientesSeanElIguales(valorEsperado, valorObtenido);
    }

    private static void entoncesComparoQueAmbosIngredientesSeanElIguales(Ingrediente valorEsperado, Ingrediente valorObtenido) {
        assertThat(valorObtenido).isEqualTo(valorEsperado);
    }

    private Ingrediente cuandoLlamoAlServicioParaQueDevuelvaUnIngredientePorID(Ingrediente ingr) throws IngredienteInvalidoException {
        return this.servicio.obtenerIngredientePorId(ingr.getIdIngrediente());
    }

    @Test (expected = IngredienteInvalidoException.class)
    public void alPedirUnIngredientePorIdDesconocidoEsteNoExista() throws IngredienteInvalidoException {
        cuandoLeSoliciteAlRepositorioBuscarUnIngredientePorId(1L);
        Ingrediente n2 = dadoQueExisteUnIngrediente(2L, "Pan Negro", 100F, 1, "detalle", "Vegano");
        cuandoLlamoAlServicioParaQueDevuelvaUnIngredientePorID(n2);
    }

    @Test (expected = PasoInvalidoException.class)
    public void siPidoUnIngredienteConPasoQueNoExistaMeTireUnaExcepcion() throws PasoInvalidoException {
        cuandoSeLLameAlRepositorioParaObtenerUnaListaIngredientesPorPasoInvalidoMeDevuelvaUnaListaVacia(7);
        cuandoLlamoAlServicioParaTraerUnaListaDeIngredientesPorPaso(7);

    }

    private List<Ingrediente> cuandoLlamoAlServicioParaTraerUnaListaDeIngredientesPorPaso(Integer paso) throws PasoInvalidoException {
        return this.servicio.obtenerIngredientesPorPaso(paso);
    }

    private void cuandoSeLLameAlRepositorioParaObtenerUnaListaIngredientesPorPasoInvalidoMeDevuelvaUnaListaVacia(Integer paso_invalido){
        when(this.repo.obtenerIngredientePorPaso(paso_invalido)).thenReturn(new ArrayList<>());
    }

    @Test
    public void siPidoUnaListaDeIngredientesConPasoValidoDevuelvaListaNoVacia() throws PasoInvalidoException {
        Integer paso_valido = 1;
        obtenerTodosLosIngredientesDelPaso1();
        List<Ingrediente> valor_obtenido = cuandoLlamoAlServicioParaTraerUnaListaDeIngredientesPorPaso(paso_valido);
        entoncesVerificoQueLaListaDeIngredientesNoSeaUnaListaVacia(valor_obtenido);
    }

    private static void entoncesVerificoQueLaListaDeIngredientesNoSeaUnaListaVacia(List<Ingrediente> valor_obtenido) {
        assertThat(valor_obtenido).isNotEqualTo(new ArrayList<>());
    }

    @Test
    public void siPidoUnaListaDeIngredientesAptosParaSinRestriccionMeDevuelvaTodoSusIngredientes(){
        String esApto = "SinRestriccion";
        cuandoSeInvoqueAlRespositorioParaDevolverUnaListaDeIngredientesPorPreferencia(esApto);
        List<Ingrediente> valorObtenido = cuandoLLameAlServicioParaObtenerUnaListaDeIngredientesPorPreferencia(esApto);
        entoncesVerificoQueLoDevueltoTengaUnTamanioDe(valorObtenido, 4);
    }

    private static void entoncesVerificoQueLoDevueltoTengaUnTamanioDe(List<Ingrediente> valorObtenido, Integer tamanio) {
        assertThat(valorObtenido).hasSize(tamanio);
    }

    private List<Ingrediente> cuandoLLameAlServicioParaObtenerUnaListaDeIngredientesPorPreferencia(String esApto) {
        return this.servicio.obtenerIngredienteSiEsApto(esApto);
    }

    private void cuandoSeInvoqueAlRespositorioParaDevolverUnaListaDeIngredientesPorPreferencia(String preferencia) {
        when(this.repo.obtenerIngredienteSiEsApto(preferencia)).thenReturn(this.obtengoTodosLosIngredientesSinRestriccion());
    }

    @Test
    public void siPidoUnaListaDeIngredientesAptosQueNoExistanMeDevuelvaUnaListaVacia(){
        String esApto = "Vegano";
        cuandoSeLlameAlRepositorioParaObenerUnaListaDeIngredientesPorUnaPreferanciaQueNoExistaDevuelvaUnaListaVacia(esApto);
        List<Ingrediente> valorObenido = cuandoLLameAlServicioParaObtenerUnaListaDeIngredientesPorPreferencia(esApto);
        entoncesVerificoQueLaListaObtenidaSeaUnaListaVacia(valorObenido);
    }

    private static void entoncesVerificoQueLaListaObtenidaSeaUnaListaVacia(List<Ingrediente> valorObenido) {
        assertThat(valorObenido).isEmpty();
    }

    private void cuandoSeLlameAlRepositorioParaObenerUnaListaDeIngredientesPorUnaPreferanciaQueNoExistaDevuelvaUnaListaVacia(String esApto) {
        when(this.repo.obtenerIngredienteSiEsApto(esApto)).thenReturn(new ArrayList<>());
    }

    @Test
    public void queAlSolicitarUnaListaDeIngredientesPorPasoyPreferenciaMeDevuelvaUnaListaNoNula(){
        List<Ingrediente> valorEsperado = obtengoTodosLosIngredientesSinRestriccion();
        cuandoSeInvoqueAlRepositorioParaObtenerUnaListaDeIngredientesPorUnPasoYPreferenciaMeDevuelvaUnaListaNoVacia(1,"SinRestriccion",obtengoTodosLosIngredientesSinRestriccion());
        List<Ingrediente> valorObtenido = entoncesLlamoAlServicioPidiendoleUnaListaDeIUngredientesFiltradaPorPasoYPreferencia(1,"SinRestriccion");
        entoncesComparoQueAmbasListasSeanIguales(valorObtenido,valorEsperado);
    }

    private void cuandoSeInvoqueAlRepositorioParaObtenerUnaListaDeIngredientesPorUnPasoYPreferenciaMeDevuelvaUnaListaNoVacia(Integer paso, String preferencia, List<Ingrediente> t) {
        when(this.repo.obtenerIngredientesPorPasoYPorPreferencia(1, "SinRestriccion")).thenReturn(t);
    }

    private void entoncesComparoQueAmbasListasSeanIguales(List<Ingrediente> valorObtenido, List<Ingrediente> valorEsperado) {
        assertThat(valorObtenido).isNotNull();
        assertThat(valorObtenido).isEqualTo(valorEsperado);
    }

    private List<Ingrediente> entoncesLlamoAlServicioPidiendoleUnaListaDeIUngredientesFiltradaPorPasoYPreferencia(Integer paso, String preferencia) {
        return this.servicio.obtenerIngredientesFiltradoPorPasoYPreferencia(paso,preferencia);
    }

    private List<Ingrediente> obtengoTodosLosIngredientesSinRestriccion(){
        Ingrediente n1 = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco", "SinRestriccion");
        Ingrediente n2 = dadoQueExisteUnIngrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco", "SinRestriccion");
        Ingrediente n3 = dadoQueExisteUnIngrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco", "SinRestriccion");
        Ingrediente n4 = dadoQueExisteUnIngrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral", "SinRestriccion");
        return dadoQueTengoEstosIngredientesEnLaBaseDeDatos(n1,n2,n3,n4);
    }

    private List<Ingrediente> dadoQueTengoEstosIngredientesEnLaBaseDeDatos(Ingrediente n1, Ingrediente n2, Ingrediente n3, Ingrediente n4) {
        List<Ingrediente> valorEsperado= new ArrayList<>();
        valorEsperado.add(n1);
        valorEsperado.add(n2);
        valorEsperado.add(n3);
        valorEsperado.add(n4);
        return valorEsperado;
    }

    private Ingrediente dadoQueExisteUnIngrediente(long idIngrediente, String Pan_clasico, float precio, int paso, String Pan_lactal_blanco, String SinRestriccion) {
        Ingrediente n1 = new Ingrediente(idIngrediente, Pan_clasico, precio, paso, Pan_lactal_blanco, SinRestriccion);
        return n1;
    }

    private void cuandoLeSoliciteAlRepositorioBuscarUnIngredientePorId(Long id) {
        Ingrediente ejemplo = dadoQueExisteUnIngrediente(1L, "Pan Centeno", 123F, 1, "detalle", "Vegano");
        when(this.repo.obtenerIngredientePorId(id)).thenReturn(ejemplo);
    }

    private void obtenerTodosLosIngredientesDelPaso1() throws PasoInvalidoException {
        List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = dadoQueExisteUnIngrediente(1L, "Pan Centeno", 123F, 1, "detalle", "Vegano");
        Ingrediente n2 = dadoQueExisteUnIngrediente(2L, "Pan Negro", 100F, 1, "detalle", "Vegano");
        Ingrediente n3 = dadoQueExisteUnIngrediente(3L, "Pan Pizza", 160F, 1, "detalle", "Vegano");
        Ingrediente n4 = dadoQueExisteUnIngrediente(4L, "Pan Casero", 200F, 1, "detalle", "Vegano");
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        when(this.repo.obtenerIngredientePorPaso(1)).thenReturn(valor_esperado);
    }

}
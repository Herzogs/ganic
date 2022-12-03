package ar.edu.unlam.tallerweb1.domain.Sandwich;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.NoHaySandwichEnPromocionException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioSandwichTest extends SpringTest {


    private RepositorioSandwich repo;
    private ServicioSandwich serv;

    @Before
    public void init(){
        this.repo = mock(RepositorioSandwich.class);
        this.serv = new ServicioSandwichImp(this.repo);
    }

    //TODO: cambiar nombre del metodo
    @Test
    public void prueboQueAlSolicitarUnSandwichPorIdMeDevuelvaElSandwichEnCuestion() throws SandwichNoExistenteException {

        Sandwich valorEsperado = cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwichPorId();
        Sandwich valorObtenido = solicitoAlServicioUnSandwitchPorId(1L);
        entoncesVerificoQueLoRetornadoSeaLoEsperado(valorObtenido,valorEsperado);

    }

    @Test (expected = SandwichNoExistenteException.class)
    public void prueboQueAlSolicitarUnSandwichPorIdInvalidoMeDevuelvaUnaExcepcion() throws SandwichNoExistenteException {
        cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwichPorIdInvalidoMeDevuelvaExcepcion(100L);
        solicitoAlServicioUnSandwitchPorId(100L);
    }

    private void cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwichPorIdInvalidoMeDevuelvaExcepcion(Long id) {
        when(this.repo.obtenerSandwichPorId(id)).thenReturn(null);
    }

    //TODO: cambiar nombre del metodo
    @Test
    public void prueboQueAlSolicitarTodaLaListaDeSandwichesMeDevuelvaUnaListaConElementos() throws SandwichNoExistenteException {
        List<Sandwich> valorEsperado = dadoQueExistenVariosSandwiches();
        cuandoAlRepositorioLePidaQueMeDevuelvaTodosLosSandwiches(valorEsperado);
        List<Sandwich> valorObtenido = solicitoAlServicioQueMeDevuelvaTodosLosSandwiches();
        entoncesVerificoQueLoRetornadoSeaLoMismo(valorObtenido,valorEsperado);
    }

    @Test
    public void prueboQueAlObtenerTodosLosSandwichesEnPromocionMeDevuelvaUnaListaNoVacia() throws NoHaySandwichEnPromocionException {
        List<Sandwich> lista = dadoQueExistenSandwichesEnPromocion();
        cuandoAlRepositorioLePidaQueMeDevuelvaLaListaEnPromocion(lista);
        List<Sandwich> enPromocion = solicitoAlServicioQueMeDevuelvaTodosLosSandwichesEnPromocion();
        entoncesVerificoQueLoRetornadoSeaLoMismo(lista,enPromocion);
    }

    @Test (expected = NoHaySandwichEnPromocionException.class)
    public void prueboQueAlObtenerTodosLosSandwichesEnPromocionMeDevuelvaUnaExcepcion() throws NoHaySandwichEnPromocionException {
        cuandoAlRepositorioLePidaQueMeDevuelvaLaListaEnPromocion(new ArrayList<>());
        solicitoAlServicioQueMeDevuelvaTodosLosSandwichesEnPromocion();
    }

    @Test
    public void prueboQueAlObtenerTodosLosSandwichesDeUnTipoMeDevuelvaUnaListaNoVacia()throws SandwichNoExistenteException{
        List<Sandwich> esperado = dadoQueExistenVariosSandwichesDeUnTipo();
        cuandoAlRepositorioLePidaQueMeDevuelvaLaListaDeUnTipo(esperado);
        List<Sandwich> obtenido = solicitoAlServicioListaDeSandwichesDeUnTipo("Vegano");
        entoncesVerificoQueLoRetornadoSeaLoMismo(esperado,obtenido);
    }

    private void cuandoAlRepositorioLePidaQueMeDevuelvaLaListaDeUnTipo(List<Sandwich> esperado) {
        when(this.repo.obtenerTodosLosSandwitchPorPreferencia("Vegano")).thenReturn(esperado);
    }

    @Test (expected = SandwichNoExistenteException.class)
    public void prueboQueAlObtenerTodosLosSandwichesDeUnTipoMeDevuelvaUnaExcepcion()throws SandwichNoExistenteException{
        cuandoAlRepositorioLePidaQueMeDevuelvaLaListaEnPromocion(new ArrayList<>());
        solicitoAlServicioListaDeSandwichesDeUnTipo("Vegano");
    }

    @Test
    public void prueboQueAlSolcitarLosIngredientesDeUnSandwichMeDevuelvaUnaListaNoVacia() throws SandwichNoExistenteException{
        Sandwich nuevo = dadoQueTengoUnSandwichConIngredientes();
        cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwich(nuevo);
        List<Ingrediente> obtenido = cuandoLePidoAlServicioQueMeTraigaLosIngredientesDelSandwich(nuevo.getIdSandwich());
        assertThat(obtenido).hasSize(2);
    }

    @Test (expected = SandwichNoExistenteException.class)
    public void prueboQueAlSolcitarLosIngredientesDeUnSandwichQueNoExistaMeDevuelvaUnaExcepcion() throws SandwichNoExistenteException {
        cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwichNulo(100L);
        cuandoLePidoAlServicioQueMeTraigaLosIngredientesDelSandwich(100L);
    }

    private void cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwichNulo(Long id) {
        when(this.repo.obtenerSandwichPorId(id)).thenReturn(null);
    }

    private List<Ingrediente> cuandoLePidoAlServicioQueMeTraigaLosIngredientesDelSandwich(Long idIngrediente) throws SandwichNoExistenteException {
        return this.serv.obtenerLosIngredientesDeUnSandwich(idIngrediente);
    }

    private void cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwich(Sandwich s) {
        when(this.repo.obtenerSandwichPorId(s.getIdSandwich())).thenReturn(s);
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

    private List<Sandwich> solicitoAlServicioListaDeSandwichesDeUnTipo(String vegano) throws SandwichNoExistenteException {
        return this.serv.obtenerTodosLosSandwichesDeUnTipo(vegano);
    }

    private List<Sandwich> dadoQueExistenVariosSandwichesDeUnTipo() {
        List<Sandwich> lista = new ArrayList<>();
        Sandwich s1 = new Sandwich(1L,"sandwich1","sandwich1");
        s1.setEsApto("Vegano");
        Sandwich s2 = new Sandwich(2L,"sandwich2","sandwich2");
        s1.setEsApto("Vegano");
        lista.add(s1);
        lista.add(s2);
        return lista;
    }

    private void entoncesVerificoQueLoRetornadoSeaLoMismo(List<Sandwich> lista, List<Sandwich> enPromocion) {
        assertThat(lista).isEqualTo(enPromocion);
    }

    private List<Sandwich> solicitoAlServicioQueMeDevuelvaTodosLosSandwichesEnPromocion() throws NoHaySandwichEnPromocionException {
        return this.serv.obtenerTodosLosSandwichesEnPromocion();
    }

    private void cuandoAlRepositorioLePidaQueMeDevuelvaLaListaEnPromocion(List<Sandwich> lista) {
        when(this.repo.obtenerTodosLosSandwitchEnPromocion()).thenReturn(lista);
    }

    private List<Sandwich> dadoQueExistenSandwichesEnPromocion() {
        List<Sandwich> lista = new ArrayList<>();
        lista.add(new Sandwich(1L,"sandwich1","sandwich1"));
        return lista;
    }


    private void entoncesVerificoQueLoRetornadoNoSeaUnaListaVacia(List<Sandwich> valorObtenido, List<Sandwich> valorEsperado) {
        assertThat(valorObtenido).isNotEmpty();
        assertThat(valorObtenido).hasSize(valorEsperado.size());
        assertThat(valorObtenido).isEqualTo(valorEsperado);
    }

    private List<Sandwich> solicitoAlServicioQueMeDevuelvaTodosLosSandwiches() throws SandwichNoExistenteException {
        return this.serv.obtenerTodosLosSandwiches();
    }

    private void cuandoAlRepositorioLePidaQueMeDevuelvaTodosLosSandwiches(List<Sandwich> lista) {
        when(this.repo.obtenerTodosLosSandwiches()).thenReturn(lista);
    }

    private Sandwich dadoQueExisteUnSandwich(String nombre, String desc) {
        Sandwich nuevo = new Sandwich();
        nuevo.setNombre(nombre);
        nuevo.setDescripcion(desc);
        return nuevo;
    }

    private List<Sandwich> dadoQueExistenVariosSandwiches() {
        Sandwich s1 = this.dadoQueExisteUnSandwich("sandwich1", "sandwich1");
        Sandwich s2 = this.dadoQueExisteUnSandwich("sandwich2", "sandwich2");
        Sandwich s3 = this.dadoQueExisteUnSandwich("sandwich3", "sandwich3");
        List<Sandwich> lista = new ArrayList<>();
        lista.add(s1);
        lista.add(s2);
        lista.add(s3);
        return lista;
    }


    private void entoncesVerificoQueLoRetornadoSeaLoEsperado(Sandwich valorObtenido, Sandwich valorEsperado) {
        assertThat(valorObtenido).isNotNull();
        assertThat(valorObtenido).isEqualTo(valorEsperado);
    }

    private Sandwich solicitoAlServicioUnSandwitchPorId(long idSandwich) throws SandwichNoExistenteException {
        return this.serv.obtenerSandwichPorId(idSandwich);
    }

    private Sandwich cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwichPorId() {
        Sandwich s = new Sandwich();
        s.setNombre("ganic");
        s.setDescripcion("ganic");
        when(this.repo.obtenerSandwichPorId(1L)).thenReturn(s);
        return s;
    }
}
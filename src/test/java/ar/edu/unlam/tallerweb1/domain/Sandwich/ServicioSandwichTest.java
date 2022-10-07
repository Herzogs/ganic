package ar.edu.unlam.tallerweb1.domain.Sandwich;

import ar.edu.unlam.tallerweb1.SpringTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void queAlSolicitarUnSandwichPorIdMeDevuelvaUnValor(){

        Sandwich valorEsperado = cuandoAlRepositorioLePidaQueMeDevuelvaUnSandwichPorId();
        Sandwich valorObtenido = solicitoAlServicioUnSandwitchPorId(1L);
        entoncesVerificoQueLoRetornadoSeaLoEsperado(valorObtenido,valorEsperado);

    }

    //TODO: cambiar nombre del metodo
    @Test
    public void queAlSolicitarLaListaDeSandwichesMeDevuelvaUnaListaNoVacia(){

        cuandoAlRepositorioLePidaQueMeDevuelvaTodosLosSandwiches();
        List<Sandwich> valorObtenido = solicitoAlServicioQueMeDevuelvaTodosLosSandwiches();
        entoncesVerificoQueLoRetornadoNoSeaUnaListaVacia(valorObtenido);
    }

    private void entoncesVerificoQueLoRetornadoNoSeaUnaListaVacia(List<Sandwich> valorObtenido) {
        assertThat(valorObtenido).isNotEmpty();
    }

    private List<Sandwich> solicitoAlServicioQueMeDevuelvaTodosLosSandwiches() {
        return this.serv.obtenerTodosLosSandwiches();
    }

    private void cuandoAlRepositorioLePidaQueMeDevuelvaTodosLosSandwiches() {
        when(this.repo.obtenerTodosLosSandwiches()).thenReturn(dadoQueExistenVariosSandwiches());
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

    private Sandwich solicitoAlServicioUnSandwitchPorId(long idSandwich) {
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

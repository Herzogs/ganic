package ar.edu.unlam.tallerweb1.infrastructure.Sandwich;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Sandwich.RepositorioSandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RepositorioSandwichTest extends SpringTest {

    @Autowired
    private RepositorioSandwich repositorioSandwich;

    @Test @Rollback @Transactional
    public void queAlCrearUnSandwitchSinIngredientesYSeGuardeElMismo(){
        Sandwich valorEsperado = dadoQueExisteUnSandwich("ganic","ganic");
        guardoElSandwichCreado(valorEsperado);
        Sandwich valorObtenido = buscoUnSandwichPor(valorEsperado);
        entoncesComparoAmbosSandwiches(valorObtenido,valorEsperado);
    }

    @Test @Rollback @Transactional
    public void queAlCrearUnSandwitchConIngredientesSeGuardeElMismo(){
        Ingrediente pan = dadoQueExisteUnIngrediente(1L,"Pan Blanco",12F,1,"Pan Blanco","SinRestriccion");
        Ingrediente principal = dadoQueExisteUnIngrediente(2L, "Cerdo",12F,2,"Cerdo","SinRestricciones");
        Ingrediente opcional = dadoQueExisteUnIngrediente(3L,"Ensalada Mixta",12F,3,"Ensalada Mixta","SinRestricciones");
        Sandwich valorEsperado = dadoQueExisteUnSandwich("ganic","ganic");
        agregarIngredientesAlSandwich(valorEsperado,pan,principal,opcional);
        guardoElSandwichCreado(valorEsperado);
        Sandwich valorObtenido = buscoUnSandwichPor(valorEsperado);
        entoncesComparoAmbosSandwiches(valorObtenido,valorEsperado);
    }

    @Test @Rollback @Transactional
    public void queAlSolicitarLaListaDeSandwichesMeDevuelvaUnaListaNoVacia(){
        List<Sandwich> valorObtenido = obtengoTodasLosSandwichesDeLaBaseDeDatos();
        entoncesVerificoQueLaListaObtenidaTenga(valorObtenido, 6);
    }

    private void entoncesVerificoQueLaListaObtenidaTenga(List<Sandwich> valorObtenido, Integer expected) {
        assertThat(valorObtenido).hasSize(expected);
    }

    @Test @Rollback @Transactional
    public void queAlSolicitarLaListaDeSandwichesEnPromocionMeDevuelvaUnaListaNoVacia(){
        List<Sandwich> valorObtenido = obtengoTodasLosSandwichesEnPromocionDeLaBaseDeDatos();
        entoncesVerificoQueLaListaObtenidaTenga(valorObtenido, 5);
    }

    // TODO:: Agregar referencia a base H2 y modificar todos los test
    @Test @Rollback @Transactional
    public void queAlSolicitarLaListaDeSandwichesConPreferenciaVeganoMeDevuelvaUnaListaNoVacia(){

        List<Sandwich> valorObtenido = obtengoTodasLosSandwichesDeUnTipoDeLaBaseDeDatos("Vegano");
        entoncesVerificoQueLaListaObtenidaTenga(valorObtenido, 2);
    }

    private List<Sandwich> obtengoTodasLosSandwichesDeUnTipoDeLaBaseDeDatos(String pref) {
        return this.repositorioSandwich.obtenerTodosLosSandwitchPorPreferencia(pref);
    }

    private List<Sandwich> obtengoTodasLosSandwichesEnPromocionDeLaBaseDeDatos() {
        return this.repositorioSandwich.obtenerTodosLosSandwitchEnPromocion();
    }

    private void entoncesComparoAmbasListasDeSandwiches(List<Sandwich> valorObtenido, List<Sandwich> valorEsperado) {
        assertThat(valorObtenido).isNotEmpty();
        entoncesVerificoQueLaListaObtenidaTenga(valorObtenido, valorEsperado.size());
        assertThat(valorObtenido).isEqualTo(valorEsperado);
    }

    private List<Sandwich> obtengoTodasLosSandwichesDeLaBaseDeDatos() {
        return this.repositorioSandwich.obtenerTodosLosSandwiches();
    }

    private List<Sandwich> dadoQueExistenVariosSandwiches() {
        Sandwich s1 = this.dadoQueExisteUnSandwich("sandwich1", "sandwich1");
        Sandwich s2 = this.dadoQueExisteUnSandwich("sandwich2", "sandwich2");
        Sandwich s3 = this.dadoQueExisteUnSandwich("sandwich3", "sandwich3");
        List<Sandwich> lista = new ArrayList<>();
        lista.add(s1);
        lista.add(s2);
        lista.add(s3);
        lista.add(s1);
        lista.add(s2);
        lista.add(s3);
        return lista;
    }


    private void entoncesComparoAmbosSandwiches(Sandwich valorObtenido, Sandwich valorEsperado) {
        System.err.println(valorObtenido.getIngrediente());
        assertThat(valorObtenido).isNotNull();
        assertThat(valorObtenido).isEqualTo(valorEsperado);
    }

    private Sandwich buscoUnSandwichPor(Sandwich valorEsperado) {
        return this.repositorioSandwich.obtenerSandwichPorId(valorEsperado.getIdSandwich());
    }

    private void guardoElSandwichCreado(Sandwich valorEsperado) {
        session().save(valorEsperado);
    }

    private void agregarIngredientesAlSandwich(Sandwich valorEsperado, Ingrediente pan, Ingrediente principal, Ingrediente opcional) {
        valorEsperado.agregarIngrediente(pan);
        valorEsperado.agregarIngrediente(principal);
        valorEsperado.agregarIngrediente(opcional);
    }

    private Ingrediente dadoQueExisteUnIngrediente(long l, String nombre, float precio, int paso, String detalle, String tipo) {
        Ingrediente nuevo = new Ingrediente(1L,"Pan Blanco",12F,1,"Pan Blanco","SinRestricciones");
        session().save(nuevo);
        return nuevo;
    }

    private Sandwich dadoQueExisteUnSandwich(String nombre, String desc) {
        Sandwich nuevo = new Sandwich();
        nuevo.setNombre(nombre);
        nuevo.setDescripcion(desc);
        return nuevo;
    }
}

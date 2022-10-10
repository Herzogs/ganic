package ar.edu.unlam.tallerweb1.infrastructure.Ingredientes;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.RepositorioIngredientes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class RepositorioIngredientesImplTest extends SpringTest {

    @Autowired
    private RepositorioIngredientes repo;

    @Test @Transactional @Rollback
    public void buscarIngredientePorID() {
        Ingrediente nuevo = dadoQueExisteUnIngrediente(50L,"Pan",12F,1,"pan","SinRestriccion");
        entoncesGuardoElIngredienteExistenteEnLaBaseDeDatos(nuevo);
        Ingrediente res = entoncesBuscoElIngredienteEnLaBaseDeDAtos(nuevo);
        seVerificaQueLaBusquedaDioElResultadoEsperado(nuevo, res);
    }

    private Ingrediente entoncesBuscoElIngredienteEnLaBaseDeDAtos(Ingrediente ing) {
        return this.repo.obtenerIngredientePorId(ing.getIdIngrediente());
    }

    private void entoncesGuardoElIngredienteExistenteEnLaBaseDeDatos(Ingrediente nuevo) {
        this.repo.guardarIngrediente(nuevo);
    }

    private void seVerificaQueLaBusquedaDioElResultadoEsperado(Ingrediente nuevo, Ingrediente res) {
        assertThat(res).isEqualTo(nuevo);
    }

    @Test @Transactional @Rollback
    public void queCuandoSeIntenteBuscarUnIngredienteQueNoExistaDevuelvaUnNULL(){
        Ingrediente nuevo = dadoQueExisteUnIngrediente(1L,"Pan",12F,1,"pan","SinRestriccion");
        entoncesGuardoElIngredienteExistenteEnLaBaseDeDatos(nuevo);
        Ingrediente ingredienteInexistente = dadoQueExisteUnIngrediente(2L,"Error",12F,2,"error","SinRestriccion");
        Ingrediente retorno_busqueda = entoncesBuscoElIngredienteEnLaBaseDeDAtos(ingredienteInexistente);
        entoncesVerificoQueElIgredienteNoExistaEnLaBaseDeDatos(retorno_busqueda);
    }

    private static void entoncesVerificoQueElIgredienteNoExistaEnLaBaseDeDatos(Ingrediente retorno_busqueda) {
        assertThat(retorno_busqueda).isNull();
    }

    @Test @Transactional
    public void queAlBuscarPorUnPasoEspecificoValidoMeRetorneUnaListaNoVacia(){
        List<Ingrediente> valor_obtenido = cuandoLePidaAlRepositorioQueTraigaLosIngredientesPorUnPasoEspecifico(1);
        entoncesVerificoQueLaListaNoSeaNula(valor_obtenido);
    }

    private static void entoncesVerificoQueLaListaNoSeaNula(List<Ingrediente> valor_obtenido) {
        assertThat(valor_obtenido).isNotNull();
        assertThat(valor_obtenido).isNotEmpty();
    }

    private List<Ingrediente> cuandoLePidaAlRepositorioQueTraigaLosIngredientesPorUnPasoEspecifico(Integer pasoEspecifico) {
        return this.repo.obtenerIngredientePorPaso(pasoEspecifico);
    }

    @Test @Transactional
    public void queAlBuscarSiEsAptoMeRetorneAlgunValor(){
        Ingrediente n1 = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco","SinRestriccion");
        Ingrediente n2 = dadoQueExisteUnIngrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco","SinRestriccion");
        Ingrediente n3 = dadoQueExisteUnIngrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco","SinRestriccion");
        Ingrediente n4 = dadoQueExisteUnIngrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral","SinRestriccion");
        Ingrediente n5 = dadoQueExisteUnIngrediente(9L, "Medallo de carne", 450F, 2, "Carne de ternera a la parrilla","SinRestriccion");
        Ingrediente n6 = dadoQueExisteUnIngrediente(10L, "Cerdo", 450F, 2, "Bondiola de cerdo a la parrilla","SinRestriccion");
        Ingrediente n7 = dadoQueExisteUnIngrediente(11L, "Mila-pollo", 450F, 2, "Milanesa de pollo","SinRestriccion");
        Ingrediente n8 = dadoQueExisteUnIngrediente(12L, "Vegetalizima", 400F, 2, "Milanesa de soja","SinRestriccion");
        Ingrediente n9 = dadoQueExisteUnIngrediente(13L, "Mix verde", 380F, 2, "Salteado de verduras","SinRestriccion");
        Ingrediente n10 = dadoQueExisteUnIngrediente(14L, "Barbacoa", 150F, 3, "Salsa de barbacoa","SinRestriccion");
        Ingrediente n11 = dadoQueExisteUnIngrediente(15L, "Pure", 200F, 3, "Pure de papas","SinRestriccion");
        Ingrediente n12 = dadoQueExisteUnIngrediente(16L, "Mostayesa", 150F, 3, "Mix mostaza y mayonesa","SinRestriccion");
        Ingrediente n13 = dadoQueExisteUnIngrediente(17L, "La casa", 250F, 3, "Guacamole y mayonesa","SinRestriccion");
        List<Ingrediente> valorEsperado = dadoQueTengoEstosIngredientesEnLaBaseDeDatos(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13);
        List<Ingrediente> valor_obtenido = entoncesLeDigoAlRepositorioQueMeTraigaTodosLosIngredientesQueNoTenganRestriccion();
        entoncesVerificoQueLasListasSeanIguales(valorEsperado, valor_obtenido);
    }

    private void entoncesVerificoQueLasListasSeanIguales(List<Ingrediente> valorEsperado, List<Ingrediente> valor_obtenido) {
        assertThat(valorEsperado).isEqualTo(valor_obtenido);
    }

    private List<Ingrediente> entoncesLeDigoAlRepositorioQueMeTraigaTodosLosIngredientesQueNoTenganRestriccion() {
        return this.repo.obtenerIngredienteSiEsApto("SinRestriccion");
    }

    private static List<Ingrediente> dadoQueTengoEstosIngredientesEnLaBaseDeDatos(Ingrediente n1, Ingrediente n2, Ingrediente n3, Ingrediente n4, Ingrediente n5, Ingrediente n6, Ingrediente n7, Ingrediente n8, Ingrediente n9, Ingrediente n10, Ingrediente n11, Ingrediente n12, Ingrediente n13) {
        List<Ingrediente> valorEsperado= new ArrayList<>();
        valorEsperado.add(n1);
        valorEsperado.add(n2);
        valorEsperado.add(n3);
        valorEsperado.add(n4);
        valorEsperado.add(n5);
        valorEsperado.add(n6);
        valorEsperado.add(n7);
        valorEsperado.add(n8);
        valorEsperado.add(n9);
        valorEsperado.add(n10);
        valorEsperado.add(n11);
        valorEsperado.add(n12);
        valorEsperado.add(n13);
        return valorEsperado;
    }

    private static Ingrediente dadoQueExisteUnIngrediente(long idIngrediente, String Pan_clasico, float precio, int paso, String Pan_lactal_blanco, String esApto) {
        return new Ingrediente(idIngrediente, Pan_clasico, precio, paso, Pan_lactal_blanco,esApto);
    }

    @Test @Transactional
    public void queAlPedirUnaListaDeIngredientesDeLaBaseDeDatosSeFiltrePorPasoYPreferencia(){
        List<Ingrediente> listaFiltrada = entoncesLeSolicitoALaBaseDeDatosQueMeTraigaTodosLosIngredientesFiltradosPor(1,"SinRestriccion");
        System.err.println(listaFiltrada);
        entoncesVerificoQueLaListaNoSeaNula(listaFiltrada);
    }

    private List<Ingrediente> entoncesLeSolicitoALaBaseDeDatosQueMeTraigaTodosLosIngredientesFiltradosPor(Integer i, String preferencia) {
        return this.repo.obtenerIngredientesPorPasoYPorPreferencia(i,preferencia);
    }


}
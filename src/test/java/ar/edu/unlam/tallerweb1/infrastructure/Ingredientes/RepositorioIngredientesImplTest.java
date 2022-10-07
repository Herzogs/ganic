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

    @Test
    @Transactional
    @Rollback
    public void buscarIngredientePorNombre() {
        Ingrediente nuevo = primeroGeneramosUnIngrediente();
        this.repo.guardarIngrediente(nuevo);
        List<Ingrediente> res = seBuscarElIngredienteSolicitado(nuevo.getNombre());
        seVerificaQueLaBusquedaDioElResultadoEsperado(nuevo, res);
    }

    private List<Ingrediente> seBuscarElIngredienteSolicitado(String desc) {
        return repo.obtenerIngredientePorNombre(desc);
    }

    private void seVerificaQueLaBusquedaDioElResultadoEsperado(Ingrediente nuevo, List<Ingrediente> res) {
        assertThat(res.get(0)).isEqualTo(nuevo);
    }

    private Ingrediente primeroGeneramosUnIngrediente() {
        Ingrediente nuevo = new Ingrediente();
        nuevo.setIdIngrediente(2L);
        nuevo.setNombre("pan");
        nuevo.setPrecio(12F);
        nuevo.setPaso(2);
        return nuevo;
    }

    @Test @Transactional @Rollback
    public void queAlBuscarPorUnIngredienteEsteNoExista(){
        String producto_no_existente = "carne";
        List<Ingrediente> retorno_busqueda = seBuscarElIngredienteSolicitado(producto_no_existente);
        seVerificaQueLaBusquedaDioElResultadoEsperado(retorno_busqueda);
    }

    private void seVerificaQueLaBusquedaDioElResultadoEsperado(List<Ingrediente> retorno_busqueda) {
        List<Ingrediente> valor_esperado = new ArrayList<>();
        assertThat(retorno_busqueda).isEqualTo(valor_esperado);
    }


    @Test @Transactional
    public void queAlBuscarPorUnPasoEspecificoMeRetorneAlgunValor(){
        /*List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = new Ingrediente(5L,"Pan clasico",150F,1, "Pan lactal blanco","SinRestriccion");
        Ingrediente n2 = new Ingrediente(6L,"Pan flauta",120F,1, "Pan de mesa blanco","SinRestriccion");
        Ingrediente n3 = new Ingrediente(7L,"Pan de campo",250F,1, "Pan de campo blanco","SinRestriccion");
        Ingrediente n4 = new Ingrediente(8L,"Pan integral",280F,1, "Pan lactal integral","SinRestriccion");
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);*/
        List<Ingrediente> valor_obtenido = this.repo.obtenerIngredientePorPaso(1);
        /*assertThat(valor_esperado).isEqualTo(valor_obtenido);*/
        assertThat(valor_obtenido).isNotNull();
        assertThat(valor_obtenido).isNotEmpty();
    }

    @Test @Transactional
    public void queAlBuscarSiEsAptoMeRetorneAlgunValor(){
        List<Ingrediente> valorEsperado= new ArrayList<>();

        Ingrediente n1 = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco","SinRestriccion");
        Ingrediente n2 = new Ingrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco","SinRestriccion");
        Ingrediente n3 = new Ingrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco","SinRestriccion");
        Ingrediente n4 = new Ingrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral","SinRestriccion");
        Ingrediente n5 = new Ingrediente (9L,"Medallo de carne",450F,2,"Carne de ternera a la parrilla","SinRestriccion");
        Ingrediente n6 = new Ingrediente (10L,"Cerdo",450F,2,"Bondiola de cerdo a la parrilla","SinRestriccion");
        Ingrediente n7 = new Ingrediente(11L,"Mila-pollo",450F,2,"Milanesa de pollo","SinRestriccion");
        Ingrediente n8 = new Ingrediente(12L,"Vegetalizima",400F,2,"Milanesa de soja","SinRestriccion");
        Ingrediente n9 = new Ingrediente(13L,"Mix verde",380F,2,"Salteado de verduras","SinRestriccion");
        Ingrediente n10 = new Ingrediente (14L,"Barbacoa",150F,3,"Salsa de barbacoa","SinRestriccion");
        Ingrediente n11 = new Ingrediente(15L,"Pure",200F,3,"Pure de papas","SinRestriccion");
        Ingrediente n12 = new Ingrediente(16L,"Mostayesa",150F,3,"Mix mostaza y mayonesa","SinRestriccion");
        Ingrediente n13 = new Ingrediente (17L,"La casa",250F,3,"Guacamole y mayonesa","SinRestriccion");
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
        List<Ingrediente> valor_obtenido = this.repo.obtenerIngredienteSiEsApto("SinRestriccion");
        assertThat(valorEsperado).isEqualTo(valor_obtenido);
    }


}
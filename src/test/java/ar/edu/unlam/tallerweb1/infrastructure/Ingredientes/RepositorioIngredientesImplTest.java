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
        List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = new Ingrediente(5L,"Pan clasico",150F,1, "Pan lactal blanco");
        Ingrediente n2 = new Ingrediente(6L,"Pan flauta",120F,1, "Pan de mesa blanco");
        Ingrediente n3 = new Ingrediente(7L,"Pan de campo",250F,1, "Pan de campo blanco");
        Ingrediente n4 = new Ingrediente(8L,"Pan integral",280F,1, "Pan lactal integral");
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        List<Ingrediente> valor_obtenido = this.repo.obtenerIngredientePorPaso(1);
        System.out.println(repo.obtenerIngredientePorId(1L).getNombre()+ "Datos esperados");
        assertThat(valor_esperado).isEqualTo(valor_obtenido);
    }
}
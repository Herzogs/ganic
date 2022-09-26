package ar.edu.unlam.tallerweb1.domain.Ingredientes;

import ar.edu.unlam.tallerweb1.SpringTest;
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
    public void inicializandoComponentes() {
        this.repo = mock(RepositorioIngredientes.class);
        this.servicio = new ServicioDeIngredienteImpl(this.repo);
    }

    @Test
    public void siNoEncuentraIngredienteConPasoDevuelveListaVacia() {
        Integer paso = 2;
        dadoQueNoExistenIngredientesParaElPasoSolicitado(paso);
        List<Ingrediente> valor_obtenido = this.servicio.obtenerIngredientesPorPaso(2);
        assertThat(valor_obtenido).isEmpty();
    }

    @Test
    public void siSeEncuentraIngredienteConPasoDevuelveListaDeIngrediente() {
        Integer paso = 1;
        dadoQueNoExistenIngredientesParaElPasoSolicitado(paso);
        List<Ingrediente> valor_obtenido = this.servicio.obtenerIngredientesPorPaso(2);
        assertThat(valor_obtenido).isEmpty();
    }

    private void dadoQueNoExistenIngredientesParaElPasoSolicitado(Integer paso) {
        when(this.repo.obtenerIngredientePorPaso(paso)).thenReturn(new ArrayList<>());
    }

    private void dadoQueSiExistenIngredientesParaElPasoSolicitado(Integer paso) {
        Integer pasoValido = 1;
        dadoQueSiExisteIngrdienteParaElPasoSolicitado(pasoValido);
        List<Ingrediente> valor_obtenido = cuandoBuscoUnIngredientePorPaso(pasoValido);
        assertThat(valor_obtenido).isNotEmpty();
    }

    private List<Ingrediente> cuandoBuscoUnIngredientePorPaso(Integer paso) {
        return this.servicio.obtenerIngredientesPorPaso(paso);
    }

    private void dadoQueSiExisteIngrdienteParaElPasoSolicitado(Integer paso) {
        List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = new Ingrediente(1L, "Pan Centeno", 123F, 1);
        Ingrediente n2 = new Ingrediente(2L, "Pan Negro", 100F, 1);
        Ingrediente n3 = new Ingrediente(3L, "Pan Pizza", 160F, 1);
        Ingrediente n4 = new Ingrediente(4L, "Pan Casero", 200F, 1);
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        when(this.repo.obtenerIngredientePorPaso(paso)).thenReturn(new ArrayList<>());
    }

}

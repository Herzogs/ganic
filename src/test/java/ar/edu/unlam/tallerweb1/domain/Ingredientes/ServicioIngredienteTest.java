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
    public void init() {
        this.repo = mock(RepositorioIngredientes.class);
        this.servicio = new ServicioDeIngredienteImpl(this.repo);
    }

    @Test
    public void alPedirUnIngredienteQueLoDevuelta() {
        obtenerIngredientePorID(1L);
        Ingrediente n1 = new Ingrediente(1L, "Pan Centeno", 123F, 1, "detalle");
        System.out.println("VALOR QUES ESTAMOS ESPERANDO ID: " + this.repo.obtenerIngredientePorId(1L));
        assertThat(this.repo.obtenerIngredientePorId(1L)).isEqualTo(n1);
    }

    @Test
    public void alPedirUnIngredientePorIdEsteNoCoincida() {
        obtenerIngredientePorID(1L);
        Ingrediente n2 = new Ingrediente(2L, "Pan Negro", 100F, 1, "detalle");
        System.out.println("VALOR QUES ESTAMOS ESPERANDO ID: " + this.repo.obtenerIngredientePorId(1L));
        assertThat(this.repo.obtenerIngredientePorId(1L)).isNotEqualTo(n2);
    }

    @Test
    public void siPidoUnIngredienteConPasoQueNoExistaDevuelvaListaVacia() {
        Integer paso_invalido = 7;
        obtenerTodosLosIngredientesDelPaso1();
        List<Ingrediente> valor_obtenido = this.servicio.obtenerIngredientesPorPaso(paso_invalido);
        System.out.println("VALOR DEL SERVICIO: " + this.servicio.obtenerIngredientesPorPaso(7));
        assertThat(valor_obtenido).isEmpty();
    }

    @Test
    public void siPidoUnaListaDeIngredientesConPasoValidoDevuelvaListaNoVacia() {
        Integer paso_valido = 1;
        obtenerTodosLosIngredientesDelPaso1();
        List<Ingrediente> valor_obtenido = this.servicio.obtenerIngredientesPorPaso(paso_valido);
        System.out.println("VALOR DEL SERVIÇIO DEL PASO 1:" + valor_obtenido);
        assertThat(valor_obtenido).isNotEqualTo(new ArrayList<>());
    }

    private void obtenerIngredientePorID(Long id){
        Ingrediente ejemplo = new Ingrediente(1L, "Pan Centeno", 123F, 1, "detalle");
        when(this.repo.obtenerIngredientePorId(id)).thenReturn(ejemplo);
    }

    private void obtenerTodosLosIngredientesDelPaso1(){
        List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = new Ingrediente(1L, "Pan Centeno", 123F, 1, "detalle");
        Ingrediente n2 = new Ingrediente(2L, "Pan Negro", 100F, 1, "detalle");
        Ingrediente n3 = new Ingrediente(3L, "Pan Pizza", 160F, 1, "detalle");
        Ingrediente n4 = new Ingrediente(4L, "Pan Casero", 200F, 1, "detalle");
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        when(this.repo.obtenerIngredientePorPaso(1)).thenReturn(valor_esperado);
    }
}
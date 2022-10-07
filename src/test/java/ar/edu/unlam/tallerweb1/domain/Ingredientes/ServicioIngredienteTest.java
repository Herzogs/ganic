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
    public void alPedirUnIngredienteQueLoDevuelta() {
        obtenerIngredientePorID(1L);
        Ingrediente n1 = new Ingrediente(1L, "Pan Centeno", 123F, 1, "detalle","Vegano");
        System.out.println("VALOR QUES ESTAMOS ESPERANDO ID: " + this.repo.obtenerIngredientePorId(1L));
        assertThat(this.repo.obtenerIngredientePorId(1L)).isEqualTo(n1);
    }

    @Test
    public void alPedirUnIngredientePorIdEsteNoCoincida() {
        obtenerIngredientePorID(1L);
        Ingrediente n2 = new Ingrediente(2L, "Pan Negro", 100F, 1, "detalle","Vegano");
        System.out.println("VALOR QUES ESTAMOS ESPERANDO ID: " + this.repo.obtenerIngredientePorId(1L));
        assertThat(this.repo.obtenerIngredientePorId(1L)).isNotEqualTo(n2);
    }

    @Test (expected = PasoInvalidoException.class)
    public void siPidoUnIngredienteConPasoQueNoExistaDevuelvaListaVacia() throws PasoInvalidoException {
        Integer paso_invalido = 7;
        /*obtenerTodosLosIngredientesDelPaso1();*/
        when(this.servicio.obtenerIngredientesPorPaso(paso_invalido)).thenThrow(new PasoInvalidoException("Paso Invalido"));
        List<Ingrediente> valor_obtenido = this.servicio.obtenerIngredientesPorPaso(paso_invalido);
        /*System.out.println("VALOR DEL SERVICIO: " + this.servicio.obtenerIngredientesPorPaso(7));*/
    }

    @Test
    public void siPidoUnaListaDeIngredientesConPasoValidoDevuelvaListaNoVacia() throws PasoInvalidoException {
        Integer paso_valido = 1;
        obtenerTodosLosIngredientesDelPaso1();
        List<Ingrediente> valor_obtenido = this.servicio.obtenerIngredientesPorPaso(paso_valido);
        System.out.println("VALOR DEL SERVIÇIO DEL PASO 1:" + valor_obtenido);
        assertThat(valor_obtenido).isNotEqualTo(new ArrayList<>());
    }

    @Test
    public void siPidoUnaListaDeIngredientesAptosParaSinRestriccionMeDevuelvaTodoSusIngredientes(){
        String esApto = "SinRestriccion";
        when(this.repo.obtenerIngredienteSiEsApto(esApto)).thenReturn(this.obtengoTodosLosIngredientesSinRestriccion());
        assertThat(this.servicio.obtenerIngredienteSiEsApto(esApto)).hasSize(13);
    } //poner excepcioon

    @Test
    public void siPidoUnaListaDeIngredientesAptosQueNoExistanMeDevuelvaUnaListaVacia(){
        String esApto = "Vegano";
        when(this.repo.obtenerIngredienteSiEsApto(esApto)).thenReturn(new ArrayList<>());
        assertThat(this.servicio.obtenerIngredienteSiEsApto(esApto)).isEmpty();
    }

    private List<Ingrediente> obtengoTodosLosIngredientesSinRestriccion(){
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
        return valorEsperado;
    }

    private void obtenerIngredientePorID(Long id) {
        Ingrediente ejemplo = new Ingrediente(1L, "Pan Centeno", 123F, 1, "detalle","Vegano");
        when(this.repo.obtenerIngredientePorId(id)).thenReturn(ejemplo);
    }

    private void obtenerTodosLosIngredientesDelPaso1(){
        List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = new Ingrediente(1L, "Pan Centeno", 123F, 1, "detalle","Vegano");
        Ingrediente n2 = new Ingrediente(2L, "Pan Negro", 100F, 1, "detalle","Vegano");
        Ingrediente n3 = new Ingrediente(3L, "Pan Pizza", 160F, 1, "detalle","Vegano");
        Ingrediente n4 = new Ingrediente(4L, "Pan Casero", 200F, 1, "detalle","Vegano");
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        when(this.repo.obtenerIngredientePorPaso(1)).thenReturn(valor_esperado);
    }

}
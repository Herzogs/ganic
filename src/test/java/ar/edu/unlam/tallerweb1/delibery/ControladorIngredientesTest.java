package ar.edu.unlam.tallerweb1.delibery;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorDeIngredientes;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.RepositorioIngredientes;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngrediente;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorIngredientesTest extends SpringTest {

    private ServicioDeIngrediente servicio;
    private ControladorDeIngredientes controladorDeIngredientes;
    private RepositorioIngredientes repositorioIngredientes;

    @Before
    public void init() {
        this.servicio = mock(ServicioDeIngrediente.class);
        this.controladorDeIngredientes = new ControladorDeIngredientes(this.servicio);

    }


    @Test
    public void queAlPedirUnaListaDeIngredientesMeLoMuestre() {
        //preparacion
        dadoQueExistenIngredientes();

        //ejecucion
        ModelAndView modelAndView = cuandoListoIngredientes();

        // verificacion
        entoncesEncuentro(modelAndView, 13);
        ebtoncesMeLLevaALaVista(modelAndView, "ingredientes");
    }

    @Test
    public void cuandoPidoPanesQueMelosMuestre() {
        //preparacion
        dadaQueExistenPanes(1);

        //ejecucion
        ModelAndView modelAndView = cuandoListoPanes();

        // verificacion

        entoncesEncuentroPanes(modelAndView, 1);
        entoncesEncuentroLaVistaPanes(modelAndView, "panes");


    }

    private void entoncesEncuentroLaVistaPanes(ModelAndView modelAndView, String listaDePanes) {
        List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco");
        Ingrediente n2 = new Ingrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco");
        Ingrediente n3 = new Ingrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco");
        Ingrediente n4 = new Ingrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral");
        Ingrediente n5 = new Ingrediente (9L,"Medallo de carne",450F,2,"Carne de ternera a la parrilla");
        Ingrediente n6 = new Ingrediente (10L,"Cerdo",450F,2,"Bondiola de cerdo a la parrilla");
        Ingrediente n7 = new Ingrediente(11L,"Mila-pollo",450F,2,"Milanesa de pollo");
        Ingrediente n8 = new Ingrediente(12L,"Vegetalizima",40F,2,"Milanesa de soja");
        Ingrediente n9 = new Ingrediente(13L,"Mix verde",38F,2,"Salteado de verduras");
        Ingrediente n10 = new Ingrediente (14L,"Barbacoa",15F,3,"Salsa de barbacoa");
        Ingrediente n11 = new Ingrediente(15L,"Pure",200F,3,"Pure de papas");
        Ingrediente n12 = new Ingrediente(16L,"Mostayesa",150F,3,"Mix mostaza y mayonesa");
        Ingrediente n13 = new Ingrediente (17L,"La casa",250F,3,"Guacamo y mayonesa");

        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        when(this.servicio.obtenerIngredientesPorPaso(1)).thenReturn(generarLaListaDeIngredientes());


    }

    private void entoncesEncuentroPanes(ModelAndView mav, Integer pasoEsaperado) {
        Integer paso = 1;
        List<Ingrediente> ingredienteList = (List<Ingrediente>) mav.getModel().get("ListaDePanes");
        for (Ingrediente ing : ingredienteList) {
            if (ing.getPaso() != paso) {
                paso = ing.getPaso();

            }
            System.out.println(paso + " el paso ");
        }

        assertThat(paso).isEqualTo(pasoEsaperado);
    }

    private ModelAndView cuandoListoPanes() {
        return controladorDeIngredientes.tiposDePanes();
    }

    private void dadaQueExistenPanes(Integer codPasoPasn) {
        List<Ingrediente> valor_esperado = new ArrayList<>();
        for(Ingrediente ing:generarLaListaDeIngredientes()){
            if(ing.getPaso().equals(codPasoPasn)){
                valor_esperado.add(ing);
            }
        }

        when(this.servicio.obtenerIngredientesPorPaso(codPasoPasn)).thenReturn(valor_esperado);
    }


    private void ebtoncesMeLLevaALaVista(ModelAndView mav, String vistaEsperada) {
        assertThat(mav.getViewName()).isEqualTo(vistaEsperada);
    }

    private void entoncesEncuentro(ModelAndView mav, int cantidadIngredientesEsperado) {
        assertThat((List<Ingrediente>) mav.getModel().get("ingredientes")).hasSize(cantidadIngredientesEsperado);
    }

    private ModelAndView cuandoListoIngredientes() {
        return controladorDeIngredientes.ingredientes();
    }

    private void dadoQueExistenIngredientes() {
        when(this.servicio.obtenerTodosLosIngredientes()).thenReturn(generarLaListaDeIngredientes());
    }
    private List<Ingrediente> generarLaListaDeIngredientes(){
        List<Ingrediente> ingredienteList= new ArrayList<>();

        Ingrediente n1 = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco");
        Ingrediente n2 = new Ingrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco");
        Ingrediente n3 = new Ingrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco");
        Ingrediente n4 = new Ingrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral");
        Ingrediente n5 = new Ingrediente (9L,"Medallo de carne",450F,2,"Carne de ternera a la parrilla");
        Ingrediente n6 = new Ingrediente (10L,"Cerdo",450F,2,"Bondiola de cerdo a la parrilla");
        Ingrediente n7 = new Ingrediente(11L,"Mila-pollo",450F,2,"Milanesa de pollo");
        Ingrediente n8 = new Ingrediente(12L,"Vegetalizima",40F,2,"Milanesa de soja");
        Ingrediente n9 = new Ingrediente(13L,"Mix verde",38F,2,"Salteado de verduras");
        Ingrediente n10 = new Ingrediente (14L,"Barbacoa",15F,3,"Salsa de barbacoa");
        Ingrediente n11 = new Ingrediente(15L,"Pure",200F,3,"Pure de papas");
        Ingrediente n12 = new Ingrediente(16L,"Mostayesa",150F,3,"Mix mostaza y mayonesa");
        Ingrediente n13 = new Ingrediente (17L,"La casa",250F,3,"Guacamo y mayonesa");
        ingredienteList.add(n1);
        ingredienteList.add(n2);
        ingredienteList.add(n3);
        ingredienteList.add(n4);
        ingredienteList.add(n5);
        ingredienteList.add(n6);
        ingredienteList.add(n7);
        ingredienteList.add(n8);
        ingredienteList.add(n9);
        ingredienteList.add(n10);
        ingredienteList.add(n11);
        ingredienteList.add(n12);
        ingredienteList.add(n13);
        return ingredienteList;
    }


}

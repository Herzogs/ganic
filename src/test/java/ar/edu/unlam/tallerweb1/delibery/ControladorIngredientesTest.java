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
        entoncesEncuentro(modelAndView, 4);
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
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        when(this.servicio.obtenerIngredientesPorPaso(1)).thenReturn(valor_esperado);


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
        Ingrediente n1 = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco");
        Ingrediente n2 = new Ingrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco");
        Ingrediente n3 = new Ingrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco");
        Ingrediente n4 = new Ingrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral");
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);

        when(this.servicio.obtenerIngredientesPorPaso(codPasoPasn)).thenReturn(valor_esperado);
    }


    private void ebtoncesMeLLevaALaVista(ModelAndView mav, String vistaEsperada) {
        assertThat(mav.getViewName()).isEqualTo(vistaEsperada);
    }

    private void entoncesEncuentro(ModelAndView mav, int cantidadIngredientesEsperado) {
        assertThat((List<Ingrediente>) mav.getModel().get("ingredientes")).hasSize(4);
    }

    private ModelAndView cuandoListoIngredientes() {
        return controladorDeIngredientes.ingredientes();
    }

    private void dadoQueExistenIngredientes() {
        List<Ingrediente> valor_esperado = new ArrayList<>();
        Ingrediente n1 = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco");
        Ingrediente n2 = new Ingrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco");
        Ingrediente n3 = new Ingrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco");
        Ingrediente n4 = new Ingrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral");
        valor_esperado.add(n1);
        valor_esperado.add(n2);
        valor_esperado.add(n3);
        valor_esperado.add(n4);
        when(this.servicio.obtenerTodosLosIngredientes()).thenReturn(valor_esperado);
    }


}

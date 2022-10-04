package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.IngredienteInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.PasoInvalidoException;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
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

    @Before
    public void init() {
        this.servicio = mock(ServicioDeIngrediente.class);
        this.controladorDeIngredientes = new ControladorDeIngredientes(this.servicio);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: Listar los ingrediente
    @Test
    public void queAlPedirUnaListaDeIngredientesMeLoMuestre() {
        //preparacion
        dadoQueExistenIngredientes();

        //ejecucion
        ModelAndView modelAndView = cuandoListoIngredientes();

        // verificacion
        entoncesEncuentro(modelAndView, 13);
        entoncesMeLLevaALaVista(modelAndView, "ingredientes");
    }

    @Test
    public void cuandoQuieraGenerarUnSandwichMeGenereLosPanes() throws PasoInvalidoException {
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=1";

        // Ejecución
        ModelAndView mod = this.cuandoSeleccionoGenerarPedido(1);
        // Verificación

        entoncesMeLLevaALaVista(mod, vista_solicitada);
    }

    @Test
    public void cuandoElijaUnPanMeRedirijaHaciaLosIngredientesPrincipal() throws IngredienteInvalidoException {
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=2";
        Ingrediente pan = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco");
        Long idIngrediente = 5L;
        dadoQueSeleccioneUnIngrediente(pan);
        // Ejecución
        ModelAndView mod = this.cuandoSeleccioneUnPan(idIngrediente);
        // Verificación
        entoncesMeLLevaALaVista(mod,vista_solicitada);
    }

    @Test
    public void cuandoElijaUnPrincipalMeRedirijaHaciaLosIngredientesOpcionales() throws IngredienteInvalidoException {
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=3";
        Long idIngredienteSeleccionado = 9L;
        Ingrediente ing = new Ingrediente (9L,"Medallo de carne",450F,2,"Carne de ternera a la parrilla");
        dadoQueSeleccioneUnIngrediente(ing);
        // Ejecución
        ModelAndView mod = this.cuandoSeleccioneIngrediente(idIngredienteSeleccionado);
        // Verificación
        entoncesMeLLevaALaVista(mod,vista_solicitada);
    }

    @Test
    public void cuandoElijaUnOpcionalMeRedirijaOtraVezHaciaLosIngredientesOpcionales() throws IngredienteInvalidoException {
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=3";
        Long idIngredienteSeleccionado = 12L;
        Ingrediente ing = new Ingrediente(12L,"Vegetalizima",40F,3,"Milanesa de soja");
        dadoQueSeleccioneUnIngrediente(ing);
        // Ejecución
        ModelAndView mod = this.cuandoSeleccioneIngrediente(idIngredienteSeleccionado);
        // Verificación
        entoncesMeLLevaALaVista(mod,vista_solicitada);
    }

    private void entoncesMeLLevaALaVista(ModelAndView mav, String vistaEsperada) {
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

    private ModelAndView cuandoSeleccionoGenerarPedido(Integer i) throws PasoInvalidoException {
        return this.controladorDeIngredientes.cargarPagina(i);
    }

    private void dadoQueSeleccioneUnIngrediente(Ingrediente ing) throws IngredienteInvalidoException {
        when(this.servicio.obtenerIngredientePorId(ing.getIdIngrediente())).thenReturn(ing);
    }

    private ModelAndView cuandoSeleccioneUnPan(Long idIngrediente) throws IngredienteInvalidoException {

        return this.controladorDeIngredientes.agregarIngredientes(idIngrediente);
    }

    private ModelAndView cuandoSeleccioneIngrediente(Long idIngrediente) throws IngredienteInvalidoException {
        return this.controladorDeIngredientes.agregarIngredientes(idIngrediente);
    }

/*
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: Cuando envien un número de paso invalido mande mensaje de error y redirija a el paso 1

    @Test
    public void cuandoEnvienUnNumeroDePasoInvalidoRedirijaAElPaso1(){
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=1";
        Integer nroPaso = 100;

        ModelAndView mod = this.cuandoEnvianUnNroPasoInvalido(nroPaso);
        // Verificación
        entoncesEncuentroLaVistaPanes(mod,vista_solicitada);
        verificarQueSeEnvioUnMensajeDeError(mod);
    }

    private ModelAndView cuandoEnvianUnNroPasoInvalido(Integer nro){
        return this.controladorDeIngredientes.cargarPagina(nro);
    }

    private void verificarQueSeEnvioUnMensajeDeError(ModelAndView m){
        assertThat(m.getModel().get("error")).isEqualTo("Paso Incorrecto");
    }*/
}

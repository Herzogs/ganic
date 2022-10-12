package ar.edu.unlam.tallerweb1.delivery.Ingrediente;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorDeIngredientes;
import ar.edu.unlam.tallerweb1.delivery.datosDelSandwich;
import ar.edu.unlam.tallerweb1.domain.Excepciones.IngredienteInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.PasoInvalidoException;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngrediente;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.ServletContext;
import java.util.ArrayList;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorIngredientesTest extends SpringTest {

    private ServicioDeIngrediente servicio;
    private ControladorDeIngredientes controladorDeIngredientes;

    private datosDelSandwich sandwich;

    @Before
    public void init() {
        this.servicio = mock(ServicioDeIngrediente.class);
        this.controladorDeIngredientes = new ControladorDeIngredientes(this.servicio);
        this.sandwich = mock(datosDelSandwich.class);
    }

    @Test
    public void queAlPedirUnaListaDeIngredientesMeLoMuestre() {
        //preparacion
        List<Ingrediente> ingredientes = generarLaListaDeIngredientes();
        cuandoLeSolicitenAlServicioQueDevuelaLaListaCompletaDeLosIngredientesDevuelva(ingredientes);

        //ejecucion
        ModelAndView modelAndView = cuandoLePidaAlControladorQueListeTodosLosIngrediente();

        // verificacion
        entoncesVerificoQueSeLeMandenLaCantidadDeIngredientesSolicitados(modelAndView, 13);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(modelAndView, "ingredientes");
    }

    @Test
    public void cuandoQuieraGenerarUnSandwichMeGenereLosPanes() throws PasoInvalidoException {
        // Preparación

        // Ejecución
        ModelAndView mod = cuandoLePidaAlControladorQueMeRedirijaALaVistaDeIngredienteDelPaso(1);
        // Verificación

        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod, "generarPedido");
    }

    @Test
    public void cuandoElijaUnPanMeRedirijaHaciaLosIngredientesPrincipal() throws IngredienteInvalidoException {
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=2";
        Ingrediente ingredienteSeleccionado = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco","SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ingredienteSeleccionado);
        // Ejecución
        ModelAndView mod = cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes(ingredienteSeleccionado);
        // Verificación
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod,vista_solicitada);
    }

    @Test
    public void cuandoElijaUnPrincipalMeRedirijaHaciaLosIngredientesOpcionales() throws IngredienteInvalidoException {
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=3";
        Ingrediente ingredienteSeleccionado = dadoQueExisteUnIngrediente (9L,"Medallo de carne",450F,2,"Carne de ternera a la parrilla","SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ingredienteSeleccionado);
        // Ejecución
        ModelAndView mod = cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes(ingredienteSeleccionado);
        // Verificación
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod,vista_solicitada);
    }

    @Test
    public void cuandoElijaUnOpcionalMeRedirijaOtraVezHaciaLosIngredientesOpcionales() throws IngredienteInvalidoException {
        // Preparación
        String vista_solicitada = "redirect:/generarPedido?paso=3";
        Ingrediente ingredienteSeleccionado = dadoQueExisteUnIngrediente(13L,"Mix verde",38F,2,"Salteado de verduras","SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ingredienteSeleccionado);
        // Ejecución
        ModelAndView mod = cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes(ingredienteSeleccionado);
        // Verificación
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod,vista_solicitada);
    }

    /*
    @Test
    public void cuandoSeleccioneUnaCantidadInsuficienteDeIngredientesYQuieraConfirmarMeRedirijaALaVistaDeLPrimerIngredienteSiEstoyLogeado(){
        String vistaEsperada = "redirect:/generarPedido?paso=1";
        this.request.addParameter("id", String.valueOf(1L));
        ModelAndView model = cuandoElControladorVerifiqueQueNoSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(1,this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model,vistaEsperada);
    }

    public ModelAndView cuandoElControladorVerifiqueQueNoSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich (Integer paso, MockHttpServletRequest req){
        return this.controladorDeIngredientes.confirmarIngredientesSeleccionados(paso, req);
    }

    @Test
    public void cuandoSeleccioneUnaCantidadSuficienteDeIngredientesYQuieraConfirmarMeRedirijaALaVistaDeConfirmacion(){
        String vistaEsperada = "confirmar";
        Lista<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        cuandoIngreseLosIngredientesSeleccionadosALaListaDeIngredientesParaFormarElSandwich(lista);
        ModelAndView model = cuandoElControladorVerifiqueQueSiSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(3,this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model.getViewName(),vistaEsperada);
    }

    public ModelAndView cuandoElControladorVerifiqueQueSiSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich (Integer paso, HttpServerlet req){
        return this.controladorDeIngredientes.confirmarIngredientesSeleccionados(paso, req);

    private void cuandoIngreseLosIngredientesSeleccionadosALaListaDeIngredientesParaFormarElSandwich(Lista<Ingrediente> lista){
        when(this.sandwich.getIngredientesSandwich()).thenReturn(lista);
    }

    private List<Ingrediente> dadoQueTengoUnaListaDeIngredientesSeleccionados(){
        Ingrediente ing1 = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco","SinRestriccion");
        Ingrediente ing2 = dadoQueExisteUnIngrediente (9L,"Medallo de carne",450F,2,"Carne de ternera a la parrilla","SinRestriccion");
        Ingrediente ing3 = dadoQueExisteUnIngrediente(13L,"Mix verde",38F,2,"Salteado de verduras","SinRestriccion");
        List<Ingrediente> lista = new ArrayList<>();
        lista.add(ing1);
        lista.add(ing1);
        lista.add(ing1);
        return lista;
    }

    @Test
    public void cuandoSeleccioneUnaCantidadInsuficienteDeIngredientesPeroNoEsteLogeadoMeRedirijaAlLogin(){
        String vistaEsperada = "redirect:/login";
        Lista<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        ModelAndView model = cuandoElControladorVerifiqueQueNoSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(1,this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model.getViewName(),vistaEsperada);
    }

     @Test
     public void cuandoSeleccioneUnaCantidadSuficienteDeIngredientesYEsteLogeadoMeRedirijaAlaPantallaDeExito(){
        String vistaEsperada = "exito";
        Lista<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        cuandoIngreseLosIngredientesSeleccionadosALaListaDeIngredientesParaFormarElSandwich(lista);
        ModelAndView model = tcuandoElControladorVerifiqueQueSiSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(3,this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model.getViewName(),vistaEsperada);
    }*/


    private void entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(ModelAndView mav, String vistaEsperada) {
        assertThat(mav.getViewName()).isEqualTo(vistaEsperada);
    }

    private void entoncesVerificoQueSeLeMandenLaCantidadDeIngredientesSolicitados(ModelAndView mav, int cantidadIngredientesEsperado) {
        assertThat((List<Ingrediente>) mav.getModel().get("ingredientes")).hasSize(cantidadIngredientesEsperado);
    }

    private ModelAndView cuandoLePidaAlControladorQueListeTodosLosIngrediente() {
        return controladorDeIngredientes.ingredientes();
    }

    private void cuandoLeSolicitenAlServicioQueDevuelaLaListaCompletaDeLosIngredientesDevuelva(List<Ingrediente> listaIngredientesDisponibles) {
        when(this.servicio.obtenerTodosLosIngredientes()).thenReturn(listaIngredientesDisponibles);
    }
    private List<Ingrediente> generarLaListaDeIngredientes(){
        List<Ingrediente> ingredienteList= new ArrayList<>();

        Ingrediente n1 = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco","SinRestriccion");
        Ingrediente n2 = new Ingrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco","SinRestriccion");
        Ingrediente n3 = new Ingrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco","SinRestriccion");
        Ingrediente n4 = new Ingrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral","SinRestriccion");
        Ingrediente n5 = new Ingrediente (9L,"Medallo de carne",450F,2,"Carne de ternera a la parrilla","SinRestriccion");
        Ingrediente n6 = new Ingrediente (10L,"Cerdo",450F,2,"Bondiola de cerdo a la parrilla","SinRestriccion");
        Ingrediente n7 = new Ingrediente(11L,"Mila-pollo",450F,2,"Milanesa de pollo","SinRestriccion");
        Ingrediente n8 = new Ingrediente(12L,"Vegetalizima",40F,2,"Milanesa de soja","SinRestriccion");
        Ingrediente n9 = new Ingrediente(13L,"Mix verde",38F,2,"Salteado de verduras","SinRestriccion");
        Ingrediente n10 = new Ingrediente (14L,"Barbacoa",15F,3,"Salsa de barbacoa","SinRestriccion");
        Ingrediente n11 = new Ingrediente(15L,"Pure",200F,3,"Pure de papas","SinRestriccion");
        Ingrediente n12 = new Ingrediente(16L,"Mostayesa",150F,3,"Mix mostaza y mayonesa","SinRestriccion");
        Ingrediente n13 = new Ingrediente (17L,"La casa",250F,3,"Guacamo y mayonesa","SinRestriccion");
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

    private ModelAndView cuandoLePidaAlControladorQueMeRedirijaALaVistaDeIngredienteDelPaso(Integer paso) {
        return this.controladorDeIngredientes.cargarPagina(paso);
    }

    private void cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(Ingrediente ing) throws IngredienteInvalidoException {
        when(this.servicio.obtenerIngredientePorId(ing.getIdIngrediente())).thenReturn(ing);
    }

    private ModelAndView cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes(Ingrediente ing) throws IngredienteInvalidoException {

        return this.controladorDeIngredientes.agregarIngredientes(ing.getIdIngrediente());
    }

    private ModelAndView cuandoSeleccioneIngrediente(Long idIngrediente) throws IngredienteInvalidoException {
        return this.controladorDeIngredientes.agregarIngredientes(idIngrediente);
    }

    private Ingrediente dadoQueExisteUnIngrediente(long idIngrediente, String nombre, float precio, int paso, String desc, String esApto) {
        return new Ingrediente(idIngrediente, nombre, precio, paso, desc,esApto);
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
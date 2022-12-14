package ar.edu.unlam.tallerweb1.delivery.Ingrediente;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorDeIngredientes;
import ar.edu.unlam.tallerweb1.delivery.DatosDelSandwich;
import ar.edu.unlam.tallerweb1.domain.Excepciones.IngredienteInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.PasoInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorIngredientesTest extends SpringTest {

    private ServicioDeIngrediente servicio;

    private ControladorDeIngredientes controladorDeIngredientes;

    private ServicioSandwich servicioSandwich;

    private DatosDelSandwich sandwich;

    private HttpServletRequest request;

    @Before
    public void init() {
        this.servicio = mock(ServicioDeIngrediente.class);
        this.servicioSandwich = mock(ServicioSandwich.class);
        this.controladorDeIngredientes = new ControladorDeIngredientes(this.servicio,this.servicioSandwich);
        this.sandwich = new DatosDelSandwich();
        this.request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(session);
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
        // Preparaci??n

        // Ejecuci??n
        ModelAndView mod = cuandoLePidaAlControladorQueMeRedirijaALaVistaDeIngredienteDelPaso(1,"SinRestriccion");
        // Verificaci??n

        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod, "generarPedido");
    }

    @Test
    public void cuandoElijaUnPanMeRedirijaHaciaLosIngredientesPrincipal() throws IngredienteInvalidoException {
        // Preparaci??n
        String vista_solicitada = "redirect:/generarPedido?paso=2";
        Ingrediente ingredienteSeleccionado = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ingredienteSeleccionado);
        // Ejecuci??n
        ModelAndView mod = cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes(ingredienteSeleccionado);
        // Verificaci??n
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod, vista_solicitada);
    }

    @Test
    public void cuandoElijaUnPrincipalMeRedirijaHaciaLosIngredientesOpcionales() throws IngredienteInvalidoException {
        // Preparaci??n
        String vista_solicitada = "redirect:/generarPedido?paso=3";
        Ingrediente ingredienteSeleccionado = dadoQueExisteUnIngrediente(9L, "Medallo de carne", 450F, 2, "Carne de ternera a la parrilla", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ingredienteSeleccionado);
        // Ejecuci??n
        ModelAndView mod = cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes(ingredienteSeleccionado);
        // Verificaci??n
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod, vista_solicitada);
    }

    @Test
    public void cuandoElijaUnOpcionalMeRedirijaOtraVezHaciaLosIngredientesOpcionales() throws IngredienteInvalidoException {
        // Preparaci??n
        String vista_solicitada = "redirect:/generarPedido?paso=3";
        Ingrediente ingredienteSeleccionado = dadoQueExisteUnIngrediente(13L, "Mix verde", 38F, 2, "Salteado de verduras", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ingredienteSeleccionado);
        // Ejecuci??n
        ModelAndView mod = cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes(ingredienteSeleccionado);
        // Verificaci??n
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(mod, vista_solicitada);
    }

    @Test
    public void cuandoEsteEnLaVistaDeConfirmarIngredientesSeleccionadosMePuedaDejarEliminarAlgunIngredienteAgregado() throws IngredienteInvalidoException {
        Ingrediente ing3 = dadoQueExisteUnIngrediente(13L, "Mix verde", 38F, 2, "Salteado de verduras", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ing3);
        ModelAndView model = cuandoLLamasAlControladorParaQueElimineElIngrediente(ing3);
        entoncesVerificoQueElModeloMeDevuelva(model, "ok", "Se a elminado el elemento seleccionado");
    }

    @Test
    public void cuandoEsteEnLaVistaDeConfirmarIngredientesSeleccionadosPeroIntentoEliminarUnIngredienteDelPaso1MeDEvuelveError() throws IngredienteInvalidoException {
        Ingrediente ing1 = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ing1);
        ModelAndView model = cuandoLLamasAlControladorParaQueElimineElIngrediente(ing1);
        entoncesVerificoQueElModeloMeDevuelva(model, "error", "No Se Puede Eliminar El Ingrediente Seleccionado");
    }

    @Test
    public void cuandoEsteEnLaVistaDeConfirmarIngredientesSeleccionadosPeroIntentoEliminarUnIngredienteDelPaso2MeDEvuelveError() throws IngredienteInvalidoException {
        Ingrediente ing2 = dadoQueExisteUnIngrediente(9L, "Medallo de carne", 450F, 2, "Carne de ternera a la parrilla", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ing2);
        ModelAndView model = cuandoLLamasAlControladorParaQueElimineElIngrediente(ing2);
        entoncesVerificoQueElModeloMeDevuelva(model, "error", "No Se Puede Eliminar El Ingrediente Seleccionado");
    }

    @Test
    public void cuandoEsteEnLaVistaDeConfirmarIngredientesSeleccionadosPeroIntentoEliminarUnIngredienteQueNoExista() throws IngredienteInvalidoException {
        Ingrediente ingInexistente = dadoQueExisteUnIngrediente(100L, "Pure", 200F, 3, "Pure de papas", "SinRestriccion");
        cuandoLePidoAlServicioQueMeBusqueUnIngredienteInexistenteMeLAnzeUnaExcepcion(ingInexistente);
        ModelAndView model = cuandoLLamasAlControladorParaQueElimineElIngrediente(ingInexistente);
        entoncesVerificoQueElModeloMeDevuelva(model, "error", "No Existe El Ingrediente Solicitado");
    }

    @Test
    public void cuandoEnLaVistaDeConfirmarIngredienteQuieranModificarAlgunIngredienteMeLleveALaVistaDeModificarIngrediente() throws IngredienteInvalidoException {
        Ingrediente ing3 = dadoQueExisteUnIngrediente(13L, "Mix verde", 38F, 2, "Salteado de verduras", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(ing3);
        ModelAndView model = cuandoLlamoAlControladorParaQueMeGenereLaPaginaParaModificarElIngredienteAPartirDeUnIngrediente(ing3);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model, "modificarIngrediente");
    }

    @Test
    public void cuandoSeleccioneUnIngredienteNuevoEsteSeInsertaEnLaListaDeIngrediente() throws IngredienteInvalidoException {
        Ingrediente n13 = dadoQueExisteUnIngrediente(17L, "La casa", 250F, 3, "Guacamo y mayonesa", "SinRestriccion");
        cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(n13);
        ModelAndView model = cuandoLLamasAlControladorParaQueMeModifiqueElIngrediente(n13);
        assertThat(model.getModelMap().get("IngredientesQueElUsuarioSelecciono")).isEqualTo(dadoQueTengoUnaNuevaListaDeIngredientesSeleccionados());
    }

    /*@Test
    public void cuandoSeleccioneUnaCantidadInsuficienteDeIngredientesYQuieraConfirmarMeRedirijaALaVistaDeLPrimerIngredienteSiEstoyLogeado() {
        String vistaEsperada = "redirect:/generarPedido?paso=1";
        cuandoLePidoAlHttpServletRequestQueMeTraigaElId();
        ModelAndView model = cuandoElControladorVerifiqueQueNoSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(1, this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model, vistaEsperada);
    }*/

    @Test
    public void cuandoSeleccioneUnaCantidadSuficienteDeIngredientesYQuieraConfirmarMeRedirijaALaVistaDeConfirmacion() {
        String vistaEsperada = "confirmar";
        List<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        cuandoIngreseLosIngredientesSeleccionadosALaListaDeIngredientesParaFormarElSandwich(lista);
        cuandoLePidoAlHttpServletRequestQueMeTraigaElId();
        ModelAndView model = cuandoElControladorVerifiqueQueSiSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(3, this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model, vistaEsperada);
    }

    @Test
    public void cuandoSeleccioneUnaCantidadInsuficienteDeIngredientesPeroNoEsteLogeadoMeRedirijaAlLogin() {
        String vistaEsperada = "redirect:/login";
        List<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        cuandoLePidoAlHttpServletRequestQueMeTraigaUnIdNulo();
        cuandoIngreseLosIngredientesSeleccionadosALaListaDeIngredientesParaFormarElSandwich(lista);
        ModelAndView model = cuandoElControladorVerifiqueQueNoSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(1, this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model, vistaEsperada);
    }

    @Test
    public void cuandoSeleccioneUnaCantidadSuficienteDeIngredientesYEsteLogeadoMeRedirijaAlaPantallaDeExito() {
        String vistaEsperada = "confirmar";
        List<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        cuandoIngreseLosIngredientesSeleccionadosALaListaDeIngredientesParaFormarElSandwich(lista);
        cuandoLePidoAlHttpServletRequestQueMeTraigaElId();
        ModelAndView model = cuandoElControladorVerifiqueQueSiSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich(3, this.request);
        entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(model, vistaEsperada);
    }

    private ModelAndView cuandoLlamoAlControladorParaQueMeGenereLaPaginaParaModificarElIngredienteAPartirDeUnIngrediente(Ingrediente ing3) {
        List<Ingrediente> listaIngredientes = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        this.controladorDeIngredientes.getSandwich().cargarIngredietnesAlSandwich(listaIngredientes);
        ModelAndView model = this.controladorDeIngredientes.generarPaginaDeIngredienteParaCambiar(ing3.getIdIngrediente());
        return model;
    }

    private ModelAndView cuandoLLamasAlControladorParaQueMeModifiqueElIngrediente(Ingrediente ing) {
        Ingrediente ing1 = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco", "SinRestriccion");
        Ingrediente ing2 = dadoQueExisteUnIngrediente(9L, "Medallo de carne", 450F, 2, "Carne de ternera a la parrilla", "SinRestriccion");
        Ingrediente ing3 = dadoQueExisteUnIngrediente(13L, "Mix verde", 38F, 2, "Salteado de verduras", "SinRestriccion");
        List<Ingrediente> lista = new ArrayList<>();
        lista.add(ing1);
        lista.add(ing2);
        lista.add(ing3);
        lista.add(null);
        this.controladorDeIngredientes.getSandwich().cargarIngredietnesAlSandwich(lista);
        ModelAndView model = this.controladorDeIngredientes.cambiarIngrediente(ing.getIdIngrediente());
        return model;
    }

    private ModelAndView cuandoLLamasAlControladorParaQueElimineElIngrediente(Ingrediente ing) {
        List<Ingrediente> listaIngredientes = dadoQueTengoUnaListaDeIngredientesSeleccionados();
        this.controladorDeIngredientes.getSandwich().cargarIngredietnesAlSandwich(listaIngredientes);
        ModelAndView model = this.controladorDeIngredientes.eliminarIngredienteSeleccionado(ing.getIdIngrediente());
        return model;
    }

    private void entoncesVerificoQueElModeloMeDevuelva(ModelAndView model, String ok, String expected) {
        assertThat(model.getModelMap().get(ok)).isEqualTo(expected);
    }

    private void cuandoLePidoAlServicioQueMeBusqueUnIngredienteInexistenteMeLAnzeUnaExcepcion(Ingrediente ingInexistente) throws IngredienteInvalidoException {
        when(this.servicio.obtenerIngredientePorId(ingInexistente.getIdIngrediente())).thenThrow(new IngredienteInvalidoException("No Existe El Ingrediente"));
    }

    private void cuandoLePidoAlHttpServletRequestQueMeTraigaElId() {
        when(this.request.getSession().getAttribute("id")).thenReturn(1L);
    }

    private void cuandoLePidoAlHttpServletRequestQueMeTraigaUnIdNulo() {
        when(this.request.getSession().getAttribute("id")).thenReturn(null);
    }

    private void cuandoIngreseLosIngredientesSeleccionadosALaListaDeIngredientesParaFormarElSandwich
            (List<Ingrediente> lista) {
        this.controladorDeIngredientes.getSandwich().cargarIngredietnesAlSandwich(lista);
    }


    public ModelAndView cuandoElControladorVerifiqueQueSiSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich
            (Integer paso, HttpServletRequest req) {
        return this.controladorDeIngredientes.confirmarIngredientesSeleccionados(paso, req);
    }


    public ModelAndView cuandoElControladorVerifiqueQueNoSeleccioneLaCantidadDeIngredientesParaFormarUnSandwich
            (Integer paso, HttpServletRequest req) {
        return this.controladorDeIngredientes.confirmarIngredientesSeleccionados(paso, req);
    }


    private List<Ingrediente> dadoQueTengoUnaListaDeIngredientesSeleccionados() {
        Ingrediente ing1 = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco", "SinRestriccion");
        Ingrediente ing2 = dadoQueExisteUnIngrediente(9L, "Medallo de carne", 450F, 2, "Carne de ternera a la parrilla", "SinRestriccion");
        Ingrediente ing3 = dadoQueExisteUnIngrediente(13L, "Mix verde", 38F, 2, "Salteado de verduras", "SinRestriccion");
        Ingrediente ing4 = dadoQueExisteUnIngrediente(15L, "Pure", 200F, 3, "Pure de papas", "SinRestriccion");
        List<Ingrediente> lista = new ArrayList<>();
        lista.add(ing1);
        lista.add(ing2);
        lista.add(ing3);
        lista.add(ing4);
        return lista;
    }

    private List<Ingrediente> dadoQueTengoUnaNuevaListaDeIngredientesSeleccionados() {
        Ingrediente ing1 = dadoQueExisteUnIngrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco", "SinRestriccion");
        Ingrediente ing2 = dadoQueExisteUnIngrediente(9L, "Medallo de carne", 450F, 2, "Carne de ternera a la parrilla", "SinRestriccion");
        Ingrediente ing3 = dadoQueExisteUnIngrediente(13L, "Mix verde", 38F, 2, "Salteado de verduras", "SinRestriccion");
        Ingrediente ing4 = dadoQueExisteUnIngrediente(17L, "La casa", 250F, 3, "Guacamo y mayonesa", "SinRestriccion");
        List<Ingrediente> lista = new ArrayList<>();
        lista.add(ing1);
        lista.add(ing2);
        lista.add(ing3);
        lista.add(ing4);
        return lista;
    }


    private void entoncesVerificoQueElControladorMeLLeveALaVistaSolicitada(ModelAndView mav, String vistaEsperada) {
        assertThat(mav.getViewName()).isEqualTo(vistaEsperada);
    }

    private void entoncesVerificoQueSeLeMandenLaCantidadDeIngredientesSolicitados(ModelAndView mav,
                                                                                  int cantidadIngredientesEsperado) {
        assertThat((List<Ingrediente>) mav.getModel().get("ingredientes")).hasSize(cantidadIngredientesEsperado);
    }

    private ModelAndView cuandoLePidaAlControladorQueListeTodosLosIngrediente() {
        return controladorDeIngredientes.ingredientes();
    }

    private void cuandoLeSolicitenAlServicioQueDevuelaLaListaCompletaDeLosIngredientesDevuelva
            (List<Ingrediente> listaIngredientesDisponibles) {
        when(this.servicio.obtenerTodosLosIngredientes()).thenReturn(listaIngredientesDisponibles);
    }

    private List<Ingrediente> generarLaListaDeIngredientes() {
        List<Ingrediente> ingredienteList = new ArrayList<>();

        Ingrediente n1 = new Ingrediente(5L, "Pan clasico", 150F, 1, "Pan lactal blanco", "SinRestriccion");
        Ingrediente n2 = new Ingrediente(6L, "Pan flauta", 120F, 1, "Pan de mesa blanco", "SinRestriccion");
        Ingrediente n3 = new Ingrediente(7L, "Pan de campo", 250F, 1, "Pan de campo blanco", "SinRestriccion");
        Ingrediente n4 = new Ingrediente(8L, "Pan integral", 280F, 1, "Pan lactal integral", "SinRestriccion");
        Ingrediente n5 = new Ingrediente(9L, "Medallo de carne", 450F, 2, "Carne de ternera a la parrilla", "SinRestriccion");
        Ingrediente n6 = new Ingrediente(10L, "Cerdo", 450F, 2, "Bondiola de cerdo a la parrilla", "SinRestriccion");
        Ingrediente n7 = new Ingrediente(11L, "Mila-pollo", 450F, 2, "Milanesa de pollo", "SinRestriccion");
        Ingrediente n8 = new Ingrediente(12L, "Vegetalizima", 40F, 2, "Milanesa de soja", "SinRestriccion");
        Ingrediente n9 = new Ingrediente(13L, "Mix verde", 38F, 2, "Salteado de verduras", "SinRestriccion");
        Ingrediente n10 = new Ingrediente(14L, "Barbacoa", 15F, 3, "Salsa de barbacoa", "SinRestriccion");
        Ingrediente n11 = new Ingrediente(15L, "Pure", 200F, 3, "Pure de papas", "SinRestriccion");
        Ingrediente n12 = new Ingrediente(16L, "Mostayesa", 150F, 3, "Mix mostaza y mayonesa", "SinRestriccion");
        Ingrediente n13 = new Ingrediente(17L, "La casa", 250F, 3, "Guacamo y mayonesa", "SinRestriccion");
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

    private ModelAndView cuandoLePidaAlControladorQueMeRedirijaALaVistaDeIngredienteDelPaso(Integer paso, String pref) {
        return this.controladorDeIngredientes.cargarPagina(paso, pref);
    }

    private void cuandoLePidoAlServicioQueMeDevuelvaUnIngredientePorID(Ingrediente ing) throws
            IngredienteInvalidoException {
        when(this.servicio.obtenerIngredientePorId(ing.getIdIngrediente())).thenReturn(ing);
    }

    private ModelAndView cuandoSeleccioneUnIngredienteEntoncesElControladorLoAgregueALaListaDeIngredientes
            (Ingrediente ing) throws IngredienteInvalidoException {

        return this.controladorDeIngredientes.agregarIngredientes(ing.getIdIngrediente());
    }

    private ModelAndView cuandoSeleccioneIngrediente(Long idIngrediente) throws IngredienteInvalidoException {
        return this.controladorDeIngredientes.agregarIngredientes(idIngrediente);
    }

    private Ingrediente dadoQueExisteUnIngrediente(long idIngrediente, String nombre, float precio,
                                                   int paso, String desc, String esApto) {
        return new Ingrediente(idIngrediente, nombre, precio, paso, desc, esApto);
    }

}
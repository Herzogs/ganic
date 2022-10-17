package ar.edu.unlam.tallerweb1.delivery;

import java.util.List;

import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.IngredienteInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.PasoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngrediente;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorDeIngredientes {

    private ServicioDeIngrediente servicioDeIngrediente;
    private ServicioEmail se;
    private datosDelSandwich sandwich;
    private static final Integer MAX_PASOS_PERMITIDOS = 3;

    @Autowired
    public ControladorDeIngredientes(ServicioDeIngrediente servicioDeIngrediente) {
        super();
        this.servicioDeIngrediente = servicioDeIngrediente;
        this.sandwich = new datosDelSandwich();
        this.se = new ServicioEmailImp();
    }

    @RequestMapping(path = "/ingredientes", method = RequestMethod.GET)
    public ModelAndView ingredientes() {

        ModelMap model = new ModelMap();

        List<Ingrediente> ingrediente = servicioDeIngrediente.obtenerTodosLosIngredientes();

        model.put("ingredientes", ingrediente);

        return new ModelAndView("ingredientes", model);

    }

    @RequestMapping(path = "/generarPedido", method = RequestMethod.GET)
    public ModelAndView cargarPagina(@RequestParam(value = "paso", defaultValue = "1", required = false) Integer paso) {
        ModelMap mod = new ModelMap();
        List<Ingrediente> lista = null;
        try{
            lista = this.servicioDeIngrediente.obtenerIngredientesPorPaso(paso);
            mod.put("ListaDePanes", lista);
            mod.put("paso", paso);
        }catch(PasoInvalidoException e) {
            mod.put("error","Paso Incorrecto");
            this.sandwich.borrarDatosDelSandwich();
            return new ModelAndView("redirect:/error404",mod);
        }
        return new ModelAndView("generarPedido",mod);
    }

    @RequestMapping(path = "/agregarIngrediente", method = RequestMethod.GET)
    public ModelAndView agregarIngredientes(@RequestParam(value = "id") Long idIng) {
        Ingrediente ing = null;
        Integer nuevoPaso=1;
        try {
            ing = this.servicioDeIngrediente.obtenerIngredientePorId(idIng);

            Integer paso = ing.getPaso();
            this.sandwich.cargarIngredienteAlSandwich(ing);
            nuevoPaso = (paso < ControladorDeIngredientes.MAX_PASOS_PERMITIDOS) ? paso + 1 : paso;
        }catch(IngredienteInvalidoException excepcion){
            this.sandwich.borrarDatosDelSandwich();
            return new ModelAndView("redirect:/error404",new ModelMap("error",excepcion.getMessage()));
        }
        return new ModelAndView(String.format("redirect:/generarPedido?paso=%d",nuevoPaso));
    }

    @RequestMapping(path = "/confirmar", method = RequestMethod.GET)
    public ModelAndView confirmarIngredientesSeleccionados(@RequestParam(value = "paso", required = false) Integer paso,HttpServletRequest request) {
        Long idLogeado = (Long) request.getSession().getAttribute("id");
        if ( idLogeado != null) {
            ModelMap model = new ModelMap();
            List<Ingrediente> ingredientesSeleccionados = this.sandwich.getIngredientesSandwich();
            if (ingredientesSeleccionados.size() <= 1) {
                model.put("error", "Para poder seguir, debe seleccionar minimante 2 ingredientes");
                return new ModelAndView(String.format("redirect:/generarPedido?paso=%d", paso), model);
            }
            model.put("montoFinal", sandwich.getMonto());
            model.put("IngredientesQueElUsuarioSelecciono", sandwich.getIngredientesSandwich());
            return new ModelAndView("confirmar", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/exito", method = RequestMethod.GET)
    public ModelAndView exito(HttpServletRequest request){
        String email = (String) request.getSession().getAttribute("email");
        se.sendEmail(email, "Pedido Exitoso", "Se le estara enviando su pedido en unos momentos");
        return new ModelAndView("alerta_exitosa");
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        this.sandwich.borrarDatosDelSandwich();
        return new ModelAndView("home");
    }

    public datosDelSandwich getSandwich() {
        return sandwich;
    }

    @RequestMapping(path = "eliminarIngrediente", method = RequestMethod.GET)
    public ModelAndView eliminarIngredienteSeleccionado(@RequestParam(value = "ing") Long id){
        ModelMap model = new ModelMap();
        Ingrediente ing = null;
        try {
            ing = this.servicioDeIngrediente.obtenerIngredientePorId(id);
            if(compararIngredienteEnLaPosicion(0, ing) || compararIngredienteEnLaPosicion(1, ing)){
                model.put("error", "No Se Puede Eliminar El Ingrediente Seleccionado");
            }else {
            this.sandwich.eliminarIngrediente(ing);
            model.put("ok", "Se a elminado el elemento seleccionado");
            }

        }catch(IngredienteInvalidoException ex){
            model.put("error", "No Existe El Ingrediente Solicitado");
        }
        model.put("IngredientesQueElUsuarioSelecciono", sandwich.getIngredientesSandwich());
        model.put("montoFinal", sandwich.getMonto());
        return new ModelAndView("confirmar", model);
    }

    private boolean compararIngredienteEnLaPosicion(int i, Ingrediente ing) {
        return this.sandwich.getIngredientesSandwich().get(i).equals(ing);
    }

    @RequestMapping(path = "modificarIngrediente", method = RequestMethod.GET)
    public ModelAndView generarPaginaDeIngredienteParaCambiar(@RequestParam(value = "ing", defaultValue = "1", required = false) Long id) {
        ModelMap mod = new ModelMap();
        List<Ingrediente> lista = null;
        try{
            Ingrediente ingrediente = this.servicioDeIngrediente.obtenerIngredientePorId(id);
            Integer idx = this.sandwich.buscarIngredientePorID(id);
            if(!idx.equals(-1)) {
                this.sandwich.getIngredientesSandwich().set(idx, null);
                this.sandwich.setMonto(0F);
            }
            lista = this.servicioDeIngrediente.obtenerIngredientesPorPaso(ingrediente.getPaso());
            mod.put("ListaDeIngredientes", lista);
        }catch(IngredienteInvalidoException  | PasoInvalidoException e) {
            /*mod.put("error", "Paso Incorrecto");
            this.sandwich.borrarDatosDelSandwich();*/
            return new ModelAndView("redirect:/error404", mod);
        }
        return new ModelAndView("modificarIngrediente",mod);
    }

    @RequestMapping(path = "mod", method = RequestMethod.GET)
    public ModelAndView cambiarIngrediente(@RequestParam(value = "id") Long idIng) {
        Ingrediente ing = null;
        ModelMap model = new ModelMap();
        try {
            ing = this.servicioDeIngrediente.obtenerIngredientePorId(idIng);

            this.sandwich.cambiarIngrediente(ing);
        }catch(IngredienteInvalidoException excepcion){
            this.sandwich.borrarDatosDelSandwich();
            return new ModelAndView("redirect:/error404",new ModelMap("error",excepcion.getMessage()));
        }
        model.put("IngredientesQueElUsuarioSelecciono", sandwich.getIngredientesSandwich());
        model.put("montoFinal", sandwich.getMonto());
        return new ModelAndView("confirmar", model);
    }
}

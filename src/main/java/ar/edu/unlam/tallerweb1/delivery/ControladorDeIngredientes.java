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
    public ModelAndView cargarPagina(@RequestParam(value = "paso", defaultValue = "1", required = false) Integer paso) throws PasoInvalidoException {
        ModelMap mod = new ModelMap();
        List<Ingrediente> lista = this.servicioDeIngrediente.obtenerIngredientesPorPaso(paso);
        if(!lista.isEmpty() && paso <= ControladorDeIngredientes.MAX_PASOS_PERMITIDOS) {
            mod.put("ListaDePanes", lista);
            mod.put("paso", paso);
        }else {
            mod.put("error","Paso Incorrecto");
            return new ModelAndView("redirect:/generarPedido?paso=1",mod);

        }
        return new ModelAndView("generarPedido",mod);
    }

    @RequestMapping(path = "/agregarIngrediente", method = RequestMethod.GET)
    public ModelAndView agregarIngredientes(@RequestParam(value = "id") Long idIng) throws IngredienteInvalidoException {
        Ingrediente ing = this.servicioDeIngrediente.obtenerIngredientePorId(idIng);
        Integer paso = ing.getPaso();
        this.sandwich.cargarIngredienteAlSandwich(ing);
        Integer nuevoPaso = (paso < ControladorDeIngredientes.MAX_PASOS_PERMITIDOS)?paso+1:paso;
        return new ModelAndView(String.format("redirect:/generarPedido?paso=%d",nuevoPaso));
    }

    @RequestMapping(path = "/confirmar", method = RequestMethod.GET)
    public ModelAndView confirmarIngredientesSeleccionados(@RequestParam(value = "paso", required = false) Integer paso) {

        ModelMap model = new ModelMap();
        List<Ingrediente> ingredientesSeleccionados = this.sandwich.getIngredientesSandwich();
        System.err.println(ingredientesSeleccionados.size());
        if(ingredientesSeleccionados.size() <= 1){
            model.put("error", "Para poder seguir, debe seleccionar minimante 2 ingredientes");
            return new ModelAndView(String.format("redirect:/generarPedido?id=%d",paso), model);
        }


        model.put("montoFinal", sandwich.getMonto());
        model.put("IngredientesQueElUsuarioSelecciono", sandwich.getIngredientesSandwich());
        return new ModelAndView("confirmar", model);
    }

    @RequestMapping(path = "/exito", method = RequestMethod.GET)

    public ModelAndView exito(){
        se.sendEmail("crisefeld@gmail.com", "Pedido Exitoso", "Se le estara enviando su pedido en unos momentos");
        return new ModelAndView("alerta_exitosa");
    }

}

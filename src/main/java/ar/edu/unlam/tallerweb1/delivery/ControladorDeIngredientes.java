package ar.edu.unlam.tallerweb1.delivery;

import java.util.ArrayList;
import java.util.List;

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

    private datosDelSandwich sandwich;
    private static final Integer MAX_PASOS_PERMITIDOS = 3;

    @Autowired
    public ControladorDeIngredientes(ServicioDeIngrediente servicioDeIngrediente) {
        super();
        this.servicioDeIngrediente = servicioDeIngrediente;
        this.sandwich = new datosDelSandwich();
    }

    @RequestMapping(path = "/ingredientes", method = RequestMethod.GET)
    public ModelAndView ingredientes() {

        ModelMap model = new ModelMap();

        List<Ingrediente> ingrediente = servicioDeIngrediente.obtenerTodosLosIngredientes();

        model.put("ingredientes", ingrediente);

        return new ModelAndView("ingredientes", model);

    }

    @RequestMapping(path = "/generarPedido", method = RequestMethod.GET)
    public ModelAndView cargarPagina(@RequestParam(value = "id", defaultValue = "1", required = false) Integer id, @RequestParam(value = "paso", defaultValue = "1") Integer paso) {
        ModelMap mod = new ModelMap();
        List<Ingrediente> lista = this.servicioDeIngrediente.obtenerIngredientesPorPaso(id);
        if(!lista.isEmpty()) {
            mod.put("ListaDePanes", lista);
            mod.put("paso", id);
        }else {
            mod.put("error","Paso Incorrecto");
        }
        return new ModelAndView("generarPedido",mod);
    }

    @RequestMapping(path = "/agregarIngrediente", method = RequestMethod.GET)
    public ModelAndView agregarIngredientes(@RequestParam(value = "id") Long id, @RequestParam(value = "paso") Integer paso) {
        Ingrediente ing = this.servicioDeIngrediente.obtenerIngredientePorId(id);
        this.sandwich.cargarIngredienteAlSandwich(ing);
        Integer nuevoPaso = (paso < ControladorDeIngredientes.MAX_PASOS_PERMITIDOS)?paso+1:paso;
        return new ModelAndView(String.format("redirect:/generarPedido?id=%d",nuevoPaso));
    }



    /*@RequestMapping(path = "/panes", method = RequestMethod.GET)
    public ModelAndView tiposDePanes() {

        ModelMap model = new ModelMap();

        List<Ingrediente> panes = servicioDeIngrediente.obtenerIngredientesPorPaso(1);

        model.put("ListaDePanes", panes);

        return new ModelAndView("panes", model);

    }

    @RequestMapping(path = "/agregarIngredientesAlSandwich", method = RequestMethod.GET)
    public ModelAndView agregarIngredientes(@RequestParam("id") Long id) {
        ModelMap model = new ModelMap();

        Ingrediente ingrediente = servicioDeIngrediente.obtenerIngredientePorId(id);
        Integer paso = ingrediente.getPaso();
        if (paso.equals(1)) {
            sandwich.cargarIngredienteAlSandwich(ingrediente);
            List<Ingrediente> principal = servicioDeIngrediente.obtenerIngredientesPorPaso(2);
            model.put("principales", principal);
            return new ModelAndView("principales", model);
        } else if (paso.equals(2)) {
            sandwich.cargarIngredienteAlSandwich(ingrediente);
            List<Ingrediente> principal = servicioDeIngrediente.obtenerIngredientesPorPaso(3);
            model.put("opcionales", principal);
            return new ModelAndView("opcionales", model);
        }
        List<Ingrediente> principal = servicioDeIngrediente.obtenerIngredientesPorPaso(3);
        model.put("opcionales", principal);
        sandwich.cargarIngredienteAlSandwich(ingrediente);
        return new ModelAndView("opcionales", model);

    }*/

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
    public ModelAndView exito() {
        return new ModelAndView("alerta_exitosa");
    }

}

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
    public ModelAndView cargarPagina(@RequestParam(value = "id", defaultValue = "1", required = false) Integer paso) {
        ModelMap mod = new ModelMap();
        List<Ingrediente> lista = this.servicioDeIngrediente.obtenerIngredientesPorPaso(paso);
        if(!lista.isEmpty() && paso <= ControladorDeIngredientes.MAX_PASOS_PERMITIDOS) {
            mod.put("ListaDePanes", lista);
            mod.put("paso", paso);
        }else {
            mod.put("error","Paso Incorrecto");
            System.out.println(new ModelAndView("redirect:/generarPedido?id=1",mod).getModel().get("error").equals("Paso Incorrecto"));
            return new ModelAndView("redirect:/generarPedido?id=1",mod);

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

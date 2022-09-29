package ar.edu.unlam.tallerweb1.delivery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngrediente;

@Controller
public class ControladorDeIngredientes {

    private ServicioDeIngrediente servicioDeIngrediente;
    private List<Ingrediente> ingredientesDelUsuario;
    private datosDelSandwich sandwich;

    @Autowired
    public ControladorDeIngredientes(ServicioDeIngrediente servicioDeIngrediente) {
        super();
        this.servicioDeIngrediente = servicioDeIngrediente;
        this.ingredientesDelUsuario = new ArrayList<>();
        this.sandwich=new datosDelSandwich();
    }

    @RequestMapping(path = "/ingredientes", method = RequestMethod.GET)
    public ModelAndView ingredientes() {

        ModelMap model = new ModelMap();

        List<Ingrediente> ingrediente = servicioDeIngrediente.obtenerTodosLosIngredientes();

        model.put("ingredientes", ingrediente);

        return new ModelAndView("ingredientes", model);

    }

    @RequestMapping(path = "/panes", method = RequestMethod.GET)
    public ModelAndView tiposDePanes() {

        ModelMap model = new ModelMap();

        List<Ingrediente> panes = servicioDeIngrediente.obtenerIngredientesPorPaso(1);

        model.put("ListaDePanes", panes);

        return new ModelAndView("panes", model);

    }

    @RequestMapping(path = "/agregarIngredientesAlSandwich", method = RequestMethod.GET)
    public ModelAndView confirmarIngredientePan(@RequestParam("id") Long id) {

        ModelMap model = new ModelMap();

        Ingrediente ingrediente = servicioDeIngrediente.obtenerIngredientePorId(id);
        Integer paso = ingrediente.getPaso();
        if(paso.equals(1)){
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
        ingredientesDelUsuario.add(ingrediente);
        return new ModelAndView("opcionales", model);

    }










    @RequestMapping(path = "/confirmar", method = RequestMethod.GET)
    public ModelAndView confirmarIngredientesSeleccionados() {

        ModelMap model = new ModelMap();

        Float precioFinal = 0.0F;
        for (Ingrediente precio : ingredientesDelUsuario) {
            precioFinal += precio.getPrecio();
        }

        model.put("montoFinal", precioFinal);
        model.put("IngredientesQueElUsuarioSelecciono", ingredientesDelUsuario);

        return new ModelAndView("confirmar", model);
    }

    @RequestMapping(path = "/exito", method = RequestMethod.GET)
    public ModelAndView exito() {
        return new ModelAndView("alerta_exitosa");
    }

}

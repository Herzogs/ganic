package ar.edu.unlam.tallerweb1.delivery;


import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.ServicioDeIngrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorSandwich {

    private ServicioDeIngrediente servicioDeIngrediente;
    private List<Ingrediente> listaIngrediente;

    @Autowired

    public ControladorSandwich(ServicioDeIngrediente servicioDeIngrediente) {
        this.servicioDeIngrediente = servicioDeIngrediente;
        this.listaIngrediente = new ArrayList<>();
    }

    @RequestMapping (path = "/ingredientes", method = RequestMethod.GET)
    public ModelAndView listarTodos(){
        ModelMap modelo = new ModelMap();
        List<Ingrediente> ing = this.servicioDeIngrediente.obtenerTodosLosIngredientes();
        modelo.put("ingredientes",ing);
        return new ModelAndView("ingredientes",modelo);
    }
}

package ar.edu.unlam.tallerweb1.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorDeIngredientePrincipal {
	
	@RequestMapping(path = "/ingrediente-principal", method = RequestMethod.GET)
	public ModelAndView ingredientePrincipal() {
		return new ModelAndView("ingrediente-princial");
	
	
	}
	@RequestMapping(path = "/volver-a-tipos-de-panes", method = RequestMethod.GET)
	public ModelAndView volverATiposDePanes() {
		return new ModelAndView("volver-a-tipos-de-panes");
}

	@RequestMapping(path = "/confirmar-ingrediente-principal", method = RequestMethod.GET)
	public ModelAndView confirmarIngredientePrincipal() {
		return new ModelAndView("confirmar-ingrediente-principal");
	}
}
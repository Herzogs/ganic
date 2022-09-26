package ar.edu.unlam.tallerweb1.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorDeIngredientes {

	//TipoDePan
	@RequestMapping(path = "/tipos-de-panes", method = RequestMethod.GET)
	public ModelAndView tiposDePanes() {
		return new ModelAndView("tipos-de-panes");
		
}
	@RequestMapping(path = "/volver-a-menu", method = RequestMethod.GET)
	public ModelAndView volverAMenu() {
		return new ModelAndView("volver-a-menu");
		
}
	@RequestMapping(path = "/confirmar-pan", method = RequestMethod.GET)
	public ModelAndView confirmarPan() {
		return new ModelAndView("ingrediente-principal");
		
		
}
	//IngredientePrincipal
	@RequestMapping(path = "/ingrediente-principal", method = RequestMethod.GET)
	public ModelAndView ingredientePrincipal() {
		return new ModelAndView("ingrediente-principal");
	
	
	}
	@RequestMapping(path = "/volver-a-tipos-de-panes", method = RequestMethod.GET)
	public ModelAndView volverATiposDePanes() {
		return new ModelAndView("volver-a-tipos-de-panes");
}

	@RequestMapping(path = "/confirmar-ingrediente-principal", method = RequestMethod.GET)
	public ModelAndView confirmarIngredientePrincipal() {
		return new ModelAndView("ingrediente-opcional");
	}
	
	//IngredienteOpcional
	@RequestMapping(path = "/ingrediente-opcional", method = RequestMethod.GET)
	public ModelAndView ingredienteOpcional() {
		return new ModelAndView("ingrediente-opcional");
		

}
	@RequestMapping(path = "/volver-a-ingrediente-principal", method = RequestMethod.GET)
	public ModelAndView voloverAIngredientePrincipal() {
		return new ModelAndView("volver-a-ingrediente-principal");
}
	@RequestMapping(path = "/confirmar-sanguche", method = RequestMethod.GET)
	public ModelAndView confirmarSanguche() {
		return new ModelAndView("confirmar-sanguche");
	}
}

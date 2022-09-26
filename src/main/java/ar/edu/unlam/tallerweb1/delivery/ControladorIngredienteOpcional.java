package ar.edu.unlam.tallerweb1.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorIngredienteOpcional {
	
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
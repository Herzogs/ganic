package ar.edu.unlam.tallerweb1.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPan {

	@RequestMapping(path = "/tipos-de-panes", method = RequestMethod.GET)
	public ModelAndView tiposDePanes() {
		return new ModelAndView("tipos-de-panes");
		
}
	@RequestMapping(path = "/volver", method = RequestMethod.GET)
	public ModelAndView volver() {
		return new ModelAndView("menu");
		
}
	@RequestMapping(path = "/confirmar", method = RequestMethod.GET)
	public ModelAndView confirmar() {
		return new ModelAndView("ingrediente-principal");
		
}
}
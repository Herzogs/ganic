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

	@Autowired
	public ControladorDeIngredientes(ServicioDeIngrediente servicioDeIngrediente) {
		super();
		this.servicioDeIngrediente = servicioDeIngrediente;
		this.ingredientesDelUsuario = new ArrayList<>();
	}

	@RequestMapping(path = "/ingredientes", method = RequestMethod.GET)
	public ModelAndView ingredientes() {

		ModelMap model = new ModelMap();

		List<Ingrediente> ingrediente = servicioDeIngrediente.obtenerTodosLosIngredientes();

		model.put("ingredientes", ingrediente);

		return new ModelAndView("ingredientes", model);

	}

	// TipoDePan
	@RequestMapping(path = "/panes", method = RequestMethod.GET)
	public ModelAndView tiposDePanes() {

		ModelMap model = new ModelMap();

		List<Ingrediente> panes = servicioDeIngrediente.obtenerIngredientesPorPaso(1);

		model.put("ListaDePanes", panes);

		return new ModelAndView("panes", model);

	}

	@RequestMapping(path = "/volver-a-menu", method = RequestMethod.GET)
	public ModelAndView volverAMenu() {
		return new ModelAndView("volver-a-menu");

	}

	@RequestMapping(path = "/agregar-ingrediente-pan", method = RequestMethod.GET)
	public ModelAndView confirmarIngredientePan(@RequestParam("id")Long id) {

		ModelMap model = new ModelMap();
		
		Ingrediente ingrediente = servicioDeIngrediente.obtenerIngredientePorId(id);

		ingredientesDelUsuario.add(ingrediente);



		return new ModelAndView("redirect:/principales", model);
	}

	// IngredientePrincipal
	@RequestMapping(path = "/principales", method = RequestMethod.GET)
	public ModelAndView ingredientePrincipal() {

		ModelMap model = new ModelMap();

		List<Ingrediente> ingredientePrincipal = servicioDeIngrediente.obtenerIngredientesPorPaso(2);

		model.put("ListaDeIngredientesPrincipales", ingredientePrincipal);

		return new ModelAndView("principales", model);

	}

	@RequestMapping(path = "/volver-a-tipos-de-panes", method = RequestMethod.GET)
	public ModelAndView volverATiposDePanes() {
		return new ModelAndView("volver-a-tipos-de-panes");
	}

	@RequestMapping(path = "/agregar-ingrediente-principal", method = RequestMethod.GET)
	public ModelAndView confirmarIngredientePrincipal(@RequestParam("id")Long id) {

		ModelMap model = new ModelMap();
		
		Ingrediente ingrediente = servicioDeIngrediente.obtenerIngredientePorId(id);
		
		ingredientesDelUsuario.add(ingrediente);

		return new ModelAndView("redirect:/opcionales", model);
	}

	// IngredienteOpcional
	@RequestMapping(path = "/opcionales", method = RequestMethod.GET)
	public ModelAndView ingredienteOpcional() {

		ModelMap model = new ModelMap();

		List<Ingrediente> ingredienteOpcional = servicioDeIngrediente.obtenerIngredientesPorPaso(3);

		model.put("ListaDeIngredientesOpcionales", ingredienteOpcional);

		return new ModelAndView("opcionales", model);

	}

	@RequestMapping(path = "/volver-a-ingrediente-principal", method = RequestMethod.GET)
	public ModelAndView voloverAIngredientePrincipal() {
		return new ModelAndView("volver-a-ingrediente-principal");
	}

	@RequestMapping(path = "/agregar-ingrediente-opcional", method = RequestMethod.GET)
	public ModelAndView confirmarIngredienteOpcional(@RequestParam("id")Long id) {

		ModelMap model = new ModelMap();
		
		Ingrediente ingrediente = servicioDeIngrediente.obtenerIngredientePorId(id);

		ingredientesDelUsuario.add(ingrediente);
		
		model.put("IngredientesQueElUsuarioSelecciono", ingredientesDelUsuario);

		return new ModelAndView("redirect:/opcionales", model);
	}
	@RequestMapping(path = "/confirmar", method = RequestMethod.GET)
	public ModelAndView confirmarIngredientesSeleccionados() {

		ModelMap model = new ModelMap();

		Float precioFinal=0.0F;
		for(Ingrediente precio:ingredientesDelUsuario){
			precioFinal+= precio.getPrecio();
		}

		model.put("montoFinal", precioFinal);
		model.put("IngredientesQueElUsuarioSelecciono", ingredientesDelUsuario);

		return new ModelAndView("confirmar", model);
	}

}

package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Excepciones.NoHaySandwichEnPromocionException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ControladorSandwich {


    private ServicioSandwich servicioSandwich;

    @Autowired
    public ControladorSandwich(ServicioSandwich servicioSandwich) {
        this.servicioSandwich = servicioSandwich;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView listarSandwichPorPromocion(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<Sandwich> sandwichList = null;
        try {
            sandwichList = servicioSandwich.obtenerTodosLosSandwichesEnPromocion();
            model.put("listaEnPromocion", sandwichList);

        } catch (NoHaySandwichEnPromocionException e) {
            model.put("msj", "no hay sandwich disponibles");
        }
        return new ModelAndView("home", model);

    }

    @RequestMapping(path = "/sandwichPreferencia", method = RequestMethod.GET)
    public ModelAndView mostrarSandwichFiltradosPorPreferencia(@RequestParam(value = "preferencia") String preferencia) {
        ModelMap model = new ModelMap();
        List<Sandwich> sandwichList = null;
        try {
            sandwichList = servicioSandwich.obtenerTodosLosSandwichesDeUnTipo(preferencia);
            model.put("listaEnPromocion", sandwichList);
        } catch (SandwichNoExistenteException e) {
            model.put("msj", "no hay sandwich disponibles");
        }
        return new ModelAndView("home", model);
    }
}
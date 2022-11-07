package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.NoHaySandwichEnPromocionException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class ControladorSandwich {


    private final ServicioSandwich servicioSandwich;
    private final ServicioLogin servicioLogin;

    private final ServicioEmail servicioEmail;

    @Autowired
    public ControladorSandwich(ServicioSandwich servicioSandwich, ServicioLogin servicioLogin) {
        this.servicioSandwich = servicioSandwich;
        this.servicioLogin = servicioLogin;
        this.servicioEmail = new ServicioEmailImp();

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

    @RequestMapping(path = "confirmarSandwich", method = RequestMethod.GET)
    public ModelAndView confirmarSandwich(@RequestParam(value = "idSandwich") Long idSandwich, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Sandwich sandwichObtenido = null;
        Long idLogeado = (Long) request.getSession().getAttribute("id");

        if(idLogeado == null)
            return new ModelAndView("redirect:/login");
        try{
            sandwichObtenido = this.servicioSandwich.obtenerSandwichPorId(idSandwich);
            request.getSession().setAttribute("sandwichElegido",sandwichObtenido);
            System.err.println(this.convertirSetToList(sandwichObtenido.getIngrediente()));
            model.put("IngredientesDelSandwich",this.convertirSetToList(sandwichObtenido.getIngrediente()));
            model.put("nombre",sandwichObtenido.getNombre());
            model.put("idSandwich",sandwichObtenido.getIdSandwich());
            model.put("montoFinal", sandwichObtenido.obtenerMonto());
            request.getSession().setAttribute("SANDWICH_ELEGIDO",sandwichObtenido);
            return new ModelAndView("confirmarSandwich",model);
        } catch (SandwichNoExistenteException e) {
            model.put("error", "No existe el sandwich");
        }
        return new ModelAndView("redirect:/home",model);
    }


    private List<Ingrediente> convertirSetToList(Set<Ingrediente> ing){
        return ing.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    private Float obtenerMontoFinalDeUnSandwich(List<Ingrediente> listaIngrediente){
        Float monto = 0F;
        for (Ingrediente ing: listaIngrediente) {
            monto += ing.getPrecio();
        }
        return monto;
    }

}
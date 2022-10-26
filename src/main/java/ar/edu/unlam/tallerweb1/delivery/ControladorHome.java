package ar.edu.unlam.tallerweb1.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorHome {


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/nosotros")
    public ModelAndView nosotros() {
        return new ModelAndView("nosotros");
    }

    @RequestMapping(path = "/contacto")
    public ModelAndView contacto() {
        return new ModelAndView("contacto");
    }

    @RequestMapping(path = "/Salir", method = RequestMethod.GET)
    public ModelAndView salirSession(HttpServletRequest request){
        request.getSession().setAttribute("id",null);
        return new ModelAndView("redirect:/login");
    }


}

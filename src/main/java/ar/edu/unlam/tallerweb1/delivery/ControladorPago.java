package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorPago {

    private ServicioEmail servicioEmail;
    private ServicioLogin servicioLogin;
    private ServicioSandwich servicioSandwich;

    private ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorPago(ServicioLogin servicioLogin, ServicioSandwich servicioSandwich, ServicioMercadoPago servicioMercadoPago) {
        this.servicioLogin = servicioLogin;
        this.servicioSandwich = servicioSandwich;
        this.servicioMercadoPago = servicioMercadoPago;
        this.servicioEmail = new ServicioEmailImp();
    }

    @RequestMapping(path = "prepago" , method = RequestMethod.GET)
    public ModelAndView guardar (HttpServletRequest request){
        Sandwich sandwichGuardado = (Sandwich) request.getSession().getAttribute("sandwich");
        this.servicioSandwich.guardarSandwich(sandwichGuardado);
        return new ModelAndView("/pago");
    }

    @RequestMapping(path = "/pago", method = RequestMethod.GET)
    public ModelAndView pagarSandwich (HttpServletRequest request){
        Long idCliente = (Long) request.getSession().getAttribute("id");
        Long idSand = (Long) request.getSession().getAttribute("SANDWICHELEGIDO");
        System.err.println(idSand);
        Preference preference = null;
        Usuario us = null;
        ModelMap modelo = new ModelMap();
        try{
            us = this.servicioLogin.consultarPorID(idCliente);

            preference  = this.servicioMercadoPago.generarPago(idSand);
            modelo.put("preference", preference);
        }catch (UsuarioInvalidoException e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView("pago", modelo);
    }


    @RequestMapping(path = "/payment/success/{sandId}", method = RequestMethod.GET)
    public ModelAndView success(@PathVariable("sandId") Long alquilerId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", "Pago exitoso");
        return new ModelAndView("redirect:/homeLogeado");
    }

    @RequestMapping(path = "/payment/failure", method = RequestMethod.GET)
    public ModelAndView failure(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "No se pudo comprobar el pago. Intente nuevamente más tarde");
        return new ModelAndView("redirect:/homeLogeado");
    }
}
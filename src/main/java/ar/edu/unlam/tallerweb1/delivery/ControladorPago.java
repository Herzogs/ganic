package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ControladorPago {

    private ServicioEmail servicioEmail;
    private ServicioLogin servicioLogin;

    private ServicioMercadoPago servicioMercadoPago;

    private Pago nuevo;

    @Autowired
    public ControladorPago(ServicioLogin servicioLogin, ServicioMercadoPago servicioMercadoPago) {
        this.servicioLogin = servicioLogin;
        this.servicioMercadoPago = servicioMercadoPago;
        this.servicioEmail = new ServicioEmailImp();
        nuevo = new Pago();
    }

    @RequestMapping(path = "/pago", method = RequestMethod.GET)
    public ModelAndView pagarSandwich (HttpServletRequest request) {
        Sandwich sandwich_elegido = (Sandwich) request.getSession().getAttribute("SANDWICH_ELEGIDO");
        Float recargo = (Float) request.getSession().getAttribute("RECARGO");
        Preference preference = null;

        nuevo.setSandwich(sandwich_elegido);
        nuevo.setImpTot(sandwich_elegido.obtenerMonto()+recargo);

        ModelMap modelo = new ModelMap();
        modelo.put("IngredientesDelSandwich",sandwich_elegido.getIngrediente());
        modelo.put("nombre",sandwich_elegido.getNombre());
        modelo.put("montoFinal", sandwich_elegido.obtenerMonto()+recargo);
        preference = this.servicioMercadoPago.generarPago(nuevo);
        modelo.put("preference", preference);
        return new ModelAndView("pago", modelo);
    }

    // TODO:: FIX TEST

    @RequestMapping(path = "/alerta_exitosa", method = RequestMethod.GET)
    public ModelAndView pagoCorrecto(@RequestParam("payment_type") String paymentType, HttpServletRequest request){
        Usuario cliente = null;
        ModelMap modelo = new ModelMap();
        Email nuevoEmail = new Email();
        Long idCliente = (Long) request.getSession().getAttribute("id");
        try{
            cliente = this.servicioLogin.consultarPorID(idCliente);
            nuevoEmail.setUser(cliente);
            nuevoEmail.setMetodoPago(paymentType);
            nuevoEmail.setLista(this.convertirSetToList(nuevo.getSandwich().getIngrediente()));
            this.servicioEmail.sendEmail(nuevoEmail,"Envio De Pedido");
            modelo.put("msg","Se ha enviado el email de confirmación");
        } catch (UsuarioInvalidoException e) {
            modelo.put("error", "a ocurrido un error en el proceso de envio");
        }
        return new ModelAndView("redirect:/home");
    }

    private List<Ingrediente> convertirSetToList(Set<Ingrediente> ing){
        return ing.stream().collect(Collectors.toCollection(ArrayList::new));
    }
}
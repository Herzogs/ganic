package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ControladorPago {

    private final ServicioEmail servicioEmail;
    private final ServicioLogin servicioLogin;

    private final ServicioSandwich servicioSandwich;
    private final ServicioCompra servicioCompra;

    private final Pago nuevo = new Pago();

    @Autowired
    public ControladorPago(ServicioLogin servicioLogin, ServicioCompra servicioCompra, ServicioSandwich servicioSandwich) {
        this.servicioLogin = servicioLogin;
        this.servicioEmail = new ServicioEmailImp();
        this.servicioSandwich = servicioSandwich;
        this.servicioCompra = servicioCompra;

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
        modelo.put("formPago", new FormularioDePago());
        return new ModelAndView("pago", modelo);
    }

    @RequestMapping(path = "/validarPago",method = RequestMethod.POST)
    public ModelAndView validarPago(@ModelAttribute("formPago") FormularioDePago fp, HttpServletRequest request){
       ModelMap model = new ModelMap();
        Sandwich sandwich_elegido = (Sandwich) request.getSession().getAttribute("SANDWICH_ELEGIDO");
        Float recargo = (Float) request.getSession().getAttribute("RECARGO");
        model.put("IngredientesDelSandwich",sandwich_elegido.getIngrediente());
        model.put("nombre",sandwich_elegido.getNombre());
        model.put("montoFinal", sandwich_elegido.obtenerMonto()+recargo);
        model.put("formPago", new FormularioDePago());
       LocalDate venc = LocalDate.parse(fp.getVencTarjeta(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
       LocalDate hoy = LocalDate.now(ZoneId.of("America/Buenos_Aires"));
        if(hoy.isAfter(venc)){
            model.put("msg","valores incorrectos en el ingreso de la tarjeta");
            return new ModelAndView("pago",model);
        }
        if(fp.getNroTarjeta().length() < 16){
            model.put("msg","valores incorrectos en el ingreso de la tarjeta");
            return new ModelAndView("pago",model);
        }
        if(fp.getCodSeguridad().length() < 3){
            model.put("msg","valores incorrectos en el ingreso de la tarjeta");
            return new ModelAndView("pago",model);
        }
        return new ModelAndView("redirect:/alerta_exitosa");
    }




    @RequestMapping(path = "/alerta_exitosa", method = RequestMethod.GET)
    public ModelAndView pagoCorrecto(HttpServletRequest request){
        Usuario cliente = null;
        ModelMap modelo = new ModelMap();
        Email nuevoEmail = new Email();
        Long idCliente = (Long) request.getSession().getAttribute("id");
        try{
            cliente = this.servicioLogin.consultarPorID(idCliente);
            nuevoEmail.setUser(cliente);
            nuevoEmail.setMetodoPago("Tarjeta");
            nuevoEmail.setLista(this.convertirSetToList(nuevo.getSandwich().getIngrediente()));
            nuevoEmail.setRecargo((Float) request.getSession().getAttribute("RECARGO"));
            nuevo.getSandwich().setEnPromocion(false);
            this.servicioSandwich.guardarSandwich(nuevo.getSandwich());
            this.servicioCompra.guardarCompra(generarCompra(cliente,nuevo.getSandwich()));
            this.servicioEmail.sendEmail(nuevoEmail,"Envio De Pedido");
            modelo.put("msg","Se ha enviado el email de confirmación");
        } catch (UsuarioInvalidoException e) {
            modelo.put("error", "a ocurrido un error en el proceso de envio");
        }
        return new ModelAndView("alerta_exitosa");
    }

    private List<Ingrediente> convertirSetToList(Set<Ingrediente> ing){
        return ing.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    private Compra generarCompra(Usuario user, Sandwich sandwich){
        Compra nueva = new Compra();
        nueva.setCliente(user);
        List<Sandwich> list = new ArrayList<>();
        list.add(sandwich);
        nueva.setEstado(EstadoDeCompra.PREPARACION);
        nueva.setDetalle(list);
        nueva.setFecha(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")));
        nueva.setFechaEntrega(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).plusMinutes(5));
        return nueva;
    }

}
package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.MpEntidad;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ControladorPago {

    private final ServicioEmail servicioEmail;
    private final ServicioLogin servicioLogin;

    private final ServicioMercadoPago servicioMercadoPago;

    private final ServicioCompra servicioCompra;

    private Pago nuevo;

    @Autowired
    public ControladorPago(ServicioLogin servicioLogin, ServicioMercadoPago servicioMercadoPago, ServicioCompra servicioCompra) {
        this.servicioLogin = servicioLogin;
        this.servicioMercadoPago = servicioMercadoPago;
        this.servicioEmail = new ServicioEmailImp();
        this.servicioCompra = servicioCompra;
        nuevo = new Pago();
    }
    @RequestMapping(path = "/prepago",  method = RequestMethod.GET)
    public ModelAndView generarPago(HttpServletRequest request){
        String rec= (String)request.getSession().getAttribute("DONDE_VENGO"); // NORMAL,CARRITO
        System.err.println(rec);
        MpEntidad producto = new MpEntidad();
        if(rec.equals("NORMAL")){
            Sandwich sandwich_elegido = (Sandwich) request.getSession().getAttribute("SANDWICH_ELEGIDO");
            producto.setSandwich(sandwich_elegido);
            producto.setCant(1);
            nuevo.getListaCobrar().add(producto);
            nuevo.setImpTot(nuevo.getListaCobrar().get(0).getSandwich().obtenerMonto());
        }

        return new ModelAndView("redirect:/pago");
    }

    @RequestMapping(path = "/pago", method = RequestMethod.GET)
    public ModelAndView pagarSandwich (HttpServletRequest request) {
        //Sandwich sandwich_elegido = (Sandwich) request.getSession().getAttribute("SANDWICH_ELEGIDO");
        //Carrito carritoSalvado = (Carrito) request.getSession().getAttribute("CARRITO_USUARIO");
        Float recargo = (Float) request.getSession().getAttribute("RECARGO");
        Preference preference = null;
        /*if()
        nuevo.setSandwich(sandwich_elegido);
        nuevo.setImpTot(sandwich_elegido.obtenerMonto()+recargo);*/

        ModelMap modelo = new ModelMap();
        /*modelo.put("IngredientesDelSandwich",sandwich_elegido.getIngrediente());
        modelo.put("nombre",sandwich_elegido.getNombre());
        modelo.put("montoFinal", sandwich_elegido.obtenerMonto()+recargo);*/
        modelo.put("IngredientesDelSandwich",nuevo.getListaCobrar().get(0).getSandwich().getIngrediente());
        modelo.put("nombre",nuevo.getListaCobrar().get(0).getSandwich().getNombre());
        modelo.put("montoFinal", nuevo.getListaCobrar().get(0).getSandwich().obtenerMonto()+recargo);
        preference = this.servicioMercadoPago.generarPago(nuevo);
        modelo.put("preference", preference);
        return new ModelAndView("pago", modelo);
    }

    @RequestMapping(path = "/alerta_exitosa", method = RequestMethod.GET)
    public ModelAndView pagoCorrecto(@RequestParam("payment_type") String paymentType, HttpServletRequest request){
        Usuario cliente = null;
        ModelMap modelo = new ModelMap();
        Email nuevoEmail = new Email();
        Long idCliente = (Long) request.getSession().getAttribute("id");
        String dir = (String) request.getSession().getAttribute("DESTINO");
        try{
            cliente = this.servicioLogin.consultarPorID(idCliente);
            cliente.setDireccion(generateDomicilio(dir));
            nuevoEmail.setUser(cliente);
            nuevoEmail.setMetodoPago(paymentType);
            nuevoEmail.setLista(this.convertirSetToList(nuevo.getListaCobrar().get(0).getSandwich().getIngrediente()));
            nuevoEmail.setRecargo((Float) request.getSession().getAttribute("RECARGO"));
            guardarCompra(cliente);
            this.servicioEmail.sendEmail(nuevoEmail,"Envio De Pedido");
            modelo.put("msg","Se ha enviado el email de confirmación");
        } catch (UsuarioInvalidoException e) {
            modelo.put("error", "a ocurrido un error en el proceso de envio");
        }
        /*return new ModelAndView("redirect:/home");*/
        return new ModelAndView("alerta_exitosa",modelo);
    }

    private void guardarCompra(Usuario cliente) {
        nuevo.getListaCobrar().forEach(mpEntidad -> {
            this.servicioCompra.guardarCompra(generarCompra(cliente,mpEntidad.getSandwich()));
        });
    }

    private List<Ingrediente> convertirSetToList(Set<Ingrediente> ing){
        return ing.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    private Compra generarCompra(Usuario user, Sandwich sandwich){
        Compra nueva = new Compra();
        nueva.setCliente(user);
        List<Sandwich> list = new ArrayList<>();
        list.add(sandwich);
        nueva.setDetalle(list);
        nueva.setEstado(EstadoDeCompra.PREPARACION);
        nueva.setFecha(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")));
        nueva.setFechaEntrega(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).plusMinutes(5));
        return nueva;
    }


    private String generateDomicilio(String dest){
        String aux[] = dest.split(",");
        return String.format("%s %s - %s - %s - %s",aux[1],aux[0],aux[2],aux[3],aux[4]);
    }


    public void setPago(Pago p){
        this.nuevo = p;
    }
}
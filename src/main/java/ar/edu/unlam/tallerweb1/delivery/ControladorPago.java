package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.MpEntidad;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.ServicioDetalleCarro;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
public class ControladorPago {

    private final ServicioEmail servicioEmail;
    private final ServicioLogin servicioLogin;

    private final ServicioMercadoPago servicioMercadoPago;

    private final ServicioCompra servicioCompra;

    private final ServicioDetalleCarro servicioDetalleCarro;

    private Pago pagoNuevo;

    private Long payment;

    @Autowired
    public ControladorPago(ServicioLogin servicioLogin, ServicioMercadoPago servicioMercadoPago, ServicioCompra servicioCompra, ServicioDetalleCarro servicioDetalleCarro) {
        this.servicioLogin = servicioLogin;
        this.servicioMercadoPago = servicioMercadoPago;
        this.servicioEmail = new ServicioEmailImp();
        this.servicioCompra = servicioCompra;
        this.servicioDetalleCarro = servicioDetalleCarro;

    }
    @RequestMapping(path = "/prepago",  method = RequestMethod.GET)
    public ModelAndView generarPago(HttpServletRequest request){
        pagoNuevo = new Pago();

            List<DetalleCarro> lista = (List<DetalleCarro>) request.getSession().getAttribute("LISTA_DETALLE");
            lista.forEach(detalleCarro -> {
                MpEntidad producto = new MpEntidad();
                producto.setSandwich(detalleCarro.getSandwich());
                producto.setCantidad(detalleCarro.getCantidad());
                pagoNuevo.getListaCobrar().add(producto);
                pagoNuevo.setImpTot(this.obtenerMonto(lista));
            });
        System.err.println(pagoNuevo);
        return new ModelAndView("redirect:/pago");
    }

    @RequestMapping(path = "/pago", method = RequestMethod.GET)
    public ModelAndView pagarSandwich (HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Float recargo = (Float) request.getSession().getAttribute("RECARGO");
        String destino = (String) request.getSession().getAttribute("DESTINO");
        modelo.put("listaDetalle", pagoNuevo.getListaCobrar());
        Float importeTotal = pagoNuevo.getImpTot();
        pagoNuevo.setImpTot(pagoNuevo.getImpTot()+recargo);
        pagoNuevo.setRecargo(recargo);
        modelo.put("direccion",this.generateDomicilio(destino));
        modelo.put("montoFinal", importeTotal);
        modelo.put("recargo", recargo);
        modelo.put("montoTotalPagar", importeTotal+recargo);
        Preference preference = null;
        preference = this.servicioMercadoPago.generarPago(pagoNuevo);
        modelo.put("preference", preference);
        return new ModelAndView("pago", modelo);
    }

    @RequestMapping(path = "/alerta_exitosa", method = RequestMethod.GET)
    public ModelAndView pagoCorrecto(@RequestParam("payment_type") String paymentType, @RequestParam(value = "payment_id", required = false,defaultValue = "1") Long payment, HttpServletRequest request){
        Usuario cliente = null;
        ModelMap modelo = new ModelMap();
        Email nuevoEmail = new Email();
        Long idCliente = (Long) request.getSession().getAttribute("id");
        String dir = (String) request.getSession().getAttribute("DESTINO");
        String dondeVengo = (String) request.getSession().getAttribute("DONDE_VENGO");
        this.payment = payment;
        try{
            cliente = this.servicioLogin.consultarPorID(idCliente);
            cliente.setDireccion(generateDomicilio(dir));
            nuevoEmail.setUser(cliente);
            nuevoEmail.setMetodoPago(paymentType);
            nuevoEmail.setLista(this.convertirSetToList(pagoNuevo.getListaCobrar().get(0).getSandwich().getIngrediente()));
            nuevoEmail.setRecargo((Float) request.getSession().getAttribute("RECARGO"));
            guardarCompra(cliente);
            this.servicioEmail.sendEmail(nuevoEmail,"Envio De Pedido");
            if(dondeVengo.equals("CARRO"))
                vaciarListaDetalles(request);
            request.getSession().setAttribute("DESTINO",null);
            modelo.put("msg","Se ha enviado el email de confirmación");
        } catch (UsuarioInvalidoException e) {
            modelo.put("error", "a ocurrido un error en el proceso de envio");
        }
        return new ModelAndView("alerta_exitosa",modelo);
    }

    @RequestMapping(path = "/seguimiento", method = RequestMethod.GET)
    public ModelAndView irASeguimiento(){
        return new ModelAndView("seguimiento");
    }


    @RequestMapping(path="/seguirComprando")
    public ModelAndView seguirComprando(HttpServletRequest request){
        request.getSession().setAttribute("LISTA_DETALLE",null);
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/alerta_fallo", method = RequestMethod.GET)
    public ModelAndView compraFalla(){
        return new ModelAndView("alerta_fallo");
    }


    private void guardarCompra(Usuario cliente) {
        pagoNuevo.getListaCobrar().forEach(mpEntidad -> {
            Compra nueva = generarCompra(cliente,mpEntidad.getSandwich(),mpEntidad.getCantidad());
            System.err.println(nueva);
            this.servicioCompra.guardarCompra(nueva);
        });
    }

    private List<Ingrediente> convertirSetToList(Set<Ingrediente> ing){
        return ing.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    private Compra generarCompra(Usuario user, Sandwich sandwich, Integer cantidad){
        Compra nueva = new Compra();
        nueva.setCliente(user);
        List<Sandwich> list = new ArrayList<>();
        list.add(sandwich);
        nueva.setDetalle(list);
        nueva.setEstado(EstadoDeCompra.PREPARACION);
        nueva.setFecha(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")));
        nueva.setFechaEntrega(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).plusMinutes(8));
        nueva.setCant(cantidad);
        nueva.setPayment(this.payment);
        return nueva;
    }


    private String generateDomicilio(String dest){
        String aux[] = dest.split(",");
        return String.format("%s %s - %s - %s - %s",aux[1],aux[0],aux[2],aux[3],aux[4]);
    }

    private Float obtenerMonto(List<DetalleCarro> det){
        AtomicReference<Float> tot = new AtomicReference<>((float) 0L);
        det.forEach(detalleCarro -> {
            tot.updateAndGet(v -> v + detalleCarro.calcularMonto());
        });
        return tot.get();
    }

    private void vaciarListaDetalles(HttpServletRequest request){
        List<DetalleCarro> lista = (List<DetalleCarro>) request.getSession().getAttribute("LISTA_DETALLE");
        lista.forEach(detalleCarro -> {
            System.err.println(detalleCarro);
            this.servicioDetalleCarro.eliminarDetalle(detalleCarro);
        });
    }

    public void setPago(Pago p){
        this.pagoNuevo = p;
    }
}
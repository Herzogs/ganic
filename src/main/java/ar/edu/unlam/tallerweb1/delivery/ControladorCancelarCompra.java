package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.ServicioMercadoPago;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class ControladorCancelarCompra {

    private final ServicioCompra servicioCompra;
    private final ServicioLogin servicioLogin;

    private final ServicioMercadoPago mercadoPago;

    @Autowired
    public ControladorCancelarCompra(ServicioCompra servicioCompra, ServicioLogin servicioLogin, ServicioMercadoPago servicioMercadoPago) {
        this.servicioCompra = servicioCompra;
        this.servicioLogin = servicioLogin;
        this.mercadoPago = servicioMercadoPago;
    }

    @RequestMapping(path = "enPreparacion", method = RequestMethod.GET)
    public ModelAndView listarComprasEnPreparacion(HttpServletRequest request){
        Long idUsuario = 0L;
        List<Compra> compraList = null;
        Usuario user = null;
        ModelMap modelo = new ModelMap();
        try {
            idUsuario = (Long) request.getSession().getAttribute("id");
            user = this.servicioLogin.consultarPorID(idUsuario);
            compraList = this.servicioCompra.listarComprasDeUsuarioPorEstado(user, EstadoDeCompra.PREPARACION);
            modelo.put("listaDeCompras",compraList);
            modelo.put("estado",true);
        }catch (UsuarioInvalidoException | CompraNoEncontradaExeption e){
            modelo.put("msg","No hay compras");
            modelo.put("estado","false");

        }
        return new ModelAndView("enPreparacion",modelo);
    }

    // TODO: Arreglar refund de MP
    @RequestMapping(path = "cancelarCompra", method = RequestMethod.GET)
    public ModelAndView cancelarCompra(@RequestParam(value = "idCompra") Long idCompra, HttpServletRequest request){
        Compra compra = null;
        ModelMap modelMap = new ModelMap();
        try{
            compra = this.servicioCompra.buscarCompra(idCompra);
            if(compra.getEstado().equals(EstadoDeCompra.PREPARACION)){
                this.servicioCompra.cancelarCompra(compra, EstadoDeCompra.CANCELADO);
                /*System.err.println(this.mercadoPago.reembolso(compra));*/
                modelMap.put("msg","Se a eliminado la compra seleccionada");

            }else {
                modelMap.put("msg","Su pedido esta en preparacion, no se pudo eliminar su compra");
            }
        }catch (CompraNoEncontradaExeption e){
            modelMap.put("msg","No exite la compra selecciona");
        }finally {
            modelMap.put("estado","false");
        }
        return new ModelAndView("redirect:/enPreparacion",modelMap);
    }

}

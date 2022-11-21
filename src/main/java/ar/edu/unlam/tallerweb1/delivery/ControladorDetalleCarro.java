package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Excepciones.DetalleInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.ServicioSandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.carro.ServicioCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.ServicioDetalleCarro;
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
import java.util.List;

@Controller
public class ControladorDetalleCarro {
    private ServicioDetalleCarro servicioDetalleCarro;
    private ServicioLogin servicioLogin;
    private ServicioSandwich servicioSandwich;
    private ServicioCarro servicioCarro;

    @Autowired
    public ControladorDetalleCarro(ServicioCarro servicioCarro, ServicioSandwich servicioSandwich, ServicioDetalleCarro servicioDetalle, ServicioLogin servicioLogin) {
        this.servicioDetalleCarro = servicioDetalle;
        this.servicioLogin = servicioLogin;
        this.servicioSandwich = servicioSandwich;
        this.servicioCarro=servicioCarro;
    }

    @RequestMapping(path = "cargarCarrito", method = RequestMethod.GET)
    public ModelAndView verCarrito(HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        List<DetalleCarro> detalle = null;
        Long idUsuario = 0L;
        try {
            idUsuario = (Long) request.getSession().getAttribute("id");
            Usuario usuario = servicioLogin.consultarPorID(idUsuario);
            detalle = servicioDetalleCarro.obtenerDetalleDeCarroDeUsuario(usuario);
            Integer cantidad = 1;
            modelMap.put("cantidadDetalle", detalle.size());
        } catch (UsuarioInvalidoException e) {
            modelMap.put("msg", "Usuario invalido");
        } catch (DetalleInexistenteExeption e) {
            modelMap.put("msg", "Carrito vacio");
        }
        return new ModelAndView("home", modelMap);

    }

    @RequestMapping(path = "/verCarrito", method = RequestMethod.GET)
    public ModelAndView listarCarrito(HttpServletRequest request) {
        ModelMap modelMap = new ModelMap();
        List<DetalleCarro> detalle = null;
        Long idUsuario = 0L;
        try {
            idUsuario = (Long) request.getSession().getAttribute("id");
            Usuario usuario = servicioLogin.consultarPorID(idUsuario);
            detalle = servicioDetalleCarro.obtenerDetalleDeCarroDeUsuario(usuario);
            modelMap.put("listaDetalle", detalle);
            modelMap.put("montoCarrito",this.calcularMontoDelCarrito(detalle));
        } catch (UsuarioInvalidoException e) {
            modelMap.put("msg", "Usuario invalido");
        } catch (DetalleInexistenteExeption e) {
            modelMap.put("msg", "Carrito vacio");
        }
        return new ModelAndView("verCarrito", modelMap);

    }

    @RequestMapping(path = "/agregarAlCarrito")
    public ModelAndView agregarAlCarrito(HttpServletRequest request,
                                         @RequestParam(value = "idSandwich") Long idSandwich,
                                         @RequestParam(value = "cantidad",required = false,defaultValue = "1") Integer cantidad,
                                         @RequestParam(value = "bandera",required = true,defaultValue = "1") Boolean bandera) {
        ModelMap modelMap = new ModelMap();
        try {
            Long idUsuario = (Long) request.getSession().getAttribute("id");
            Usuario usuario = servicioLogin.consultarPorID(idUsuario);
            Sandwich sandwich = servicioSandwich.obtenerSandwichPorId(idSandwich);
            Carro carro = servicioCarro.obtenerCarroDeCLiente(usuario);
            if(servicioDetalleCarro.incrementarCntidad(cantidad,usuario,sandwich).equals(false)){
                guardarDetalle(cantidad, sandwich, carro);
            }

            if(bandera.equals(true))
                return new ModelAndView("redirect:/verCarrito", modelMap);


            return new ModelAndView("redirect:/home", modelMap);

        } catch (SandwichNoExistenteException e) {
            modelMap.put("msg", "Sandwich agotado");

        } catch (UsuarioInvalidoException e) {
            modelMap.put("msg", "Usuario invalido");
        }
        return new ModelAndView("redirect:/home", modelMap);

    }

    @RequestMapping(path = "/salvarCarro")
    public ModelAndView salvarCarroEnServlet(HttpServletRequest request){
        Long idUsuario = 0L;
        Usuario usuario = null;
        List<DetalleCarro> detalleCarro = null;
        try {
            idUsuario = (Long)request.getSession().getAttribute("id");
            usuario = this.servicioLogin.consultarPorID(idUsuario);
            detalleCarro = this.servicioDetalleCarro.obtenerDetalleDeCarroDeUsuario(usuario);
            request.getSession().setAttribute("LISTA_DETALLE",detalleCarro);
            request.getSession().setAttribute("DONDE_VENGO","CARRO");
        } catch (UsuarioInvalidoException | DetalleInexistenteExeption e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView("redirect:/destino");
    }

    private void guardarDetalle(Integer cantidad, Sandwich sandwich, Carro carro) {
        DetalleCarro detalleCarro = new DetalleCarro();
        detalleCarro.setCarro(carro);
        detalleCarro.setSandwich(sandwich);
        detalleCarro.setCantidad(cantidad);
        servicioDetalleCarro.guardarDetalle(detalleCarro);
    }

    @RequestMapping(path = "/vaciarCarro")
    public ModelAndView vaciarCarro(HttpServletRequest request) {
        ModelMap modelMap= new ModelMap();
        Long idUsuario = 0L;
        Usuario usuario = null;

        try {
             idUsuario = (Long) request.getSession().getAttribute("id");
             usuario = servicioLogin.consultarPorID(idUsuario);
            servicioDetalleCarro.vaciarCarro(usuario);
            request.getSession().setAttribute("DESTINO",null);
            modelMap.put("msg", "Carrito vacio");
        } catch (UsuarioInvalidoException e) {
            modelMap.put("msg", "Usuario inexistente");
        }
        return new ModelAndView("verCarrito", modelMap);
    }
    @RequestMapping(path = "/eliminarDetalle")
    public ModelAndView eliminarDetalle(@RequestParam(value = "idDetalle")Long idDetalle ){
        ModelMap modelMap= new ModelMap();
        DetalleCarro detalle= null;
        try {
            detalle = servicioDetalleCarro.obtenerDetalle(idDetalle);
            servicioDetalleCarro.eliminarDetalle(detalle);
        } catch (DetalleInexistenteExeption e) {
            modelMap.put("msg", "Detalle inexistente, no se pudo borrar");
        }
        return new ModelAndView("redirect:/verCarrito", modelMap);
    }

    private Float calcularMontoDelCarrito(List<DetalleCarro> detalleCarroList){
        Float montoFinal = 0F;
        for (DetalleCarro det: detalleCarroList) {
            montoFinal += det.calcularMonto();
        }
        return montoFinal;
    }

}
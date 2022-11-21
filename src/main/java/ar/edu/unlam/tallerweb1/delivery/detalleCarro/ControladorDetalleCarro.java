package ar.edu.unlam.tallerweb1.delivery.detalleCarro;

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
        } catch (UsuarioInvalidoException e) {
            modelMap.put("msg", "Usuario invalido");
        } catch (DetalleInexistenteExeption e) {
            modelMap.put("msg", "Carrito vacio");
        }
        return new ModelAndView("verCarrito", modelMap);

    }

    @RequestMapping(path = "/agregarAlCarrito")
    public ModelAndView agregarAlCarrito(HttpServletRequest request, @RequestParam(value = "idSandwich") Long idSandwich, @RequestParam(value = "cantidad",required = false,defaultValue = "5") Integer cantidad) {
        ModelMap modelMap = new ModelMap();
        try {
            Long idUsuario = (Long) request.getSession().getAttribute("id");
            Usuario usuario = servicioLogin.consultarPorID(idUsuario);
            Sandwich sandwich = servicioSandwich.obtenerSandwichPorId(idSandwich);
            Carro carro = servicioCarro.obtenerCarroDeCLiente(usuario);
            if(servicioDetalleCarro.incrementarCntidad(cantidad,usuario,sandwich).equals(false)){
                guardarDetalle(cantidad, sandwich, carro);
            }

            return new ModelAndView("redirect:/home", modelMap);

        } catch (SandwichNoExistenteException e) {
            modelMap.put("msg", "Sandwich agotado");

        } catch (UsuarioInvalidoException e) {
            modelMap.put("msg", "Usuario invalido");
        }
        return new ModelAndView("redirect:/home", modelMap);

    }

    private void verificarSiEsta(List<DetalleCarro> listaDetalle, Sandwich sandwich) {
        listaDetalle.forEach(detalleCarro -> {
            if(detalleCarro.getSandwich().equals(sandwich)){
                detalleCarro.setCantidad(detalleCarro.getCantidad());
            }
        });

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



}

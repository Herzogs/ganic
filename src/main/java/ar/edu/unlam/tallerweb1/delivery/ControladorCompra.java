package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
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
import java.util.List;

@Controller
public class ControladorCompra {

    private ServicioCompra servicio;
    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorCompra(ServicioCompra servicio, ServicioLogin servicioLogin) {
        this.servicio = servicio;
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping(path = "/historial", method = RequestMethod.GET)
    public ModelAndView listarTodasLasCompras(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<Compra> listaComprado = null;
        Long idUsuario = 0L;
        try {
            idUsuario = (Long) request.getSession().getAttribute("id");
            listaComprado = servicio.listarTodasLasCompras(idUsuario);
            model.put("listaDeCompras", listaComprado);

        } catch (CompraNoEncontradaExeption e) {
            model.put("msg", "Todavía no realizó ninguna compra");
            return new ModelAndView("historial", model);
        }

        return new ModelAndView("historial", model);
    }

    @RequestMapping(path = "/comentarios", method = RequestMethod.GET)
    public ModelAndView comentario(@RequestParam(value = "idCompra") Long idCompra) throws CompraNoEncontradaExeption {
        ModelMap model = new ModelMap();
        Compra compraObtenida = null;
        compraObtenida = servicio.buscarCompra(idCompra);
        model.put("formularioComentario", new FormularioComentario());
        model.put("compra", compraObtenida);
        return new ModelAndView("comentario", model);
    }


    @RequestMapping(path = "/agregarComentario", method = RequestMethod.POST)
    public ModelAndView agregarComentarioAlPedido(FormularioComentario formularioComentario) {
        ModelMap model = new ModelMap();
        try {
            Compra miCompra = servicio.buscarCompra(formularioComentario.getIdCompra());
            miCompra.setComentario(formularioComentario.getComentario());
            this.servicio.actualizarCompra(miCompra);
            return new ModelAndView("redirect:/historial", model);

        } catch (CompraNoEncontradaExeption e) {
            throw new RuntimeException(e);
        }

    }
}

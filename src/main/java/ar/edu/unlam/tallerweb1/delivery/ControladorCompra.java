package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ControladorCompra {

    private ServicioCompra servicio;
    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorCompra(ServicioCompra servicio,ServicioLogin servicioLogin) {
        this.servicio = servicio;
        this.servicioLogin= servicioLogin;
    }

    @RequestMapping(path = "/historial", method = RequestMethod.GET)
    public ModelAndView listarTodasLasCompras(HttpServletRequest request) {
        ModelMap model= new ModelMap();
        List<Compra> listaComprado= null;
        Long idUsuario= 0L;
        try {
            idUsuario= (Long) request.getSession().getAttribute("id");
            listaComprado= servicio.listarTodasLasCompras(idUsuario);
            model.put("listaDeCompras",listaComprado);

        } catch (CompraNoEncontradaExeption e){
            model.put("msg","No existe compra");
            return new ModelAndView("historial",model);
        }

        return  new ModelAndView("historial",model);
    }
}

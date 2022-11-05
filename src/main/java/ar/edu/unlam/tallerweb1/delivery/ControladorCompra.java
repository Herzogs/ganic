package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ControladorCompra {

    private ServicioCompra servicio;

    @Autowired
    public ControladorCompra(ServicioCompra servicio) {
        this.servicio = servicio;
    }

    @RequestMapping(path = "/guardarCompra", method = RequestMethod.POST)
    public ModelAndView guardarCompra(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        Sandwich sandwich = (Sandwich) request.getSession().getAttribute("sandwich");
        List<Sandwich> lista = new ArrayList<>();
        lista.add(sandwich);
        Compra compra= new Compra(usuario, lista);
        servicio.guardarCompra(compra);
        model.put("compra",compra );
        return new ModelAndView("guardarCompra");
    }
}

package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    private ServicioLogin servicioLogin;
    @Autowired
    public ControladorRegistro(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping(path = "/registrar", method = RequestMethod.GET)
    public ModelAndView registrarUsuario(){
        ModelMap model = new ModelMap();
        model.put("datosLogin",new DatosLogin());
        return new ModelAndView("registrar",model);
    }

    @RequestMapping(path = "/crearUsuario", method = RequestMethod.POST)
    public ModelAndView crearRegistro(DatosLogin datosLogin) {
        ModelMap model = new ModelMap();
        Usuario usuarioRegistrado = new Usuario(datosLogin.getEmail(),datosLogin.getPassword());
        model.put("estado","Registro Exitoso");
        this.servicioLogin.crearUsuario(usuarioRegistrado);
        return new ModelAndView("home",model);
    }
}

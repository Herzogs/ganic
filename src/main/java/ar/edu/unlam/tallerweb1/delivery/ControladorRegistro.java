package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorRegistro {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorRegistro(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping(path = "/registrar", method = RequestMethod.GET)
    public ModelAndView registrarUsuario() {
        ModelMap model = new ModelMap();
        model.put("datosLogin", new DatosLogin());
        return new ModelAndView("registrar", model);
    }

    @RequestMapping(path = "/crearUsuario", method = RequestMethod.POST)
    public ModelAndView crearRegistro(DatosLogin datosLogin) throws UsuarioNoRegistradoExepcion {
        ModelMap model = new ModelMap();
        if (esValido(datosLogin.getEmail())) {
            Usuario usuarioGenerado = servicioLogin.consultarUsuario(datosLogin.getEmail());
            if (usuarioGenerado == null) {
                Usuario usuarioRegistrado = new Usuario(datosLogin.getEmail(), datosLogin.getPassword());
                model.put("estado", "Registro Exitoso");
                this.servicioLogin.crearUsuario(usuarioRegistrado);
                return new ModelAndView("login", model);
            }
            model.put("msg", "El mail ya se encuentrta registrado");
            return new ModelAndView("registrar", model);
        }
        model.put("msg", "El mail debe ser de formato valido");
        return new ModelAndView("registrar", model);

    }
    // TODO: realizar test
    @RequestMapping("/verificar")
    public ModelAndView verificarDatos(HttpServletRequest request) {
        Long idLogeado = (Long) request.getSession().getAttribute("id");
        if ( idLogeado != null){
            ModelMap modelo = new ModelMap();
            modelo.put("datosUsuario", new DatosUsuario());
            return new ModelAndView("verificar", modelo);
        }
        return new ModelAndView("redirect:/login");

    }

    // TODO: realizar test , ver si se puede simplificar el seteo de datos
    @RequestMapping(path = "/verificarDatos", method = RequestMethod.POST)
    public ModelAndView envioDeVerificacion(DatosUsuario datosUsuario,HttpServletRequest request) throws UsuarioNoRegistradoExepcion {
        ModelMap model = new ModelMap();

        Long idLogeado = (Long) request.getSession().getAttribute("id");
        Usuario miUsuario = this.servicioLogin.consultarPorID(idLogeado);

        miUsuario.setNombre(datosUsuario.getNombre());
        miUsuario.setApellido(datosUsuario.getApellido());
        miUsuario.setDireccion(datosUsuario.getDireccion());
        miUsuario.setPreferencia(datosUsuario.getPreferencia());

        this.servicioLogin.actualizarUsuario(miUsuario);
        return new ModelAndView("home", model);

    }


    private boolean esValido(String email) {
        return email.endsWith(".com") && email.contains("@");
    }

}

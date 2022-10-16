package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Excepciones.PassswordIncorrectoExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorUsuario {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorUsuario(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();

        modelo.put("datosLogin", new DatosLogin());

        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();


        try {
            Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
            request.getSession().setAttribute("id", usuarioBuscado.getId());
            request.getSession().setAttribute("email", usuarioBuscado.getEmail());
            return new ModelAndView("redirect:/home");

        } catch (UsuarioNoRegistradoExepcion e) {
            model.put("error", e.getMessage());
            return new ModelAndView("login", model);
        } catch (PassswordIncorrectoExeption e) {
            model.put("error", e.getMessage());
            return new ModelAndView("login", model);
        }

    }

    @RequestMapping(path = "/registrar", method = RequestMethod.GET)
    public ModelAndView registrarUsuario() {
        ModelMap model = new ModelMap();
        model.put("datosLogin", new DatosLogin());
        return new ModelAndView("registrar", model);
    }

    @RequestMapping(path = "/crearUsuario", method = RequestMethod.POST)
    public ModelAndView crearRegistro(DatosLogin datosLogin) {
        ModelMap model = new ModelMap();
        if (esValido(datosLogin.getEmail())) {
            if (!servicioLogin.estaRegistrado(datosLogin.getEmail())) {
                Usuario usuarioRegistrado = new Usuario(datosLogin.getEmail(), datosLogin.getPassword());
                model.put("estado", "Registro Exitoso");
                this.servicioLogin.crearUsuario(usuarioRegistrado);
                return new ModelAndView("redirect:/login", model);
            }
            model.put("msg", "El mail ya se encuentrta registrado");
            return new ModelAndView("registrar", model);
        }
        model.put("msg", "El mail debe ser de formato valido");
        return new ModelAndView("registrar", model);

    }

    @RequestMapping("/verificar")
    public ModelAndView verificarDatos(HttpServletRequest request) {
        Long idLogeado = (Long) request.getSession().getAttribute("id");
        if (idLogeado != null) {
            ModelMap modelo = new ModelMap();
            modelo.put("datosUsuario", new DatosUsuario());
            return new ModelAndView("verificar", modelo);
        }
        return new ModelAndView("redirect:/login");

    }

    @RequestMapping(path = "/verificarDatos", method = RequestMethod.POST)
    public ModelAndView envioDeVerificacion(DatosUsuario datosUsuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario miUsuario = null;
        Long idLogeado = (Long) request.getSession().getAttribute("id");
        try {
            miUsuario = this.servicioLogin.consultarPorID(idLogeado);
            miUsuario.setNombre(datosUsuario.getNombre());
            miUsuario.setApellido(datosUsuario.getApellido());
            miUsuario.setDireccion(datosUsuario.getDireccion());
            miUsuario.setPreferencia(datosUsuario.getPreferencia());
            this.servicioLogin.actualizarUsuario(miUsuario);
            return new ModelAndView("home", model);
        } catch (UsuarioInvalidoException exc) {
            model.put("error", exc.getMessage());
            return new ModelAndView("redirect:/login", model);
        }
    }


    private boolean esValido(String email) {

        return email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(path = "/nosotros")
    public ModelAndView nosotros() {
        return new ModelAndView("nosotros");
    }

    @RequestMapping(path = "/contacto")
    public ModelAndView contacto() {
        return new ModelAndView("contacto");
    }

}

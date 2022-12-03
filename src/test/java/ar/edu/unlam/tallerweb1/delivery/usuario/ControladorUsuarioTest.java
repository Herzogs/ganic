package ar.edu.unlam.tallerweb1.delivery.usuario;

import ar.edu.unlam.tallerweb1.delivery.ControladorUsuario;
import ar.edu.unlam.tallerweb1.delivery.DatosLogin;
import ar.edu.unlam.tallerweb1.delivery.DatosUsuario;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ControladorUsuarioTest {

    private ServicioLogin servicioLogin;
    private ControladorUsuario controladorDeRegistro;
    private DatosLogin datosLogin;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void init() {
        this.servicioLogin = mock(ServicioLogin.class);
        this.controladorDeRegistro = new ControladorUsuario(this.servicioLogin);
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(this.session);
    }

    @Test
    public void cuandoSeleccionoElBotonDeRegistarMeEnviaALaVistaRegistrar() {
        String vistaDestino = "registrar";
        ModelAndView mod = cuandoLlamoAlControlador();
        assertThat(mod.getViewName()).isEqualTo(vistaDestino);
    }

    private ModelAndView cuandoLlamoAlControlador() {
        ModelAndView mod = this.controladorDeRegistro.registrarUsuario();
        return mod;
    }

    @Test
    public void alIngresarLosDatosCorrectosSeCreeElRegistroYMeRedirijaAlLogin() throws UsuarioNoRegistradoExepcion {
        String vista_destino = dadoQueTengoLaVistaDestino("redirect:/login");
        DatosLogin nuevoDatosLogin = dadoQueTengoUnDatosLogin();
        ModelAndView model = cuandoQuieroCrearUnNuevoRegistro(nuevoDatosLogin);
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, vista_destino);
    }

    @Test
    public void queNoSePuedaGuardarUnUsuarioConEmailYaRegistrado() throws UsuarioNoRegistradoExepcion {
        String vista_destino = dadoQueTengoLaVistaDestino("registrar");
        DatosLogin nuevoDatosLogin = dadoQueTengoUnDatosLogin();
        cuandoUnUsuarioYaExiste("test@test.com");
        ModelAndView model = cuandoQuieroCrearUnNuevoRegistro(nuevoDatosLogin);
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, vista_destino);
    }

    @Test
    public void queNoMeDejeGuardarUnUsurioConFormatoDeMAilInavlido() {
        String vista_destino = dadoQueTengoLaVistaDestino("registrar");
        DatosLogin nuevoDatosLogin = dadoQueTengoUnDatosLoginConErrores();
        ModelAndView model = cuandoQuieroCrearUnNuevoRegistro(nuevoDatosLogin);
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, vista_destino);
        entoncesVerificoQueMeEnvieElMensajeDeError(model, "El mail debe ser de formato valido");
    }

    @Test
    public void cuandoElUsuarioEstaLogeadoYVaAVerificarMeRedirijeAVerificar() {
        String vista_destino = dadoQueTengoLaVistaDestino("verificar");
        dadoQueTengoUnUsuarioLogeado(1L);
        ModelAndView model = cuandoQuieroVerificarLosDatos();
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, vista_destino);
    }

    @Test
    public void cuandoElUsuarioNoEstaLogeadoYVaAVerificarLoRedirijoALLogin() {
        String vista_destino = dadoQueTengoLaVistaDestino("redirect:/login");
        dadoQueTengoUnUsuarioLogeado(null);
        ModelAndView model = cuandoQuieroVerificarLosDatos();
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, vista_destino);
    }

    @Test
    public void cuandoElUsuarioEstaLogeadoYRellenoLaVistaVerificacionEnvieAHome() throws UsuarioInvalidoException {
        DatosUsuario datosUsuario = dadoQueTengoUnosDatosDeUsuario("Juan", "Galvez", "Direccion", "SinRestriccion");
        Usuario usuario = dadoQueTengoUnUsuarioRegistrado("test@test.com", "123");
        dadoQueTengoUnUsuarioLogeado(1L);
        cuandoLeDigaAlServicioQueMeBusqueElUsuarioPorID(1L, usuario);
        ModelAndView model = entoncesLlamoAlEnvioDeVerificacion(datosUsuario, this.request);
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, "home");
    }

    @Test
    public void cuandoElUsuarioNoEstaLogeadoYRellenoLaVistaVerificacionEnvieALLogin() throws UsuarioInvalidoException {
        DatosUsuario datosUsuario = dadoQueTengoUnosDatosDeUsuario("Juan", "Galvez", "Direccion", "SinRestriccion");
        Usuario usuario = dadoQueTengoUnUsuarioRegistrado("test@test.com", "123");
        dadoQueTengoUnUsuarioLogeado(null);
        cuandoLeDigaAlServicioQueMeBusqueElUsuarioPorIDMeLanzeExcepcion(null);
        ModelAndView model = entoncesLlamoAlEnvioDeVerificacion(datosUsuario, this.request);
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, "redirect:/login");
    }

    @Test
    public void cuandoElUsuarioLogeadoEntraASuPerfilLoEnviaAMisdatos() throws UsuarioInvalidoException {
        DatosUsuario datosUsuario = dadoQueTengoUnosDatosDeUsuario("Juan", "Galvez", "Direccion", "SinRestriccion");
        Usuario usuario = dadoQueTengoUnUsuarioRegistrado("test@test.com", "123");
        dadoQueTengoUnUsuarioLogeado(1L);
        cuandoLeDigaAlServicioQueMeBusqueElUsuarioPorID(1L, usuario);
        ModelAndView model = cuandoEjecuteElMetodoVerDatosDeUsuario();
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model, "misdatos");
    }

    private void cuandoLeDigaAlServicioQueMeBusqueElUsuarioPorIDMeLanzeExcepcion(Long id) throws UsuarioInvalidoException {
        when(this.servicioLogin.consultarPorID(id)).thenThrow(new UsuarioInvalidoException("No Existe Usuario"));
    }

    private void entoncesVerificoQueMeEnvieALaVistaCorrecta(ModelAndView model, String vistaEsperada) {
        assertThat(model.getViewName()).isEqualTo(vistaEsperada);
    }

    private Usuario dadoQueTengoUnUsuarioRegistrado(String email, String pass) {
        Usuario us = new Usuario();
        us.setId(1L);
        us.setEmail(email);
        us.setPassword(pass);
        return us;
    }

    private void cuandoLeDigaAlServicioQueMeBusqueElUsuarioPorID(Long l, Usuario us) throws UsuarioInvalidoException {
        when(this.servicioLogin.consultarPorID(l)).thenReturn(us);
    }

    private ModelAndView entoncesLlamoAlEnvioDeVerificacion(DatosUsuario du, HttpServletRequest request) {
        return this.controladorDeRegistro.envioDeVerificacion(du, request);
    }

    private void dadoQueTengoUnUsuarioLogeado(Long l) {
        when(this.request.getSession().getAttribute("id")).thenReturn(l);
    }

    private DatosUsuario dadoQueTengoUnosDatosDeUsuario(String nombre, String apellido, String direccion, String pref) {
        DatosUsuario us = new DatosUsuario();
        us.setNombre(nombre);
        us.setApellido(apellido);
        us.setDireccion(direccion);
        us.setPreferencia(pref);
        return us;
    }

    private DatosLogin dadoQueTengoUnDatosLogin() {
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test.com");
        this.datosLogin.setPassword("123");
        return datosLogin;
    }

    private DatosLogin dadoQueTengoUnDatosLoginConErrores() {
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test");
        this.datosLogin.setPassword("123");
        return datosLogin;
    }

    private void cuandoUnUsuarioYaExiste(String mail) throws UsuarioNoRegistradoExepcion {
        when(this.servicioLogin.estaRegistrado(mail)).thenThrow(new UsuarioNoRegistradoExepcion("usuario registrado"));
    }

    private ModelAndView cuandoEjecuteElMetodoVerDatosDeUsuario() throws UsuarioInvalidoException {
        return this.controladorDeRegistro.verDatosDeUsuario(request);
    }

    private String dadoQueTengoLaVistaDestino(String vista) {
        String vista_destino = vista;
        return vista_destino;
    }

    private ModelAndView cuandoQuieroCrearUnNuevoRegistro(DatosLogin datos) {
        ModelAndView model = this.controladorDeRegistro.crearRegistro(datos);
        return model;
    }

    private void entoncesVerificoQueMeEnvieElMensajeDeError(ModelAndView model, String mensaje) {
        assertThat(model.getModel().get("msg")).isEqualTo(mensaje);
    }

    private ModelAndView cuandoQuieroVerificarLosDatos() {
        ModelAndView model = controladorDeRegistro.verificarDatos(request);
        return model;
    }
}

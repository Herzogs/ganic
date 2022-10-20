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
    public void alIngresarLosDatosSeGuardenEnLaBaseDeDatos()  {
        String vista_destino ="redirect:/login";
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test.com");
        this.datosLogin.setPassword("123");

        ModelAndView model = this.controladorDeRegistro.crearRegistro(this.datosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);
    }
    @Test
    public void queNSePuedaGuardarUnUsusrioConMamilYaRegistrado()  {
        String vista_destino ="registrar";
        DatosLogin nuevoDatosLogin = obtenerUnDatosLogin();
        ModelAndView model;
        cuandoUnUsuarioYaExiste("test@test.com");
        model = this.controladorDeRegistro.crearRegistro(nuevoDatosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);

    }

    @Test
    public void queNoMeDejeGuardarUnUsurioConFormatoDeMAilInavlido()  {
        String vista_destino ="registrar";
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test");
        this.datosLogin.setPassword("123");
        ModelAndView model = this.controladorDeRegistro.crearRegistro(this.datosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);
        assertThat(model.getModel().get("msg")).isEqualTo("El mail debe ser de formato valido");
    }

    @Test
    public void cuandoIngresoAVerificarDatosMeDevuelveLaVistaVerificar() {
        String vistaDestino = "verificar";
        when((Long) request.getSession().getAttribute("id")).thenReturn(1L);
        ModelAndView mod = controladorDeRegistro.verificarDatos(request);
        assertThat(mod.getViewName()).isEqualTo(vistaDestino);
    }

    // El GetSession pasando el true por parametro, deberia generar una nueva session, pero da null
    @Test
    public void cuandoElUsuarioEstaLogeadoYVaAVerificar(){
        String vistaDestino ="verificar";

        when(this.request.getSession().getAttribute("id")).thenReturn(1L);
        ModelAndView mod = controladorDeRegistro.verificarDatos(this.request);

        assertThat(mod.getViewName()).isEqualTo(vistaDestino);
    }

    @Test
    public void cuandoElUsuarioNoEstaLogeadoYVaAVerificarLoRedirijoALLogin(){
        String vistaDestino ="redirect:/login";

        when(this.request.getSession().getAttribute("id")).thenReturn(null);
        ModelAndView mod = controladorDeRegistro.verificarDatos(this.request);

        assertThat(mod.getViewName()).isEqualTo(vistaDestino);
    }

    @Test
    public void cuandoElUsuarioEstaLogeadoYRellenoLaVistaVerificacionEnvieAHome() throws UsuarioInvalidoException {
        DatosUsuario datosUsuario = dadoQueTengoUnosDatosDeUsuario("Juan","Galvez","Direccion","SinRestriccion");
        Usuario usuario = dadoQueTengoUnUsuarioRegistrado("test@test.com","123");
        dadoQueTengoUnUsuarioLogeado(1L);
        cuandoLeDigaAlServicioQueMeBusqueElUsuarioPorID(1L,usuario);
        ModelAndView model = entoncesLlamoAlEnvioDeVerificacion(datosUsuario,this.request);
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model,"home");
    }

    @Test
    public void cuandoElUsuarioNoEstaLogeadoYRellenoLaVistaVerificacionEnvieALLogin() throws UsuarioInvalidoException {
        DatosUsuario datosUsuario = dadoQueTengoUnosDatosDeUsuario("Juan","Galvez","Direccion","SinRestriccion");
        Usuario usuario = dadoQueTengoUnUsuarioRegistrado("test@test.com","123");
        dadoQueTengoUnUsuarioLogeado(null);
        cuandoLeDigaAlServicioQueMeBusqueElUsuarioPorIDMeLanzeExcepcion(null);
        ModelAndView model = entoncesLlamoAlEnvioDeVerificacion(datosUsuario,this.request);
        entoncesVerificoQueMeEnvieALaVistaCorrecta(model,"redirect:/login");
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

    private ModelAndView entoncesLlamoAlEnvioDeVerificacion(DatosUsuario du,HttpServletRequest request) {
        return this.controladorDeRegistro.envioDeVerificacion(du,request);
    }

    private void dadoQueTengoUnUsuarioLogeado(Long l) {
        when(this.request.getSession().getAttribute("id")).thenReturn(l);
    }

    private DatosUsuario dadoQueTengoUnosDatosDeUsuario(String nombre , String apellido, String direccion, String pref) {
        DatosUsuario us = new DatosUsuario();
        us.setNombre(nombre);
        us.setApellido(apellido);
        us.setDireccion(direccion);
        us.setPreferencia(pref);
        return us;
    }

    private DatosLogin obtenerUnDatosLogin() {
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test.com");
        this.datosLogin.setPassword("123");
        DatosLogin nuevoDatosLogin= datosLogin;
        return nuevoDatosLogin;
    }
    private void cuandoUnUsuarioYaExiste(String mail){
         when(this.servicioLogin.estaRegistrado(mail)).thenReturn(true);
    }

}

package ar.edu.unlam.tallerweb1.delivery.usuario;

import ar.edu.unlam.tallerweb1.delivery.ControladorRegistro;
import ar.edu.unlam.tallerweb1.delivery.DatosLogin;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ControladorDeRegistroTest {

    private ServicioLogin servicioLogin;
    private ControladorRegistro controladorDeRegistro;
    private DatosLogin datosLogin;

    @Before
    public void inti() {
        this.servicioLogin = mock(ServicioLogin.class);
        this.controladorDeRegistro = new ControladorRegistro(this.servicioLogin);
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
        String vista_destino ="login";
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test.com");
        this.datosLogin.setPassword("123");

        ModelAndView model = this.controladorDeRegistro.crearRegistro(this.datosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);
    }
    @Test
    public void queNSePuedaGuardarUnUsusrioConMamilYaRegistrado() throws UsuarioNoRegistradoExepcion {
        String vista_destino ="registrar";
        DatosLogin nuevoDatosLogin = obtenerUnDatosLogin();
        ModelAndView model;
        cuandoUnUsuarioYaExiste("test@test.com");
        model = this.controladorDeRegistro.crearRegistro(nuevoDatosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);

    }

    @Test
    public void queNoMeDejeGuardarUnUsusrioConFormatoDeMAilInavlido()  {
        String vista_destino ="registrar";
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test");
        this.datosLogin.setPassword("123");
        ModelAndView model = this.controladorDeRegistro.crearRegistro(this.datosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);
        assertThat(model.getModel().get("msg")).isEqualTo("El mail debe ser de formato valido");
    }

    public void buscarUnUsuarioPorEmailConMokito(String mail) throws UsuarioNoRegistradoExepcion {
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test.com");
        this.datosLogin.setPassword("123");
        Usuario usuarioEsperado = new Usuario(datosLogin.getEmail(),datosLogin.getPassword());
        when(servicioLogin.consultarUsuario(mail)).thenReturn(usuarioEsperado);
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

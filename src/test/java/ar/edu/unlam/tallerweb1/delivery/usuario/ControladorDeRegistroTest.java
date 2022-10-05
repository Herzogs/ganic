package ar.edu.unlam.tallerweb1.delivery.usuario;

import ar.edu.unlam.tallerweb1.delivery.ControladorRegistro;
import ar.edu.unlam.tallerweb1.delivery.DatosLogin;
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
        ModelAndView mod = this.controladorDeRegistro.registrarUsuario();
        assertThat(mod.getViewName()).isEqualTo(vistaDestino);
    }

    @Test
    public void alIngresarLosDatosSeGuardenEnLaBaseDeDatos(){
        String vista_destino ="home";
        this.datosLogin = new DatosLogin();
        this.datosLogin.setEmail("test@test.com");
        this.datosLogin.setPassword("123");
        Usuario usuarioEsperado = new Usuario(datosLogin.getEmail(),datosLogin.getPassword());
        ModelAndView model = this.controladorDeRegistro.crearRegistro(this.datosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);
    }
    @Test
    public void queNSePuedaGuardarUnUsusrioConMamilYaRegistrado(){
        String vista_destino ="registrar";
        DatosLogin nuevoDatosLogin = obtenerUnDatosLogin();
        ModelAndView model;
        buscarUnUsuarioPorEmailConMokito("test@test.com");
        model = this.controladorDeRegistro.crearRegistro(nuevoDatosLogin);
        assertThat(model.getViewName()).isEqualTo(vista_destino);

    }




    public void buscarUnUsuarioPorEmailConMokito(String mail){
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
}

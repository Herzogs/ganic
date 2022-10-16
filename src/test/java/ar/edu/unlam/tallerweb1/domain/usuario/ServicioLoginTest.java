package ar.edu.unlam.tallerweb1.domain.usuario;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.PassswordIncorrectoExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLoginImpl;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ServicioLoginTest extends SpringTest {

    private ServicioLogin servicioLogin;
    private RepositorioUsuario repositorioUsuario;

    @Before
    public void init() {
        this.repositorioUsuario = mock(RepositorioUsuario.class);
        this.servicioLogin = new ServicioLoginImpl(repositorioUsuario);

    }

    @Test
    public void queLuegoDeCrearUnUsuarioSePuedaVerificarSiSeGuardo() throws UsuarioNoRegistradoExepcion, PassswordIncorrectoExeption {
        Usuario usuarioBuscado = dadoQueExisteUnUsuario();
        cuandoLLamoAlRepositorioYbusco(usuarioBuscado);
        verificoQueNoSeaNull(usuarioBuscado);

    }

    @Test(expected = UsuarioInvalidoException.class )
    public void queSiQuieroActualizarUnUsuarioQueNoExistaMeLAnseUNaExepcion() throws UsuarioInvalidoException {
    Usuario usuario=usuarioQueNoExisteEnLAbase();
    cuandoLLameAlServicioParaConsultarQueElUSuarioExistaMeDevuelvaUnaExcepcion(usuario);
    actualizarDatosDelUSuario(usuario,"Juan","calleFalsa123");

    }

    private void cuandoLLameAlServicioParaConsultarQueElUSuarioExistaMeDevuelvaUnaExcepcion(Usuario usuario) throws UsuarioInvalidoException {
        when(this.servicioLogin.consultarPorID(usuario.getId())).thenThrow(new UsuarioInvalidoException("error"));
    }

    private void actualizarDatosDelUSuario(Usuario usuario, String nombre, String direccion){

        this.servicioLogin.actualizarUsuario(usuario);
    }

    private Usuario usuarioQueNoExisteEnLAbase() {
        Usuario user = new Usuario();
        user.setEmail("juan@gmail.com");
        user.setPassword("123");
        user.setId(100L);
        return user;
    }

    private void cuandoLLamoAlRepositorioYbusco(Usuario usuarioBuscado) throws UsuarioNoRegistradoExepcion, PassswordIncorrectoExeption {
        when(repositorioUsuario.buscarUsuario(usuarioBuscado.getEmail(), usuarioBuscado.getPassword())).
                thenReturn(usuarioBuscado);
    }

    private void verificoQueNoSeaNull(Usuario usuarioBuscado) {
        assertThat(usuarioBuscado).isNotNull();
    }


    private Usuario dadoQueExisteUnUsuario() {
        return new Usuario("pablo@gmail.com", "123");
    }

}

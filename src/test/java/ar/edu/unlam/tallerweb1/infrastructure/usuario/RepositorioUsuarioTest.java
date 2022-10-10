package ar.edu.unlam.tallerweb1.infrastructure.usuario;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RepositorioUsuarioTest extends SpringTest {
    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Test
    @Transactional
    @Rollback
    public void queLuegoDeCrearUnUsusrioLoPuedaEncontar() throws UsuarioNoRegistradoExepcion {
        dadoQueExisteUnUsuario();
        Boolean existeUsusrio = entoncesEncuento("pablo@gmail.com");
        entoncesVerificoQueNoSeaNull(existeUsusrio);
    }
    @Test
    @Transactional
    @Rollback
    public void queSePuedaActualizarUnUsuarioQueExista() throws UsuarioNoRegistradoExepcion {
        dadoQueExisteUnUsuario();
        entoncesActualizo();
        Usuario usuarioAModificar= obtenerUsuario("pablo@gmail.com", "123");
        String nombre = "Pablo";
        entoncesVerificoQueSeHaYaMOdificado(usuarioAModificar,nombre);
    }

    @Test(expected =UsuarioNoRegistradoExepcion.class )
    @Transactional
    @Rollback
    public void queSiPidoActualizarUnUsuarioQueNoExistaLanseUnaExepcion() throws UsuarioNoRegistradoExepcion {
        dadoUnUsuarioNoRegistradoEnLABAseDeDatos();
        Usuario usuarioAModificar= obtenerUsuario("noestoyregistrado@gmail.com", "123");
        String nombre = "Pablo";

    }

    private void dadoUnUsuarioNoRegistradoEnLABAseDeDatos() {
        Usuario usuarioQueNoExiste= new Usuario("noestoyregistrado@gmail.com", "123");

    }

    private void entoncesVerificoQueSeHaYaMOdificado(Usuario usuarioAModificar, String nombre) {
        assertThat(usuarioAModificar.getNombre()).isEqualTo(nombre);
    }

    private void entoncesActualizo() throws UsuarioNoRegistradoExepcion {
        Usuario usuario = obtenerUsuario("pablo@gmail.com", "123");
        usuario.setNombre("Pablo");
        usuario.setApellido("Aimar");
        usuario.setDireccion("calleFalsa 123");
        usuario.setPreferencia("SinRestriccion");
        repositorioUsuario.modificar(usuario);
        
    }

    private Usuario obtenerUsuario(String email, String password) throws UsuarioNoRegistradoExepcion {
        Usuario usuario = repositorioUsuario.buscarUsuario(email,password);
        return usuario;
    }


    private void entoncesVerificoQueNoSeaNull(Boolean usuarioBuscado) {
        assertEquals(true, usuarioBuscado);
    }

    private Boolean entoncesEncuento(String email) throws UsuarioNoRegistradoExepcion {
        return repositorioUsuario.estaRegistrado(email);
    }
    private void dadoQueExisteUnUsuario() {
        Usuario usuario = new Usuario("pablo@gmail.com", "123");
        repositorioUsuario.guardar(usuario);
    }


}

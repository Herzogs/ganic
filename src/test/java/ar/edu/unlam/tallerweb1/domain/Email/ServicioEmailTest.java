package ar.edu.unlam.tallerweb1.domain.Email;

import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class ServicioEmailTest {

    private ServicioEmail serv;

    @Before
    public void init (){
        this.serv = new ServicioEmailImp();
    }

    @Test
    public void queSePuedaEnviarUnMailAUnDestinatarioValido(){
        String email = "test@gmail.com";
        String password = "1234";
        Boolean envioExitoso = true;
        Usuario user = dadoQueTengoUnUsuario(email,password);
        Boolean est = entoncesLeDigoAlServicioQueLeEnvieUnEmailAlUsuario(user);
        entoncesVerificoQueSiSePudoEnviarCorrectamenteElMail(est,envioExitoso);
    }

    @Test
    public void queDeErrorAlEnviarUnEmailCuandoLaDireccionNoExista(){
        String email = "";
        String password = "1234";
        Boolean envioExitoso = false;
        Usuario user = dadoQueTengoUnUsuario(email,password);
        Boolean est = entoncesLeDigoAlServicioQueLeEnvieUnEmailAlUsuario(user);
        entoncesVerificoQueSiSePudoEnviarCorrectamenteElMail(est,envioExitoso);

    }

    private void entoncesVerificoQueSiSePudoEnviarCorrectamenteElMail(Boolean est, Boolean b) {
        assertThat(est).isEqualTo(b);
    }

    private Boolean entoncesLeDigoAlServicioQueLeEnvieUnEmailAlUsuario(Usuario user){
        return this.serv.sendEmail(user.getEmail(),"Envio De Padido","Pevio De Pedido");
    }

    private Usuario dadoQueTengoUnUsuario(String email, String password) {
        return new Usuario(email,password);
    }


}

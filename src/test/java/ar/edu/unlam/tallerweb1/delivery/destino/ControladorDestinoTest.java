package ar.edu.unlam.tallerweb1.delivery.destino;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.delivery.ControladorDestino;

import ar.edu.unlam.tallerweb1.delivery.FormularioDestino;
import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Excepciones.EnvioFueraDeZonaException;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorDestinoTest extends SpringTest {

    private ControladorDestino controladorDestino;
    private HttpServletRequest request;
    private HttpSession session;


    @Before
    public void init(){
        this.controladorDestino = new ControladorDestino();
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(this.session);
    }

   @Test
   public void probarQueAlSeleccionarUnDestinoQueEsteAMenosDe300MtrsNoCobreRecargo(){

        Usuario cliente = dadoQueTengoUnUsuario("Damian","Spizzirri","test@tes.com","123");
       List<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSinRestriccion();
       Email email = dadoQueTengoUnEmailConUnPedido(cliente,lista);
       Float distancia = dadoQueTengoUnaDistanciaEnMtrs(250F);
       FormularioDestino fd = dadoQueTengoUnDestino(250F,"test");
        cuandoLePidaAlRequestElEmailConLosDatosDelPedido(email);
       ModelAndView model = cuandoSeLoPasoAlControladorLaDistancia(fd,this.request);
        entoncesVerificoQueNoHuboRecargo(email);

   }

    @Test
    public void probarQueAlSeleccionarUnDestinoQueEsteAMasDe2000MtrsMeMuestreMensajeFueraDeRango(){
        String msg ="Fuera De rango de Envio";
        Usuario cliente = dadoQueTengoUnUsuario("Damian","Spizzirri","test@tes.com","123");
        List<Ingrediente> lista = dadoQueTengoUnaListaDeIngredientesSinRestriccion();
        Email email = dadoQueTengoUnEmailConUnPedido(cliente,lista);
        FormularioDestino fd = dadoQueTengoUnDestino(4000F,"Test");
        cuandoLePidaAlRequestElEmailConLosDatosDelPedido(email);
        ModelAndView model = cuandoSeLoPasoAlControladorLaDistancia(fd,this.request);
        entoncesVerificoQueMeMuestreElMsgDeError(model,msg);

    }

    private FormularioDestino dadoQueTengoUnDestino(float v, String test) {
        FormularioDestino fd = new FormularioDestino();
        fd.setDestino(test);
        fd.setDistance(v);
        return fd;
    }

    private void entoncesVerificoQueMeMuestreElMsgDeError(ModelAndView model, String msg) {
        assertThat(model.getViewName()).isEqualTo("destino");
        assertThat(model.getModel().get("msg")).isEqualTo(msg);
    }

    private void entoncesVerificoQueNoHuboRecargo(Email email) {
        assertThat(email.getRecargo()).isEqualTo(0F);
    }

    private void cuandoLePidaAlRequestElEmailConLosDatosDelPedido(Email email) {
        when(this.request.getSession().getAttribute("email")).thenReturn(email);
    }

    private List<Ingrediente> dadoQueTengoUnaListaDeIngredientesSinRestriccion() {
        List<Ingrediente> lista = new ArrayList<>();
        Ingrediente ing1 = new Ingrediente(1l, "test", 1F, 1, "test", "Vegano");
        Ingrediente ing2 = new Ingrediente(2l, "test", 1F, 1, "test", "Vegano");
        lista.add(ing1);
        lista.add(ing2);
        return lista;
    }

    private Usuario dadoQueTengoUnUsuario(String damian, String spizzirri, String s, String s1) {
        Usuario usuario = new Usuario();
        usuario.setNombre(damian);
        usuario.setApellido(spizzirri);
        usuario.setEmail(s);
        usuario.setPassword(s1);
        return usuario;
    }

    private Email dadoQueTengoUnEmailConUnPedido(Usuario user, List<Ingrediente> lista) {
        Email nuevo = new Email();
        nuevo.setUser(user);
        nuevo.setLista(lista);
        return nuevo;
    }

    private ModelAndView cuandoSeLoPasoAlControladorLaDistancia(FormularioDestino fd, HttpServletRequest request) {
        return this.controladorDestino.irASeleccionDestino(fd, request);
    }

    private Float dadoQueTengoUnaDistanciaEnMtrs(Float i) {
        return i;
    }
}

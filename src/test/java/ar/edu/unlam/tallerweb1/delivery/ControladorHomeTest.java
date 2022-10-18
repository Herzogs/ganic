package ar.edu.unlam.tallerweb1.delivery;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorHomeTest {

    private ControladorHome controladorDeHome;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void init() {
        this.controladorDeHome = new ControladorHome();
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(this.session);
    }

    @Test
    public void queAlIngresarEnLaUrlPorDefectoNosEnvieAHome() {
        String vistaEsperada = dadoQueEsperoLaVistaLlamada("redirect:/home");
        ModelAndView model = cuandoEjecuteElMetodoIrAlInicio();
        entoncesVerificoQueSeanIguales(vistaEsperada,model);
    }

    @Test
    public void queAlSeleccionarElLinkNosotrosNosEnvieANosotros() {
        String vistaEsperada = dadoQueEsperoLaVistaLlamada("nosotros");
        ModelAndView model = cuandoEjecuteElMetodoIrANosotros();
        assertThat(model.getViewName()).isEqualTo(vistaEsperada);
    }

    @Test
    public void queAlSeleccionarElLinkContactoNosEnvieAContacto() {
        String vistaEsperada = dadoQueEsperoLaVistaLlamada("contacto");
        ModelAndView model = cuandoEjecuteElMetodoIrAContacto();
        assertThat(model.getViewName()).isEqualTo(vistaEsperada);
    }

    // TODO revisar request set
/*
    @Test
    public void queAlSeleccionarElLinkSalirDelPerfilMeEnvieAlHome() {
        String vistaEsperada = dadoQueEsperoLaVistaLlamada("redirect:/home");

        when( this.request.getSession().setAttribute("id",null)).thenReturn(null);
        ModelAndView model = this.controladorDeHome.salirSession(request);

        assertThat(model.getViewName()).isEqualTo(vistaEsperada);
    }
*/


    private String dadoQueEsperoLaVistaLlamada(String esperada) {
        return esperada;
    }
    private void entoncesVerificoQueSeanIguales(String vista, ModelAndView model) {
        assertThat(model.getViewName()).isEqualTo(vista);
    }
    private ModelAndView cuandoEjecuteElMetodoIrAlInicio() {
        return this.controladorDeHome.inicio();
    }
    private ModelAndView cuandoEjecuteElMetodoIrANosotros() {
        return this.controladorDeHome.nosotros();
    }
    private ModelAndView cuandoEjecuteElMetodoIrAContacto() {
        return this.controladorDeHome.contacto();
    }


}

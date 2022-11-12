package ar.edu.unlam.tallerweb1.domain.Scheduler;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SchedulerTaskTest extends SpringTest {

    private SchedulerTask schedulerTask;
    private ServicioEmail servicioEmail;
    
    private ServicioCompra servicioCompra;

    @Before
    public void init(){
        this.servicioCompra = mock(ServicioCompra.class);
        this.schedulerTask = new SchedulerTask();
        this.servicioEmail = mock(ServicioEmail.class);
    }

    @Test
    public void probarQueCuandoFalten5minutosAntesDeLLegarElPedidoElSistemaTeEnvieUnaNotificacionAlEMail() throws CompraNoEncontradaExeption {
        Sandwich snd = dadoQueTengoUnSandwich();
        Compra cmp = dadoQueTengoUnaCompra(snd);
        cuandoLePidaAlServicioDeCompraQueDevuelaUnaListaDeComprasEnEstadoDePReparacion(cmp);
        this.schedulerTask.EnvioDeEmailCuandoFalten5Minutos();
        verify(this.servicioEmail,times(1)).sendEmail("test","test","test");
    }

    private void cuandoLePidaAlServicioDeCompraQueDevuelaUnaListaDeComprasEnEstadoDePReparacion(Compra snd) throws CompraNoEncontradaExeption {
        List<Compra> compraList = new ArrayList<>();
        compraList.add(snd);
        when(this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PREPARACION)).thenReturn(compraList);
    }

    private Sandwich dadoQueTengoUnSandwich() {
        Sandwich s1 = new Sandwich();
        s1.setEsApto("SinRestriccion");
        s1.setDescripcion("test");
        s1.setNombre("test");
        s1.setEnPromocion(false);
        s1.setIngrediente(null);
        return s1;
    }

    private Compra dadoQueTengoUnaCompra(Sandwich snd) {
        Compra nueva = new Compra();
        nueva.setCliente(new Usuario("test@test.com","123"));
        nueva.setEstado(EstadoDeCompra.PREPARACION);
        LocalDateTime aux = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        nueva.setFecha(aux);
        nueva.setFechaEntrega(aux.plusMinutes(10));
        List<Sandwich> l = new ArrayList<>();
        l.add(snd);
        nueva.setDetalle(l);
        return nueva;
    }
}

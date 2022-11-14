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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SchedulerTaskTest extends SpringTest {

    @Autowired
    private SchedulerTask schedulerTask;
    private ServicioEmail servicioEmail;
    
    private ServicioCompra servicioCompra;

    private Sandwich snd;
    private Compra cmp;

    @Before
    public void init(){
        this.servicioCompra = mock(ServicioCompra.class);
        this.servicioEmail = new ServicioEmailImp();
        snd = dadoQueTengoUnSandwich();
        cmp = dadoQueTengoUnaCompra(snd);
    }

    @Test
    public void probarQueCuandoFalten5minutosAntesDeLLegarElPedidoElSistemaTeEnvieUnaNotificacionAlEMail() throws CompraNoEncontradaExeption {
        agrego5minutosALaHoraEstimadaDeEntrga();
        cuandoLePidaAlServicioDeCompraQueDevuelaUnaListaDeComprasEnEstadoDePReparacion();
        cuandoLlamoAlSchedulerParaQueAviseAlClienteQueSuPedidoEstaPorLlegar();
        entoncesVerificoQueSeLeEnvioLaNotificacion();
    }

    private void entoncesVerificoQueSeLeEnvioLaNotificacion() {
        assertThat(this.servicioEmail.sendEmail(cmp.getCliente().getEmail(),"Envio de Pedido", "Ya se le enviara el pedido")).isTrue();
    }

    private void cuandoLlamoAlSchedulerParaQueAviseAlClienteQueSuPedidoEstaPorLlegar() {
        this.schedulerTask.EnvioDeEmailCuandoFalten5Minutos();
    }

    private void agrego5minutosALaHoraEstimadaDeEntrga() {
        cmp.setFechaEntrega(cmp.getFechaEntrega().plusMinutes(5));
    }

    @Test
    public void probarQueCuandoSeaLaHoraDeEntregaElPedidoPaseAEstadoDeEntregado() throws CompraNoEncontradaExeption {
        cuandoLePidaAlServicioDeCompraQueDevuelaUnaListaDeComprasEnEstadoDePReparacion();
        cuandoLlamoAlServicioDeCompraParaQueActualizeElEstadoDeUnaCompraAEntregado();
        cuandoLlamaAlSchedulerParaQueverifiqueSiAlgunaCompraSePuedaEntregar();
        entoncesVerificoQueSiSePudoActualizarElEstadoDeLaCompra();
    }

    private void entoncesVerificoQueSiSePudoActualizarElEstadoDeLaCompra() throws CompraNoEncontradaExeption {
        assertThat(this.servicioCompra.entregarCompra(cmp.getIdCompra())).isTrue();
    }

    private void cuandoLlamaAlSchedulerParaQueverifiqueSiAlgunaCompraSePuedaEntregar() {
        this.schedulerTask.actualizarEstadoDeEntrega();
    }

    private void cuandoLlamoAlServicioDeCompraParaQueActualizeElEstadoDeUnaCompraAEntregado() throws CompraNoEncontradaExeption {
        when(this.servicioCompra.entregarCompra(cmp.getIdCompra())).thenReturn(true);
    }

    private void cuandoLePidaAlServicioDeCompraQueDevuelaUnaListaDeComprasEnEstadoDePReparacion() throws CompraNoEncontradaExeption {
        List<Compra> compraList = new ArrayList<>();
        compraList.add(cmp);
        when(this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PREPARACION)).thenReturn(compraList);
    }

    private Sandwich dadoQueTengoUnSandwich() {
        Sandwich s1 = new Sandwich();
        s1.setIdSandwich(1L);
        s1.setEsApto("SinRestriccion");
        s1.setDescripcion("test");
        s1.setNombre("test");
        s1.setEnPromocion(false);
        s1.setIngrediente(null);
        return s1;
    }

    private Compra dadoQueTengoUnaCompra(Sandwich snd) {
        Compra nueva = new Compra();
        nueva.setIdCompra(1L);
        nueva.setCliente(new Usuario("test@test.com","123"));
        nueva.setEstado(EstadoDeCompra.PREPARACION);
        LocalDateTime aux = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        nueva.setFecha(aux);
        nueva.setFechaEntrega(aux);
        List<Sandwich> l = new ArrayList<>();
        l.add(snd);
        nueva.setDetalle(l);
        return nueva;
    }
}

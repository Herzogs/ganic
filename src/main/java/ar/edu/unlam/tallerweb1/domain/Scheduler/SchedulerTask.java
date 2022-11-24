package ar.edu.unlam.tallerweb1.domain.Scheduler;


import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class SchedulerTask {

    @Autowired
    private ServicioCompra servicioCompra;
    private static final Logger log = Logger.getLogger(SchedulerTask.class);

    private static final ServicioEmail servicioEmail = new ServicioEmailImp();

    @Async
    @Scheduled(cron = "0 */5 * ? * *", zone = "America/Buenos_Aires")
    public void EnvioDeEmailCuandoFalten5Minutos() {
        List<Compra> compraList = null;
        try {
            compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PREPARACION);
            LocalDateTime actual = LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).withNano(0);
            for (Compra compra: compraList)  {
                LocalDateTime venc = compra.getFechaEntrega().minusMinutes(5);
                Long minutes = actual.until(venc, ChronoUnit.MINUTES);
                log.info("FECHA DE AVISO: " + venc + " , DEL PEDIDO: " + compra.getIdCompra() + " , FECHA ACTUAL: " + actual + " CANTIDAD DE MINUTOS FALTANTES: " + minutes );
                if (minutes >= 0 && minutes <= 5) {
                    servicioEmail.sendEmail(compra.getUsuario().getEmail(), "Su pedido esta en camino", "En breve estará llegando nuestro repartidor, por favor presta atención y disfruta de tu compra!");
                    log.info(String.format("Enviando email a %s", compra.getUsuario().getEmail()));
                }
            }
        } catch (CompraNoEncontradaExeption e) {
            log.info("No hay entregas en estado pendientes en la base de datos");
        }
    }

    @Async
    @Scheduled(cron = "0 * * ? * *", zone = "America/Buenos_Aires")
    public void actualizarEstadoDeEntrega() {
        List<Compra> compraList = null;
        try {
            compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PREPARACION);
            LocalDateTime actual = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
            for (Compra comp : compraList) {
                LocalDateTime fech1=actual.withNano(0).withSecond(0);
                LocalDateTime fech2=comp.getFechaEntrega().withNano(0).withSecond(0);
                if (fech1.isAfter(fech2)) {
                    this.servicioCompra.entregarCompra(comp.getIdCompra());
                    log.info("ENTREGANDO EL PEDIDO: " + comp.getIdCompra());
                }
            }

        } catch (CompraNoEncontradaExeption e) {
            log.info("No hay pedidos en espera en la cola");
        }
    }
}

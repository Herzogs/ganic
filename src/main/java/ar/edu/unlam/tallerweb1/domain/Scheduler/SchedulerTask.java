package ar.edu.unlam.tallerweb1.domain.Scheduler;


import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import io.github.cdimascio.dotenv.Dotenv;
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

    private static final Dotenv dotenv = Dotenv.load();

    private static final ServicioEmail servicioEmail = new ServicioEmailImp();



    @Async
    @Scheduled(cron = "0 * * ? * *", zone = "America/Buenos_Aires")
    public void actualizarEstadoDeEntregaDePreparacionAEnCurso() {
        List<Compra> compraList = null;
        final Integer MINUTO_PARA_PASAR_A_ENCURSO = Integer.valueOf(dotenv.get("MINUTO_PARA_PASAR_A_ENCURSO"));
        try {
            compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PREPARACION);
            LocalDateTime actual = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
            for (Compra comp : compraList) {
                LocalDateTime fechaSistema=actual.withNano(0).withSecond(0);
                LocalDateTime fechaCompra = comp.getFecha().withNano(0).withSecond(0);
                log.warn(fechaSistema + " - " + fechaCompra + " - " + "minutos: "+ fechaCompra.until(fechaSistema,ChronoUnit.MINUTES));
                if(fechaCompra.until(fechaSistema,ChronoUnit.MINUTES) == MINUTO_PARA_PASAR_A_ENCURSO) {
                    log.info("ENTRANDO EN LA COPRA");
                    this.servicioCompra.compraEnCurso(comp.getIdCompra(), EstadoDeCompra.ENCURSO);
                }
            }

        } catch (CompraNoEncontradaExeption e) {
            log.info("No hay pedidos en espera en la cola");
        }
    }

    @Async
    @Scheduled(cron = "0 * * ? * *", zone = "America/Buenos_Aires")
    public void EnvioDeEmailCuandoFalten5Minutos() {
        List<Compra> compraList = null;
        Boolean envioCorrecto = false;
        try {
            compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.ENCURSO);
            LocalDateTime actual = LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).withNano(0);
            Long max_minutes = Long.valueOf(dotenv.get("SCHEDULER_MAX_MINUTES")); //5
            for (Compra compra: compraList)  {
                LocalDateTime fechaEntrega = compra.getFechaEntrega();
                Long minutes = actual.until(fechaEntrega, ChronoUnit.MINUTES);
                log.info("FECHA DE AVISO: " + fechaEntrega + " , DEL PEDIDO: " + compra.getIdCompra() + " , FECHA ACTUAL: " + actual + " CANTIDAD DE MINUTOS FALTANTES: " + minutes );
                if ((minutes == max_minutes) || (minutes <= 0) && envioCorrecto == false) {
                    servicioEmail.sendEmail(compra.getUsuario().getEmail(), "Su pedido esta en camino", "En breve estará llegando nuestro repartidor, por favor presta atención y disfruta de tu compra!");
                    log.info(String.format("Enviando email a %s, sobre la compra %d", compra.getUsuario().getEmail(),compra.getIdCompra()));
                    envioCorrecto = true;
                }
            }
        } catch (CompraNoEncontradaExeption e) {
            log.info("No hay entregas en estado pendientes en la base de datos");
        }
    }

    @Async
    @Scheduled(cron = "0 * * ? * *", zone = "America/Buenos_Aires")
    public void actualizarEstadoDeEntregaEnCursoAEntregado() {
        List<Compra> compraList = null;
        try {
            compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.ENCURSO);
            LocalDateTime actual = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
            for (Compra comp : compraList) {
                LocalDateTime fech1=actual.withNano(0).withSecond(0); //Sistema
                LocalDateTime fech2=comp.getFechaEntrega().withNano(0).withSecond(0); // Entrega
                log.warn(fech1 + " - " + fech2 + " - " + "minutos: "+ fech2.until(fech1,ChronoUnit.MINUTES));
                Long diferenciaEntreFechas=fech1.until(fech2,ChronoUnit.MINUTES);
                log.info("Diferencia: " + diferenciaEntreFechas);
                if (diferenciaEntreFechas <= 0) {
                    this.servicioCompra.entregarCompra(comp.getIdCompra());
                    log.info("ENTREGANDO EL PEDIDO: " + comp.getIdCompra());
                }
            }

        } catch (CompraNoEncontradaExeption e) {
            log.info("No hay pedidos en espera en la cola");
        }
    }


}

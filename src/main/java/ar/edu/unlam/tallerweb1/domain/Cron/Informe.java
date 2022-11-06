package ar.edu.unlam.tallerweb1.domain.Cron;


import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class Informe {

    @Autowired
    private ServicioCompra servicioCompra;
    private static final Logger log = Logger.getLogger(Informe.class);

    private static final ServicioEmail servicioEmail = new ServicioEmailImp();

    @Async
    @Scheduled(cron = "0/5 * * * * *",zone = "America/Buenos_Aires")
    public void informeEmail(){
        List<Compra> compraList = null;
        try {
            compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PEDIDO);
            LocalDateTime actual = LocalDateTime.now();
            for (Compra comp : compraList) {
                LocalDateTime venc = comp.getFecha().plusMinutes(5);
                log.info("HORA DE ENTREGA: " + venc + " , DEL PEDIDO: "+ comp.getIdCompra());
                Long minutes = actual.until(venc, ChronoUnit.MINUTES);
                log.info("CANTIDAD DE MINUTOS FALTANTES: " + minutes + ", DEL PEDIDO: "+ comp.getIdCompra());
                if (minutes == 0) {
                    servicioEmail.sendEmail(comp.getUsuario().getEmail(), "Envio de Pedido", "Ya se le enviara el pedido");
                    log.info(String.format("Enviando email a %s", comp.getUsuario().getEmail()));
                }
            }
        }catch (CompraNoEncontradaExeption e){
            log.info("No hay entregas en estado pendientes en la base de datos");
        }
    }

    @Scheduled(cron = "1 * * * * *",zone = "America/Buenos_Aires")
    @Async
    public void entregarCompra(){
        List<Compra> compraList = null;
        try {
            compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PEDIDO);
            LocalDateTime actual = LocalDateTime.now();
            for (Compra comp : compraList) {
                if (actual.withNano(0).withSecond(0).equals(comp.getFechaEntrega())) {
                    this.servicioCompra.entregarCompra(comp.getIdCompra());
                    log.info("ENTREGANDO EL PEDIDO: "+ comp.getIdCompra());
                }
            }
        }catch (CompraNoEncontradaExeption e){
            log.info("No hay pedidos en espera en la cola");
        }
    }
}

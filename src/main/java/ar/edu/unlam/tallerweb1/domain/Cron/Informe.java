package ar.edu.unlam.tallerweb1.domain.Cron;


import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmail;
import ar.edu.unlam.tallerweb1.domain.Email.ServicioEmailImp;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.ServicioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class Informe {

    @Autowired
    private ServicioCompra servicioCompra;
    private static ServicioEmail servicioEmail;
    public static final Integer minutes = (59*60);

    static {
        servicioEmail = new ServicioEmailImp();
    }

    @Scheduled(cron = "* 0/20 * * * *", zone = "America/Buenos Aires")
    public void informeEmail(){
        List<Compra> compraList = this.servicioCompra.listarComprasPorEstado(EstadoDeCompra.PEDIDO);
        LocalDateTime actual = LocalDateTime.now();
        for (Compra comp: compraList) {
            Long minutes = comp.getFecha().until(actual, ChronoUnit.MINUTES);
            if(minutes.equals(5L))
                servicioEmail.sendEmail(comp.getUsuario().getEmail(),"Envio de Pedido","Ya se le enviara el pedido");
        }
    }
}

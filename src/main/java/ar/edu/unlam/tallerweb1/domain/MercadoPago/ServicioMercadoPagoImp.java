package ar.edu.unlam.tallerweb1.domain.MercadoPago;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImp implements ServicioMercadoPago{

    private RepositorioCarrito repositorioCarrito;

    @Autowired

    public ServicioMercadoPagoImp(RepositorioCarrito repositorioCarrito) {
        this.repositorioCarrito = repositorioCarrito;
    }
}

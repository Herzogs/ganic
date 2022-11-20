package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CarroInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioCarrito")
@Transactional
public class ServicioCarroImp implements ServicioCarro {
    private RepositorioCarro repo;

    @Autowired
    public ServicioCarroImp(RepositorioCarro repo) {
        this.repo = repo;
    }

    @Override
    public void guardarCarro(Carro carro) {
        repo.guardarCarro(carro);
    }


    @Override
    public Carro obtenerCarro(Long idCarro) throws CarroInexistenteExeption {
        Carro carro = repo.obtenerCarro(idCarro);
        if (carro == null) {
            throw new CarroInexistenteExeption("No existe carro");
        }
        return carro;

    }

    @Override
    public Carro obtenerCarroDeCLiente(Usuario usuario) {
        Carro carro = repo.obtenerCarroCliente(usuario);
        if (carro != null) {
            return carro;
        }
        Carro nuevo = new Carro();
        nuevo.setUsuario(usuario);
        repo.guardarCarro(nuevo);
        return nuevo;
    }


//
//    public void agregarSandwich(Sandwich sandwich, Usuario usuario) {
//        Integer cantidad=1;
//        Carro carro= obtenerCarroDeCLiente(usuario);
//        List<DetalleCarro> detalleCarro= carro.getDetalleCarro();
//        for (DetalleCarro detalle:detalleCarro){
//            if(detalle.getSandwich().equals(sandwich)){
//                cantidad++;
//                detalle.setCantidad(detalle.getCantidad()+1);
//
//            }
//        }
//
//
//    }

    @Override
    public void agregarDetalleAlCarro(DetalleCarro detalleCarro1, Usuario usuario) {
        Carro carro = repo.obtenerCarroCliente(usuario);
        carro.getDetalleCarro().add(detalleCarro1);
    }


}

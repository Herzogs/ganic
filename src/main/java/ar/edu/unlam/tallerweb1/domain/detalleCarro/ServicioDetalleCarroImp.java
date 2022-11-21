package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.Excepciones.DetalleInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.NoSePudoQuitarException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("servicioDetalleCarro")
@Transactional
public class ServicioDetalleCarroImp implements ServicioDetalleCarro{
    private RepositorioDetalleCarro repo;
    @Autowired
    public ServicioDetalleCarroImp(RepositorioDetalleCarro repo) {
        this.repo= repo;
    }

    @Override
    public void guardarDetalle(DetalleCarro detalleCarro) {
      repo.guardarDetalleCarro(detalleCarro);
    }

    public Boolean incrementarCntidad( Integer cantidad, Usuario usuario, Sandwich sandwich){
        List<DetalleCarro> detalleCarros= repo.obtenerDetalleDeCarrPorUsuario(usuario);
        Boolean encontrado= false;
        for (DetalleCarro detalleCarro1:detalleCarros){
            if(detalleCarro1.getSandwich().equals(sandwich)){
                encontrado=true;
                detalleCarro1.setCantidad(detalleCarro1.getCantidad()+cantidad);
                this.repo.actualizarDetalleCarro(detalleCarro1);
            }

        }
        return encontrado;
    }

    public Boolean decrementarCantidad(Integer cantidad, Usuario usuario, Sandwich sandwich) throws NoSePudoQuitarException {
        List<DetalleCarro> detalleCarros= repo.obtenerDetalleDeCarrPorUsuario(usuario);
        Boolean encontrado= false;
        for (DetalleCarro detalleCarro1:detalleCarros){
            if(detalleCarro1.getSandwich().equals(sandwich)){
                encontrado=true;
                if(detalleCarro1.getCantidad()<=1)
                    throw new NoSePudoQuitarException("No se puede quitar");
                else {
                    detalleCarro1.setCantidad(detalleCarro1.getCantidad() - cantidad);
                    this.repo.actualizarDetalleCarro(detalleCarro1);
                }

            }
        }
        return encontrado;
    }


    @Override
    public DetalleCarro obtenerDetalle(Long idDetalle) throws DetalleInexistenteExeption {
        DetalleCarro detalleCarro= repo.obtnerDetalleCarro(idDetalle);
        if(detalleCarro!=null){
            return detalleCarro;
        }
        throw new DetalleInexistenteExeption("No existe detalle");
    }
    @Override
    public List<DetalleCarro> obtenerDetalleDeCarroDeUsuario(Usuario usuario) throws DetalleInexistenteExeption {
        List<DetalleCarro> detalleCarros =repo.obtenerDetalleDeCarrPorUsuario(usuario);
        if(!detalleCarros.isEmpty()){
            return detalleCarros;
        }
        throw new DetalleInexistenteExeption("No existe detalle");
    }

    @Override
    public void actualizarDetalle(DetalleCarro detalleCarro) {
     repo.actualizarDetalleCarro(detalleCarro);
    }

    @Override
    public void eliminarDetalle(DetalleCarro detalleCarro) {
        repo.borrarDetalleDeCarro(detalleCarro);
    }
    public void vaciarCarro(Usuario usuario){
        List<DetalleCarro>lista= repo.obtenerDetalleDeCarrPorUsuario(usuario);
        lista.forEach(detalleCarro -> repo.borrarDetalleDeCarro(detalleCarro));
    }

}

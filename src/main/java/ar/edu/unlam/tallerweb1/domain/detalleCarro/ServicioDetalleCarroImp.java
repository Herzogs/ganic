package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.Excepciones.DetalleInexistenteExeption;
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


}

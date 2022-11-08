package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("servicioCompra")
@Transactional
public class ServicioCompraImp implements ServicioCompra {

    private RepositorioCompra repo;

    @Autowired
    public ServicioCompraImp(RepositorioCompra repo) {
        this.repo = repo;
    }

    @Override
    public void guardarCompra(Compra compra) {
        repo.guardarCompra(compra);
    }

    @Override
    public void eliminarCompra(Long idCompra) {

    }

    @Override
    public Compra buscarCompra(Long idCompra) throws CompraNoEncontradaExeption {
        Compra buscado = repo.buscarCompra(idCompra);
        if (buscado != null) {
            return buscado;
        }
        throw new CompraNoEncontradaExeption("No se pudo encontrar la compra");

    }

    @Override
    public List<Compra> buscarComprasPorUsuario(Usuario usuario) throws CompraNoEncontradaExeption {
        List<Compra> buscado = repo.buscarCompraPorCliente(usuario);
        if (buscado.size() == 0) {
            throw new CompraNoEncontradaExeption("No se pudo encontrar la compra");

        }
        return buscado;
    }

    @Override
    public List<Compra> listarComprasDeUsuarioPorEstado(Usuario usuario, EstadoDeCompra estado) throws CompraNoEncontradaExeption {
        List<Compra> buscado = repo.buscarPorEstado(usuario, estado);
        if (buscado.size() == 0) {
            throw new CompraNoEncontradaExeption("No se pudo encontrar la compra");
        }
        return buscado;
    }

    @Override
    public void cancelarCompra(Compra compra, EstadoDeCompra estadoDeCompra) {
        compra.setEstado(estadoDeCompra);
        this.repo.actualizarCompra(compra);
    }


    @Override
    public List<Compra> listarComprasPorEstado(EstadoDeCompra estadoDeCompra) throws CompraNoEncontradaExeption {
        List<Compra> list = this.repo.obtenerCompraPorEstado(EstadoDeCompra.PREPARACION);
        if (list.isEmpty())
            throw new CompraNoEncontradaExeption("No hay compra en marcha");
        return list;
    }

    @Override
   public void actualizarCompra(Compra compra){
        repo.actualizarCompra(compra);

    }

    @Override
    public void entregarCompra(Long idCompra) throws CompraNoEncontradaExeption {
        Compra compra = this.repo.buscarCompra(idCompra);
        if (compra == null)
            throw new CompraNoEncontradaExeption("No existe la compra");
        compra.setEstado(EstadoDeCompra.ENTREGADO);
        this.repo.actualizarCompra(compra);
    }

    @Override
    public List<Compra> listarTodasLasCompras(Long idUsuario) throws CompraNoEncontradaExeption {
       List<Compra> lista= repo.buscarCompraPorCliente(idUsuario);
       if(lista.isEmpty())
           throw  new CompraNoEncontradaExeption("No existe compra");
       return lista;
    }
}

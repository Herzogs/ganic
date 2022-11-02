package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
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
        List<Compra> buscado = repo.buscarPorEstado(usuario,estado);
        if (buscado.size() == 0) {
            throw new CompraNoEncontradaExeption("No se pudo encontrar la compra");
        }
        return buscado;
    }

    @Override
    public void agregarSandwich(Sandwich sandwich) {

    }

    @Override
    public void eliminarSandwich(Sandwich sandwich) {

    }
}

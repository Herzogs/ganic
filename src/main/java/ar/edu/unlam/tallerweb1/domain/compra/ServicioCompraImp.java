package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import ar.edu.unlam.tallerweb1.infrastructure.RepositorioCompraImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServicioCompraImp implements ServicioCompra{

    private RepositorioCompraImp repo;
    @Autowired
    public ServicioCompraImp(RepositorioCompraImp repo) {
        this.repo = repo;
    }

    @Override
    public void guardarCompra(Usuario usuario, Set<Sandwich> detalle) {

    }

    @Override
    public void eliminarCompra(Long idCompra) {

    }

    @Override
    public Compra buscarCompra(Long idCompra) {
        return null;
    }

    @Override
    public List<Compra> buscarComprasPorUsuario(Long idUsuario) {
        return null;
    }
}

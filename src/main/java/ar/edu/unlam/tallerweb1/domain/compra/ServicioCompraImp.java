package ar.edu.unlam.tallerweb1.domain.compra;

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
    public Compra buscarCompra(Long idCompra) {
        return repo.buscarCompra(idCompra);

    }

    @Override
    public List<Compra> buscarComprasPorUsuario(Long idUsuario) {
        return null;
    }

    @Override
    public void agregarSandwich(Sandwich sandwich) {

    }

    @Override
    public void eliminarSandwich(Sandwich sandwich) {

    }
}

package ar.edu.unlam.tallerweb1.domain.Sandwich;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioSandwichImp implements ServicioSandwich {

    private RepositorioSandwich repositorioSandwich;

    @Autowired
    public ServicioSandwichImp(RepositorioSandwich repo) {
        this.repositorioSandwich = repo;
    }

    @Override
    public Sandwich obtenerSandwichPorId(Long id) {
        return this.repositorioSandwich.obtenerSandwichPorId(id);
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwiches() {
        return this.repositorioSandwich.obtenerTodosLosSandwiches();
    }
}

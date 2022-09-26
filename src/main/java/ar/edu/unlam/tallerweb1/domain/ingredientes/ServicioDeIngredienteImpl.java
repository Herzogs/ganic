package ar.edu.unlam.tallerweb1.domain.ingredientes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service ("ServicioDeIngrediente")
@Transactional
public class ServicioDeIngredienteImpl implements ServicioDeIngrediente {

    private RepositorioIngredientes repo;

    @Autowired
    public ServicioDeIngredienteImpl(RepositorioIngredientes repo) {
        this.repo = repo;
    }

    @Override
    public List<Ingrediente> obtenerIngredientesPorPaso(Integer paso) {
        return this.repo.obtenerIngredientePorPaso(paso);
    }

    @Override
    public Ingrediente obtenerIngredientePorId(Long id) {
        return this.repo.obtenerIngredientePorId(id);
    }

    @Override
    public List<Ingrediente> obtenerTodosLosIngredientes() {
        return this.repo.obtenerIngrediente();
    }
}

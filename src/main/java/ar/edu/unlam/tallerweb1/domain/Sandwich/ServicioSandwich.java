package ar.edu.unlam.tallerweb1.domain.Sandwich;

import java.util.List;

public interface ServicioSandwich {

    public Sandwich obtenerSandwichPorId(Long id);
    public List<Sandwich> obtenerTodosLosSandwiches();
}

package ar.edu.unlam.tallerweb1.domain.Sandwich;

import java.util.List;

public interface RepositorioSandwich {

    Sandwich obtenerSandwichPorId(Long id);

    List<Sandwich> obtenerTodosLosSandwiches();

    List<Sandwich> obtenerTodosLosSandwitchEnPromocion();

    List<Sandwich> obtenerTodosLosSandwitchPorPreferencia(String pref);

}

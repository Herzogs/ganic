package ar.edu.unlam.tallerweb1.domain.ingredientes;

import java.util.List;

public interface ServicioDeIngrediente {

    List<Ingrediente> obtenerIngredientesPorPaso(Integer paso);
    Ingrediente obtenerIngredientePorId(Long id);
}

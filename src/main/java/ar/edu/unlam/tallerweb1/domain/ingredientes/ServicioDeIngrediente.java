package ar.edu.unlam.tallerweb1.domain.ingredientes;

import ar.edu.unlam.tallerweb1.domain.Excepciones.IngredienteInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.PasoInvalidoException;

import java.util.List;

public interface ServicioDeIngrediente {

    List<Ingrediente> obtenerIngredientesPorPaso(Integer paso) throws PasoInvalidoException;
    Ingrediente obtenerIngredientePorId(Long id) throws IngredienteInvalidoException;

    List<Ingrediente> obtenerTodosLosIngredientes();
    List<Ingrediente> obtenerIngredienteSiEsApto(String esApto);

    List<Ingrediente> obtenerIngredientesFiltradoPorPasoYPreferencia(Integer paso, String preferencia);
}

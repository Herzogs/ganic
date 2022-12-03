package ar.edu.unlam.tallerweb1.domain.ingredientes;

import java.util.List;

public interface RepositorioIngredientes {

    public List<Ingrediente> obtenerIngrediente();
    public Ingrediente obtenerIngredientePorId(Long id);
    public List<Ingrediente> obtenerIngredientePorNombre (String desc);
    public void guardarIngrediente(Ingrediente ing);
    List<Ingrediente> obtenerIngredientePorPaso(Integer paso);
    List<Ingrediente> obtenerIngredienteSiEsApto(String esApto);

    List<Ingrediente> obtenerIngredientesPorPasoYPorPreferencia(Integer paso, String preferencia);
}

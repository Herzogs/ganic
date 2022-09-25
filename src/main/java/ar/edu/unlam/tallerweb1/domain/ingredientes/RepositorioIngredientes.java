package ar.edu.unlam.tallerweb1.domain.ingredientes;

import java.util.List;

public interface RepositorioIngredientes {

    public List<Ingrediente> obtenerIngrediente();
    public Ingrediente obtenerIngredientePorId(Long id);
    public Ingrediente obtenerIngredientePorNombre (String n);
    public void guardarIngrediente(Ingrediente ing);
}

package ar.edu.unlam.tallerweb1.domain.Sandwich;

import ar.edu.unlam.tallerweb1.domain.Excepciones.NoHaySandwichEnPromocionException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;

import java.util.List;

public interface ServicioSandwich {

    public Sandwich obtenerSandwichPorId(Long id) throws SandwichNoExistenteException;
    public List<Sandwich> obtenerTodosLosSandwiches() throws SandwichNoExistenteException;
    public List<Sandwich> obtenerTodosLosSandwichesEnPromocion() throws NoHaySandwichEnPromocionException;
    public List<Sandwich> obtenerTodosLosSandwichesDeUnTipo(String pref) throws SandwichNoExistenteException;

    public List<Ingrediente> obtenerLosIngredientesDeUnSandwich(Long id) throws SandwichNoExistenteException;

    public void guardarSandwich(Sandwich sandwich);
}

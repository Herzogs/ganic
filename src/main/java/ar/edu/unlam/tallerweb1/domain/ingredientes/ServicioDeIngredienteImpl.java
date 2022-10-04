package ar.edu.unlam.tallerweb1.domain.ingredientes;

import ar.edu.unlam.tallerweb1.domain.Excepciones.IngredienteInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.PasoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("servicioDeIngrediente")
@Transactional

public class ServicioDeIngredienteImpl implements ServicioDeIngrediente {

	private RepositorioIngredientes repo;

	@Autowired
	public ServicioDeIngredienteImpl(RepositorioIngredientes repo) {
		this.repo = repo;
	}

	@Override
	public List<Ingrediente> obtenerIngredientesPorPaso(Integer paso) throws PasoInvalidoException {
		List<Ingrediente> lista = this.repo.obtenerIngredientePorPaso(paso);
		if(lista.isEmpty())
			throw new PasoInvalidoException("No existe paso");
		return lista;
	}

	@Override
	public Ingrediente obtenerIngredientePorId(Long id) throws IngredienteInvalidoException {
		Ingrediente ing = this.repo.obtenerIngredientePorId(id);
		if (ing == null)
			throw new IngredienteInvalidoException("No Existe El Ingrediente solicitado");
		return ing;
	}

	@Override
	public List<Ingrediente> obtenerTodosLosIngredientes() {
		return this.repo.obtenerIngrediente();
	}
}

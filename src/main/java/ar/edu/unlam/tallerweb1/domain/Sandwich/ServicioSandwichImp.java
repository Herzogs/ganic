package ar.edu.unlam.tallerweb1.domain.Sandwich;

import ar.edu.unlam.tallerweb1.domain.Excepciones.NoHaySandwichEnPromocionException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.SandwichNoExistenteException;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
/*@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)*/
@Transactional(Transactional.TxType.REQUIRED)
public class ServicioSandwichImp implements ServicioSandwich {

    private RepositorioSandwich repositorioSandwich;

    @Autowired
    public ServicioSandwichImp(RepositorioSandwich repo) {
        this.repositorioSandwich = repo;
    }

    @Override
    public Sandwich obtenerSandwichPorId(Long id) throws SandwichNoExistenteException {
        Sandwich obtenido = this.repositorioSandwich.obtenerSandwichPorId(id);
        if(obtenido == null)
            throw new SandwichNoExistenteException("No existe el sandwich");
        return obtenido;
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwiches() throws SandwichNoExistenteException {
        List<Sandwich> lista = this.repositorioSandwich.obtenerTodosLosSandwiches();
        if(lista.isEmpty())
            throw new SandwichNoExistenteException("No hay sandwiches Disponibles");
        return lista;
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwichesEnPromocion() throws NoHaySandwichEnPromocionException {
        List<Sandwich> lista = this.repositorioSandwich.obtenerTodosLosSandwitchEnPromocion();
        if(lista.isEmpty())
            throw new NoHaySandwichEnPromocionException("No hay sandwiches en promocion");
        return lista;
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwichesDeUnTipo(String pref) throws SandwichNoExistenteException {
        List<Sandwich> obtenido = this.repositorioSandwich.obtenerTodosLosSandwitchPorPreferencia(pref);
        if(obtenido.isEmpty())
            throw new SandwichNoExistenteException("No existen sandwiches disponibles con esa preferencia");
        return obtenido;
    }

    @Override
    public List<Ingrediente> obtenerLosIngredientesDeUnSandwich(Long id) throws SandwichNoExistenteException {
        Sandwich obtenido = this.repositorioSandwich.obtenerSandwichPorId(id);
        if(obtenido == null)
            throw new SandwichNoExistenteException("No existe el sandwich");
        return obtenido.getIngrediente().stream().collect(Collectors.toCollection(ArrayList::new)); //convierte de Set a List
    }

}

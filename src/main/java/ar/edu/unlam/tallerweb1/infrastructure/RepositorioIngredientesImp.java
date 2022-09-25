package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.RepositorioIngredientes;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositorioIngredientesImp implements RepositorioIngredientes {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioIngredientesImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Ingrediente> obtenerIngrediente() {
        return (List<Ingrediente>) sessionFactory.getCurrentSession().createCriteria(Usuario.class).list();
    }

    @Override
    public Ingrediente obtenerIngredientePorId(Long id) {
        return (Ingrediente) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

    }

    @Override
    public Ingrediente obtenerIngredientePorNombre(String n) {
        return null;
    }

    @Override
    public void guardarIngrediente(Ingrediente ing) {

    }
}

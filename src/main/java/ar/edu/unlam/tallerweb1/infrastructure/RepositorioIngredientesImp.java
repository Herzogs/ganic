package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import ar.edu.unlam.tallerweb1.domain.ingredientes.RepositorioIngredientes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository("repositorioIngredientes")
public class RepositorioIngredientesImp implements RepositorioIngredientes {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioIngredientesImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Ingrediente> obtenerIngrediente() {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Ingrediente>) session.createCriteria(Ingrediente.class).list();
    }

    @Override
    public Ingrediente obtenerIngredientePorId(Long id) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (Ingrediente) session.createCriteria(Ingrediente.class)
                .add(Restrictions.eq("idIngrediente", id))
                .uniqueResult();

    }

    @Override
    public List<Ingrediente> obtenerIngredientePorNombre(String desc) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Ingrediente>) session.createCriteria(Ingrediente.class)
                .add(Restrictions.eq("nombre", desc))
                .list();
    }

    @Override
    public void guardarIngrediente(Ingrediente ing) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(ing);
    }

    @Override
    public List<Ingrediente> obtenerIngredientePorPaso(Integer paso) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Ingrediente>) session.createCriteria(Ingrediente.class)
                .add(Restrictions.eq("paso",paso))
                .list();
    }

    @Override
    public List<Ingrediente> obtenerIngredienteSiEsApto(String esApto) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Ingrediente>) session.createCriteria(Ingrediente.class)
                .add(Restrictions.eq("esApto",esApto))
                .list();
    }

    @Override
    public List<Ingrediente> obtenerIngredientesPorPasoYPorPreferencia(Integer paso, String preferencia) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Ingrediente>) session.createCriteria(Ingrediente.class)
                .add(Restrictions.eq("esApto",preferencia))
                .add(Restrictions.eq("paso",paso))
                .list();
    }
}

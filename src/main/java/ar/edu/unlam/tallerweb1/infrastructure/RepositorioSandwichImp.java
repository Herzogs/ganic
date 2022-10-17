package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.Sandwich.RepositorioSandwich;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioSandwichImp implements RepositorioSandwich {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioSandwichImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Sandwich obtenerSandwichPorId(Long id) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (Sandwich) session.createCriteria(Sandwich.class)
                .setFetchMode("idSandwich", FetchMode.JOIN)
                .add(Restrictions.eq("idSandwich", id))
                .uniqueResult();
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwiches() {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Sandwich>) session.createCriteria(Sandwich.class)
                .setFetchMode("idSandwich", FetchMode.JOIN)
                .list();
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwitchEnPromocion() {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Sandwich>) session.createCriteria(Sandwich.class)
                .setFetchMode("idSandwich", FetchMode.JOIN)
                .add(Restrictions.eq("enPromocion",true))
                .list();
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwitchPorPreferencia(String pref) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<Sandwich>) session.createCriteria(Sandwich.class)
                .setFetchMode("idSandwich", FetchMode.JOIN)
                .add(Restrictions.eq("esApto",pref))
                .list();
    }
}

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

import java.util.*;

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
        List<Sandwich> lista=  (List<Sandwich>) session.createCriteria(Sandwich.class)
                .setFetchMode("idSandwich", FetchMode.JOIN)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        return lista;
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwitchEnPromocion() {
        final Session session = this.sessionFactory.getCurrentSession();
        List<Sandwich> lista= (List<Sandwich>) session.createCriteria(Sandwich.class)
                .setFetchMode("idSandwich",FetchMode.JOIN)
                .add(Restrictions.eq("enPromocion",true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        return lista;
    }

    @Override
    public List<Sandwich> obtenerTodosLosSandwitchPorPreferencia(String pref) {
        final Session session = this.sessionFactory.getCurrentSession();
        List<Sandwich> lista=  (List<Sandwich>) session.createCriteria(Sandwich.class)
                .setFetchMode("idSandwich", FetchMode.JOIN)
                .add(Restrictions.eq("esApto",pref))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        return lista;
    }

    @Override
    public void guardarSandwich(Sandwich sandwich) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.save(sandwich);
    }
}
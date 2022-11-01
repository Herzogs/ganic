package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.RepositorioCompra;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RepositorioCompraImp implements RepositorioCompra {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioCompraImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void eliminarCompra(Compra compra) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.delete(compra);
    }

    @Override
    public Compra buscarCompra(Long idCompra) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (Compra) session.createCriteria(Compra.class)
                .setFetchMode("idCompra", FetchMode.JOIN)
                .add(Restrictions.eq("idCompra", idCompra))
                .uniqueResult();

    }


    @Override
    public void guardarCompra(Compra compra) {
        sessionFactory.getCurrentSession().save(compra);
    }
}

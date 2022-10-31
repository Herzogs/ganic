package ar.edu.unlam.tallerweb1.infrastructure;


import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.RepositorioCompra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public class RepositorioCompraImp implements RepositorioCompra {
    SessionFactory sessionFactory;
    @Autowired
    public RepositorioCompraImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarCompra(Usuario usuario, Set<Sandwich> detalle) {

    }

    @Override
    public void eliminarCompra(Long idCompra) {

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
    public List<Compra> buscarComprasPorUsuario(Long idUsuario) {
        return null;
    }

    @Override
    public void guardarCompra(Long idCompra, Usuario usuario, Set<Sandwich> sandwich) {

        sessionFactory.getCurrentSession().save(new Compra(idCompra,usuario,sandwich));
    }
}

package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.MercadoPago.MercadoCompra;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.RepositorioMercadoCompra;
import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioMercadoCompraImp implements RepositorioMercadoCompra {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMercadoCompraImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    @Override
    public void guardarMercadoCompra(MercadoCompra mc) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(mc);
    }

    @Override
    public void eliminarMercadoCompra(MercadoCompra mc) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(mc);
    }

    @Override
    public List<MercadoCompra> obtenerMercadoCompra(Usuario user) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<MercadoCompra>)session.createCriteria(MercadoCompra.class)
                .createAlias("usuario", "usuario")
                .add(Restrictions.eq("usuario.id",user.getId()))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public MercadoCompra buscarMercadoCompra(Compra compra) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (MercadoCompra) session.createCriteria(MercadoCompra.class)
                .add(Restrictions.eq("compra",compra))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();
    }
}

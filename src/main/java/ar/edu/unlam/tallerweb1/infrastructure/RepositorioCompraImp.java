package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.compra.EstadoDeCompra;
import ar.edu.unlam.tallerweb1.domain.compra.RepositorioCompra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


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
    public List<Compra> buscarCompraPorCliente(Usuario usuario) {
        final Session session = this.sessionFactory.getCurrentSession();
        List lista = session.createCriteria(Compra.class)
                .createAlias("usuario", "usuario")
                .add(Restrictions.eq("usuario", usuario))
                .list();
        return lista;
    }

    @Override
    public List<Compra> buscarCompraPorCliente(Long idUsuario) {
        final Session session = this.sessionFactory.getCurrentSession();
        List lista = session.createCriteria(Compra.class)
                .createAlias("usuario", "usuario")
                .add(Restrictions.eq("usuario.id", idUsuario))
                .list();
        return lista;
    }

    @Override
    public void actualizoLaCompra(Compra compra) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.update(compra);
    }

    @Override
    public List<Compra> buscarPorEstado(Usuario usuario, EstadoDeCompra estado) {
        final Session session = this.sessionFactory.getCurrentSession();
        List lista = session.createCriteria(Compra.class)
                .createAlias("usuario", "usuario")
                .add(Restrictions.eq("usuario.id", usuario.getId()))
                .add(Restrictions.eq("estado", estado))
                .list();
        return lista;
    }

    @Override
    public void actualizarCompra(Compra compra) {
        this.sessionFactory.getCurrentSession().update(compra);
    }

    @Override
    public List<Compra> buscarPorEstado(Long idUsuario, EstadoDeCompra estado) {
        final Session session = this.sessionFactory.getCurrentSession();
        List lista = session.createCriteria(Compra.class)
                .createAlias("usuario", "usuario")
                .add(Restrictions.eq("usuario.id", idUsuario))
                .add(Restrictions.eq("estado", estado))
                .list();
        return lista;
    }


    @Override
    public void guardarCompra(Compra compra) {
        sessionFactory.getCurrentSession().save(compra);
    }
}

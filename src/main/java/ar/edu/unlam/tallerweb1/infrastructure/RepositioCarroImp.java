package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.carro.RepositorioCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("repositorioCarro")
@Transactional
public class RepositioCarroImp implements RepositorioCarro {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositioCarroImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarCarro(Carro carro) {
    sessionFactory.getCurrentSession().save(carro);
    }

    @Override
    public void actualizarCarro(Carro carro) {
    sessionFactory.getCurrentSession().update(carro);
    }

    @Override
    public void eliminarCarro(Carro carro) {
        sessionFactory.getCurrentSession().delete(carro);
    }

    @Override
    public Carro obtenerCarro(Long idCarro) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (Carro) session.createCriteria(Carro.class)
                .setFetchMode("idCarro", FetchMode.JOIN)
                .add(Restrictions.eq("idCarro", idCarro))
                .uniqueResult();
    }

    @Override
    public Carro obtenerCarroCliente(Usuario usuario) {
        final Session session = this.sessionFactory.getCurrentSession();
        Carro buscado = (Carro) session.createCriteria(Carro.class)
                .createAlias("usuario", "usuario")
                .add(Restrictions.eq("usuario", usuario))
                .uniqueResult();
        return buscado;
    }


}

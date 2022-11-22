package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.carro.RepositorioCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.RepositorioDetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RepositoriodDetalleCarroImp implements RepositorioDetalleCarro {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositoriodDetalleCarroImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarDetalleCarro(DetalleCarro detalleCarro) {
        sessionFactory.getCurrentSession().save(detalleCarro);
    }

    @Override
    public void actualizarDetalleCarro(DetalleCarro detalleCarro) {
        sessionFactory.getCurrentSession().update(detalleCarro);
    }



    @Override
    public DetalleCarro obtnerDetalleCarro(Long idDetalleCarro) {
        final Session session = this.sessionFactory.getCurrentSession();
        DetalleCarro detalleCarro= (DetalleCarro) session.createCriteria(DetalleCarro.class)
                .add(Restrictions.eq("idDetalleCarro",idDetalleCarro))
                .uniqueResult();


        return detalleCarro;
    }

    @Override
    public List<DetalleCarro> obtenerDetalleDeCarrPorUsuario(Usuario usuario) {
        final Session session = this.sessionFactory.getCurrentSession();
        List<DetalleCarro>  detalleCarro= (List<DetalleCarro>) session.createCriteria(DetalleCarro.class)
                .createAlias("carro", "carro")
                .add(Restrictions.eq("carro.usuario",usuario))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
        return detalleCarro;
    }


    @Override
    public void borrarDetalleDeCarro(DetalleCarro detalleDeCarro) {
        sessionFactory.getCurrentSession().delete(detalleDeCarro);
    }
}

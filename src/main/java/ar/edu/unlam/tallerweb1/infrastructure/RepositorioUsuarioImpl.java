package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.Excepciones.PassswordIncorrectoExeption;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {


	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Usuario buscarUsuario(String email, String password) throws UsuarioNoRegistradoExepcion, PassswordIncorrectoExeption {

		if (estaRegistrado(email)) {
			final Session session = sessionFactory.getCurrentSession();
			Usuario buscado= (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", email))
					.add(Restrictions.eq("password", password))
					.uniqueResult();
			if(buscado!=null){
				return buscado;
			}throw new PassswordIncorrectoExeption("Password incorrecto");
		}
		throw new UsuarioNoRegistradoExepcion("No se encuentra registrado el Usuario");

	}

	@Override
	public void guardar(Usuario usuario) {
		sessionFactory.getCurrentSession().save(usuario);
	}

	@Override
	public Usuario buscar(String email){
		Usuario buscado = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
			return buscado;

	}
	@Override
	public Usuario buscarPorId(Long id) {
		return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}


	@Override
	public void modificar(Usuario usuario) {
		sessionFactory.getCurrentSession().update(usuario);
	}

	@Override
	public Boolean estaRegistrado(String email) {
		Usuario buscado = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
		if (buscado != null) {
			return true;
		}
			return false;
		}


}

package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// implelemtacion del repositorio de usuarios, la anotacion @Repository indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.dao
// para encontrar esta clase.
@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

	// Maneja acciones de persistencia, normalmente estara inyectado el session factory de hibernate
	// el mismo esta difinido en el archivo hibernateContext.xml
	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioUsuarioImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Usuario buscarUsuario(String email, String password) throws UsuarioNoRegistradoExepcion {

		if(estaRegistrado(email)){
			final Session session = sessionFactory.getCurrentSession();
			return (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", email))
					.add(Restrictions.eq("password", password))
					.uniqueResult();
		}
		throw new UsuarioNoRegistradoExepcion("No se encuentra registrado el Usuario");


	}

	@Override
	public void guardar(Usuario usuario) {
		sessionFactory.getCurrentSession().save(usuario);
	}


	@Override
	public Usuario buscar(String email) throws UsuarioNoRegistradoExepcion {
		Usuario buscado=(Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
		if(buscado!=null) {
			return buscado;

		}
		throw new UsuarioNoRegistradoExepcion("Usuario no registrado");


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
	public Boolean estaRegistrado(String email) throws UsuarioNoRegistradoExepcion {
		Usuario usuario =buscar(email);
		if(!usuario.equals(null))
			return true;
		return false;
	}

}

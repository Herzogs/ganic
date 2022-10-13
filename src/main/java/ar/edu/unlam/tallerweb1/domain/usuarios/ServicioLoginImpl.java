package ar.edu.unlam.tallerweb1.domain.usuarios;

import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Implelemtacion del Servicio de usuarios, la anotacion @Service indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.servicios
// para encontrar esta clase.
// La anotacion @Transactional indica que se debe iniciar una transaccion de base de datos ante la invocacion de cada metodo del servicio,
// dicha transaccion esta asociada al transaction manager definido en el archivo spring-servlet.xml y el mismo asociado al session factory definido
// en hibernateCOntext.xml. De esta manera todos los metodos de cualquier dao invocados dentro de un servicio se ejecutan en la misma transaccion
@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

	private RepositorioUsuario servicioLoginDao;

	@Autowired
	public ServicioLoginImpl(RepositorioUsuario servicioLoginDao){
		this.servicioLoginDao = servicioLoginDao;
	}

	@Override
	public Usuario consultarUsuario (String email, String password) throws UsuarioNoRegistradoExepcion {
		return servicioLoginDao.buscarUsuario(email, password);
	}

	@Override
	public Usuario consultarPorID (Long id) throws UsuarioInvalidoException {
		Usuario buscado = servicioLoginDao.buscarPorId(id);
		if(buscado == null)
			throw new UsuarioInvalidoException("No Existe Usuario Con Ese ID");
		return buscado;
	}

	@Override
	public void crearUsuario(Usuario usuario) {
		this.servicioLoginDao.guardar(usuario);
	}


	public void actualizarUsuario(Usuario usuario){
		this.servicioLoginDao.modificar(usuario);
	}

	@Override
	public Usuario consultarUsuario(String email) throws UsuarioNoRegistradoExepcion {
		Usuario buscado= this.servicioLoginDao.buscar(email);
		return buscado;
	}

	@Override
	public Boolean estaRegistrado(String email) {
		return this.servicioLoginDao.estaRegistrado(email);
	}
}

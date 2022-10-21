package ar.edu.unlam.tallerweb1.domain.usuarios;

import ar.edu.unlam.tallerweb1.domain.Excepciones.LoginInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(String email, String password) throws LoginInvalidoException;
	void crearUsuario(Usuario usuario);

	void actualizarUsuario(Usuario usuario);

	Usuario consultarPorID(Long id) throws UsuarioInvalidoException;

	Usuario consultarUsuario(String email) throws UsuarioNoRegistradoExepcion;
	Boolean estaRegistrado(String email) throws UsuarioNoRegistradoExepcion;
}

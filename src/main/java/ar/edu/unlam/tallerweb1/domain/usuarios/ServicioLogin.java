package ar.edu.unlam.tallerweb1.domain.usuarios;

import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(String email, String password) throws UsuarioNoRegistradoExepcion;
	void crearUsuario(Usuario usuario);

	void actualizarUsuario(Usuario usuario) throws UsuarioNoRegistradoExepcion;

	Usuario consultarPorID(Long id);

	Usuario consultarUsuario(String email) throws UsuarioNoRegistradoExepcion;
	Boolean estaRegistrado(String email) ;
}

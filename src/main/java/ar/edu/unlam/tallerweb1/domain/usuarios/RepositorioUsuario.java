package ar.edu.unlam.tallerweb1.domain.usuarios;

import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioNoRegistradoExepcion;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

// Interface que define los metodos del Repositorio de Usuarios.
public interface RepositorioUsuario {
	
	Usuario buscarUsuario(String email, String password) throws UsuarioNoRegistradoExepcion;
	void guardar(Usuario usuario);
    Usuario buscar(String email) throws UsuarioNoRegistradoExepcion;
	Usuario buscarPorId(Long id);
	void modificar(Usuario usuario);

    Boolean estaRegistrado(String email) throws UsuarioNoRegistradoExepcion;
}

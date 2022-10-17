package ar.edu.unlam.tallerweb1.domain.usuarios;


// Interface que define los metodos del Repositorio de Usuarios.
public interface RepositorioUsuario {
	
	Usuario buscarUsuario(String email, String password);
	void guardar(Usuario usuario);
    Usuario buscar(String email) ;
	Usuario buscarPorId(Long id);
	void modificar(Usuario usuario);

    Boolean estaRegistrado(String email) ;
}

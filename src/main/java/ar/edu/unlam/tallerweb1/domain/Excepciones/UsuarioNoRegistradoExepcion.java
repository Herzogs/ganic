package ar.edu.unlam.tallerweb1.domain.Excepciones;

import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

public class UsuarioNoRegistradoExepcion extends Exception {
    public UsuarioNoRegistradoExepcion(String mensaje) {
        super(mensaje);
    }
}

package ar.edu.unlam.tallerweb1.domain.Excepciones;

public class LoginInvalidoException extends Exception {
    public LoginInvalidoException(String password_incorrecto) {
        super(password_incorrecto);
    }
}

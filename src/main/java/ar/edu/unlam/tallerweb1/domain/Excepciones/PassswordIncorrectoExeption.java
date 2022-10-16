package ar.edu.unlam.tallerweb1.domain.Excepciones;

public class PassswordIncorrectoExeption extends Exception {
    public PassswordIncorrectoExeption(String password_incorrecto) {
        super(password_incorrecto);
    }
}

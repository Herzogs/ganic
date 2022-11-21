package ar.edu.unlam.tallerweb1.domain.Excepciones;

public class DetalleInexistenteExeption extends Exception {
    public DetalleInexistenteExeption(String no_existe_detalle) {
        super(no_existe_detalle);
    }
}

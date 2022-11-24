package ar.edu.unlam.tallerweb1.domain.Excepciones;

public class CarroInexistenteExeption extends Exception {
    public CarroInexistenteExeption(String no_existe_carro) {
        super(no_existe_carro);
    }
}

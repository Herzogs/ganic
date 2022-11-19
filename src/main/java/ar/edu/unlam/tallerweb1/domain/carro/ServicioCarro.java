package ar.edu.unlam.tallerweb1.domain.carro;

import java.security.PublicKey;

public interface ServicioCarro {
    public void guardarCarro(Carro carro);
    public void actrualizarCarro(Carro carro);
    public void eliminarCarro(Carro carro);
    public Carro obtenerCarro(Carro carro);
}

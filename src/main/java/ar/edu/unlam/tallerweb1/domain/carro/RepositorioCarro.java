package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import java.util.List;

public interface RepositorioCarro {

    public void guardarCarro(Carro carro);
    public void actualizarCarro(Carro carro);
    public void eliminarCarro(Carro carro);
    public Carro obtenerCarro(Long idCarro);


    Carro obtenerCarroCliente(Usuario usuario);


}

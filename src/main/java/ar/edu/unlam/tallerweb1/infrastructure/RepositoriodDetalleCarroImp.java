package ar.edu.unlam.tallerweb1.infrastructure;

import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.carro.RepositorioCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

public class RepositoriodDetalleCarroImp implements RepositorioCarro{
    @Override
    public void guardarCarro(Carro carro) {

    }

    @Override
    public void actualizarCarro(Carro carro) {

    }

    @Override
    public void eliminarCarro(Carro carro) {

    }

    @Override
    public Carro obtenerCarro(Long idCarro) {
        return null;
    }

    @Override
    public Carro obtenerCarroCliente(Usuario usuario) {
        return null;
    }


    public Carro obtenerCarro(Usuario usuario) {
        return null;
    }
}

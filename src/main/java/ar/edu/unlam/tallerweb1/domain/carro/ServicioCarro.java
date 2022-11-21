package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CarroInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

public interface ServicioCarro {
    public void guardarCarro(Carro carro);

    public Carro obtenerCarro(Long carro) throws CarroInexistenteExeption;
    public Carro obtenerCarroDeCLiente(Usuario usuario);


    void agregarDetalleAlCarro(DetalleCarro detalleCarro1, Usuario usuario);

}

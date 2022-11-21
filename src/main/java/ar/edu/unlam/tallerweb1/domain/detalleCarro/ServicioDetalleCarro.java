package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.Excepciones.DetalleInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import java.util.List;

public interface ServicioDetalleCarro {
    public void guardarDetalle(DetalleCarro detalleCarro);
    public void actualizarDetalle(DetalleCarro detalleCarro);
    public void eliminarDetalle(DetalleCarro detalleCarro);
    public DetalleCarro obtenerDetalle(Long idDetalle) throws DetalleInexistenteExeption;

    public List<DetalleCarro> obtenerDetalleDeCarroDeUsuario(Usuario usuario) throws DetalleInexistenteExeption;
    public void vaciarCarro(Usuario usuario);
    public Boolean incrementarCntidad( Integer cantidad, Usuario usuario, Sandwich sandwich);




}

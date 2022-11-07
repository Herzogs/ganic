package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CompraNoEncontradaExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import java.util.List;
import java.util.Set;

public interface ServicioCompra {
    public void guardarCompra(Compra compra);
    public void eliminarCompra(Long idCompra);
    public Compra buscarCompra(Long idCompra) throws CompraNoEncontradaExeption;
    public List<Compra> buscarComprasPorUsuario(Usuario idUsuario) throws CompraNoEncontradaExeption;
    public List<Compra> listarComprasDeUsuarioPorEstado(Usuario usuario, EstadoDeCompra estado) throws CompraNoEncontradaExeption;
    public void cancelarCompra(Compra compra, EstadoDeCompra estadoDeCompra);

    public List<Compra> listarComprasPorEstado(EstadoDeCompra estadoDeCompra) throws CompraNoEncontradaExeption;

    public void entregarCompra(Long idCompra) throws CompraNoEncontradaExeption;
}

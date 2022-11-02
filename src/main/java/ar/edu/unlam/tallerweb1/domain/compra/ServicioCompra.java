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
    public List<Compra> listarComprasDeUsuarioPorEstado(Usuario usuario, EstadoDeCompra estado);

    public void agregarSandwich(Sandwich sandwich);
    public void eliminarSandwich(Sandwich sandwich);

}

package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import java.util.List;
import java.util.Set;

public interface ServicioCompra {
    public void guardarCompra(Usuario usuario, Set<Sandwich> detalle);
    public void eliminarCompra(Long idCompra);
    public Compra buscarCompra(Long idCompra);
    public List<Compra> buscarComprasPorUsuario(Long idUsuario);

}

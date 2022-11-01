package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import java.util.List;
import java.util.Set;

public interface ServicioCompra {
    public void guardarCompra(Compra compra);
    public void eliminarCompra(Long idCompra);
    public Compra buscarCompra(Long idCompra);
    public List<Compra> buscarComprasPorUsuario(Long idUsuario);
    public void agregarSandwich(Sandwich sandwich);
    public void eliminarSandwich(Sandwich sandwich);
}

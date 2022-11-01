package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;

import java.util.List;

public interface RepositorioCompra {
    public void guardarCompra(Compra compra);
    public void eliminarCompra(Compra compra);
    public Compra buscarCompra(Long idCompra);

}

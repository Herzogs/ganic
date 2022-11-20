package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import java.util.List;

public interface RepositorioMercadoCompra {

    void guardarMercadoCompra(MercadoCompra mc);
    void eliminarMercadoCompra (MercadoCompra mc));
    List<MercadoCompra> obtenerMercadoCompra(Usuario user);
    MercadoCompra buscarMercadoCompra(Compra compra);
}

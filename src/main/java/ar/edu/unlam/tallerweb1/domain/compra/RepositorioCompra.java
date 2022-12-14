package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import com.mysql.cj.xdevapi.Client;

import java.util.List;

public interface RepositorioCompra {
    public void guardarCompra(Compra compra);

    public void eliminarCompra(Compra compra);

    public Compra buscarCompra(Long idCompra);

    public List<Compra> buscarCompraPorCliente(Usuario usuario);

    public List<Compra> buscarCompraPorCliente(Long idUsuario);

    public void actualizoLaCompra(Compra compra);

    public List<Compra> buscarPorEstado(Usuario usuario, EstadoDeCompra estado);
    public void actualizarCompra(Compra compra);

    public List<Compra> buscarPorEstado(Long idUsuario, EstadoDeCompra estado);

    public List<Compra> obtenerCompraPorEstado(EstadoDeCompra estado);
}

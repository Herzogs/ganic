package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import java.util.List;

public interface RepositorioDetalleCarro {
    public void guardarDetalleCarro(DetalleCarro detalleCarro);
    public void actualizarDetalleCarro(DetalleCarro detalleCarro);

    public DetalleCarro obtnerDetalleCarro(Long idDetalleCarro);


    public List<DetalleCarro> obtenerDetalleDeCarrPorUsuario(Usuario usuario);

    void borrarDetalleDeCarro(DetalleCarro detalleBuscado);
}

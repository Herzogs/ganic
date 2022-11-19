package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.carro.Carro;

public interface ServicioDetalleCarro {
    public void guardarDetalle(DetalleCarro detalleCarro);
    public void actualizarDetalle(DetalleCarro detalleCarro);
    public void eliminarDetalle(DetalleCarro detalleCarro);
    public DetalleCarro obtenerDetalle(Long idDetalle);




}

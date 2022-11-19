package ar.edu.unlam.tallerweb1.domain.detalleCarro;

public interface RepositorioDetalleCarro {
    public void guardarDetalleCarro(DetalleCarro detalleCarro);
    public void actualizarDetalleCarro(DetalleCarro detalleCarro);
    public  void eliminarDetalleCarro(DetalleCarro detalleCarro);
    public DetalleCarro obtnerDetalleCarro(Long idDetalleCarro);
}

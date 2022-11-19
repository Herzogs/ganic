package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.domain.Excepciones.CarroInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioCarrito")
@Transactional
public class ServicioCarroImp implements ServicioCarro{
    private RepositorioCarro repo;
    @Autowired
    public  ServicioCarroImp(RepositorioCarro repo){
        this.repo= repo;
    }
    @Override
    public void guardarCarro(Carro carro) {
        repo.guardarCarro(carro);
    }

    @Override
    public void actrualizarCarro(Carro carro) {
        repo.actualizarCarro(carro);
    }

    @Override
    public void eliminarCarro(Carro carro) {

    }

    @Override
    public Carro obtenerCarro(Long idCarro) throws CarroInexistenteExeption {
      Carro carro= repo.obtenerCarro(idCarro);
      if(carro== null){
          throw new CarroInexistenteExeption("No existe carro");
      }
      return carro;

    }

    public Carro obtenerCarroDeCLiente(Usuario usuario) {
        Carro carro = repo.obtenerCarroCliente(usuario);
        if(carro!=null){
            return carro;
        }
        Carro nuevo= new Carro();
        nuevo.setUsuario(usuario);
        repo.guardarCarro(nuevo);
        return nuevo;
    }
}

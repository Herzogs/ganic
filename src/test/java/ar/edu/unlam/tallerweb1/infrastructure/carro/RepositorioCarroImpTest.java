package ar.edu.unlam.tallerweb1.infrastructure.carro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.carro.RepositorioCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RepositorioCarroImpTest extends SpringTest {
    @Autowired
    private RepositorioCarro repo;
    @Test
    @Transactional
    public void queSePuedaObtenerUnCarroDeUnCliente(){
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Carro carro = dadoQueTengoElCarroDeUnCliente(usuario);
        verificoQueElCarroSeaDelCliente(carro,usuario);
    }
    @Test
    @Transactional
    public void queSiTengoUnCArroGuardadoLoPuedaObtenerPorIdCarro(){
        Usuario usuario = dadoQueTengoUnUsuario(1L, "diego@ganic.com", "123");
        Carro carro= generoUnCarro(50L,usuario);
        guardaoElCarro(carro);
        Carro carroBuscado=buscoElCarroPorIdCarro(carro.getIdCarro());
        verificoQueElCarroSeaElMismo(carro, carroBuscado);
    }

    private void verificoQueElCarroSeaElMismo(Carro carro, Carro carroBuscado) {
        assertThat(carro).isNotNull();
        assertThat(carroBuscado).isNotNull();
    }

    private Carro buscoElCarroPorIdCarro(Long idCarrito) {
        return repo.obtenerCarro(idCarrito);
    }

    private void guardaoElCarro(Carro carro) {
        repo.guardarCarro(carro);
        session().save(carro);
    }

    private Carro generoUnCarro(long idCarro, Usuario usuario) {
        Carro carro = new Carro();
        carro.setUsuario(usuario);
        carro.setIdCarro(idCarro);
        session().save(carro);
        return carro;
    }


    private void verificoQueElCarroSeaDelCliente(Carro carro, Usuario usuario) {
    assertThat(carro).isNotNull();
    }

    private Carro dadoQueTengoElCarroDeUnCliente(Usuario usuario) {
        Carro carro= new Carro();
        carro.setUsuario(usuario);
        session().save(carro);
        return repo.obtenerCarroCliente(usuario);
    }

    private Usuario dadoQueTengoUnUsuario(Long id, String email, String password) {
        Usuario nuevo = new Usuario();
        nuevo.setId(id);
        nuevo.setEmail(email);
        nuevo.setPassword(password);
        session().save(nuevo);
        return nuevo;
    }


}

package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CarroInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioCarroImpTest extends SpringTest {
    private RepositorioCarro repo;
    private ServicioCarroImp servicio;
    @Before
    public void init(){
        this.repo= mock(RepositorioCarro.class);
        this.servicio= new ServicioCarroImp(repo);
    }

    @Test
    @Transactional
    public void dadoQueTengoUnUsuarioConCarritoQueSePuedaObtenerSuCarroPoUsuario(){
        Usuario usuario = dadoQueTengoUnUsuario(2L, "diego@ganic.com", "123");
        Carro carro= dadoQueTengoUnCarro(usuario, 2L);
        busacoElCarroPorMoquito(usuario,carro);
        Carro carroBuscado= buscoElCarroPorUsuario(carro,usuario);
        verificoQueElCarroSeaElMismo(carro,carroBuscado);
    }


    @Test
    @Transactional
    public void siExisteCarroDeUsuarioLoPuedaBUscarPorIdCarro() throws CarroInexistenteExeption {
        Usuario usuario = dadoQueTengoUnUsuario(3L, "diego@ganic.com", "123");
        Carro carro= dadoQueTengoUnCarro(usuario, 2L);
        busacoElCarroPorIdCarroConMoquito(carro.getIdCarro(),carro);
        Carro carroBuscado= buscoElCArroPorIdCArro(carro.getIdCarro());
        comprueboQueMetraigaUnCarro(carroBuscado,carro);
    }
    @Test(expected = CarroInexistenteExeption.class)
    @Transactional
    public void siBuscoPorIdCarroInexistenteMeLanceLaExeptionCarroInexistenteExeption() throws CarroInexistenteExeption {
     when(repo.obtenerCarro(3L)).thenReturn(null);
      servicio.obtenerCarro(3L);
    }


    private void cuandoLePidaAlRepoMoquiteado(Long idCarroQueNoExiste) {
        when(repo.obtenerCarro(idCarroQueNoExiste)).thenReturn(null);
    }

    private Carro buscoElCArroQueNoExiste(Long idCarroQueNoExiste) throws CarroInexistenteExeption {
    return servicio.obtenerCarro(idCarroQueNoExiste);
    }


    private void comprueboQueMetraigaUnCarro(Carro carroBuscado, Carro carro) {
        assertThat(carroBuscado).isNotNull();
        assertThat(carro).isNotNull();
    }
    private void busacoElCarroPorMoquito(Usuario usuario, Carro carro) {
        when(repo.obtenerCarroCliente(usuario)).thenReturn(carro);
    }

    private void busacoElCarroPorIdCarroConMoquito(Long idCarro, Carro carro) {
    when(repo.obtenerCarro(2L)).thenReturn(carro);
    }

    private Carro buscoElCArroPorIdCArro(Long idCarro) throws CarroInexistenteExeption {
        return servicio.obtenerCarro(idCarro);
    }

    private void verificoQueElCarroSeaElMismo(Carro carro, Carro carroBuscado) {
    assertThat(carro).isNotNull();
    assertThat(carro).isEqualTo(carroBuscado);
    assertThat(carro.getUsuario()).isEqualTo(carroBuscado.getUsuario());
    }

    private Carro buscoElCarroPorUsuario(Carro carro, Usuario usuario) {
    return servicio.obtenerCarroDeCLiente(usuario);
    }

    private Carro dadoQueTengoUnCarro(Usuario usuario, long idCarro) {
    Carro carro= new Carro();
    carro.setUsuario(usuario);
    carro.setIdCarro(idCarro);
    servicio.guardarCarro(carro);
    return carro;
    }

    private Usuario dadoQueTengoUnUsuario(long id, String email, String password) {
        Usuario nuevo = new Usuario();
        nuevo.setId(id);
        nuevo.setEmail(email);
        nuevo.setPassword(password);
        return nuevo;
    }
}

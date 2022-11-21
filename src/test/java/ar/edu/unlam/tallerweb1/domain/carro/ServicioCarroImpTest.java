package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.domain.Excepciones.CarroInexistenteExeption;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

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
        Carro carroBuscado= buscoElCarroPorUsuario(usuario);
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
    @Test
    @Transactional
    public void queSePuedaAgregarUnDetalleDeCarroAlCArro() throws CarroInexistenteExeption {
        Usuario usuario = dadoQueTengoUnUsuario(3L, "diego@ganic.com", "123");
        Carro carro= dadoQueTengoUnCarro(usuario, 2L);
        Sandwich sandwich2 = dadoQueTengoUnSandwich(2L, "sandwichDePrube", "miPedido");
        DetalleCarro detalleCarro1 = dadoQueTengoUnDetalleDeCarro(2L, carro, sandwich2, 8);
        buscoElCArroConMoquito(usuario,carro);
        Carro carroConDetalle=  agregoElDetalleAlCarro(usuario,detalleCarro1);
        Carro carroBuscado= buscoElCarroPorUsuario(usuario);
        verificoQueElCarroTengaElDetalleBuscado(carroBuscado,detalleCarro1);
    }



    private void verificoQueElCarroTengaElDetalleBuscado(Carro carroBuscado, DetalleCarro detalleCarro1) {
    assertThat(carroBuscado).isNotNull();
    assertThat(carroBuscado.getDetalleCarro()).hasSize(1);
    assertThat(carroBuscado.getDetalleCarro().get(0).getSandwich().getIdSandwich()).isEqualTo(2L);
    }

    private void buscoElCArroConMoquito(Usuario usuario, Carro carroConDetalle) {
        when(repo.obtenerCarroCliente(usuario)).thenReturn(carroConDetalle);
    }

    private Carro agregoElDetalleAlCarro(Usuario usuario, DetalleCarro detalleCarro1) {
        Carro carro= new Carro();
        carro.setUsuario(usuario);
        carro.getDetalleCarro().add(detalleCarro1);
        servicio.agregarDetalleAlCarro(detalleCarro1,usuario);
        return carro;
    }

    private DetalleCarro dadoQueTengoUnDetalleDeCarro(long idDetalleDeCarro, Carro carro, Sandwich sandwich, int cantidad) {
        DetalleCarro detalleCarro = new DetalleCarro();
        detalleCarro.setCarro(carro);
        detalleCarro.setSandwich(sandwich);
        detalleCarro.setCantidad(cantidad);
        detalleCarro.setIdDetalleCarro(idDetalleDeCarro);
        return detalleCarro;
    }
    private Sandwich dadoQueTengoUnSandwich(long idSandwich, String sandwichDePrube, String miPedido) {
        Sandwich sandwich = new Sandwich();
        sandwich.setIdSandwich(idSandwich);
        sandwich.setDescripcion(miPedido);
        sandwich.setNombre(miPedido);
        sandwich.setIngrediente(new HashSet<>());
        sandwich.setEsApto("Vegano");
        sandwich.setEnPromocion(false);
        return sandwich;
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

    private Carro buscoElCarroPorUsuario( Usuario usuario) {
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

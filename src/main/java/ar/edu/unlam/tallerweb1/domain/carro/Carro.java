package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.detalleCarro.DetalleCarro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarro;
    @OneToOne
    private Usuario usuario;
    @OneToMany
    public List<DetalleCarro> detalleCarro;

    public List<DetalleCarro> getDetalleCarro() {
        return detalleCarro;
    }

    public void setDetalleCarro(List<DetalleCarro> detalleCarro) {
        this.detalleCarro = detalleCarro;
    }

    public Carro(Long idCarro, Usuario usuario, List<DetalleCarro> detalleCarro) {
        this.idCarro = idCarro;
        this.usuario = usuario;
        this.detalleCarro = detalleCarro;
    }

    public Carro(Long idCarrito, Usuario usuario) {
        this.idCarro = idCarrito;
        this.usuario = usuario;
    }

    public Carro() {
        this.detalleCarro= new ArrayList<>();
    }

    public Long getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(Long idCarrito) {
        this.idCarro = idCarrito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

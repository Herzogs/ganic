package ar.edu.unlam.tallerweb1.domain.carro;

import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import javax.persistence.*;

@Entity
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarro;
    @OneToOne
    private Usuario usuario;

    public Carro(Long idCarrito, Usuario usuario) {
        this.idCarro = idCarrito;
        this.usuario = usuario;
    }

    public Carro() {
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

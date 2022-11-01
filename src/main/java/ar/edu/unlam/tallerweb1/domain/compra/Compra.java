package ar.edu.unlam.tallerweb1.domain.compra;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "detalle",
            joinColumns = @JoinColumn(name = "idCompra"),
            inverseJoinColumns = @JoinColumn(name = "idSandwich"))
    private List<Sandwich> detalle;
    private LocalDateTime fecha;

    public Compra(Long idCompra, Usuario cliente, List<Sandwich> detalle) {
        this.idCompra = idCompra;
        this.usuario = cliente;
        this.detalle = detalle;
        this.fecha = LocalDateTime.now();
    }
    public Compra( Usuario cliente, List<Sandwich> detalle) {
        this.idCompra = idCompra;
        this.usuario = cliente;
        this.detalle = detalle;
        this.fecha = LocalDateTime.now();
    }

    public Compra() {
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Usuario getCliente() {
       return usuario;
    }

    public void setCliente(Usuario cliente) {
    this.usuario = cliente;
    }

    public List<Sandwich> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Sandwich> detalle) {
        this.detalle = detalle;
    }
}

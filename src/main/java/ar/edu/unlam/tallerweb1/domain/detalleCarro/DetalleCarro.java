package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;

import javax.persistence.*;

@Entity
public class DetalleCarro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCarroUsuario;
    @ManyToOne(fetch = FetchType.EAGER)
    private Carro carro;
    @ManyToOne(fetch = FetchType.EAGER)
    private Sandwich sandwich;
    private Integer cantidad;

    public DetalleCarro(Long idDetalleCarroUsuario, Carro carro, Sandwich sandwich, Integer cantidad) {
        this.idDetalleCarroUsuario = idDetalleCarroUsuario;
        this.carro = carro;
        this.sandwich = sandwich;
        this.cantidad = cantidad;
    }

    public DetalleCarro() {

    }

    public Long getIdDetalleCarroUsuario() {
        return idDetalleCarroUsuario;
    }

    public void setIdDetalleCarroUsuario(Long idDetalleCarroUsuario) {
        this.idDetalleCarroUsuario = idDetalleCarroUsuario;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Sandwich getSandwich() {
        return sandwich;
    }

    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

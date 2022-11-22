package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;

import javax.persistence.*;

@Entity
public class DetalleCarro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idDetalleCarro;
    @ManyToOne(fetch = FetchType.EAGER)
    private Carro carro;
    @ManyToOne(fetch = FetchType.EAGER)
    private Sandwich sandwich;
    private Integer cantidad;

    public DetalleCarro(Long idDetalleCarroUsuario, Carro carro, Sandwich sandwich, Integer cantidad) {
        this.idDetalleCarro = idDetalleCarroUsuario;
        this.carro = carro;
        this.sandwich = sandwich;
        this.cantidad = cantidad;
    }

    public DetalleCarro() {
    }

    public Long getIdDetalleCarro() {
        return idDetalleCarro;
    }

    public void setIdDetalleCarro(Long idDetalleCarroUsuario) {
        this.idDetalleCarro = idDetalleCarroUsuario;
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

    public Float calcularMonto(){
        return this.sandwich.obtenerMonto()*this.cantidad;
    }

}

package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;

public class MpEntidad {
    private Integer cantidad;
    private Sandwich sandwich;

    public MpEntidad() {
        this.cantidad = 0;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Sandwich getSandwich() {
        return sandwich;
    }

    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    public Float calcularMonto(){
        return this.sandwich.obtenerMonto()*this.cantidad;
    }
}

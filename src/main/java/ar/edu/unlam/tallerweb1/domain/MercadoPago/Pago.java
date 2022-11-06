package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;

public class Pago {

    private Sandwich sandwich;
    private Float impTot;

    public Sandwich getSandwich() {
        return sandwich;
    }

    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    public Float getImpTot() {
        return impTot;
    }

    public void setImpTot(Float impTot) {
        this.impTot = impTot;
    }
}

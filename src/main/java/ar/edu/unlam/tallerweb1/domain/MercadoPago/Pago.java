package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;

import java.util.ArrayList;
import java.util.List;

public class Pago {

    private List<MpEntidad> listaCobrar;
    private Float impTot;

    public Pago(){
        this.listaCobrar = new ArrayList<>();
    }

    public List<MpEntidad> getListaCobrar() {
        return listaCobrar;
    }

    public void setListaCobrar(List<MpEntidad> listaCobrar) {
        this.listaCobrar = listaCobrar;
    }

    public Float getImpTot() {
        return impTot;
    }

    public void setImpTot(Float impTot) {
        this.impTot = impTot;
    }
}

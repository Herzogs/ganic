package ar.edu.unlam.tallerweb1.delivery;


import java.util.Date;

public class FormularioDePago {
    private String nroTarjeta;
    private String vencTarjeta;
    private String codSeguridad;

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public String getVencTarjeta() {
        return vencTarjeta;
    }

    public void setVencTarjeta(String vencTarjeta) {
        this.vencTarjeta = vencTarjeta;
    }

    public String getCodSeguridad() {
        return codSeguridad;
    }

    public void setCodSeguridad(String codSeguridad) {
        this.codSeguridad = codSeguridad;
    }
}

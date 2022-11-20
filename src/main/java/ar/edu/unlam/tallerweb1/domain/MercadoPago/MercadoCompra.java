package ar.edu.unlam.tallerweb1.domain.MercadoPago;

import ar.edu.unlam.tallerweb1.domain.compra.Compra;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import javax.persistence.*;

@Entity
public class MercadoCompra {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long idMercadoCompra;

    @OneToOne
    @JoinColumn(name = "id")
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "idCompra")
    private Compra compra;

    private String paymentId;

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}

package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;

import java.util.List;
import java.util.Set;

public class DatosPedido {
    private List<Sandwich> pedido;

    public List<Sandwich> getPedido() {
        return pedido;
    }

    public void setPedido(List<Sandwich> pedido) {
        this.pedido = pedido;
    }
    public void agregarSandWich(Sandwich sandwich){
        pedido.add(sandwich);

    }

}

package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;

import java.util.ArrayList;
import java.util.List;
public class datosDelSandwich {
    private List<Ingrediente> ingredientesSandwich;
    private float monto;

    public datosDelSandwich() {
        this.ingredientesSandwich = new ArrayList<>();
        this.monto = 0.0F;

    }

    public List<Ingrediente> getIngredientesSandwich() {
        return ingredientesSandwich;
    }

    public void setIngredientesSandwich(List<Ingrediente> ingredientesSandwich) {
        this.ingredientesSandwich = ingredientesSandwich;
    }

    public float getMonto() {
        this.monto = 0;
        for (Ingrediente ing : ingredientesSandwich) {
            monto += ing.getPrecio();
        }
        return monto;
    }

    public void cargarIngredienteAlSandwich(Ingrediente ingrediente) {
        this.ingredientesSandwich.add(ingrediente);
    }

    public void cargarIngredietnesAlSandwich(List<Ingrediente> lista){
        this.ingredientesSandwich.addAll(lista);
    }

    public void setMonto(float monto) {
        this.monto = monto;

    }

    public Ingrediente obtenerIngredientePor(Integer posicion){
        return this.ingredientesSandwich.get(posicion);
    }

    public void borrarDatosDelSandwich(){
        this.ingredientesSandwich.clear();
        this.monto = 0;
    }

    public Boolean eliminarIngrediente(Ingrediente ing){
        this.monto -= ing.getPrecio();
       return this.ingredientesSandwich.remove(ing);
    }

}


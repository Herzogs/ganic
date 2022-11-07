package ar.edu.unlam.tallerweb1.domain.Sandwich;

import ar.edu.unlam.tallerweb1.domain.ingredientes.Ingrediente;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Sandwich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSandwich;
    private String nombre;
    private String descripcion;

    private Boolean enPromocion;
    private String esApto;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "compuesto_por",
                joinColumns = @JoinColumn(name = "idSandwich"),
                inverseJoinColumns = @JoinColumn(name = "idIngrediente"))
    private Set<Ingrediente> ingrediente = new LinkedHashSet<>();

    public Sandwich() {
    }

    public Sandwich(Long idSandwich, String nombre, String descripcion) {
        this.idSandwich = idSandwich;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Sandwich(Long idSandwich, String nombre, String descripcion, Boolean enPromocion, String esApto, Set<Ingrediente> ingrediente) {
        this.idSandwich = idSandwich;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.enPromocion = enPromocion;
        this.esApto = esApto;
        this.ingrediente = ingrediente;
    }

    public Boolean getEnPromocion() {
        return enPromocion;
    }

    public void setEnPromocion(Boolean enPromocion) {
        this.enPromocion = enPromocion;
    }

    public String getEsApto() {
        return esApto;
    }

    public void setEsApto(String esApto) {
        this.esApto = esApto;
    }

    public Long getIdSandwich() {
        return idSandwich;
    }

    public void setIdSandwich(Long idSandwich) {
        this.idSandwich = idSandwich;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Ingrediente> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Set<Ingrediente> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void agregarIngrediente(Ingrediente ingrediente) {
        this.ingrediente.add(ingrediente);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sandwich)) return false;
        Sandwich sandwich = (Sandwich) o;
        return getIdSandwich().equals(sandwich.getIdSandwich()) && getNombre().equals(sandwich.getNombre()) && getDescripcion().equals(sandwich.getDescripcion()) && Objects.equals(getIngrediente(), sandwich.getIngrediente());
    }

    public Float obtenerMonto(){
        Float tot=0F;
        for (Ingrediente ing: this.ingrediente) {
            tot += ing.getPrecio();
        }
        return tot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdSandwich(), getIngrediente());
    }
}

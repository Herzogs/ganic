package ar.edu.unlam.tallerweb1.domain.ingredientes;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIngrediente;

    private String descripcion;
    private Float precio;
    private Integer paso;

    public Ingrediente() {
    }

    public Ingrediente(Long idIngrediente, String descripcion, Float precio, Integer paso) {
        this.idIngrediente = idIngrediente;
        this.descripcion = descripcion;
        this.precio = precio;
        this.paso = paso;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getPaso() {
        return paso;
    }

    public void setPaso(Integer paso) {
        this.paso = paso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingrediente that = (Ingrediente) o;
        return getIdIngrediente().equals(that.getIdIngrediente()) && getDescripcion().equals(that.getDescripcion()) && getPrecio().equals(that.getPrecio()) && getPaso().equals(that.getPaso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdIngrediente(), getDescripcion(), getPrecio(), getPaso());
    }
}

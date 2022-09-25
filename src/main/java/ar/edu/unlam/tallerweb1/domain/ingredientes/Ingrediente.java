package ar.edu.unlam.tallerweb1.domain.ingredientes;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIngrediente;

    private String descripcion;
    private Float precio;
    private Integer paso;

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
}

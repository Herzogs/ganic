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
	private String nombre;
	private Float precio;
	private Integer paso;
	private String detalle;

	public Ingrediente() {
	}

	public Ingrediente(Long idIngrediente, String nombre, Float precio, Integer paso, String detalle) {
		this.idIngrediente = idIngrediente;
		this.nombre = nombre;
		this.precio = precio;
		this.paso = paso;
		this.detalle = detalle;
	}

	public Long getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(Long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.nombre = detalle;
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Ingrediente that = (Ingrediente) o;
		return getIdIngrediente().equals(that.getIdIngrediente()) && getNombre().equals(that.getNombre())  && getDetalle().equals(that.getDetalle())
				&& getPrecio().equals(that.getPrecio()) && getPaso().equals(that.getPaso());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdIngrediente(), getNombre(), getDetalle(), getPrecio(), getPaso());
	}
}

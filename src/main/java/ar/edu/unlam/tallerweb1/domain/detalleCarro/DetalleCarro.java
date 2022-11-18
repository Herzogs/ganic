package ar.edu.unlam.tallerweb1.domain.detalleCarro;

import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import ar.edu.unlam.tallerweb1.domain.carro.Carro;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;

import javax.persistence.*;

@Entity
public class DetalleCarroUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCarroUsuario;
    @ManyToOne
    private Carro carro;
    @ManyToOne
    private Usuario usuario;
    @OneToOne
    private Sandwich sandwich;
    private Integer cantidad;

}

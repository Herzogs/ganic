package ar.edu.unlam.tallerweb1.domain.Email;

public interface ServicioEmail {

    Boolean sendEmail(String recptor, String subject, String cuerpo);
}

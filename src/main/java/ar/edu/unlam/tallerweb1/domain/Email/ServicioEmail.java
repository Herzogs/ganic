package ar.edu.unlam.tallerweb1.domain.Email;

public interface ServicioEmail {

    Boolean sendEmail(String recptor, String subject, String cuerpo);

    Boolean sendEmail(Email email, String subject);

    Boolean sendEmail(Email email, String subject, String name);
}

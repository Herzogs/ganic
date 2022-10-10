package ar.edu.unlam.tallerweb1.domain.Email;

import javax.mail.MessagingException;

public interface ServicioEmail {

    Boolean sendEmail(String recptor, String subject, String cuerpo);
}

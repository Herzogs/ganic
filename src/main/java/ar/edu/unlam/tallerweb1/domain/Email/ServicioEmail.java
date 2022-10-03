package ar.edu.unlam.tallerweb1.domain.Email;

import javax.mail.MessagingException;

public interface ServicioEmail {

    void sendEmail(String recptor, String subject, String cuerpo);
}

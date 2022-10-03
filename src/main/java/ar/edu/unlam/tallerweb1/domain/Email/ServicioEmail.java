package ar.edu.unlam.tallerweb1.domain.Email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServicioEmail {
    private final Properties props = new Properties();

    private String password;

    private Session session;

    private void init() {

        /*props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");*/
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        this.password = "R3MB1kn";
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("crisfelmand@gmail.com", password);
                    }
                });
    }

    public void sendEmail(String recptor, String subject, String cuerpo){

        init();
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("crisefeld@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recptor));
            message.setSubject(subject);
            message.setText(cuerpo);
            Transport t = session.getTransport("smtp");
            t.connect("crisfeldman@gmail.com", this.password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }catch (MessagingException me){
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            System.err.println(me.getMessage());
            return;
        }

    }
}

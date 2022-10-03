package ar.edu.unlam.tallerweb1.domain.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ServicioEmailImp implements ServicioEmail {
    private final Properties props = new Properties();
    private String username;
    private String password;
    private Session session;

    private void init() {
        /*this.password = System.getenv().get("PASSWORD_TALLER_WEB");
        this.username = System.getenv().get("USERNAME_TALLER_WEB");*/
        this.username="example@example.net";
        this.password="test";

        System.err.println(username + " "+ password);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    @Override
    public void sendEmail(String recptor, String subject, String cuerpo) {
        init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(username);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recptor));
            message.setSubject(subject);
            message.setText(cuerpo);
            Transport t = session.getTransport("smtp");
            t.connect(username, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }catch (MessagingException e){
            System.err.println(e.getMessage());
        }
    }
}

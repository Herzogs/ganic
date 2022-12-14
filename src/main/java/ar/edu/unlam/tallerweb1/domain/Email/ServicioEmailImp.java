package ar.edu.unlam.tallerweb1.domain.Email;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ServicioEmailImp implements ServicioEmail {
    private final Properties props = new Properties();




  //  private final Dotenv dotenv = Dotenv.configure().directory(".//src//main//resources").filename(".env").load();
    private final Dotenv dotenv= Dotenv.load();
    private String username;

    private String password;

    private Session session;

    private void init() {

        this.username = dotenv.get("USUARIO_GANIC");
        this.password = dotenv.get("PASSWORD_GANIC");

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
    public Boolean sendEmail(String recptor, String subject, String cuerpo) {
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
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean sendEmail(Email email, String subject, String name) {
        init();

        try {
            FileDataSource fileDataSource = new FileDataSource(name);
            Message message = new MimeMessage(session);
            message.setFrom();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getUser().getEmail()));
            message.setSubject(subject);
            Multipart multipart = new MimeMultipart();
            BodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentBodyPart.setFileName(name);
            multipart.addBodyPart(attachmentBodyPart);
            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(email.generateEmailBody(), "text/html");
            multipart.addBodyPart(htmlBodyPart);
            message.setContent(multipart);
            Transport.send(message);


        } catch (MessagingException e) {
            return false;
        }
        return true;

    }

    @Override
    public Boolean sendEmail(Email email, String subject) {
        init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(username);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getUser().getEmail()));
            message.setSubject(subject);
            message.setContent(email.generateEmailBody(), "text/html");
            Transport t = session.getTransport("smtp");
            t.connect(username, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}

package ar.edu.unlam.tallerweb1.domain.Email;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

public class ServicioEmailImp implements ServicioEmail {
    private final Properties props = new Properties();

    private final Dotenv dotenv = Dotenv.load();

    private String username;

    private String password;

    private Session session;

    private void init() {

        this.username = dotenv.get("USERNAME");
        this.password = dotenv.get("PASSWORD");

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
    public Boolean sendEmail(String recptor, String subject, String cuerpo){
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
        } catch (MessagingException e){
            return false;
        }
        return true;
    }
    @Override
    public Boolean sendEmail(Email email, String subject, String name){
        init();

        /*try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(username);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getUser().getEmail()));
            message.setSubject(subject);
            message.setContent(email.generateEmailBody(),"text/html");
            Transport t = session.getTransport("smtp");
            t.connect(username, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException e){
            return false;
        }
        return true;*/

        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(this.username);
            mimeMessageHelper.setTo(email.getUser().getEmail());
            mimeMessageHelper.setText(email.generateEmailBody());
            mimeMessageHelper.setSubject(subject);


            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(name));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);



            // Sending the mail
            Transport t = session.getTransport("smtp");
            t.connect(username, password);
            t.sendMessage(mimeMessageHelper.getMimeMessage(), mimeMessageHelper.getMimeMessage().getAllRecipients());
            t.close();
            return true;
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return false;
        }
    }

    @Override
    public Boolean sendEmail(Email email, String subject) {
        init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(username);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getUser().getEmail()));
            message.setSubject(subject);
            message.setContent(email.generateEmailBody(),"text/html");
            Transport t = session.getTransport("smtp");
            t.connect(username, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException e){
            return false;
        }
        return true;
    }
}

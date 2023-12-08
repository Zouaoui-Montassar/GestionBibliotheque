package myclass;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String recipientEmail, String subject, String body) {
        String senderEmail = "bibliotheque.enligne.fst@gmail.com"; 

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.topnet.tn"); // topnet
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.starttls.enable", "false");
        Session session = Session.getDefaultInstance(props);
        try {
            // Création d'un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);
            session.setDebug(true);
            // Envoi du message
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}


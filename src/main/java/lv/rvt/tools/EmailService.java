package lv.rvt.tools;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {
    private final String username = "piccaveikals@gmail.com";
    private final String password = "rajh tvha zxso qagh";

    public void sendOrderConfirmation(String customerEmail, String orderDetails) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));
            message.setSubject("Pica veikals - Pasūtījuma apstiprinājums");
            message.setText("Paldies par jūsu pasūtījumu!\n\n" + orderDetails + "\n\nLabu apetīti!");

            Transport.send(message);
            System.out.println("Email nosūtīts uz: " + customerEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

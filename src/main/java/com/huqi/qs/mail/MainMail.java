package com.huqi.qs.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MainMail {
    public static void main(String[] args) {
        String from = "2715612836@qq.com";
        String to = "2827507048@qq.com";
        String host = "smtp.qq.com";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "iolnzhlkmoxpdfgh");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject("this is the subject");
            message.setText("hello");
            Transport.send(message);
            System.out.println("game over");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

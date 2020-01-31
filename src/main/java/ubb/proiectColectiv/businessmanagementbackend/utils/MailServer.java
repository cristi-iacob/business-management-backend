package ubb.proiectColectiv.businessmanagementbackend.utils;

import ubb.proiectColectiv.businessmanagementbackend.service.UserService;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

public class MailServer {
    private static UserService userService = new UserService();

    public static void sendRegistrationEmailToAdmins() {
        String to = "cristiiacob98@gmail.com";
        String from = "cristiiacob98@gmail.com";
        String user = "a3f2e849a57e73";
        String password = "6ee0819cd09ba2";
        String host = "smtp.mailtrap.io";
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject("New register");
            message.setText("New register! Go to homepage!");

            ArrayList< String > emails = (ArrayList) userService.getAllAdminEmails();

            for (String email : emails) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                Transport.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendRealEmail() {
        final String username = "business.management.ig@gmail.com";
        final String password = "Business!23";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject("BM New registration request!");
            message.setText("Hello! \n\nA new registration request is pending. Please go to the following link and check it out! \nLink: https://bmaris98.github.io/business-management-webui/");

            ArrayList< String > emails = (ArrayList) userService.getAllAdminEmails();

            for (String email : emails) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                Transport.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

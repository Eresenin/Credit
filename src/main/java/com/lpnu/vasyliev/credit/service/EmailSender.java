package com.lpnu.vasyliev.credit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public  class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static final String PORT="465";
    private static final String HOST="smtp.ukr.net";
    private static final String PASSWORD="eXLE5kg6KOdy0bIJ";
    private static final String USER="bohdanzorii@ukr.net";
    private static final String ENCODING="UTF-8";



    public static void send(String errorMessage) {
        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");

            properties.put("mail.smtp.starttls.enable", "true");

            properties.put("mail.smtp.socketFactory.port", PORT); //SSL Port

            properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class

            properties.put("mail.smtp.host", HOST);

            properties.put("mail.smtp.port", PORT);

            properties.put("mail.smtp.ssl.Enable", "true");

            properties.put("mail.smtp.user", USER);

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("eresenin@gmail.com", "eXLE5kg6KOdy0bIJ");
                }
            };

            Session session = Session.getDefaultInstance(properties, auth);
            System.out.println("Session created");

            MimeMessage message = new MimeMessage(session);

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            message.setFrom(new InternetAddress(USER));

            message.setReplyTo(InternetAddress.parse(USER, false));

            message.setSubject("The critical error occurred", "UTF-8");

            message.setText(errorMessage, "UTF-8");

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(USER));
            Transport.send(message);
            System.out.println("Message was sent successfully...");
        }catch (MessagingException e){
            logger.info("Error sending message to email");
        }
    }

}

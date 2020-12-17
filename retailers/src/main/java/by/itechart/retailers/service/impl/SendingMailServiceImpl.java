package by.itechart.retailers.service.impl;

import by.itechart.retailers.service.interfaces.SendingMailService;
import org.antlr.stringtemplate.StringTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Service
public class SendingMailServiceImpl implements SendingMailService {
    Logger logger = LoggerFactory.getLogger(SendingMailServiceImpl.class);

    @Override
    public void sendMail(StringTemplate mail, String emailAddress) {
        try {
            logger.info("Sending mail");
            Properties properties = new Properties();

            properties.load(SendingMailServiceImpl.class.getClassLoader()
                                                        .getResourceAsStream("mail.properties"));

            mail.setAttribute("retailersEmail", properties.get("mail.smtps.user")
                                                          .toString());
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.get("mail.smtps.user")
                                                          .toString()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject("Retailers");
            message.setText(mail.toString());

            Transport tr = session.getTransport();
            tr.connect(properties.get("mail.smtps.user")
                                 .toString(), properties.get("mail.smtps.password")
                                                        .toString());
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
        } catch (IOException | MessagingException e) {
            logger.error(e.getMessage());
        }
    }
}

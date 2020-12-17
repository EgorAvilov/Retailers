package by.itechart.retailers.service.interfaces;

import org.antlr.stringtemplate.StringTemplate;

public interface SendingMailService {

    void sendMail(StringTemplate mail, String emailAddress);
}

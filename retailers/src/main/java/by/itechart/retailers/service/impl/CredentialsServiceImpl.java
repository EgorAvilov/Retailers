package by.itechart.retailers.service.impl;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.service.interfaces.CredentialsService;
import by.itechart.retailers.service.interfaces.SendingMailService;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final SendingMailService sendingMailService;
    Logger logger = LoggerFactory.getLogger(CredentialsServiceImpl.class);

    @Autowired
    public CredentialsServiceImpl(SendingMailService sendingMailService) {
        this.sendingMailService = sendingMailService;
    }

    @Override
    public void sendCredentials(UserDto userDto) {
        logger.info("Send credentials");
        StringTemplateGroup group = new StringTemplateGroup("src/main/resources", DefaultTemplateLexer.class);
        StringTemplate mail = group.getInstanceOf("Credentials");
        mail.setAttribute("name", userDto.getCustomer()
                                         .getName());
        mail.setAttribute("email", userDto.getEmail());
        mail.setAttribute("password", userDto.getPassword());
        sendingMailService.sendMail(mail, userDto.getEmail());
    }
}

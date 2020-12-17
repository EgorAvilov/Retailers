package by.itechart.retailers.service.impl;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.repository.BillRepository;
import by.itechart.retailers.service.interfaces.SendingMailService;
import by.itechart.retailers.service.interfaces.ThankLetterService;
import by.itechart.retailers.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ThankLetterServiceImpl implements ThankLetterService {

    private final BillRepository billRepository;
    private final UserService userService;
    private final SendingMailService sendingMailService;
    Logger logger = LoggerFactory.getLogger(ThankLetterServiceImpl.class);

    @Autowired
    public ThankLetterServiceImpl(BillRepository billRepository, UserService userService, SendingMailService sendingMailService) {
        this.billRepository = billRepository;
        this.userService = userService;
        this.sendingMailService = sendingMailService;
    }


    @Override
    @Scheduled(cron = "0 0 17 28-31 * ?")
    public void monthEndSchedule() {
        logger.info("Check for the end of month");
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            sendThankLetter();
        }
    }

    @Override
    public void sendThankLetter() {
        logger.info("Send thank letters");
        List<Long> userIds = billRepository.findAllBestEmployeeIds();
        System.out.println(userIds);
        List<UserDto> userDtos = userService.findAllById(userIds);
        if (userDtos.size() != 0) {
            for (UserDto userDto : userDtos) {
                StringTemplateGroup group = new StringTemplateGroup("src/main/resources", DefaultTemplateLexer.class);
                StringTemplate mail = group.getInstanceOf("ThankLetter");
                mail.setAttribute("firstName", userDto.getFirstName());
                mail.setAttribute("lastName", userDto.getLastName());
                sendingMailService.sendMail(mail, userDto.getEmail());
            }
        }
    }
}

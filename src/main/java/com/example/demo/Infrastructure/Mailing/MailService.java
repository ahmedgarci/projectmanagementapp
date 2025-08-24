package com.example.demo.Infrastructure.Mailing;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
    
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void SendEmail(String from ,String to,String username,String projectName,String invitationCode)throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED,StandardCharsets.UTF_8.name());
        String link = String.format("http://localhost:5173/contributors/acceptinvite/%s",invitationCode);
        Map<String,Object> templateVariables = new HashMap<>();
        templateVariables.put("username", username);
        templateVariables.put("projectName",projectName);
        templateVariables.put("senderName",projectName);
        templateVariables.put("invitationLink",link);      
        Context context  = new Context();
        context.setVariables(templateVariables);

        messageHelper.setFrom(from);
        messageHelper.addTo(to);
        messageHelper.setSubject("Project Invitation");
        String htmlContent = templateEngine.process("ProjectInvitation", context);

        messageHelper.setText(htmlContent, true);

        mailSender.send(message);
    }
}

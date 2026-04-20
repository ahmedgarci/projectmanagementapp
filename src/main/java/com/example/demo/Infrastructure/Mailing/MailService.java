package com.example.demo.Infrastructure.Mailing;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void SendEmail(InvitationEvent event)throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED,StandardCharsets.UTF_8.name());
        String link = String.format("http://localhost:5173/contributors/acceptinvite/%s",event.code());
        Map<String,Object> templateVariables = new HashMap<>();
        templateVariables.put("username", event.username());
        templateVariables.put("projectName",event.projectName());
        templateVariables.put("senderName", event.from());
        templateVariables.put("invitationLink",link);      
        Context context  = new Context();
        context.setVariables(templateVariables);

        messageHelper.setFrom(event.from());
        messageHelper.addTo(event.to());
        messageHelper.setSubject("Project Invitation");
        String htmlContent = templateEngine.process("ProjectInvitation", context);

        messageHelper.setText(htmlContent, true);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("error occured while sending the invitation email to the user", e);
        }
    }
}

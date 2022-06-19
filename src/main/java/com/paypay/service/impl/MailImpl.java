package com.paypay.service.impl;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.paypay.dto.Request.MailRequest;
import com.paypay.service.MailService;

@Service
public class MailImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendMail(MailRequest mailRequest)  throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        helper.setFrom("dimsvalo4@gmail.com");
        helper.setTo(mailRequest.getTo());
        helper.setSubject(mailRequest.getSubject());
        String html = templateEngine.process("ForgetPassTemplate.html", context);
        helper.setText(html, true);
        javaMailSender.send(message);
    }   
}

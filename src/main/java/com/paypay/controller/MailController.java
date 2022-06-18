package com.paypay.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypay.dto.Request.MailRequest;
import com.paypay.dto.Response.Response;
import com.paypay.service.MailService;

@RestController
@RequestMapping("/paypay")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestBody MailRequest mailRequest) throws MessagingException {
        mailService.sendMail(mailRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Email Success send to: " + mailRequest.getTo());
    }

}

package com.paypay.service;

import javax.mail.MessagingException;

import com.paypay.dto.Request.MailRequest;

public interface MailService {
    void sendMail(MailRequest mailRequest) throws MessagingException;
    
}

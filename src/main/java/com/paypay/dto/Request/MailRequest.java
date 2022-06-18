package com.paypay.dto.Request;

import lombok.Data;

@Data
public class MailRequest {
    
    private String to;
    private String subject;
    private String message;
}

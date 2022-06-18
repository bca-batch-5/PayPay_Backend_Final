package com.paypay.dto.Response;

import java.time.LocalDateTime;

import com.paypay.model.User;

import lombok.Data;

@Data
public class TransactionResponse {
    private Integer idTransaction;
    private User user;
    private Integer nominal;
    private String note;
    private String transactionType;
    private User from;
    private User to;
    private LocalDateTime date;
}

package com.paypay.dto.Request;



import lombok.Data;

@Data
public class TransferRequest {
    private Integer idTransaction;
    private String email;
    private Integer nominal;
    private String note;
    private String emailTo;
}

package com.paypay.dto.Request;

import lombok.Data;

@Data
public class NewPassRequest {
    private String newPassword;
    private String sameNewPassword;
}

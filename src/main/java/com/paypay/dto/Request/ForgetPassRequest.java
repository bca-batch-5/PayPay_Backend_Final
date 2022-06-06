package com.paypay.dto.Request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ForgetPassRequest {
    private String email;
    private String newPassword;
    private String sameNewPassword;
}

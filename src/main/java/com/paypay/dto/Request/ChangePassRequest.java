package com.paypay.dto.Request;

import lombok.Data;

@Data
public class ChangePassRequest {
    private String password;
    private String newPassword;
    private String checkNewPassword;

}

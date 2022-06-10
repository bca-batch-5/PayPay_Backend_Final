package com.paypay.dto.Request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "email must be fill")
    private String email;
    @NotEmpty(message = "password must be fill")
    private String password;
}

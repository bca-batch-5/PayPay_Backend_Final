package com.paypay.dto.Request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RegisterRequest {
    private Integer idUser;
    @NotEmpty(message = "Email must be fill")
    private String email;
    @NotEmpty(message = "username must be fill")
    private String username;
    @NotEmpty(message = "password musti be fill")
    private String password;
}

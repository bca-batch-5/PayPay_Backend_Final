package com.paypay.dto.Request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RegisterRequest {
    private Integer idUser;
    
    private String email;
    private String username;
    private String password;
    private Integer pin;
    private String image;
}

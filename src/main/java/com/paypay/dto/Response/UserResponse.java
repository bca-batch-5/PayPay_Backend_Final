package com.paypay.dto.Response;

import lombok.Data;

@Data
public class UserResponse {
    private Integer idUser;
    private String email;
    private String username;
    private String password;
    private Integer pin;
}

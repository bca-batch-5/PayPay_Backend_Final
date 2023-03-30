package com.paypay.dto.Response;

import com.paypay.model.User;

import lombok.Data;

@Data
public class UserDetailResponse {
    private Integer idDetailUser;
    private Integer user;
    private String nama;
    private String noTelp;
    private Long saldo;
}

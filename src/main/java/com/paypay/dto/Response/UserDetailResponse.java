package com.paypay.dto.Response;

import com.paypay.model.User;

import lombok.Data;

@Data
public class UserDetailResponse {
    private Integer idDetailUser;
    private User user;
    private String nama;
    private Integer noTelp;
    private Long saldo;
    private String image;
}

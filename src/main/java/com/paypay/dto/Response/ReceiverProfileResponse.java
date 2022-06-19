package com.paypay.dto.Response;

import lombok.Data;

@Data
public class ReceiverProfileResponse {
    private Integer idUser;
    private String image;
    private String nama;
    private String email;
}

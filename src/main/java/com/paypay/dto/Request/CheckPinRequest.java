package com.paypay.dto.Request;

import java.util.List;

import lombok.Data;

@Data
public class CheckPinRequest {
    private String email;
    private List<Integer> pin;
}

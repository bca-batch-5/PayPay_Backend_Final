package com.paypay.dto.Request;

import java.util.List;

import lombok.Data;

@Data
public class CreatePinRequest {
    
    private List<Integer> pin;
}

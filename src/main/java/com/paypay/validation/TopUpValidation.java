package com.paypay.validation;

import org.springframework.stereotype.Service;

import com.paypay.Exception.BadRequestException;

@Service
public class TopUpValidation {
    
    public void checkingNominal(Long nominal) throws Exception{
        if(nominal < 10000){
            throw new BadRequestException("TopUp harus di atas Rp.10.000");
        }
    }
}

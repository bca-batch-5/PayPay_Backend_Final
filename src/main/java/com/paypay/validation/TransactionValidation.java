package com.paypay.validation;

import org.springframework.stereotype.Service;

import com.paypay.Exception.BadRequestException;
import com.paypay.model.User;

@Service
public class TransactionValidation {
    
    public void senderUserValid(User senderUser) throws Exception{
        if (senderUser == null) {
            throw new BadRequestException("User Tidak Ada");
        }
    }
    public void receiverUserValid(User receiverUser) throws Exception{
        if (receiverUser == null) {
            throw new BadRequestException("User Penerima Tidak Ada");
        }
    }


}

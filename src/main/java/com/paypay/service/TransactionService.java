package com.paypay.service;

import com.paypay.dto.Request.TransferRequest;
import com.paypay.dto.Response.Response;


public interface TransactionService {
    public Response transfer(TransferRequest transferRequest)throws Exception;
    public Response getTransactionUser(Integer id) throws Exception;
    public Response getTransactionUserLimit4(Integer id) throws Exception;
    public Response getTransactionUserLimit7(Integer id) throws Exception;
    public Response updateBalance(Integer id) throws Exception;
}

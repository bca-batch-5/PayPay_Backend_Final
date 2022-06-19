package com.paypay.service;

import com.paypay.dto.Request.TransferRequest;
import com.paypay.dto.Response.Response;


public interface TransactionService {
    public Response transfer(TransferRequest transferRequest, String email)throws Exception;
    public Response getTransactionUser(String email) throws Exception;
    public Response getTransactionUserLimit4(String email) throws Exception;
    public Response getTransactionUserLimit5(String email) throws Exception;
    public Response getTransactionUserLimit7Kredit(String email) throws Exception;
    public Response getTransactionUserLimit7Debit(String email) throws Exception;
    public Response updateBalance(String email) throws Exception;
}

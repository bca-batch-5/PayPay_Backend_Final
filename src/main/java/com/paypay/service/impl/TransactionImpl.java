package com.paypay.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.paypay.constant.VariableConstant;
import com.paypay.dto.Request.TransferRequest;
import com.paypay.dto.Response.Response;
import com.paypay.dto.Response.TransactionResponse;
import com.paypay.dto.Response.UserDetailResponse;
import com.paypay.model.Transaction;
import com.paypay.model.User;
import com.paypay.model.UserDetail;
import com.paypay.repository.TransactionRepo;
import com.paypay.repository.UserDetailRepo;
import com.paypay.repository.UserRepo;
import com.paypay.service.TransactionService;
import com.paypay.validation.TransactionValidation;



@Service
public class TransactionImpl implements TransactionService {
    private Response response;

    @Autowired
    UserDetailRepo userDetailRepo;

    @Autowired
    TransactionValidation transactionValidation;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private VariableConstant varconstant;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public Response transfer(TransferRequest transferRequest,String email) throws Exception {
        Transaction debitTransaction = new Transaction();
        User senderUser = userRepo.findByEmail(email);
        transactionValidation.senderUserValid(senderUser);
        User receiverUser = userRepo.findByEmail(transferRequest.getEmailTo());
        transactionValidation.receiverUserValid(receiverUser);
        debitTransaction = mapper.map(transferRequest, Transaction.class);
        debitTransaction.setUser(senderUser);
        debitTransaction.setTo(receiverUser);
        debitTransaction.setType("Debit");
        debitTransaction.setDate(LocalDateTime.now());
        transactionRepo.save(debitTransaction);
        //////////////////////////// transaksi penerima
        Transaction kreditTransaction = new Transaction();
        kreditTransaction.setNominal(transferRequest.getNominal());
        kreditTransaction.setNote(transferRequest.getNote());
        kreditTransaction.setUser(receiverUser);
        kreditTransaction.setFrom(senderUser);
        kreditTransaction.setType("Kredit");
        kreditTransaction.setDate(LocalDateTime.now());
        transactionRepo.save(kreditTransaction);
        
        TransactionResponse res = mapper.map(debitTransaction, TransactionResponse.class);
        response = new Response(varconstant.getSTATUS_CREATED(), "transaksi berhasil", res);

        return response;
    }

    @Override
    public Response getTransactionUser(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        List<Transaction> transactionUser = transactionRepo.findByUser(user.getIdUser());
        List<TransactionResponse> res = new ArrayList<>();
        for (int i = 0; i < transactionUser.size(); i++) {
            res.add(mapper.map(transactionUser.get(i), TransactionResponse.class));
        }
        response = new Response(HttpStatus.FOUND.value(), "data Ditemukan", res);
        return response;
    }

    @Override
    public Response getTransactionUserLimit4(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        List<Transaction> transactionUser = transactionRepo.findByUserLimit4(user.getIdUser());
        List<TransactionResponse> res = new ArrayList<>();
        for (int i = 0; i < transactionUser.size(); i++) {
            res.add(mapper.map(transactionUser.get(i), TransactionResponse.class));
        }
        response = new Response(HttpStatus.FOUND.value(), "data Ditemukan", res);
        return response;
    }

    @Override
    public Response getTransactionUserLimit7(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        List<Transaction> transactionUser = transactionRepo.findByUserLimit7(user.getIdUser());
        List<TransactionResponse> res = new ArrayList<>();
        for (int i = 0; i < transactionUser.size(); i++) {
            res.add(mapper.map(transactionUser.get(i), TransactionResponse.class));
        }
        response = new Response(HttpStatus.FOUND.value(), "data Ditemukan", res);
        return response;
    }

    @Override
    public Response updateBalance(String email) throws Exception {
        User userDb = userRepo.findByEmail(email);
        UserDetail user = userDetailRepo.findByIdUser(userDb.getIdUser());
        Long saldo = user.getSaldo();
        Long kredit = transactionRepo.getKredit(userDb.getIdUser());
        Long debit = transactionRepo.getDebit(userDb.getIdUser());
        if (kredit !=null) {
            user.setSaldo(saldo + kredit);
        }
        if (debit !=null) {
            user.setSaldo(saldo - debit);
        }
        userDetailRepo.save(user);
        UserDetailResponse res = mapper.map(user, UserDetailResponse.class);
        response = new Response(HttpStatus.OK.value(), "saldo berhasil diupdate", res);
        return response;
    }

    @Override
    public Response getTransactionUserLimit5(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        List<Transaction> transactionUser = transactionRepo.findByUserLimit5(user.getIdUser());
        List<TransactionResponse> res = new ArrayList<>();
        for (int i = 0; i < transactionUser.size(); i++) {
            res.add(mapper.map(transactionUser.get(i), TransactionResponse.class));
        }
        response = new Response(HttpStatus.FOUND.value(), "data Ditemukan", res);
        return response;
    }

}

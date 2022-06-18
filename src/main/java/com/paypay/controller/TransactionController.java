package com.paypay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypay.dto.Request.GetTransactionrequest;
import com.paypay.dto.Request.TransferRequest;
import com.paypay.dto.Response.Response;
import com.paypay.service.TransactionService;

@RestController
@RequestMapping("/paypay")
public class TransactionController {
    private Response response;

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest) throws Exception{
        response = transactionService.transfer(transferRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/transaksi/{email}")
    public ResponseEntity<?> getTransactionUser(@PathVariable(value = "email")String email)throws Exception{
        response = transactionService.getTransactionUser(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/transaksi/limit4/{email}")
    public ResponseEntity<?> getTransactionUserLimit4(@PathVariable(value = "email") String email)throws Exception{
        response = transactionService.getTransactionUserLimit4(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/transaksi/limit5/{email}")
    public ResponseEntity<?> getTransactionUserLimit5(@PathVariable(value = "email") String email)throws Exception{
        response = transactionService.getTransactionUserLimit5(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/transaksi/limit7/user")
    public ResponseEntity<?> getTransactionUserLimit7(@RequestBody GetTransactionrequest getTransactionrequest)throws Exception{
        response = transactionService.getTransactionUserLimit7(getTransactionrequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/transaksi/balance/user")
    public ResponseEntity<?> updateBalance(@RequestBody GetTransactionrequest getTransactionrequest)throws Exception{
        response = transactionService.updateBalance(getTransactionrequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

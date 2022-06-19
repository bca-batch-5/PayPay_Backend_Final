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

import com.paypay.dto.Request.TransferRequest;
import com.paypay.dto.Response.Response;
import com.paypay.service.TransactionService;

@RestController
@RequestMapping("/paypay")
public class TransactionController {
    private Response response;

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer/{email}")
    public ResponseEntity<?> transfer(@PathVariable(value ="email" ) String email, @RequestBody TransferRequest transferRequest) throws Exception{
        response = transactionService.transfer(transferRequest,email);
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
    @GetMapping("/transaksi/limit7/Kredit/{email}")
    public ResponseEntity<?> getTransactionUserLimit7Kredit(@PathVariable(value = "email") String email)throws Exception{
        response = transactionService.getTransactionUserLimit7Kredit(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/transaksi/limit7/Debit/{email}")
    public ResponseEntity<?> getTransactionUserLimit7Debit(@PathVariable(value = "email") String email)throws Exception{
        response = transactionService.getTransactionUserLimit7Debit(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/transaksi/balance/{email}")
    public ResponseEntity<?> updateBalance(@PathVariable(value = "email") String email)throws Exception{
        response = transactionService.updateBalance(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

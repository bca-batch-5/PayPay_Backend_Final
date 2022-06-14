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
@RequestMapping("/transaction")
public class TransactionController {
    private Response response;

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest) throws Exception{
        response = transactionService.transfer(transferRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/transaksi/{id}")
    public ResponseEntity<?> getTransactionUser(@PathVariable Integer id)throws Exception{
        response = transactionService.getTransactionUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/transaksi/limit4/{id}")
    public ResponseEntity<?> getTransactionUserLimit4(@PathVariable Integer id)throws Exception{
        response = transactionService.getTransactionUserLimit4(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/transaksi/limit7/{id}")
    public ResponseEntity<?> getTransactionUserLimit7(@PathVariable Integer id)throws Exception{
        response = transactionService.getTransactionUserLimit7(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/transaksi/balance/{id}")
    public ResponseEntity<?> updateBalance(@PathVariable Integer id)throws Exception{
        response = transactionService.updateBalance(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/transaksi/getbalance/{id}")
    public ResponseEntity<?> getBalance(@PathVariable Integer id)throws Exception{
        response = transactionService.getBalance(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

package org.anaya.financialapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anaya.financialapp.dto.TransactionRequest;
import org.anaya.financialapp.dto.TransactionResponse;
import org.anaya.financialapp.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        log.info("Creating transaction");
        return transactionService.createTransaction(transactionRequest);
    }
}

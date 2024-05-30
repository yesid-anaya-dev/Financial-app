package org.anaya.financialapp.controller;

import org.anaya.financialapp.domain.enums.TransactionType;
import org.anaya.financialapp.dto.TransactionRequest;
import org.anaya.financialapp.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionController transactionController;

    @Test
    void givenTransactionRequest_whenCreateTransaction_thenReturnTransactionResponse() {
        // given
        var transactionRequest = TransactionRequest.builder()
                .type(TransactionType.DEPOSIT)
                .amount(1000.0)
                .receiverAccountNumber("1234567890")
                .description("Deposit")
                .build();

        // when
        var transactionResponse = transactionController.createTransaction(transactionRequest);

        // then
        Mockito.verify(transactionService, Mockito.times(1)).createTransaction(transactionRequest);
    }
}

package org.anaya.financialapp.service;

import org.anaya.financialapp.domain.enums.AccountStatus;
import org.anaya.financialapp.domain.enums.TransactionType;
import org.anaya.financialapp.domain.model.Account;
import org.anaya.financialapp.dto.TransactionRequest;
import org.anaya.financialapp.repository.AccountRepository;
import org.anaya.financialapp.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private static final String RECEIVER_ACCOUNT_NUMBER_VALID = "1234567890";
    private static final String SENDER_ACCOUNT_NUMBER_VALID = "7894561230";

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        lenient().when(accountRepository.findByAccountNumber(RECEIVER_ACCOUNT_NUMBER_VALID)).thenReturn(Optional.of(getReceiverAccount()));
        lenient().when(accountRepository.findByAccountNumber(SENDER_ACCOUNT_NUMBER_VALID)).thenReturn(Optional.of(getSenderAccount()));
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private Account getReceiverAccount() {
        return Account.builder()
                .accountNumber(RECEIVER_ACCOUNT_NUMBER_VALID)
                .balance(1000.0)
                .status(AccountStatus.ACTIVE)
                .build();
    }
    private Account getSenderAccount() {
        return Account.builder()
                .accountNumber(SENDER_ACCOUNT_NUMBER_VALID)
                .balance(2000.0)
                .status(AccountStatus.ACTIVE)
                .build();
    }

    @Test
    void givenDepositTransaction_whenCreateTransaction_thenSaveTransaction() {
        // given
        var transactionRequest = TransactionRequest.builder()
                .type(TransactionType.DEPOSIT)
                .amount(7000.0)
                .receiverAccountNumber(RECEIVER_ACCOUNT_NUMBER_VALID)
                .description("Deposit")
                .build();

        // when
        var transactionResponse = transactionService.createTransaction(transactionRequest);

        // then
        assertThat(transactionResponse).isNotNull();
        assertThat(transactionResponse.getReceiverAccount().getAccountNumber()).isEqualTo(RECEIVER_ACCOUNT_NUMBER_VALID);
        assertThat(transactionResponse.getReceiverAccount().getBalance()).isEqualTo(8000.0);
    }

    @Test
    void givenWithdrawalTransaction_whenCreateTransaction_thenSaveTransaction() {
        // given
        var transactionRequest = TransactionRequest.builder()
                .type(TransactionType.WITHDRAWAL)
                .amount(300.0)
                .senderAccountNumber(SENDER_ACCOUNT_NUMBER_VALID)
                .description("Withdrawal")
                .build();

        // when
        var transactionResponse = transactionService.createTransaction(transactionRequest);

        // then
        assertThat(transactionResponse).isNotNull();
        assertThat(transactionResponse.getSenderAccount().getAccountNumber()).isEqualTo(SENDER_ACCOUNT_NUMBER_VALID);
        assertThat(transactionResponse.getSenderAccount().getBalance()).isEqualTo(1700.0);
    }

    @Test
    void givenTransferTransaction_whenCreateTransaction_thenSaveTransaction() {
        // given
        var transactionRequest = TransactionRequest.builder()
                .type(TransactionType.TRANSFER)
                .amount(450.0)
                .senderAccountNumber(SENDER_ACCOUNT_NUMBER_VALID)
                .receiverAccountNumber(RECEIVER_ACCOUNT_NUMBER_VALID)
                .description("Transfer")
                .build();

        // when
        var transactionResponse = transactionService.createTransaction(transactionRequest);

        // then
        assertThat(transactionResponse).isNotNull();
        assertThat(transactionResponse.getSenderAccount().getAccountNumber()).isEqualTo(SENDER_ACCOUNT_NUMBER_VALID);
        assertThat(transactionResponse.getSenderAccount().getBalance()).isEqualTo(1550.0);
        assertThat(transactionResponse.getReceiverAccount().getAccountNumber()).isEqualTo(RECEIVER_ACCOUNT_NUMBER_VALID);
        assertThat(transactionResponse.getReceiverAccount().getBalance()).isEqualTo(1450.0);
    }
}

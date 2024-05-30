package org.anaya.financialapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anaya.financialapp.domain.enums.AccountStatus;
import org.anaya.financialapp.domain.model.Transaction;
import org.anaya.financialapp.dto.TransactionRequest;
import org.anaya.financialapp.dto.TransactionResponse;
import org.anaya.financialapp.exception.InvalidAccountNumberException;
import org.anaya.financialapp.exception.InvalidTransactionException;
import org.anaya.financialapp.mapper.AccountMapper;
import org.anaya.financialapp.repository.AccountRepository;
import org.anaya.financialapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        log.info("Creating transaction");
        return switch (transactionRequest.getType()) {
            case DEPOSIT -> createDepositTransaction(transactionRequest);
            case WITHDRAWAL -> createWithdrawalTransaction(transactionRequest);
            case TRANSFER -> createTransferTransaction(transactionRequest);
        };
    }

    protected TransactionResponse createDepositTransaction(TransactionRequest transactionRequest) {
        log.info("Creating deposit transaction");

        if(Objects.isNull(transactionRequest.getReceiverAccountNumber())) {
            throw new InvalidAccountNumberException("Receiver account number is required for deposit transaction");
        }
        var account = accountRepository.findByAccountNumber(transactionRequest.getReceiverAccountNumber())
                .orElseThrow(() -> new InvalidAccountNumberException("Receiver account number is invalid"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountNumberException("Receiver account is not active");
        }

        var transaction = Transaction.builder()
                .transactionType(transactionRequest.getType())
                .amount(transactionRequest.getAmount())
                .receiverAccount(account)
                .senderAccount(null)
                .description(transactionRequest.getDescription())
                .build();
        transaction = transactionRepository.save(transaction);

        account.setBalance(account.getBalance() + transactionRequest.getAmount());
        account = accountRepository.save(account);

        return TransactionResponse.builder()
                .type(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .receiverAccount(AccountMapper.INSTANCE.toAccountResponse(account))
                .senderAccount(null)
                .createdAt(transaction.getCreatedAt() == null ? null : transaction.getCreatedAt().toString())
                .build();
    }

    protected TransactionResponse createWithdrawalTransaction(TransactionRequest transactionRequest) {
        log.info("Creating withdraw transaction");

        if(Objects.isNull(transactionRequest.getSenderAccountNumber())) {
            throw new InvalidAccountNumberException("Sender account number is required for withdrawal transaction");
        }
        var account = accountRepository.findByAccountNumber(transactionRequest.getSenderAccountNumber())
                .orElseThrow(() -> new InvalidAccountNumberException("Sender account number is invalid"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountNumberException("Sender account is not active");
        }

        var newBalance = account.getBalance() - transactionRequest.getAmount();
        if (newBalance < 0) {
            throw new InvalidTransactionException("Insufficient balance for withdrawal transaction");
        }

        var transaction = Transaction.builder()
                .transactionType(transactionRequest.getType())
                .amount(transactionRequest.getAmount())
                .receiverAccount(null)
                .senderAccount(account)
                .description(transactionRequest.getDescription())
                .build();
        transaction = transactionRepository.save(transaction);

        account.setBalance(newBalance);
        account = accountRepository.save(account);

        return TransactionResponse.builder()
                .type(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .receiverAccount(null)
                .senderAccount(AccountMapper.INSTANCE.toAccountResponse(account))
                .createdAt(transaction.getCreatedAt() == null ? null : transaction.getCreatedAt().toString())
                .build();
    }

    protected TransactionResponse createTransferTransaction(TransactionRequest transactionRequest) {
        log.info("Creating transfer transaction");

        if(Objects.isNull(transactionRequest.getReceiverAccountNumber())) {
            throw new InvalidAccountNumberException("Receiver account number is required for deposit transaction");
        }
        if(Objects.isNull(transactionRequest.getSenderAccountNumber())) {
            throw new InvalidAccountNumberException("Sender account number is required for withdrawal transaction");
        }

        var receiverAccount = accountRepository.findByAccountNumber(transactionRequest.getReceiverAccountNumber())
                .orElseThrow(() -> new InvalidAccountNumberException("Receiver account number is invalid"));

        var senderAccount = accountRepository.findByAccountNumber(transactionRequest.getSenderAccountNumber())
                .orElseThrow(() -> new InvalidAccountNumberException("Sender account number is invalid"));

        if (receiverAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountNumberException("Receiver account is not active");
        }

        if (senderAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountNumberException("Sender account is not active");
        }

        var newBalance = senderAccount.getBalance() - transactionRequest.getAmount();

        if (newBalance < 0) {
            throw new InvalidTransactionException("Insufficient balance for transfer transaction");
        }

        var transaction = Transaction.builder()
                .transactionType(transactionRequest.getType())
                .amount(transactionRequest.getAmount())
                .receiverAccount(receiverAccount)
                .senderAccount(senderAccount)
                .description(transactionRequest.getDescription())
                .build();
        transaction = transactionRepository.save(transaction);

        senderAccount.setBalance(newBalance);
        receiverAccount.setBalance(receiverAccount.getBalance() + transactionRequest.getAmount());
        senderAccount = accountRepository.save(senderAccount);
        receiverAccount = accountRepository.save(receiverAccount);

        return TransactionResponse.builder()
                .type(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .senderAccount(AccountMapper.INSTANCE.toAccountResponse(senderAccount))
                .receiverAccount(AccountMapper.INSTANCE.toAccountResponse(receiverAccount))
                .createdAt(transaction.getCreatedAt() == null ? null : transaction.getCreatedAt().toString())
                .build();
    }

}

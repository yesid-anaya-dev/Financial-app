package org.anaya.financialapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anaya.financialapp.domain.enums.AccountStatus;
import org.anaya.financialapp.domain.enums.AccountType;
import org.anaya.financialapp.domain.model.Account;
import org.anaya.financialapp.dto.AccountRequest;
import org.anaya.financialapp.dto.AccountResponse;
import org.anaya.financialapp.dto.AccountUpdateRequest;
import org.anaya.financialapp.exception.ResourceNotFoundException;
import org.anaya.financialapp.mapper.AccountMapper;
import org.anaya.financialapp.repository.AccountRepository;
import org.anaya.financialapp.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    private final Random random = new Random();

    public AccountResponse createAccount(AccountRequest accountRequest) {
        log.info("Creating account");
        var client = clientRepository.findById(accountRequest.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client with id %s not found".formatted(accountRequest.getClientId())));
        var account = AccountMapper.INSTANCE.toAccount(accountRequest);
        account.setAccountNumber(generateAccountNumber(account.getAccountType()));
        account.setStatus(AccountStatus.ACTIVE);
        account.setClient(client);
        account = accountRepository.save(account);
        return AccountMapper.INSTANCE.toAccountResponse(account);
    }

    public AccountResponse getAccount(Long id) {
        log.info("Getting account {}", id);
        var account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id %s not found".formatted(id)));
        return AccountMapper.INSTANCE.toAccountResponse(account);
    }

    public AccountResponse updateAccount(Long id, AccountUpdateRequest accountUpdateRequest) {
        log.info("Updating account {}", id);
        var account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id %s not found".formatted(id)));

        if(!Objects.isNull(accountUpdateRequest.getGmfExempt())) {
            account.setGmfExempt(accountUpdateRequest.getGmfExempt());
        }

        updateAccountStatus(accountUpdateRequest, account);

        account = accountRepository.save(account);
        return AccountMapper.INSTANCE.toAccountResponse(account);
    }

    protected String generateAccountNumber(AccountType accountType) {
        var sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return switch (accountType) {
            case SAVINGS -> "53" + sb;
            case CURRENT -> "33" + sb;
        };
    }

    protected void updateAccountStatus(AccountUpdateRequest accountUpdateRequest, Account account) {
        if(accountUpdateRequest.getStatus() != null) {
            var statusToUpdate = accountUpdateRequest.getStatus();
            if (statusToUpdate == AccountStatus.CANCELED && account.getBalance() > 0) {
                throw new IllegalArgumentException("Account with balance different to zero cannot be canceled");
            }
            account.setStatus(statusToUpdate);
        }

    }
}

package org.anaya.financialapp.service;

import org.anaya.financialapp.domain.enums.AccountStatus;
import org.anaya.financialapp.domain.enums.AccountType;
import org.anaya.financialapp.domain.model.Account;
import org.anaya.financialapp.domain.model.Client;
import org.anaya.financialapp.dto.AccountRequest;
import org.anaya.financialapp.dto.AccountUpdateRequest;
import org.anaya.financialapp.repository.AccountRepository;
import org.anaya.financialapp.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void givenASavingsAccountType_WhenGenerateAccountNumber_ThenReturnedAccountNumber() {
        // Given
        var accountType = AccountType.SAVINGS;

        // When
        var accountNumber = accountService.generateAccountNumber(accountType);

        // Then
        assertThat(accountNumber).isNotNull()
                .startsWith("53")
                .hasSize(10);
    }

    @Test
    void givenACurrentAccountType_WhenGenerateAccountNumber_ThenReturnedAccountNumber() {
        // Given
        var accountType = AccountType.CURRENT;

        // When
        var accountNumber = accountService.generateAccountNumber(accountType);

        // Then
        assertThat(accountNumber).isNotNull()
                .startsWith("33")
                .hasSize(10);
    }

    @Test
    void givenAnAccountRequest_WhenCreateAccount_ThenReturnedAccountResponse() {
        // Given
        var accountRequest = AccountRequest.builder()
                .accountType(AccountType.SAVINGS.name())
                .clientId(1L)
                .balance(50_000.0)
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(Client.builder().id(1L).build()));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        var accountResponse = accountService.createAccount(accountRequest);

        // Then
        assertThat(accountResponse).isNotNull();
        assertThat(accountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(accountResponse.getAccountNumber()).isNotNull().startsWith("53").hasSize(10);
        assertThat(accountResponse.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(accountResponse.getBalance()).isEqualTo(50000.0);
        assertThat(accountResponse.isGmfExempt()).isFalse();
    }

    @Test
    void givenAnAccountId_WhenGetAccount_ThenReturnedAccountResponse() {
        // Given
        var account = getAccount();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // When
        var accountResponse = accountService.getAccount(1L);

        // Then
        assertThat(accountResponse).isNotNull();
        assertThat(accountResponse.getId()).isEqualTo(1L);
        assertThat(accountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(accountResponse.getAccountNumber()).isEqualTo("5312345678");
        assertThat(accountResponse.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(accountResponse.getBalance()).isEqualTo(50000.0);
        assertThat(accountResponse.isGmfExempt()).isFalse();
    }

    @Test
    void givenAnAccountIdAndAccountUpdateRequest_WhenUpdateAccount_ThenReturnedAccountResponse() {
        // Given
        var account = getAccount();

        var accountUpdateRequest = AccountUpdateRequest.builder()
                .gmfExempt(true)
                .status("inactive")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        var accountResponse = accountService.updateAccount(1L, accountUpdateRequest);

        // Then
        assertThat(accountResponse).isNotNull();
        assertThat(accountResponse.getId()).isEqualTo(1L);
        assertThat(accountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(accountResponse.getAccountNumber()).isEqualTo("5312345678");
        assertThat(accountResponse.getStatus()).isEqualTo(AccountStatus.INACTIVE);
        assertThat(accountResponse.getBalance()).isEqualTo(50000.0);
        assertThat(accountResponse.isGmfExempt()).isTrue();
    }

    @Test
    void givenAnAccountWithBalance_WhenUpdateAccountWithStatusCanceled_ThenThrowIllegalArgumentException() {
        // Given
        var account = getAccount();

        var accountUpdateRequest = AccountUpdateRequest.builder()
                .status("canceled")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // When
        var exception = assertThrows(IllegalArgumentException.class, () -> accountService.updateAccount(1L, accountUpdateRequest));

        // Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Account with balance different to zero cannot be canceled");
    }

    private Account getAccount() {
        return Account.builder()
                .id(1L)
                .accountType(AccountType.SAVINGS)
                .accountNumber("5312345678")
                .status(AccountStatus.ACTIVE)
                .balance(50_000.0)
                .gmfExempt(false)
                .client(Client.builder().id(1L).build())
                .build();
    }
}

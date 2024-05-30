package org.anaya.financialapp.controller;

import org.anaya.financialapp.domain.enums.AccountStatus;
import org.anaya.financialapp.domain.enums.AccountType;
import org.anaya.financialapp.dto.AccountRequest;
import org.anaya.financialapp.dto.AccountUpdateRequest;
import org.anaya.financialapp.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;

    @Test
    void givenAnAccountRequest_WhenCreateAccount_ThenReturnedAccountResponse() {
        // Given
        var accountRequest = getAccountRequest();

        // When
        var accountResponse = accountController.createAccount(accountRequest);

        // Then
        verify(accountService).createAccount(accountRequest);
    }

    @Test
    void givenAnAccountId_WhenGetAccount_ThenReturnedAccountResponse() {
        // Given
        var accountId = 1L;

        // When
        var accountResponse = accountController.getAccount(accountId);

        // Then
        verify(accountService).getAccount(accountId);
    }

    @Test
    void givenAnAccountId_WhenUpdateAccount_ThenReturnedAccountResponse() {
        // Given
        var accountId = 1L;
        var accountUpdateRequest = getAccountUpdateRequest();

        // When
        var accountResponse = accountController.updateAccount(accountId, accountUpdateRequest);

        // Then
        verify(accountService).updateAccount(accountId, accountUpdateRequest);
    }

    private AccountUpdateRequest getAccountUpdateRequest() {
        return AccountUpdateRequest.builder()
                .gmfExempt(true)
                .status(AccountStatus.ACTIVE)
                .build();
    }

    private AccountRequest getAccountRequest() {
        return AccountRequest.builder()
                .accountType(AccountType.SAVINGS)
                .clientId(1L)
                .gmfExempt(true)
                .balance(1000.0)
                .build();
    }
}

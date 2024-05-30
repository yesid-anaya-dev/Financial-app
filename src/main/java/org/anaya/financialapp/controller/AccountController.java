package org.anaya.financialapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anaya.financialapp.dto.AccountRequest;
import org.anaya.financialapp.dto.AccountResponse;
import org.anaya.financialapp.dto.AccountUpdateRequest;
import org.anaya.financialapp.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        log.info("Creating account");
        return accountService.createAccount(accountRequest);
    }

    @GetMapping("/accounts/{id}")
    public AccountResponse getAccount(@PathVariable(value = "id") Long id) {
        log.info("Getting account {}", id);
        return accountService.getAccount(id);
    }

    @PutMapping("/accounts/{id}")
    public AccountResponse updateAccount(@PathVariable(value = "id") Long id, @Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
        log.info("Updating account {}", id);
        return accountService.updateAccount(id, accountUpdateRequest);
    }
}

package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anaya.financialapp.domain.enums.AccountStatus;
import org.anaya.financialapp.domain.enums.AccountType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;

    @JsonProperty("account_type")
    private AccountType accountType;

    @JsonProperty("account_number")
    private String accountNumber;

    private AccountStatus status;

    private Double balance;

    @JsonProperty("gmf_exempt")
    private boolean gmfExempt;

    private ClientResponse client;
}

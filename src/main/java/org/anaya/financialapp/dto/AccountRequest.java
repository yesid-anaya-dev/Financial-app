package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    @NotNull
    @NotEmpty(message = "Account type is required")
    @JsonProperty("account_type")
    private String accountType;

    @NotNull(message = "GMF exempt is required")
    @JsonProperty("gmf_exempt")
    private boolean gmfExempt;

    @NotNull(message = "Client id is required")
    @JsonProperty("client_id")
    private Long clientId;

    @NotNull
    @PositiveOrZero(message = "Balance must be positive or zero")
    private Double balance;
}

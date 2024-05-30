package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anaya.financialapp.domain.enums.TransactionType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotNull
    @JsonProperty("transaction_type")
    private TransactionType type;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    private String description;

    @JsonProperty("receiver_account_number")
    private String receiverAccountNumber;

    @JsonProperty("sender_account_number")
    private String senderAccountNumber;

}

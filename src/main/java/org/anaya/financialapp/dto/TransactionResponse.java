package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anaya.financialapp.domain.enums.TransactionType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    @JsonProperty("transaction_type")
    private TransactionType type;

    private Double amount;

    private String description;

    private AccountResponse receiverAccount;

    private AccountResponse senderAccount;

    private String createdAt;

}

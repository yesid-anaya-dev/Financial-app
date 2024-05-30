package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anaya.financialapp.domain.enums.AccountStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateRequest {

    private AccountStatus status;

    @JsonProperty("gmf_exempt")
    private Boolean gmfExempt;

}

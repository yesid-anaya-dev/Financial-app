package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateRequest {

    private String status;

    @JsonProperty("gmf_exempt")
    private Boolean gmfExempt;

}

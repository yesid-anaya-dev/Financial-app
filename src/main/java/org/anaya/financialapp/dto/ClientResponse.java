package org.anaya.financialapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anaya.financialapp.domain.enums.IdentificationType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private Long id;

    private IdentificationType identificationType;

    private String identificationNumber;

    private String names;

    private String surnames;

    private String email;

    private String birthdate;
}

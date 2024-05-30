package org.anaya.financialapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    @NotNull
    @NotEmpty
    private String identificationType;

    @NotNull
    @NotEmpty
    private String identificationNumber;

    @NotNull
    @NotEmpty
    private String names;

    @NotNull
    @NotEmpty
    private String surnames;

    private String email;

    @NotNull
    @NotEmpty
    private String birthdate;
}

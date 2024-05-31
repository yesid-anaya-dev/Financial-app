package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anaya.financialapp.domain.enums.IdentificationType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    @NotNull
    @JsonProperty("identification_type")
    private IdentificationType identificationType;

    @NotNull
    @NotEmpty
    @JsonProperty("identification_number")
    private String identificationNumber;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 255)
    private String names;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 255)
    private String surnames;

    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @NotNull
    @NotEmpty
    private String birthdate;
}

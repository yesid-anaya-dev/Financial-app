package org.anaya.financialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @JsonProperty("identification_type")
    private String identificationType;

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

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull
    @NotEmpty
    private String birthdate;
}

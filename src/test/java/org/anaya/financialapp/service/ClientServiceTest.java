package org.anaya.financialapp.service;

import org.anaya.financialapp.domain.enums.IdentificationType;
import org.anaya.financialapp.dto.ClientRequest;
import org.anaya.financialapp.mapper.ClientMapper;
import org.anaya.financialapp.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void givenAClientRequest_WhenCreateAClient_ThenReturnedClientResponse() {
        // Given
        var clientRequest = getClientRequest();
        when(clientRepository.save(Mockito.any())).thenReturn(ClientMapper.INSTANCE.toClient(clientRequest));

        // When
        var clientResponse = clientService.createClient(clientRequest);

        // Then
        assertThat(clientResponse).isNotNull()
                .hasFieldOrPropertyWithValue("identificationType", IdentificationType.CC)
                .hasFieldOrPropertyWithValue("identificationNumber", "1234567890")
                .hasFieldOrPropertyWithValue("names", "John")
                .hasFieldOrPropertyWithValue("surnames", "Doe")
                .hasFieldOrPropertyWithValue("email", "john.doe@test.com")
                .hasFieldOrPropertyWithValue("birthdate", "01/01/1990")
        ;
    }

    @Test
    void givenAClientId_WhenGetClient_ThenReturnedClientResponse() {
        // Given
        var clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(ClientMapper.INSTANCE.toClient(getClientRequest())));

        // When
        var clientResponse = clientService.getClient(clientId);

        // Then
        assertThat(clientResponse).isNotNull()
                .hasFieldOrPropertyWithValue("identificationType", IdentificationType.CC)
                .hasFieldOrPropertyWithValue("identificationNumber", "1234567890")
                .hasFieldOrPropertyWithValue("names", "John")
                .hasFieldOrPropertyWithValue("surnames", "Doe")
                .hasFieldOrPropertyWithValue("email", "john.doe@test.com")
                .hasFieldOrPropertyWithValue("birthdate", "01/01/1990");
    }

    @Test
    void givenAClientIdAndClientRequest_WhenUpdateClient_ThenReturnedClientResponse() {
        // Given
        var clientId = 1L;
        var clientRequest = getUpdateClientRequest();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(ClientMapper.INSTANCE.toClient(getClientRequest())));
        when(clientRepository.save(Mockito.any())).thenReturn(ClientMapper.INSTANCE.toClient(clientRequest));

        // When
        var clientResponse = clientService.updateClient(clientId, clientRequest);

        // Then
        assertThat(clientResponse).isNotNull()
                .hasFieldOrPropertyWithValue("identificationType", IdentificationType.CC)
                .hasFieldOrPropertyWithValue("identificationNumber", clientRequest.getIdentificationNumber())
                .hasFieldOrPropertyWithValue("names", clientRequest.getNames())
                .hasFieldOrPropertyWithValue("surnames", clientRequest.getSurnames())
                .hasFieldOrPropertyWithValue("email", clientRequest.getEmail())
                .hasFieldOrPropertyWithValue("birthdate", clientRequest.getBirthdate());
    }

    @Test
    void givenAClientId_WhenDeleteClient_ThenClientDeleted() {
        // Given
        var clientId = 1L;
        var client = ClientMapper.INSTANCE.toClient(getClientRequest());
        client.setAccounts(Collections.emptyList());
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // When
        clientService.deleteClient(clientId);

        // Then
        Mockito.verify(clientRepository, Mockito.times(1)).delete(Mockito.any());

    }

    private ClientRequest getClientRequest() {
        return ClientRequest.builder()
                .identificationType("CC")
                .identificationNumber("1234567890")
                .names("John")
                .surnames("Doe")
                .email("john.doe@test.com")
                .birthdate("01/01/1990")
                .build();
    }
    private ClientRequest getUpdateClientRequest() {
        return ClientRequest.builder()
                .identificationType("CC")
                .identificationNumber("987654321")
                .names("John F.")
                .surnames("Doe")
                .email("johnf.doe@test.com")
                .birthdate("01/01/1990")
                .build();
    }
}

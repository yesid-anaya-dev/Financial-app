package org.anaya.financialapp.controller;

import org.anaya.financialapp.domain.enums.IdentificationType;
import org.anaya.financialapp.dto.ClientRequest;
import org.anaya.financialapp.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Test
    void givenAClientRequest_WhenCreateAClient_ThenReturnedClientResponse() {
        // Given
        var clientRequest = getClientRequest();

        // When
        var clientResponse = clientController.createClient(clientRequest);

        // Then
        verify(clientService).createClient(clientRequest);
    }

    @Test
    void givenAClientId_WhenGetClient_ThenReturnedClientResponse() {
        // Given
        var clientId = 1L;

        // When
        var clientResponse = clientController.getClient(clientId);

        // Then
        verify(clientService).getClient(clientId);
    }

    @Test
    void givenAClientId_WhenUpdateClient_ThenReturnedClientResponse() {
        // Given
        var clientId = 1L;
        var clientRequest = getClientRequest();

        // When
        var clientResponse = clientController.updateClient(clientId, clientRequest);

        // Then
        verify(clientService).updateClient(clientId, clientRequest);
    }

    @Test
    void givenAClientId_WhenDeleteClient_ThenReturnedClientResponse() {
        // Given
        var clientId = 1L;

        // When
        clientController.deleteClient(clientId);

        // Then
        verify(clientService).deleteClient(clientId);
    }

    private ClientRequest getClientRequest() {
        return ClientRequest.builder()
                .identificationType(IdentificationType.CC)
                .identificationNumber("1234567890")
                .names("John")
                .surnames("Doe")
                .email("john.doe@test.com")
                .birthdate("01/01/1990")
                .build();
    }
}

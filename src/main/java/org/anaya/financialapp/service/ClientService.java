package org.anaya.financialapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anaya.financialapp.domain.enums.IdentificationType;
import org.anaya.financialapp.domain.model.Client;
import org.anaya.financialapp.dto.ClientRequest;
import org.anaya.financialapp.dto.ClientResponse;
import org.anaya.financialapp.exception.InvalidClientException;
import org.anaya.financialapp.exception.ResourceNotFoundException;
import org.anaya.financialapp.mapper.ClientMapper;
import org.anaya.financialapp.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private static final String CLIENT_WITH_ID_S_NOT_FOUND = "Client with id %s not found";
    private final ClientRepository clientRepository;

    public ClientResponse createClient(ClientRequest clientRequest) {
        log.info("Creating client");
        var client = ClientMapper.INSTANCE.toClient(clientRequest);
        validateClientAge(client);

        client = clientRepository.save(client);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    public ClientResponse getClient(Long id) {
        log.info("Getting client {}", id);
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLIENT_WITH_ID_S_NOT_FOUND.formatted(id)));
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    public ClientResponse updateClient(Long id, ClientRequest clientRequest) {
        log.info("Updating client {}", id);
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLIENT_WITH_ID_S_NOT_FOUND.formatted(id)));
        client.setIdentificationType(clientRequest.getIdentificationType());
        client.setIdentificationNumber(clientRequest.getIdentificationNumber());
        client.setNames(clientRequest.getNames());
        client.setSurnames(clientRequest.getSurnames());
        client.setEmail(clientRequest.getEmail());
        client.setBirthdate(ClientMapper.INSTANCE.mapStringToLocalDate(clientRequest.getBirthdate()));
        validateClientAge(client);
        client = clientRepository.save(client);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    public void deleteClient(Long id) {
        log.info("Deleting client {}", id);
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLIENT_WITH_ID_S_NOT_FOUND.formatted(id)));
        if (!client.getAccounts().isEmpty()) {
            throw new InvalidClientException("Client cannot be deleted because it has accounts");
        }
        clientRepository.delete(client);
    }

    protected void validateClientAge(Client clientRequest) {
        if (clientRequest.getIdentificationType() == IdentificationType.CC && clientRequest.getBirthdate().isAfter(LocalDate.now().minusYears(18))) {
            throw new InvalidClientException("Client must be at least 18 years old");
        }
    }

}

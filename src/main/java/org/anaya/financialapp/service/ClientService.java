package org.anaya.financialapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anaya.financialapp.domain.enums.IdentificationType;
import org.anaya.financialapp.dto.ClientRequest;
import org.anaya.financialapp.dto.ClientResponse;
import org.anaya.financialapp.mapper.ClientMapper;
import org.anaya.financialapp.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientResponse createClient(ClientRequest clientRequest) {
        log.info("Creating client");
        var client = ClientMapper.INSTANCE.toClient(clientRequest);
        //TODO validate the client age, email format, name and surname length

        client = clientRepository.save(client);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    public ClientResponse getClient(Long id) {
        log.info("Getting client {}", id);
        var client = clientRepository.findById(id).orElse(null);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    public ClientResponse updateClient(Long id, ClientRequest clientRequest) {
        log.info("Updating client {}", id);
        var client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        client.setIdentificationType(IdentificationType.valueOf(clientRequest.getIdentificationType().toUpperCase()));
        client.setIdentificationNumber(clientRequest.getIdentificationNumber());
        client.setNames(clientRequest.getNames());
        client.setSurnames(clientRequest.getSurnames());
        client.setEmail(clientRequest.getEmail());
        client.setBirthdate(ClientMapper.INSTANCE.mapStringToLocalDate(clientRequest.getBirthdate()));
        client = clientRepository.save(client);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    public void deleteClient(Long id) {
        log.info("Deleting client {}", id);
        var client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        if (!client.getAccounts().isEmpty()) {
            throw new RuntimeException("Client has accounts");
        }
        clientRepository.delete(client);
    }

}

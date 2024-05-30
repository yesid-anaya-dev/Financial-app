package org.anaya.financialapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anaya.financialapp.dto.ClientRequest;
import org.anaya.financialapp.dto.ClientResponse;
import org.anaya.financialapp.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/clients")
    public ClientResponse createClient(@Valid @RequestBody ClientRequest clientRequest) {
        return clientService.createClient(clientRequest);
    }

    @GetMapping("/clients/{id}")
    public ClientResponse getClient(@PathVariable(value = "id") Long id) {
        return clientService.getClient(id);
    }

    @PutMapping("/clients/{id}")
    public ClientResponse updateClient(@PathVariable(value = "id") Long id, @Valid @RequestBody ClientRequest client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable(value = "id") Long id) {
        clientService.deleteClient(id);
    }
}

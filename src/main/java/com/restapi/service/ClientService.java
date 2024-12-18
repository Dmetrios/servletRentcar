package com.restapi.service;

import com.restapi.model.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<ClientDto> getClientById(Long id);

    void save(ClientDto clientDto);

    void deleteClientById(Long id);

    List<ClientDto> getAllClients();
}

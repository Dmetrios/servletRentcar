package com.restapi.service.impl;

import com.restapi.model.Client;
import com.restapi.model.dto.ClientDto;
import com.restapi.model.mapper.ClientDtoMapper;
import com.restapi.repository.ClientRepository;
import com.restapi.service.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ClientDtoMapper clientDtoMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoMapper = clientDtoMapper;
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> client = clientRepository.findAll();
        List<ClientDto> clientsDto = client.stream()
                .map(clientDtoMapper::map)
                .collect(Collectors.toList());
        return clientsDto;
    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void save(ClientDto clientDto) {
        Client client = clientDtoMapper.map(clientDto);
        clientRepository.save(client);
    }

    @Override
    public Optional<ClientDto> getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) {
            return Optional.of(clientDtoMapper.map(client.get()));
        }
        else return Optional.empty();
    }
}

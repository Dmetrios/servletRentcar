package com.restapi.model.mapper;

import com.restapi.model.Client;
import com.restapi.model.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientDtoMapper {

    ClientDtoMapper INSTANCE = Mappers.getMapper(ClientDtoMapper.class);

    ClientDto map(Client client);

    Client map(ClientDto clientDto);
}

package com.restapi.model.mapper;

import com.restapi.model.Client;
import com.restapi.model.dto.ClientDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T08:56:12+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class ClientDtoMapperImpl implements ClientDtoMapper {

    @Override
    public ClientDto map(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setId( client.getId() );
        clientDto.setFirstname( client.getFirstname() );
        clientDto.setSurname( client.getSurname() );
        clientDto.setNumber( client.getNumber() );
        clientDto.setPass( client.getPass() );

        return clientDto;
    }

    @Override
    public Client map(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client client = new Client();

        client.setId( clientDto.getId() );
        client.setFirstname( clientDto.getFirstname() );
        client.setSurname( clientDto.getSurname() );
        client.setNumber( clientDto.getNumber() );
        client.setPass( clientDto.getPass() );

        return client;
    }
}

package com.restapi.model.mapper;

import com.restapi.model.Car;
import com.restapi.model.Client;
import com.restapi.model.Order;
import com.restapi.model.dto.CarDto;
import com.restapi.model.dto.ClientDto;
import com.restapi.model.dto.OrderDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T20:50:45+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class OrderDtoMapperImpl implements OrderDtoMapper {

    @Override
    public OrderDto map(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setCarId( carListToCarDtoList( order.getCarId() ) );
        orderDto.setClientId( clientListToClientDtoList( order.getClientId() ) );
        orderDto.setId( order.getId() );
        orderDto.setPrice( order.getPrice() );
        orderDto.setStatus( order.getStatus() );

        return orderDto;
    }

    @Override
    public Order map(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setCarId( carDtoListToCarList( orderDto.getCarId() ) );
        order.setClientId( clientDtoListToClientList( orderDto.getClientId() ) );
        order.setId( orderDto.getId() );
        order.setPrice( orderDto.getPrice() );
        order.setStatus( orderDto.getStatus() );

        return order;
    }

    protected CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setId( car.getId() );
        carDto.setBrand( car.getBrand() );
        carDto.setnModel( car.getnModel() );
        carDto.setGearBox( car.getGearBox() );
        carDto.setWD( car.getWD() );
        carDto.setYyRelease( car.getYyRelease() );

        return carDto;
    }

    protected List<CarDto> carListToCarDtoList(List<Car> list) {
        if ( list == null ) {
            return null;
        }

        List<CarDto> list1 = new ArrayList<CarDto>( list.size() );
        for ( Car car : list ) {
            list1.add( carToCarDto( car ) );
        }

        return list1;
    }

    protected ClientDto clientToClientDto(Client client) {
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

    protected List<ClientDto> clientListToClientDtoList(List<Client> list) {
        if ( list == null ) {
            return null;
        }

        List<ClientDto> list1 = new ArrayList<ClientDto>( list.size() );
        for ( Client client : list ) {
            list1.add( clientToClientDto( client ) );
        }

        return list1;
    }

    protected Car carDtoToCar(CarDto carDto) {
        if ( carDto == null ) {
            return null;
        }

        Car car = new Car();

        car.setId( carDto.getId() );
        car.setBrand( carDto.getBrand() );
        car.setnModel( carDto.getnModel() );
        car.setGearBox( carDto.getGearBox() );
        car.setWD( carDto.getWD() );
        car.setYyRelease( carDto.getYyRelease() );

        return car;
    }

    protected List<Car> carDtoListToCarList(List<CarDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Car> list1 = new ArrayList<Car>( list.size() );
        for ( CarDto carDto : list ) {
            list1.add( carDtoToCar( carDto ) );
        }

        return list1;
    }

    protected Client clientDtoToClient(ClientDto clientDto) {
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

    protected List<Client> clientDtoListToClientList(List<ClientDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Client> list1 = new ArrayList<Client>( list.size() );
        for ( ClientDto clientDto : list ) {
            list1.add( clientDtoToClient( clientDto ) );
        }

        return list1;
    }
}

package com.restapi.model.mapper;

import com.restapi.model.Car;
import com.restapi.model.dto.CarDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T08:56:12+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class CarDtoMapperImpl implements CarDtoMapper {

    @Override
    public CarDto map(Car car) {
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

    @Override
    public Car map(CarDto carDto) {
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
}

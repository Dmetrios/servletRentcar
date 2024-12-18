package com.restapi.model.mapper;


import com.restapi.model.Car;
import com.restapi.model.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarDtoMapper {

    CarDtoMapper INSTANCE = Mappers.getMapper(CarDtoMapper.class);

    CarDto map(Car car);

    Car map(CarDto carDto);
}

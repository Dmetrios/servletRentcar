package com.restapi.service;


import com.restapi.model.dto.CarDto;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Optional<CarDto> getCarById(Long id);

    void save(CarDto carDto);

    void deleteCarById(Long id);

    List<CarDto> getAllCars();
}

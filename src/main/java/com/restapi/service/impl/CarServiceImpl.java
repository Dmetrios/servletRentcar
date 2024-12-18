package com.restapi.service.impl;



import com.restapi.model.Car;
import com.restapi.model.dto.CarDto;
import com.restapi.model.mapper.CarDtoMapper;
import com.restapi.repository.CarRepository;
import com.restapi.service.CarService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarDtoMapper carDtoMapper;

    public CarServiceImpl(CarRepository carRepository, CarDtoMapper carDtoMapper) {
        this.carRepository = carRepository;
        this.carDtoMapper = carDtoMapper;
    }

    @Override
    public void save(CarDto carDto) {
        Car car = carDtoMapper.map(carDto);
        carRepository.save(car);
    }

    @Override
    public Optional<CarDto> getCarById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if(car.isPresent()) {
            return Optional.of(carDtoMapper.map(car.get()));
        }
        else return Optional.empty();
    }

    @Override
    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarDto> carsDto = cars.stream()
                .map(carDtoMapper::map)
                .collect(Collectors.toList());
        return carsDto;
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }
}

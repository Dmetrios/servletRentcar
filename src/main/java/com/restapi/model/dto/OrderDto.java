package com.restapi.model.dto;


import com.restapi.model.Car;
import com.restapi.model.Client;

import java.util.List;

public class OrderDto {

    private Long id;
    private List<CarDto> carId;
    private List<ClientDto> clientId;
    private String price;
    private String status;

    public OrderDto(Long id, List<CarDto> carId, List<ClientDto> clientId, String price, String status) {
        this.id = id;
        this.carId = carId;
        this.clientId = clientId;
        this.price = price;
        this.status = status;
    }

    public OrderDto(Long id, String price, String status) {
        this.id = id;
        this.price = price;
        this.status = status;
    }


    public List<CarDto> getCarId() {
        return carId;
    }

    public void setCarId(List<CarDto> carId) {
        this.carId = carId;
    }

    public List<ClientDto> getClientId() {
        return clientId;
    }

    public void setClientId(List<ClientDto> clientId) {
        this.clientId = clientId;
    }

    public OrderDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

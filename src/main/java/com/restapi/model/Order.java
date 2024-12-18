package com.restapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Order {

    private Long id;
    private List<Car> carId;
    private List<Client> clientId;
    private String price;
    private String status;

    public Order(Long id, String price, String status) {
        this.id = id;
        this.carId = new ArrayList<>();
        this.clientId = new ArrayList<>();
        this.price = price;
        this.status = status;
    }

    public List<Car> getCarId() {
        return carId;
    }

    public void setCarId(List<Car> carId) {
        this.carId = carId;
    }

    public List<Client> getClientId() {
        return clientId;
    }

    public void setClientId(List<Client> clientId) {
        this.clientId = clientId;
    }

    public Order() {
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

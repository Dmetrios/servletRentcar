package com.restapi.service;

import com.restapi.model.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderDto> getOrderById(Long id);

    void save(OrderDto orderDto);

    void deleteOrderById(Long id);

    List<OrderDto> getAllOrders();

    void addCarToOrder(Long orderId, Long carId);

    void addClientToOrder(Long orderId, Long clientId);
}

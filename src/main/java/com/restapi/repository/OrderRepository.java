package com.restapi.repository;

import com.restapi.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    void addCarToOrder(Long orderId, Long carId);

    void addClientToOrder(Long orderId, Long clientId);
}

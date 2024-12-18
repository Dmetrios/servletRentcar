package com.restapi.service.impl;

import com.restapi.model.Order;
import com.restapi.model.dto.OrderDto;
import com.restapi.model.mapper.OrderDtoMapper;
import com.restapi.repository.OrderRepository;
import com.restapi.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderDtoMapper orderDtoMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
    }

    @Override
    public Optional<OrderDto> getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        System.out.println(order);
        if (order.isPresent()) {
            return Optional.of(orderDtoMapper.map(order.get()));
        }
        else return Optional.empty();
    }

    @Override
    public void save(OrderDto orderDto) {
        Order order = orderDtoMapper.map(orderDto);

        orderRepository.save(order);
    }

    @Override
    public void addCarToOrder(Long orderId, Long carId) {
        orderRepository.addCarToOrder(orderId, carId);
    }

    @Override
    public void addClientToOrder(Long orderId, Long clientId) {
        orderRepository.addClientToOrder(orderId, clientId);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> order = orderRepository.findAll();
        List<OrderDto> ordersDto = order.stream()
                .map(orderDtoMapper::map)
                .collect(Collectors.toList());

        return ordersDto;
    }
}

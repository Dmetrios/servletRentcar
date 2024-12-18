package com.restapi.util;


import com.restapi.model.mapper.CarDtoMapper;
import com.restapi.model.mapper.ClientDtoMapper;
import com.restapi.model.mapper.OrderDtoMapper;
import com.restapi.repository.CarRepository;
import com.restapi.repository.ClientRepository;
import com.restapi.repository.OrderRepository;
import com.restapi.repository.impl.CarRepositoryImpl;
import com.restapi.repository.impl.ClientRepositoryImpl;
import com.restapi.repository.impl.OrderRepositoryImpl;
import com.restapi.service.CarService;
import com.restapi.service.ClientService;
import com.restapi.service.OrderService;
import com.restapi.service.impl.CarServiceImpl;
import com.restapi.service.impl.ClientServiceImpl;
import com.restapi.service.impl.OrderServiceImpl;

import javax.sql.DataSource;

public class ServiceFactory {

    private static final DataSource dataSource = DataSourceConfig.getDataSource();
    private static final CarRepository carRepository = new CarRepositoryImpl(dataSource);
    private static final CarDtoMapper carMapper = CarDtoMapper.INSTANCE;
    private static final ClientRepository clientRepository = new ClientRepositoryImpl(dataSource);
    private static final ClientDtoMapper clientMapper = ClientDtoMapper.INSTANCE;
    private static final OrderRepository orderRepository = new OrderRepositoryImpl(dataSource);
    private static final OrderDtoMapper orderMapper = OrderDtoMapper.INSTANCE;

    public static CarService getCarService(){
        return new CarServiceImpl(carRepository, carMapper);
    }

    public static ClientService getClientService(){
        return new ClientServiceImpl(clientRepository, clientMapper);
    }

    public static OrderService getOrderService(){
        return new OrderServiceImpl(orderRepository, orderMapper);
    }
}

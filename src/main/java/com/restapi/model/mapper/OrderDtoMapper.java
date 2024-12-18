package com.restapi.model.mapper;

import com.restapi.model.Order;
import com.restapi.model.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDtoMapper {

    OrderDtoMapper INSTANCE = Mappers.getMapper(OrderDtoMapper.class);

    OrderDto map(Order order);

    Order map(OrderDto orderDto);
}

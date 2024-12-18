package com.restapi.servlet;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.model.dto.CarDto;
import com.restapi.model.dto.ClientDto;
import com.restapi.model.dto.OrderDto;
import com.restapi.service.OrderService;
import com.restapi.util.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/order/*")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;
    private ObjectMapper objectMapper;

    public OrderServlet() {
        this.orderService = ServiceFactory.getOrderService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){
            getAllOrder(resp);
        }
        else {
            getOrderId(resp, pathInfo);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = req.getReader().lines().limit(4).collect(Collectors.joining());
        jsonString = jsonString.substring(0, jsonString.length()-1) + " }";
        OrderDto orderDto = objectMapper.readValue(jsonString, OrderDto.class);
        jsonString = req.getReader().readLine();
        jsonString = "{ " + jsonString.substring(0, jsonString.length()-1) + " }";
        CarDto carDto = objectMapper.readValue(jsonString, CarDto.class);
        jsonString = req.getReader().readLine();
        jsonString = "{ " + jsonString + " }";
        System.out.println(jsonString);
        ClientDto clientDto = objectMapper.readValue(jsonString, ClientDto.class);

        orderService.save(orderDto);
        orderService.addCarToOrder(orderDto.getId(), carDto.getId());
        orderService.addClientToOrder(orderDto.getId(), clientDto.getId());

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getPathInfo().substring(1));
        OrderDto upOrderDto;
        req.getReader().readLine();
        for(int i=0; i<=1; i++){
            System.out.println(req.getReader().readLine());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    private void getOrderId(HttpServletResponse resp, String pathInfo) throws IOException {
        Long id = Long.parseLong(pathInfo.substring(1));
        Optional<OrderDto> orderDto = orderService.getOrderById(id);
        System.out.println(orderDto);
        if (orderDto.isPresent()) {
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(orderDto.get()));
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "not found");
        }
    }
    private void getAllOrder(HttpServletResponse resp) throws IOException {
        List<OrderDto> orderDtos = orderService.getAllOrders();
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(orderDtos));
    }
}

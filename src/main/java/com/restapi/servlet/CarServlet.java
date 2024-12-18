package com.restapi.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.model.dto.CarDto;
import com.restapi.service.CarService;
import com.restapi.util.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/car/*")
public class CarServlet extends HttpServlet {

    private CarService carService;
    private ObjectMapper objectMapper;

    public CarServlet() {
        this.carService = ServiceFactory.getCarService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarDto carDto = objectMapper.readValue(req.getReader(), CarDto.class);

        carService.save(carDto);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getAllCar(resp);
        }
        else{
            getCarId(resp, pathInfo);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing car ID");
            return;
        }
        Long id = Long.parseLong(pathInfo.substring(1));
        carService.deleteCarById(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getPathInfo().substring(1));
        CarDto upCarDto = objectMapper.readValue(req.getReader(), CarDto.class);
        upCarDto.setId(id);
        carService.save(upCarDto);
    }
    private void getCarId(HttpServletResponse resp, String pathInfo) throws IOException{
        Long id = Long.parseLong(pathInfo.substring(1));
        System.out.print(pathInfo.substring(1));
        Optional<CarDto> carDto = carService.getCarById(id);
        if(carDto.isPresent()){
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(carDto.get()));
        }else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "not found");
        }
    }

    private void getAllCar(HttpServletResponse resp) throws IOException {
        List<CarDto> cars = carService.getAllCars();
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(cars));
    }
}

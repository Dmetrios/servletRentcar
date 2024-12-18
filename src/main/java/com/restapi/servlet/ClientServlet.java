package com.restapi.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.model.dto.ClientDto;
import com.restapi.repository.ClientRepository;
import com.restapi.service.ClientService;
import com.restapi.util.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/client/*")
public class ClientServlet extends HttpServlet {

    private ClientService clientService;
    private ObjectMapper objectMapper;

    public ClientServlet() {
        this.clientService = ServiceFactory.getClientService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){
            getAllClient(resp);
        }
        else {
            getClientId(resp, pathInfo);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientDto clientDto = objectMapper.readValue(req.getReader(), ClientDto.class);

        clientService.save(clientDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getPathInfo().substring(1));
        ClientDto upclientDto = objectMapper.readValue(req.getReader(), ClientDto.class);
        upclientDto.setId(id);
        clientService.save(upclientDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing client id");
            return;
        }
        Long id = Long.parseLong(pathInfo.substring(1));
        clientService.deleteClientById(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }


    private void getClientId(HttpServletResponse resp, String pathInfo) throws IOException {
        Long id = Long.parseLong(pathInfo.substring(1));
        Optional<ClientDto> clientDto = clientService.getClientById(id);
        if (clientDto.isPresent()) {
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(clientDto.get()));
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "not found");
        }
    }

    private void getAllClient(HttpServletResponse resp) throws IOException {
        List<ClientDto> clients = clientService.getAllClients();
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(clients));
    }
}

package com.restapi.repository.impl;

import com.restapi.exception.RepositoryExeption;
import com.restapi.model.Car;
import com.restapi.model.Client;
import com.restapi.model.Order;
import com.restapi.repository.OrderRepository;
import com.restapi.service.CarService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private static final String SAVE_SQL = """
           INSERT INTO orders(id, price, status)
           VALUES (?, ?, ?)
           """;
    private static final String DELETE_SQL = """
            DELETE FROM orders
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT orders.id, client.id as clientId, cars.id as carId, client.firstname, client.surname, client.number, client.pass,
                   cars.brand, cars.n_model, cars.gear_box, cars.wd, cars.yy_release, orders.price, orders.status
            FROM orders
                  JOIN order_car ON order_car.orderid = orders.id
                  JOIN  cars ON order_car.carid = cars.id
                  JOIN order_client on order_client.orderid = orders.id
                  JOIN client on order_client.clientid = client.id
            WHERE orders.id = ?
            LIMIT 1
            """;

    private final static String INSERT_INTO_ORDER_CARS = """
            INSERT INTO order_car (orderid, carid) 
            VALUES (?, ?)
                """;

    private final static String INSERT_INTO_ORDER_CLIENT = """
            INSERT INTO order_client (orderid, clientid) 
            VALUES (?, ?)""";



    private static final String FIND_ALL_SQL = """
            SELECT orders.id, client.id as clientId, cars.id as carId, client.firstname, client.surname, client.number, client.pass,
                   cars.brand, cars.n_model, cars.gear_box, cars.wd, cars.yy_release, orders.price, orders.status
            FROM orders
                  JOIN order_car ON order_car.orderid = orders.id
                  JOIN  cars ON order_car.carid = cars.id
                  JOIN order_client on order_client.orderid = orders.id
                  JOIN client on order_client.clientid = client.id
            """;
    private DataSource dataSource;

    public OrderRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Order save(Order order) {
        Order beforeOrder = null;
        if(findById(order.getId()).isPresent()) {
            beforeOrder = findById(order.getId()).get();
            deleteById(order.getId());
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setLong(1, order.getId());
            preparedStatement.setString(2, order.getPrice()==null? Objects.requireNonNull(beforeOrder).getPrice():order.getPrice());
            preparedStatement.setString(3, order.getStatus()==null? Objects.requireNonNull(beforeOrder).getStatus():order.getStatus());

            int savesRows = preparedStatement.executeUpdate();
            if(savesRows == 0) {
                throw new SQLException("Saves failed");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()){
                    order.setId(resultSet.getLong(1));
                    return order;
                } else {
                    throw new SQLException("Create client is failed");
                }
            }
        }
        catch (SQLException e) {
            throw new RepositoryExeption(e);
        }
    }

    @Override
    public void addCarToOrder(Long orderId, Long carId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDER_CARS)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, carId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw  new RepositoryExeption("Failed to add car to order");
        }
    }

    @Override
    public void addClientToOrder(Long orderId, Long clientId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDER_CLIENT)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, clientId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw  new RepositoryExeption("Failed to add client to order");
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Order order = new Order(
                        resultSet.getLong("id"),
                        resultSet.getString("price"),
                        resultSet.getString("status")
                );
                if(resultSet.getLong("carId")!=0){
                    Car car = new Car(
                            resultSet.getLong("carId"),
                            resultSet.getString("brand"),
                            resultSet.getString("n_model"),
                            resultSet.getString("gear_box"),
                            resultSet.getString("wd"),
                            resultSet.getInt("yy_release")
                    );
                    order.getCarId().add(car);
                }
                if(resultSet.getLong("clientId")!=0){
                    Client client = new Client(
                            resultSet.getLong("clientId"),
                            resultSet.getString("firstname"),
                            resultSet.getString("surname"),
                            resultSet.getString("number"),
                            resultSet.getString("pass")
                    );
                    order.getClientId().add(client);
                }
                return Optional.of(order);
            }
            return Optional.empty();
        }
        catch (SQLException e){
            throw new RepositoryExeption(e);
        }
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)){
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RepositoryExeption(e);
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);
            while(resultSet.next()){
                Order order = new Order(
                        resultSet.getLong("id"),
                        resultSet.getString("price"),
                        resultSet.getString("status")
                );
                orders.add(order);
                if(resultSet.getLong("id")!=0){
                    Car car = new Car(
                            resultSet.getLong("id"),
                            resultSet.getString("brand"),
                            resultSet.getString("n_model"),
                            resultSet.getString("gear_box"),
                            resultSet.getString("wd"),
                            resultSet.getInt("yy_release")
                    );
                    order.getCarId().add(car);
                }
                if(resultSet.getLong("clientid")!=0){
                    Client client = new Client(
                            resultSet.getLong("id"),
                            resultSet.getString("firstname"),
                            resultSet.getString("surname"),
                            resultSet.getString("number"),
                            resultSet.getString("pass")
                    );
                    order.getClientId().add(client);
                }
            }
            return orders;
        }
        catch (SQLException e){
            throw new RepositoryExeption(e);
        }
    }
}

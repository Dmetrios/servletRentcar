package com.restapi.repository.impl;


import com.restapi.exception.RepositoryExeption;
import com.restapi.model.Car;
import com.restapi.repository.CarRepository;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CarRepositoryImpl implements CarRepository {
    private static final String SAVE_SQL = """
           INSERT INTO cars(id, brand, n_model, gear_box, wd, yy_release)
           VALUES (?, ?, ?, ?, ?, ?) ;
           """;
    private static final String DELETE_SQL = """
            DELETE FROM cars
            WHERE id = ? ;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM cars
            WHERE id = ?
            LIMIT 1;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT * FROM cars
            """;

    private DataSource dataSource;
//    private final ConnectionManager connectionManager;

    public CarRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Car save(Car car) {
        Car beforeCar = null;
        if(findById(car.getId()).isPresent()) {
            beforeCar = findById(car.getId()).get();
            deleteById(car.getId());
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setLong(1, car.getId());
            preparedStatement.setString(2, car.getBrand()==null? Objects.requireNonNull(beforeCar).getBrand(): car.getBrand());
            preparedStatement.setString(3, car.getnModel()==null? Objects.requireNonNull(beforeCar).getnModel(): car.getnModel());
            preparedStatement.setString(4, car.getGearBox()==null? Objects.requireNonNull(beforeCar).getGearBox(): car.getGearBox());
            preparedStatement.setString(5, car.getWD()==null? Objects.requireNonNull(beforeCar).getWD(): car.getWD());
            preparedStatement.setInt(6, car.getYyRelease()==0? Objects.requireNonNull(beforeCar).getYyRelease(): car.getYyRelease());

            int savesRows = preparedStatement.executeUpdate();

            if(savesRows == 0){
                throw new SQLException("Saves failed");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    car.setId(resultSet.getLong(1));
                    return car;
                } else {
                    throw new SQLException("Creating car failed, no ID obtained.");
                }
            }
        }
        catch (SQLException e) {
            throw new RepositoryExeption(e);
        }

    }

    @Override
    public Optional<Car> findById(Long id) {
        Car car = null;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                car = createCar(resultSet);
            }
            return Optional.ofNullable(car);
        }
        catch (SQLException e){
            throw new RepositoryExeption(e);
        }
    }

    @Override
    public List<Car> findAll() {
        Car car;
        List<Car> cars = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);
            while (resultSet.next()){
                car = createCar(resultSet);
                cars.add(car);
            }
            return cars;
        }
        catch (SQLException e){
            throw new RepositoryExeption(e);
        }
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


    private Car createCar(ResultSet resultSet) throws SQLException{
        Long carId = resultSet.getLong("id");
        return new Car(
                carId,
                resultSet.getString("brand"),
                resultSet.getString("n_model"),
                resultSet.getString("gear_box"),
                resultSet.getString("wd"),
                resultSet.getInt("yy_release")
        );
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }
}

package com.restapi.repository.impl;

import com.restapi.exception.RepositoryExeption;
import com.restapi.model.Car;
import com.restapi.model.Client;
import com.restapi.repository.ClientRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {

    private static final String SAVE_SQL = """
            INSERT INTO client(id, firstname, surname, number, pass) 
            VALUES (?,?,?,?,?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM client
            WHERE id = ? ;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM client
            WHERE id = ?
            LIMIT 1;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT * FROM client
            """;

    private DataSource dataSource;

    public ClientRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Client save(Client client) {
        Client beforeClient = null;
        if(findById(client.getId()).isPresent()) {
            beforeClient = findById(client.getId()).get();
            deleteById(client.getId());
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setString(2, client.getFirstname()==null? Objects.requireNonNull(beforeClient).getFirstname():client.getFirstname());
            preparedStatement.setString(3, client.getSurname()==null? Objects.requireNonNull(beforeClient).getSurname():client.getSurname());
            preparedStatement.setString(4, client.getNumber()==null? Objects.requireNonNull(beforeClient).getNumber():client.getNumber());
            preparedStatement.setString(5, client.getPass()==null? Objects.requireNonNull(beforeClient).getPass():client.getPass());

            int savesRows = preparedStatement.executeUpdate();
            if(savesRows == 0) {
                throw new SQLException("Saves failed");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()){
                    client.setId(resultSet.getLong(1));
                    return client;
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
    public Optional<Client> findById(Long id) {
        Client client = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                client = createClient(resultSet);
            }
            return Optional.ofNullable(client);
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
    public List<Client> findAll() {
        Client client;
        List<Client> clents = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);
            while (resultSet.next()){
                client = createClient(resultSet);
                clents.add(client);
            }
            return clents;
        }
        catch (SQLException e){
            throw new RepositoryExeption(e);
        }
    }

    private Client createClient(ResultSet resultSet) throws SQLException{
        Long clientId = resultSet.getLong("id");
        return new Client(
                clientId,
                resultSet.getString("firstname"),
                resultSet.getString("surname"),
                resultSet.getString("number"),
                resultSet.getString("pass"));
    }
}

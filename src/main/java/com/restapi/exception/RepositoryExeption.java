package com.restapi.exception;

import java.sql.SQLException;

public class RepositoryExeption extends RuntimeException{
    public RepositoryExeption(String message){
        super(message);
    }

    public RepositoryExeption(SQLException e){
        super(e);
    }
}

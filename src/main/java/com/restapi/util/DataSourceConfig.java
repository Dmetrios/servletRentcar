package com.restapi.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceConfig {

    private final static HikariDataSource dataSource;


    static {
        try (InputStream inputStream  =DataSourceConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.setDriverClassName(properties.getProperty("db.driver-class-name"));

            dataSource = new HikariDataSource(config);
        }
        catch (Exception e){
            throw new RuntimeException("Not connection", e);
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}

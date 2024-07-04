package com.ustsinau.chapter2_2.repository.impl;

import java.sql.*;

public class DatabaseConnection {

    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/CrudAppWithJDBC_1";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "root";
    private final String PASSWORD = "mysql";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
     }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    public PreparedStatement getPreparedStatementWithGenerated(String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
//    private static final String PROPERTIES_FILE = "application.properties";
//    private static String DATABASE_URL;
//    private static String JDBC_DRIVER;
//    private static String USER;
//    private static String PASSWORD;
//
//    static {
//        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
//            Properties properties = new Properties();
//            if (input == null) {
//                throw new RuntimeException("Sorry, unable to find " + PROPERTIES_FILE);
//            }
//            properties.load(input);
//
//            DATABASE_URL = properties.getProperty("database.url");
//            JDBC_DRIVER = properties.getProperty("database.driver");
//            USER = properties.getProperty("database.user");
//            PASSWORD = properties.getProperty("database.password");
//
//            Class.forName(JDBC_DRIVER);
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException("Failed to load database configuration", e);
//        }
//    }
}


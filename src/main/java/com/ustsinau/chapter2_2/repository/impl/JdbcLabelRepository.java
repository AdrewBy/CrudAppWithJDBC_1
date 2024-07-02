package com.ustsinau.chapter2_2.repository.impl;


import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.repository.LabelRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcLabelRepository implements LabelRepository {

    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/CrudAppWithJDBC_1";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "root";
    private final String PASSWORD = "mysql";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Label create(Label label) throws SQLException {
        String sql = "Insert into labels (name) values (?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, label.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                label.setId(generatedKeys.getLong(1));
            }
        }
        return label;
    }

    @Override
    public Label update(Label newLabel) throws SQLException {
        String sql = "Update labels set name =? where id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newLabel.getName());
            statement.setLong(2, newLabel.getId());
            statement.executeUpdate();

        }
        return newLabel;
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "Delete from labels where id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
           statement.setLong(1,id);
        }
    }

    @Override
    public Label getById(Long id) throws SQLException {
       String sql = "Select * from labels where id = ?";
       Label label = null;
       try (Connection connection = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);
       PreparedStatement statement = connection.prepareStatement(sql)){
           statement.setLong(1,id);
           ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()){
               label = new Label();
               label.setId(resultSet.getLong("id"));
               label.setName(resultSet.getString("name"));
           }
       }
       return label;
    }

    @Override
    public List<Label> getAll() throws SQLException {
        return getAllLabelsInternal();
    }


    private List<Label> getAllLabelsInternal() throws SQLException {
        String sql = "Select * from labels";
        List<Label> labels = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);
        Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Label label = new Label();
                label.setId(resultSet.getLong("id"));
                label.setName(resultSet.getString("name"));
                labels.add(label);
            }
        }
        return labels;
    }

}


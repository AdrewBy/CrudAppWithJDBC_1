package com.ustsinau.chapter2_2.repository.impl;


import com.ustsinau.chapter2_2.mappers.LabelMapper;
import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.repository.LabelRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcLabelRepository implements LabelRepository {

    private final LabelMapper labelMapper = new LabelMapper();

    @Override
    public Label create(Label label)  {
        String sql = "Insert into labels name values ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
            statement.setString(1, label.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                label.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public Label update(Label newLabel)  {
        String sql = "Update labels set name =? where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {

            statement.setString(1, newLabel.getName());
            statement.setLong(2, newLabel.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newLabel;
    }

    @Override
    public void delete(Long id)  {
        String sql = "Delete from labels where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
           statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Label getById(Long id)  {
       String sql = "Select * from labels where id = ?";
       Label label = null;
       try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)){
           statement.setLong(1,id);
           ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()){
               label = new Label();
               labelMapper.mapLabel(resultSet,label);
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
        return label;
    }

    @Override
    public List<Label> getAll() {
        return getAllLabelsInternal();
    }


    private List<Label> getAllLabelsInternal()  {
        String sql = "Select * from labels";
        List<Label> labels = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Label label = new Label();
                labelMapper.mapLabel(resultSet,label);
                labels.add(label);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return labels;
    }

}


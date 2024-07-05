package com.ustsinau.chapter2_2.mappers;

import com.ustsinau.chapter2_2.models.Label;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LabelMapper {
    public void mapLabel(ResultSet resultSet, Label label) {

        try {
            label.setName(resultSet.getString("name"));
            label.setId(resultSet.getLong("label_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

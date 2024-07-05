package com.ustsinau.chapter2_2.mappers;

import com.ustsinau.chapter2_2.models.Label;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LabelMapper {
    public Label mapLabel(ResultSet resultSet, Label label) throws SQLException {

        label.setId(resultSet.getLong("label_id"));
        label.setName(resultSet.getString("name"));
        return label;
    }
}

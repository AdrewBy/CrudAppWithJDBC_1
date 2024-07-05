package com.ustsinau.chapter2_2.mappers;

import com.ustsinau.chapter2_2.models.Writer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WriterMapper {

    public void mapWriter(ResultSet resultSet, Writer writer)  {


        try {
            writer.setId(resultSet.getLong("writer_id"));
            writer.setFirstName(resultSet.getString("firstName"));
            writer.setLastName(resultSet.getString("lastName"));
            writer.setPosts(new ArrayList<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

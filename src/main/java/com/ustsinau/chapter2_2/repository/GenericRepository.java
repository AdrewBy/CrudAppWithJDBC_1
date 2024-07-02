package com.ustsinau.chapter2_2.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {

    T create(T value) throws SQLException;

    T update(T value) throws SQLException;

    void delete(ID id) throws SQLException;
    T getById(ID id) throws SQLException;

    List<T> getAll() throws SQLException;
}

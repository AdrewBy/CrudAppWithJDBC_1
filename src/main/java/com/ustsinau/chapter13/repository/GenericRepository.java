package com.ustsinau.chapter13.repository;

import com.ustsinau.chapter13.models.Writer;

import java.io.IOException;
import java.util.List;

public interface GenericRepository<T, ID> {


    T getById(ID id) ;
    void create(T value);

    void update(T value) ;

    void delete(ID id);

    List<T> getAll() ;
}

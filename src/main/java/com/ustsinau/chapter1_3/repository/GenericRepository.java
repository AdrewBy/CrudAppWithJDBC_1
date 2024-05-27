package com.ustsinau.chapter1_3.repository;

import java.util.List;

public interface GenericRepository<T, ID> {



    void create(T value);

    void update(T value) ;

    void delete(ID id);
    T getById(ID id) ;

    void saverFile(List<T> value);

    List<T> getAll() ;
}

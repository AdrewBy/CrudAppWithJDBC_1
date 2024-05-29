package com.ustsinau.chapter1_3.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    T create(T value);

    T update(T value) ;

    void delete(ID id);
    T getById(ID id) ;

    List<T> getAll() ;
}

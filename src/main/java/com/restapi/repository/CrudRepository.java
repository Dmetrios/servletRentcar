package com.restapi.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> extends Repository<T, ID> {

    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    long count();

    void deleteById(ID id);

    void deleteAll();

    List<T> findAll();
}

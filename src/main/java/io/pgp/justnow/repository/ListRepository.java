package io.pgp.justnow.repository;

import io.pgp.justnow.entity.List;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ListRepository extends CrudRepository<List, Integer> {
    <S extends List> S save(S entity);

    <S extends List> Iterable<S> saveAll(Iterable<S> entities);

    Optional<List> findById(Integer integer);

    boolean existsById(Integer integer);

    Iterable<List> findAll();

    Iterable<List> findAllById(Iterable<Integer> integers);

    long count();

    void deleteById(Integer integer);

    void delete(List entity);

    void deleteAll(Iterable<? extends List> entities);

    void deleteAll();
}

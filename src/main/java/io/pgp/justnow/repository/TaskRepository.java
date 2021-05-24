package io.pgp.justnow.repository;

import io.pgp.justnow.entity.List;
import io.pgp.justnow.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    <S extends Task> S save(S entity);

    <S extends Task> Iterable<S> saveAll(Iterable<S> entities);

    Optional<Task> findById(Integer integer);

    boolean existsById(Integer integer);

    Iterable<Task> findAll();

    Iterable<Task> findAllById(Iterable<Integer> integers);

    long count();

    void deleteById(Integer integer);

    void delete(Task entity);

    void deleteAll(Iterable<? extends Task> entities);

    void deleteAll();

    <S extends Task> Iterable<S> findAllByList(List list);
}

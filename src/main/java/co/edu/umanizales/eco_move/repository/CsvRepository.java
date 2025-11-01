package co.edu.umanizales.eco_move.repository;

import java.util.List;
import java.util.Optional;

public interface CsvRepository<T> {
    List<T> findAll();
    Optional<T> findById(String id);
    T save(T entity);
    void deleteById(String id);
    boolean existsById(String id);
}

package me.code;

import java.util.Collection;
import java.util.Optional;

public interface TodoRepository {

    Collection<Todo> getAll();
    Optional<Todo> getByName(String name);
    void save(Todo todo);
    void delete(String name);

}

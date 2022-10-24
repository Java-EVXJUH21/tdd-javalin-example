package me.code;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryTodoRepository implements TodoRepository {

    private final Map<String, Todo> data = new HashMap<>();

    @Override
    public Collection<Todo> getAll() {
        return data.values();
    }

    @Override
    public Optional<Todo> getByName(String name) {
        var value = data.get(name);
        return Optional.ofNullable(value);
    }

    @Override
    public void save(Todo todo) {
        data.put(todo.getName(), todo);
    }

    @Override
    public void delete(String name) {
        data.remove(name);
    }
}

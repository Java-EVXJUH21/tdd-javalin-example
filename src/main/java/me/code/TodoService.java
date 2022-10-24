package me.code;

import java.util.Collection;
import java.util.Optional;

public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo createTodo(String name, String description) throws TodoExistsException {
        var existing = repository.getByName(name);
        if (existing.isPresent()) {
            throw new TodoExistsException();
        }

        var todo = new Todo(name, description);
        repository.save(todo);
        return todo;
    }

    public Optional<Todo> getTodo(String name) {
        return repository.getByName(name);
    }

    public Collection<Todo> getAll() {
        return repository.getAll();
    }

}

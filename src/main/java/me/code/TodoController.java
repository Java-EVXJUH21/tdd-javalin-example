package me.code;

import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.HashMap;

public class TodoController {

    private final TodoService service;
    private final Gson gson = new Gson();

    public TodoController(TodoService service) {
        this.service = service;
    }

    public void getAllTodos(Context ctx) {
        var todos = service.getAll();
        var json = gson.toJson(todos);
        ctx.status(HttpStatus.OK);
        ctx.json(json);
    }

    public void getTodo(Context ctx) {
        var name = ctx.pathParam("name");
        var todo = service.getTodo(name);
        if (todo.isPresent()) {
            var json = gson.toJson(todo.get());
            ctx.status(HttpStatus.OK);
            ctx.json(json);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
        }
    }

    public void createTodo(Context ctx) {
        var object = (HashMap<String, Object>) gson.fromJson(ctx.body(), HashMap.class);
        var name = object.get("name");
        var description = object.get("description");
        if (!(name instanceof String) || !(description instanceof String)) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result("JSON object key 'name' or 'description' is not a string.");
            return;
        }

        try {
            var todo = service.createTodo((String) name, (String) description);
            var json = gson.toJson(todo);
            ctx.status(HttpStatus.OK);
            ctx.json(json);
        } catch (TodoExistsException e) {
            ctx.status(HttpStatus.CONFLICT);
            ctx.result("There exists a todo with that name already");
        }
    }

    public void deleteTodo(Context ctx) {

    }

    public void updateTodo(Context ctx) {

    }

}

package me.code;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        var repository = new InMemoryTodoRepository();
        var service = new TodoService(repository);
        var controller = new TodoController(service);

        createApp(controller).start(7070);
    }

    public static Javalin createApp(TodoController controller) {
        var app = Javalin.create(/*config*/)
                .get("/todos", controller::getAllTodos)
                .get("/todo/{name}", controller::getTodo)
                .put("/todo", controller::createTodo)
                .delete("/todo", controller::deleteTodo)
                .post("/todo", controller::updateTodo);

        return app;
    }
}

package me.code;

import com.google.gson.Gson;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Optional;

public class TodoControllerTests {

    @Test
    @DisplayName("Test that an empty list returns")
    void test_get_todos() {
        var repository = new InMemoryTodoRepository();
        var service = new TodoService(repository);
        var controller = new TodoController(service);
        var app = Main.createApp(controller);

        JavalinTest.test(app, (server, client) -> {
            var code = client.get("/todos").code();
            var body = client.get("/todos").body().string();

            Assertions.assertEquals(200, code);
            Assertions.assertEquals("[]", body);
        });
    }

    @Test
    @DisplayName("Test that a todo is created normally")
    void test_create_todo() {
        var repository = new InMemoryTodoRepository();
        var service = new TodoService(repository);
        var controller = new TodoController(service);
        var app = Main.createApp(controller);

        JavalinTest.test(app, (server, client) -> {
            var map = new HashMap<>();
            map.put("name", "hej");
            map.put("description", "en beskrivning");

            var gson = new Gson();
            var todo = new Todo("hej", "en beskrivning");
            var output = gson.toJson(todo);

            var response = client.put("/todo", map);
            var code = response.code();
            var body = response.body();

            Assertions.assertEquals(200, code);
            Assertions.assertEquals(output, body.string());
        });
    }

    @Test
    @DisplayName("Test create duplicate todo")
    void test_duplicate_todo() {
        var todo = new Todo("exempelTodo", "beskrivning");
        var repository = Mockito.mock(TodoRepository.class);
        Mockito.when(repository.getByName(todo.getName())).thenReturn(Optional.of(todo));

        var service = new TodoService(repository);
        var controller = new TodoController(service);
        var app = Main.createApp(controller);

        JavalinTest.test(app, (server, client) -> {
            var map = new HashMap<>();
            map.put("name", todo.getName());
            map.put("description", todo.getDescription());

            var response = client.put("/todo", map);
            var code = response.code();

            Assertions.assertEquals(409, code);
        });
    }

    @Test
    @DisplayName("Test find todo by name")
    void test_find_by_name() {
        var repository = new InMemoryTodoRepository();
        var service = new TodoService(repository);
        var controller = new TodoController(service);
        var app = Main.createApp(controller);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/todo/ettnamn");

            Assertions.assertEquals(404, response.code());
        });
    }

    @Test
    @DisplayName("Test find todo by name existing")
    void test_find_todo() {
        var todo = new Todo("exempelTodo", "beskrivning");
        var gson = new Gson();
        var output = gson.toJson(todo);
        var repository = Mockito.mock(TodoRepository.class);
        Mockito.when(repository.getByName(todo.getName())).thenReturn(Optional.of(todo));

        var service = new TodoService(repository);
        var controller = new TodoController(service);
        var app = Main.createApp(controller);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/todo/exempelTodo");
            var code = response.code();
            var body = response.body().string();

            Assertions.assertEquals(200, code);
            Assertions.assertEquals(output, body);
        });
    }

}

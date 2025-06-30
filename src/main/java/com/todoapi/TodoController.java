package com.todoapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
    private static List<Todo> todoList;

    public TodoController() {
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));

    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoList);
    }

    @PostMapping
    /*
    We can use this annotation to set the status code : @ResponseStatus(HttpStatus.CREATED)
     */
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {
        todoList.add(newTodo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newTodo);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId) {
        for (Todo todo : todoList) {
            if (todo.getId() == todoId) {
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Todo not found !");
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long todoId) {
        Iterator<Todo> iterator = todoList.iterator();

        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == todoId) {
                iterator.remove();
                return ResponseEntity
                        .ok("Todo Deleted Successfully");
            }
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Todo not found");
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<String> updateTodo(@PathVariable Long todoId, @RequestBody Todo updatedTodo) {
        for (Todo todo : todoList) {
            if (todo.getId() == todoId) {
                todo.setTitle(updatedTodo.getTitle());
                todo.setCompleted(updatedTodo.isCompleted());
                todo.setUserId(updatedTodo.getUserId());

                return ResponseEntity
                        .ok("Todo updated Successfully");
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Todo not found");
    }
}

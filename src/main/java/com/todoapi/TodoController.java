package com.todoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    // field injection not recommended : you cannot keep prop final this can be reassign-able , it can make testing procedure harder
    // @Autowired  //no need to do constructor dependency injection
    // @Qualifier("another") //eliminate the issue of which bean need to be injected
    private TodoService todoService; //composition

    // @Qualifier("fake")
    private TodoService todoService2;

    private static List<Todo> todoList;

    //TodoController class automatically inject, pass, send the todoService class in the controller function
    public TodoController(
            @Qualifier("another") TodoService todoService,
            @Qualifier("fake") TodoService todoService2
    ) {
        this.todoService = todoService;
        this.todoService2 = todoService2;
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));

    }

    // QUERY PARAM IN API : property (required = false) makes the param as not compulsory to make sure it gets called without mentioning isCompleted params
    // QUERY PARAM IN API : property (defaultValue = "true") we can change the default value to true also
    @GetMapping
    public ResponseEntity<List<Todo>> getTodo(@RequestParam(required = false, defaultValue = "true") boolean isCompleted) {
        System.out.println("Incoming query params: " + isCompleted + "\n" + this.todoService2.doSomething()); /*+ "\n" + todoService2.doSomething()*/
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

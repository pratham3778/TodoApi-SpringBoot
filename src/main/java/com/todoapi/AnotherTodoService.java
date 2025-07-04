package com.todoapi;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("another")
// @Primary
public class AnotherTodoService implements TodoService{

    @Override
    public String doSomething() {
        return "Another do something called...";
    }
}

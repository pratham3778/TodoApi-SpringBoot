package com.todoapi;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component // spring auto manage beans of TodoService
@Service //service layer handles business logic
public class FakeTodoService implements TodoService {

    public String doSomething() {
        return "Fake do Something called..";
    }
}

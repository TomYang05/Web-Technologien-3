package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController

public class ToDoController {
    @GetMapping("/")
    public List<ToDoEntry> index() {
        return List.of(new ToDoEntry("M1"), new ToDoEntry("M2"),new ToDoEntry("M3"),new ToDoEntry("M4"));
    }
}

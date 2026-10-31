package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController

public class ToDoController {
    record VocabEntry(int id,String vocabulary,String translation) {}

    @GetMapping("/Vocabs")
    public List<VocabEntry> index() {
        return List.of( new VocabEntry(1,"house","Haus"),
                        new VocabEntry(2,"dog","Hund"),
                        new VocabEntry(3,"Cat","Katze"),
                        new VocabEntry(4,"Rat","Ratte"));
    }
}

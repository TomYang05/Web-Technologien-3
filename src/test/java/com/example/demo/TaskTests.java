package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTests {

    private final Task task = Task.input("Frage", List.of("System.out.println(\"Hallo Welt\");"), "");

    @Test
    void acceptsExactAnswer() {
        assertTrue(task.isCorrect("System.out.println(\"Hallo Welt\");"));
    }

    @Test
    void ignoresWhitespaceAroundSymbols() {
        assertTrue(task.isCorrect("  System.out.println( \"Hallo Welt\" ) ;  "));
    }

    @Test
    void keepsWhitespaceBetweenWords() {
        assertEquals("int x=5;", Task.normalize("int   x = 5 ;"));
    }

    @Test
    void rejectsWrongOrMissingAnswer() {
        assertFalse(task.isCorrect("System.out.print(\"Hallo Welt\");"));
        assertFalse(task.isCorrect(null));
    }

    @Test
    void multipleChoiceNeedsCorrectAnswerAmongOptions() {
        assertThrows(IllegalArgumentException.class,
                () -> Task.multipleChoice("Frage", List.of("a", "b"), "c", ""));
    }
}

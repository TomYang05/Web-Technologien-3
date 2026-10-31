package com.example.demo;

import java.util.List;

public record Task(
        int id,
        TaskType type,
        String question,
        List<String> options,
        List<String> acceptedAnswers,
        String explanation,
        int xp) implements LessonStep {

    /** Phase "Lernen": Wissen mit Multiple Choice abfragen. */
    static Task multipleChoice(String question, List<String> options, String correct, String explanation) {
        if (!options.contains(correct)) {
            throw new IllegalArgumentException("Richtige Antwort fehlt in den Optionen: " + question);
        }
        return new Task(0, TaskType.MULTIPLE_CHOICE, question, options, List.of(correct), explanation, 10);
    }

    /** Phase "Anwenden": selbst Code oder Begriffe eintippen. */
    static Task input(String question, List<String> acceptedAnswers, String explanation) {
        return new Task(0, TaskType.INPUT, question, List.of(), acceptedAnswers, explanation, 15);
    }

    Task withId(int newId) {
        return new Task(newId, type, question, options, acceptedAnswers, explanation, xp);
    }

    public boolean isCorrect(String answer) {
        if (answer == null) {
            return false;
        }
        String given = normalize(answer);
        return acceptedAnswers.stream().map(Task::normalize).anyMatch(given::equals);
    }

    // Leerzeichen sollen bei Code-Eingaben keine Rolle spielen: "int x=5;" == "int x = 5 ;"
    static String normalize(String s) {
        return s.trim()
                .replaceAll("\\s+", " ")
                .replaceAll(" ?([^\\w ]) ?", "$1");
    }

    public String solution() {
        return acceptedAnswers.getFirst();
    }
}

package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Kleine DSL, mit der die Inhalte in MilestoneOne, MilestoneTwo, ... geschrieben werden.
 * IDs werden automatisch vergeben: Lektion = Meilenstein * 100 + Position,
 * Aufgabe = Lektion * 100 + laufende Nummer.
 */
final class LessonFactory {

    private LessonFactory() {
    }

    static Lesson lesson(int milestone, int position, String title, String goal, LessonStep... steps) {
        int lessonId = milestone * 100 + position;

        List<Reading> readings = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        for (LessonStep step : steps) {
            switch (step) {
                case Reading reading -> readings.add(reading);
                case Task task -> tasks.add(task);
            }
        }
        // Erst Lernen (Multiple Choice), dann Anwenden (Eingabe)
        tasks.sort(Comparator.comparing(Task::type));

        List<Task> numbered = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            numbered.add(tasks.get(i).withId(lessonId * 100 + i + 1));
        }
        return new Lesson(lessonId, milestone, position, title, goal, List.copyOf(readings), List.copyOf(numbered));
    }

    static Reading read(String title, String text) {
        return new Reading(title, text.strip(), null);
    }

    static Reading read(String title, String text, String code) {
        return new Reading(title, text.strip(), code.strip());
    }

    static Task learn(String question, List<String> options, String correct, String explanation) {
        return Task.multipleChoice(question, options, correct, explanation);
    }

    static Task apply(String question, List<String> acceptedAnswers, String explanation) {
        return Task.input(question, acceptedAnswers, explanation);
    }
}

package com.example.demo;

import java.util.List;

/**
 * Eine Lektion läuft immer in derselben Reihenfolge ab:
 * Lektion (Ziel) → Lesen (readings) → Lernen (Multiple Choice) → Anwenden (Eingabe).
 */
public record Lesson(int id, int milestone, int position, String title, String goal,
                     List<Reading> readings, List<Task> tasks) {
}

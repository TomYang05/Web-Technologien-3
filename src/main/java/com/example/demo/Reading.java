package com.example.demo;

/**
 * Phase "Lesen": ein kurzer Erklärtext, optional mit Codebeispiel.
 * Im Text trennen Leerzeilen Absätze, "- " am Zeilenanfang macht Aufzählungen
 * und `Backticks` markieren Code.
 */
public record Reading(String title, String text, String code) implements LessonStep {
}

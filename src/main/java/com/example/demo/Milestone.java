package com.example.demo;

import java.util.List;

/**
 * Ein Meilenstein des Projekts mit allen Lektionen, die man dafür braucht.
 * implemented = false heißt: Die Lektionen sind schon da, im Projekt-Code ist der Meilenstein noch nicht gebaut.
 */
public record Milestone(int number, String title, String goal, boolean implemented, List<Lesson> lessons) {
}

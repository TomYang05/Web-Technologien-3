package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Der Lernpfad: alle Meilensteine des Projekts mit ihren Lektionen.
 * Feste Inhalte, bis die Datenbank angebunden ist.
 */
@Component
public class LessonCatalog {

    private final List<Milestone> milestones = List.of(
            MilestoneOne.create(),
            MilestoneTwo.create(),
            MilestoneThree.create(),
            MilestoneFour.create(),
            MilestoneFive.create());

    public List<Milestone> milestones() {
        return milestones;
    }

    /** Alle Lektionen in Lernreihenfolge: Meilenstein für Meilenstein, Position für Position. */
    public List<Lesson> findAll() {
        return milestones.stream().flatMap(milestone -> milestone.lessons().stream()).toList();
    }

    public Optional<Milestone> findMilestone(int number) {
        return milestones.stream().filter(milestone -> milestone.number() == number).findFirst();
    }

    public Optional<Lesson> findLesson(int id) {
        return findAll().stream().filter(lesson -> lesson.id() == id).findFirst();
    }

    public Optional<Task> findTask(int id) {
        return findAll().stream()
                .flatMap(lesson -> lesson.tasks().stream())
                .filter(task -> task.id() == id)
                .findFirst();
    }
}

package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Prüft die Inhalte selbst, damit Tippfehler im Lernpfad früh auffallen. */
class LessonCatalogTests {

    private final LessonCatalog catalog = new LessonCatalog();

    @Test
    void lessonsFollowMilestoneAndPositionOrder() {
        List<Lesson> lessons = catalog.findAll();
        for (int i = 1; i < lessons.size(); i++) {
            Lesson previous = lessons.get(i - 1);
            Lesson current = lessons.get(i);
            boolean sameMilestoneNext = current.milestone() == previous.milestone()
                    && current.position() == previous.position() + 1;
            boolean nextMilestoneStart = current.milestone() == previous.milestone() + 1 && current.position() == 1;
            assertTrue(sameMilestoneNext || nextMilestoneStart, "Lücke vor Lektion " + current.id());
        }
    }

    @Test
    void everyLessonHasAllPhases() {
        for (Lesson lesson : catalog.findAll()) {
            assertFalse(lesson.readings().isEmpty(), "Lesen fehlt in " + lesson.id());
            assertTrue(lesson.tasks().stream().anyMatch(t -> t.type() == TaskType.MULTIPLE_CHOICE),
                    "Lernen fehlt in " + lesson.id());
            assertTrue(lesson.tasks().stream().anyMatch(t -> t.type() == TaskType.INPUT),
                    "Anwenden fehlt in " + lesson.id());
        }
    }

    @Test
    void learnComesBeforeApply() {
        for (Lesson lesson : catalog.findAll()) {
            List<TaskType> types = lesson.tasks().stream().map(Task::type).toList();
            int firstInput = types.indexOf(TaskType.INPUT);
            assertEquals(-1, types.subList(firstInput, types.size()).indexOf(TaskType.MULTIPLE_CHOICE),
                    "Reihenfolge in " + lesson.id());
        }
    }

    @Test
    void taskIdsAreUnique() {
        Set<Integer> ids = new HashSet<>();
        for (Lesson lesson : catalog.findAll()) {
            for (Task task : lesson.tasks()) {
                assertTrue(ids.add(task.id()), "Doppelte Aufgaben-ID " + task.id());
            }
        }
    }

    @Test
    void everySolutionIsAccepted() {
        for (Lesson lesson : catalog.findAll()) {
            for (Task task : lesson.tasks()) {
                assertTrue(task.isCorrect(task.solution()), "Lösung wird nicht akzeptiert: " + task.id());
            }
        }
    }
}

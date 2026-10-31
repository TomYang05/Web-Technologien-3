package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LessonController {

    // Antworten bleiben im Backend, das Frontend bekommt nur Frage und Optionen
    record TaskView(int id, TaskType type, String question, List<String> options, int xp) {
        static TaskView of(Task task) {
            return new TaskView(task.id(), task.type(), task.question(), task.options(), task.xp());
        }
    }

    record LessonSummary(int id, int milestone, int position, String title, String goal,
                         int readingCount, int taskCount, int totalXp) {
        static LessonSummary of(Lesson lesson) {
            int totalXp = lesson.tasks().stream().mapToInt(Task::xp).sum();
            return new LessonSummary(lesson.id(), lesson.milestone(), lesson.position(), lesson.title(),
                    lesson.goal(), lesson.readings().size(), lesson.tasks().size(), totalXp);
        }
    }

    record MilestoneView(int number, String title, String goal, boolean implemented, List<LessonSummary> lessons) {
        static MilestoneView of(Milestone milestone) {
            return new MilestoneView(milestone.number(), milestone.title(), milestone.goal(), milestone.implemented(),
                    milestone.lessons().stream().map(LessonSummary::of).toList());
        }
    }

    record LessonDetail(int id, int milestone, String milestoneTitle, boolean milestoneImplemented, int position,
                        String title, String goal, List<Reading> readings, List<TaskView> tasks) {
    }

    record CheckRequest(String answer) {}

    record CheckResult(boolean correct, String solution, String explanation, int xp) {}

    private final LessonCatalog catalog;

    public LessonController(LessonCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/milestones")
    public List<MilestoneView> milestones() {
        return catalog.milestones().stream().map(MilestoneView::of).toList();
    }

    @GetMapping("/lessons")
    public List<LessonSummary> lessons() {
        return catalog.findAll().stream().map(LessonSummary::of).toList();
    }

    @GetMapping("/lessons/{id}")
    public LessonDetail lesson(@PathVariable int id) {
        Lesson lesson = catalog.findLesson(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lektion nicht gefunden"));
        Milestone milestone = catalog.findMilestone(lesson.milestone()).orElseThrow();
        return new LessonDetail(lesson.id(), lesson.milestone(), milestone.title(), milestone.implemented(),
                lesson.position(), lesson.title(), lesson.goal(), lesson.readings(),
                lesson.tasks().stream().map(TaskView::of).toList());
    }

    @PostMapping("/tasks/{id}/check")
    public CheckResult check(@PathVariable int id, @RequestBody CheckRequest request) {
        Task task = catalog.findTask(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aufgabe nicht gefunden"));
        boolean correct = task.isCorrect(request.answer());
        return new CheckResult(correct, task.solution(), task.explanation(), correct ? task.xp() : 0);
    }
}

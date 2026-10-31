package com.example.demo;

/** Ein Baustein einer Lektion: ein Lesetext oder eine Aufgabe. */
public sealed interface LessonStep permits Reading, Task {
}

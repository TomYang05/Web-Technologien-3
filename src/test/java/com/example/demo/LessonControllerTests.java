package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LessonControllerTests {

    @Autowired
    MockMvc mvc;

    @Test
    void listsMilestonesWithTheirLessons() throws Exception {
        mvc.perform(get("/api/milestones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].implemented").value(true))
                .andExpect(jsonPath("$[3].implemented").value(false))
                .andExpect(jsonPath("$[0].number").value(1))
                .andExpect(jsonPath("$[0].title").value("Backend mit Spring Boot"))
                .andExpect(jsonPath("$[0].lessons[0].id").value(101))
                .andExpect(jsonPath("$[0].lessons[*].milestone", everyItem(is(1))))
                .andExpect(jsonPath("$[1].lessons[*].milestone", everyItem(is(2))));
    }

    @Test
    void listsAllLessonsInLearningOrder() throws Exception {
        mvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].readingCount", greaterThan(0)))
                .andExpect(jsonPath("$[0].taskCount", greaterThan(0)));
    }

    @Test
    void lessonDetailHasReadingsAndTasksWithoutAnswers() throws Exception {
        mvc.perform(get("/api/lessons/102"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java: Variablen & Datentypen"))
                .andExpect(jsonPath("$.milestoneTitle").value("Backend mit Spring Boot"))
                .andExpect(jsonPath("$.milestoneImplemented").value(true))
                .andExpect(jsonPath("$.readings[0].title").value("Variablen"))
                .andExpect(jsonPath("$.readings[0].code").isNotEmpty())
                .andExpect(jsonPath("$.tasks[0].id").value(10201))
                .andExpect(jsonPath("$.tasks[0].type").value("MULTIPLE_CHOICE"))
                .andExpect(jsonPath("$.tasks[0].acceptedAnswers").doesNotExist());
    }

    @Test
    void unknownLessonReturns404() throws Exception {
        mvc.perform(get("/api/lessons/999")).andExpect(status().isNotFound());
    }

    @Test
    void correctAnswerEarnsXp() throws Exception {
        mvc.perform(post("/api/tasks/10201/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":\"int\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correct").value(true))
                .andExpect(jsonPath("$.xp").value(10));
    }

    @Test
    void wrongAnswerShowsSolutionWithoutXp() throws Exception {
        mvc.perform(post("/api/tasks/10201/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":\"String\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correct").value(false))
                .andExpect(jsonPath("$.solution").value("int"))
                .andExpect(jsonPath("$.xp").value(0));
    }

    @Test
    void inputAnswerIgnoresWhitespace() throws Exception {
        mvc.perform(post("/api/tasks/10204/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":\"  int alter=20 ;\"}"))
                .andExpect(jsonPath("$.correct").value(true))
                .andExpect(jsonPath("$.xp").value(15));
    }

    @Test
    void allowsFrontendOrigin() throws Exception {
        mvc.perform(options("/api/lessons")
                        .header("Origin", "http://localhost:5173")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
    }
}

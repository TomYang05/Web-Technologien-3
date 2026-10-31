package com.example.demo;

import java.util.List;

import static com.example.demo.LessonFactory.apply;
import static com.example.demo.LessonFactory.learn;
import static com.example.demo.LessonFactory.lesson;
import static com.example.demo.LessonFactory.read;

/** Meilenstein 3: das Vue-Frontend holt seine Daten vom Spring-Boot-Backend. */
final class MilestoneThree {

    private MilestoneThree() {
    }

    static Milestone create() {
        return new Milestone(3, "Frontend trifft Backend",
                "Die Vue-App lädt ihre Daten per fetch vom Backend und schickt Antworten zurück.", true, List.of(

                lesson(3, 1, "Asynchrones JavaScript",
                        "Du verstehst Promises und kannst mit async/await auf Ergebnisse warten.",
                        read("Warum asynchron?", """
                                Eine Anfrage an das Backend dauert ein paar Millisekunden bis Sekunden. \
                                JavaScript wartet dabei nicht und blockiert die Seite nicht. Stattdessen \
                                bekommst du ein Promise: das Versprechen auf ein Ergebnis, das später kommt.
                                """),
                        read("async und await", """
                                Mit `await` wartest du auf ein Promise, als wäre der Code synchron. `await` \
                                funktioniert nur in Funktionen, die mit `async` markiert sind. Eine \
                                async-Funktion gibt selbst immer ein Promise zurück.
                                """, """
                                async function ladeLektionen() {
                                  const response = await fetch('http://localhost:8080/api/lessons')
                                  const lessons = await response.json()
                                  return lessons
                                }
                                """),
                        read("Fehler abfangen", """
                                Ist das Backend nicht erreichbar, wirft `fetch` einen Fehler. Mit `try` und \
                                `catch` fängst du ihn ab. `finally` läuft immer, egal ob es geklappt hat.
                                """, """
                                try {
                                  lessons.value = await ladeLektionen()
                                } catch {
                                  error.value = 'Läuft das Backend?'
                                } finally {
                                  loading.value = false
                                }
                                """),
                        learn("Was gibt eine async-Funktion zurück?",
                                List.of("Ein Promise", "Immer einen String", "Nichts", "Ein HTML-Element"), "Ein Promise",
                                "Auch wenn du einen Wert mit return zurückgibst, kommt er in einem Promise an."),
                        learn("Wo darfst du await verwenden?",
                                List.of("In einer async-Funktion", "Überall im Template", "Nur in CSS", "Nur im Backend"),
                                "In einer async-Funktion",
                                "Außerhalb einer async-Funktion ist await (fast immer) ein Syntaxfehler."),
                        learn("Wann läuft der finally-Block?",
                                List.of("Immer, egal ob ein Fehler auftrat", "Nur bei einem Fehler",
                                        "Nur wenn alles geklappt hat", "Nie"),
                                "Immer, egal ob ein Fehler auftrat",
                                "Darum setzt man dort z. B. loading wieder auf false."),
                        apply("Ergänze das Schlüsselwort, damit auf das Ergebnis gewartet wird:\nconst response = ___ fetch(url)",
                                List.of("await"),
                                "await pausiert die Funktion, bis das Promise erfüllt ist.")),

                lesson(3, 2, "Daten holen mit fetch",
                        "Du kannst Daten vom Backend abfragen und als JavaScript-Objekte nutzen.",
                        read("fetch", """
                                `fetch(url)` schickt eine HTTP-Anfrage, standardmäßig mit GET. Die Antwort \
                                enthält Status und Body. `response.ok` ist true bei Statuscodes von 200 bis \
                                299, `response.json()` liest den Body als JSON.
                                """, """
                                const response = await fetch('http://localhost:8080/api/lessons')
                                if (!response.ok) {
                                  throw new Error(`Backend antwortet mit ${response.status}`)
                                }
                                const data = await response.json()
                                """),
                        read("Typen für die Antwort", """
                                Mit TypeScript beschreibst du, wie die Daten vom Backend aussehen. Die Felder \
                                entsprechen genau dem Java-Record im Backend. So meldet der Editor Tippfehler, \
                                bevor du die App startest.
                                """, """
                                export interface LessonSummary {
                                  id: number
                                  title: string
                                  totalXp: number
                                }

                                export async function fetchLessons(): Promise<LessonSummary[]> {
                                  const response = await fetch(`${baseUrl}/api/lessons`)
                                  return response.json()
                                }
                                """),
                        learn("Was macht response.json()?",
                                List.of("Liest den Body der Antwort als JSON", "Schickt JSON an den Server",
                                        "Prüft den Statuscode", "Wandelt ein Objekt in einen String um"),
                                "Liest den Body der Antwort als JSON",
                                "Es gibt ein Promise zurück, darum steht davor await."),
                        learn("Was bedeutet response.ok === false?",
                                List.of("Der Server hat einen Fehlerstatus wie 404 geschickt", "Das JSON ist leer",
                                        "Die Seite ist offline", "Die Anfrage war ein POST"),
                                "Der Server hat einen Fehlerstatus wie 404 geschickt",
                                "fetch wirft bei 404 keinen Fehler, darum prüft man ok selbst."),
                        learn("Welche HTTP-Methode benutzt fetch, wenn du keine angibst?",
                                List.of("GET", "POST", "PUT", "Keine"), "GET",
                                "Für POST musst du method: 'POST' angeben."),
                        apply("Lies das JSON aus response (mit await) und speichere es in der Konstante data.",
                                List.of("const data = await response.json()", "const data = await response.json();"),
                                "response.json() liefert ein Promise, darum await.")),

                lesson(3, 3, "Daten in die Komponente laden",
                        "Du kannst beim Öffnen einer Seite Daten laden und Lade- und Fehlerzustände zeigen.",
                        read("onMounted", """
                                `onMounted` läuft, sobald die Komponente auf der Seite angezeigt wird. Das ist \
                                der richtige Moment, um Daten zu laden. Das Ergebnis landet in einem `ref`, \
                                damit Vue die Anzeige aktualisiert.
                                """, """
                                <script setup lang="ts">
                                import { onMounted, ref } from 'vue'
                                import { fetchLessons, type LessonSummary } from '@/api'

                                const lessons = ref<LessonSummary[]>([])
                                const loading = ref(true)
                                const error = ref<string | null>(null)

                                onMounted(async () => {
                                  try {
                                    lessons.value = await fetchLessons()
                                  } catch {
                                    error.value = 'Lektionen konnten nicht geladen werden.'
                                  } finally {
                                    loading.value = false
                                  }
                                })
                                </script>
                                """),
                        read("Zustände anzeigen", """
                                Während des Ladens zeigst du einen Hinweis, bei einem Fehler eine Meldung und \
                                sonst die Daten. Mit `v-if`, `v-else-if` und `v-for` geht das direkt im Template. \
                                So macht es auch die Startseite dieser App.
                                """, """
                                <template>
                                  <p v-if="loading">Lade Lektionen …</p>
                                  <p v-else-if="error">{{ error }}</p>
                                  <ul v-else>
                                    <li v-for="lesson in lessons" :key="lesson.id">{{ lesson.title }}</li>
                                  </ul>
                                </template>
                                """),
                        learn("Wann läuft onMounted?",
                                List.of("Sobald die Komponente angezeigt wird", "Bevor der Browser startet",
                                        "Bei jedem Klick", "Wenn das Backend startet"),
                                "Sobald die Komponente angezeigt wird",
                                "Dort lädt man typischerweise Daten für die Seite."),
                        learn("Warum steht lessons in einem ref statt in einer normalen Variable?",
                                List.of("Damit Vue die Anzeige aktualisiert, wenn die Daten ankommen",
                                        "Damit die Daten im Browser gespeichert werden", "Weil fetch das verlangt",
                                        "Damit das Backend schneller antwortet"),
                                "Damit Vue die Anzeige aktualisiert, wenn die Daten ankommen",
                                "Nur reaktive Werte lösen ein neues Rendern aus."),
                        learn("Was sollte die Seite zeigen, solange die Daten noch laden?",
                                List.of("Einen Lade-Hinweis", "Eine leere weiße Seite", "Eine Fehlermeldung", "Die alten Daten von gestern"),
                                "Einen Lade-Hinweis",
                                "So weiß der Nutzer, dass etwas passiert."),
                        apply("Ergänze, damit die geladenen Daten im ref lessons landen:\nonMounted(async () => {\n  ___ = await fetchLessons()\n})",
                                List.of("lessons.value"),
                                "Im Script schreibst du einen ref über .value.")),

                lesson(3, 4, "CORS",
                        "Du verstehst, warum der Browser Anfragen blockiert, und erlaubst sie im Backend.",
                        read("Die Same-Origin-Policy", """
                                Ein Origin besteht aus Protokoll, Host und Port. Das Frontend läuft auf \
                                `http://localhost:5173`, das Backend auf `http://localhost:8080`. Wegen des \
                                anderen Ports sind das zwei verschiedene Origins.

                                Aus Sicherheitsgründen gibt der Browser die Antwort eines fremden Origins nur \
                                heraus, wenn der Server das ausdrücklich erlaubt. Sonst steht in der Konsole \
                                ein Fehler mit "blocked by CORS policy".
                                """),
                        read("CORS im Backend erlauben", """
                                Im Spring-Boot-Backend erlaubst du das Frontend mit einer Konfigurationsklasse. \
                                Genau diese Klasse steckt auch in unserem Projekt.
                                """, """
                                @Configuration
                                public class WebConfig implements WebMvcConfigurer {

                                    @Override
                                    public void addCorsMappings(CorsRegistry registry) {
                                        registry.addMapping("/api/**")
                                                .allowedOrigins("http://localhost:5173")
                                                .allowedMethods("GET", "POST");
                                    }
                                }
                                """),
                        learn("Sind http://localhost:5173 und http://localhost:8080 derselbe Origin?",
                                List.of("Nein, der Port ist verschieden", "Ja, beides ist localhost",
                                        "Ja, beides ist http", "Nur im Dark Mode"),
                                "Nein, der Port ist verschieden",
                                "Protokoll, Host und Port müssen alle gleich sein."),
                        learn("Wer muss CORS erlauben?",
                                List.of("Das Backend", "Das Frontend", "Der Nutzer im Browser", "Gradle"), "Das Backend",
                                "Der Server sagt mit CORS-Headern, welche Origins seine Antworten lesen dürfen."),
                        learn("Wer blockiert die Antwort, wenn CORS fehlt?",
                                List.of("Der Browser", "Spring Boot", "Vite", "Das Betriebssystem"), "Der Browser",
                                "Mit curl oder direkt im Browser-Tab funktioniert die Anfrage, nur fetch von einem fremden Origin wird geblockt."),
                        apply("Ergänze die erlaubte Herkunft für das Vite-Frontend:\nregistry.addMapping(\"/api/**\").allowedOrigins(\"___\")",
                                List.of("http://localhost:5173"),
                                "Das ist der Origin, auf dem der Vite-Dev-Server läuft.")),

                lesson(3, 5, "Konfiguration & Umgebungsvariablen",
                        "Du kannst URLs und Ports so einstellen, dass die App lokal und online läuft.",
                        read("Im Frontend", """
                                Die Backend-URL ist lokal eine andere als später im Internet. Darum steht sie \
                                nicht fest im Code, sondern in einer `.env`-Datei. Vite gibt nur Variablen mit \
                                dem Präfix `VITE_` an das Frontend weiter.
                                """, """
                                # .env.development
                                VITE_BACKEND_BASE_URL=http://localhost:8080

                                // api.ts
                                const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL
                                """),
                        read("Im Backend", """
                                In `application.properties` liest `${NAME:standard}` eine Umgebungsvariable und \
                                nimmt sonst den Standardwert. Hoster wie Render geben zum Beispiel den Port so vor.
                                """, """
                                server.port=${PORT:8080}
                                app.cors.allowed-origins=${CORS_ALLOWED_ORIGINS:http://localhost:5173}
                                """),
                        learn("Welches Präfix brauchen Variablen, damit Vite sie ans Frontend weitergibt?",
                                List.of("VITE_", "VUE_", "ENV_", "PUBLIC_"), "VITE_",
                                "So landen nicht aus Versehen geheime Variablen im Browser."),
                        learn("Was bedeutet server.port=${PORT:8080}?",
                                List.of("Nimm die Umgebungsvariable PORT, sonst 8080", "Der Port ist immer 8080",
                                        "PORT und 8080 werden addiert", "Der Port wird zufällig gewählt"),
                                "Nimm die Umgebungsvariable PORT, sonst 8080",
                                "Hinter dem Doppelpunkt steht der Standardwert."),
                        learn("Warum steht die Backend-URL nicht fest im Code?",
                                List.of("Weil sie lokal und im Deployment unterschiedlich ist", "Weil URLs geheim sind",
                                        "Weil TypeScript keine Strings mag", "Weil Vite das verbietet"),
                                "Weil sie lokal und im Deployment unterschiedlich ist",
                                "So läuft derselbe Code überall, nur die Konfiguration ändert sich."),
                        apply("Wie liest du die Variable VITE_BACKEND_BASE_URL im Frontend aus?",
                                List.of("import.meta.env.VITE_BACKEND_BASE_URL"),
                                "Vite stellt alle VITE_-Variablen unter import.meta.env bereit.")),

                lesson(3, 6, "Daten senden mit POST",
                        "Du kannst Daten an das Backend schicken und die Antwort anzeigen. Damit ist M3 geschafft.",
                        read("POST im Backend", """
                                Mit `@PostMapping` nimmt eine Methode POST-Anfragen an. `@RequestBody` wandelt \
                                den JSON-Body der Anfrage in ein Java-Objekt um. So prüft unser Backend deine \
                                Antworten.
                                """, """
                                record CheckRequest(String answer) {}
                                record CheckResult(boolean correct, String solution, int xp) {}

                                @PostMapping("/api/tasks/{id}/check")
                                public CheckResult check(@PathVariable int id, @RequestBody CheckRequest request) {
                                    Task task = catalog.findTask(id);
                                    boolean correct = task.isCorrect(request.answer());
                                    return new CheckResult(correct, task.solution(), correct ? task.xp() : 0);
                                }
                                """),
                        read("POST im Frontend", """
                                Für POST gibst du `fetch` ein zweites Argument mit: die Methode, den \
                                Content-Type-Header und den Body. `JSON.stringify` macht aus einem Objekt einen \
                                JSON-String.
                                """, """
                                export function checkAnswer(taskId: number, answer: string) {
                                  return fetch(`${baseUrl}/api/tasks/${taskId}/check`, {
                                    method: 'POST',
                                    headers: { 'Content-Type': 'application/json' },
                                    body: JSON.stringify({ answer }),
                                  }).then((response) => response.json())
                                }
                                """),
                        read("Wie alles zusammenspielt", """
                                Wenn du in dieser App auf "Prüfen" klickst, passiert genau das, was du gelernt hast:

                                - Die Komponente TaskCard meldet deine Antwort an LessonView
                                - LessonView ruft `checkAnswer` auf, das schickt einen POST an das Backend
                                - CORS erlaubt die Anfrage, der Controller prüft die Antwort
                                - Das Backend antwortet mit JSON, die Antwort landet in einem `ref`
                                - Vue zeigt das Feedback an, und deine XP steigen

                                Damit kennst du jeden Baustein von Project RekSai.
                                """),
                        learn("Welche Annotation liest den JSON-Body im Controller ein?",
                                List.of("@RequestBody", "@PathVariable", "@GetMapping", "@ResponseBody"), "@RequestBody",
                                "@PathVariable liest aus der URL, @RequestBody aus dem Body."),
                        learn("Was macht JSON.stringify({ answer })?",
                                List.of("Wandelt das Objekt in einen JSON-String um", "Liest JSON aus der Antwort",
                                        "Prüft, ob die Antwort richtig ist", "Schickt die Anfrage ab"),
                                "Wandelt das Objekt in einen JSON-String um",
                                "Der Body einer Anfrage ist Text, darum muss das Objekt umgewandelt werden."),
                        learn("Welcher Header sagt dem Server, dass JSON kommt?",
                                List.of("Content-Type: application/json", "Accept-Language: de",
                                        "Origin: localhost", "Cache-Control: no-cache"),
                                "Content-Type: application/json",
                                "Ohne diesen Header weiß Spring nicht, wie es den Body lesen soll."),
                        apply("Ergänze die Methode im fetch-Aufruf:\nfetch(url, { method: '___', ... })",
                                List.of("POST"),
                                "Ohne Angabe wäre es GET, und GET hat keinen Body."),
                        apply("Wandle das Objekt { answer } in einen JSON-String um.",
                                List.of("JSON.stringify({ answer })"),
                                "JSON.stringify ist das Gegenstück zu response.json()."))
        ));
    }
}

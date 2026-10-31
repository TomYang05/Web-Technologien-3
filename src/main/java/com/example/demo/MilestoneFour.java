package com.example.demo;

import java.util.List;

import static com.example.demo.LessonFactory.apply;
import static com.example.demo.LessonFactory.learn;
import static com.example.demo.LessonFactory.lesson;
import static com.example.demo.LessonFactory.read;

/** Meilenstein 4: Daten dauerhaft in einer PostgreSQL-Datenbank speichern. Im Projekt-Code noch geplant. */
final class MilestoneFour {

    private MilestoneFour() {
    }

    static Milestone create() {
        return new Milestone(4, "Datenbank & Persistenz",
                "Lektionen und Lernfortschritt liegen dauerhaft in einer PostgreSQL-Datenbank.", false, List.of(

                lesson(4, 1, "Warum eine Datenbank?",
                        "Du verstehst, warum Daten aus dem Arbeitsspeicher verschwinden und wie Tabellen sie aufbewahren.",
                        read("Das Problem", """
                                Bis jetzt stehen alle Lektionen fest im Java-Code, und dein Fortschritt liegt \
                                nur im Browser. Startet der Server neu, ist alles, was er im Arbeitsspeicher \
                                hatte, weg. Wechselst du den Browser, ist dein Fortschritt weg.

                                Eine Datenbank speichert Daten dauerhaft auf der Festplatte des Servers. Alle \
                                Nutzer und alle Geräte sehen denselben Stand.
                                """),
                        read("Tabellen, Zeilen, Spalten", """
                                Eine relationale Datenbank wie PostgreSQL speichert Daten in Tabellen:

                                - Eine Tabelle entspricht ungefähr einer Java-Klasse, z. B. `lesson`
                                - Jede Spalte ist ein Feld, z. B. `title`
                                - Jede Zeile ist ein Datensatz, also ein Objekt
                                - Der Primärschlüssel `id` macht jede Zeile eindeutig
                                """, """
                                Tabelle lesson
                                +-----+-----------+----------+---------------------------+
                                | id  | milestone | position | title                     |
                                +-----+-----------+----------+---------------------------+
                                | 101 | 1         | 1        | So ist unser Projekt ...  |
                                | 102 | 1         | 2        | Java: Variablen ...       |
                                +-----+-----------+----------+---------------------------+
                                """),
                        learn("Was passiert mit Daten im Arbeitsspeicher, wenn der Server neu startet?",
                                List.of("Sie sind weg", "Sie werden automatisch gespeichert",
                                        "Sie landen im Browser", "Sie werden an Git geschickt"),
                                "Sie sind weg",
                                "Darum braucht man für dauerhafte Daten eine Datenbank."),
                        learn("Wofür ist der Primärschlüssel da?",
                                List.of("Er macht jede Zeile eindeutig", "Er verschlüsselt die Tabelle",
                                        "Er sortiert die Spalten", "Er ist das Passwort der Datenbank"),
                                "Er macht jede Zeile eindeutig",
                                "Über die id findest du genau einen Datensatz wieder."),
                        learn("Was entspricht in einer Tabelle einem einzelnen Objekt?",
                                List.of("Eine Zeile", "Eine Spalte", "Die ganze Tabelle", "Der Tabellenname"), "Eine Zeile",
                                "Spalten sind die Felder, Zeilen die einzelnen Datensätze."),
                        apply("Wie heißt die Spalte, die jede Zeile eindeutig macht, meistens?",
                                List.of("id", "ID"),
                                "Meist heißt der Primärschlüssel einfach id.")),

                lesson(4, 2, "SQL-Grundlagen",
                        "Du kannst mit SQL Daten lesen, anlegen, ändern und filtern.",
                        read("Daten lesen", """
                                SQL ist die Sprache, mit der du mit der Datenbank sprichst. Mit `SELECT` liest \
                                du Daten, mit `WHERE` filterst du sie, mit `ORDER BY` sortierst du sie.
                                """, """
                                SELECT title FROM lesson WHERE milestone = 1 ORDER BY position;
                                """),
                        read("Daten schreiben", """
                                - `INSERT` legt eine neue Zeile an
                                - `UPDATE` ändert vorhandene Zeilen
                                - `DELETE` löscht Zeilen

                                Vergiss bei `UPDATE` und `DELETE` nie das `WHERE`, sonst triffst du alle Zeilen.
                                """, """
                                INSERT INTO progress (user_id, lesson_id, xp) VALUES (1, 101, 45);
                                UPDATE progress SET xp = 50 WHERE user_id = 1 AND lesson_id = 101;
                                DELETE FROM progress WHERE user_id = 1;
                                """),
                        read("Du schreibst selten selbst SQL", """
                                Mit Spring Data JPA erzeugt Spring das SQL für dich. Trotzdem solltest du es \
                                lesen können: In den Logs und beim Debuggen siehst du genau diese Befehle.
                                """),
                        learn("Mit welchem Befehl liest du Daten aus einer Tabelle?",
                                List.of("SELECT", "GET", "READ", "FETCH"), "SELECT",
                                "GET und fetch gehören zu HTTP und JavaScript, in SQL heißt es SELECT."),
                        learn("Was passiert bei UPDATE progress SET xp = 0 ohne WHERE?",
                                List.of("Alle Zeilen bekommen xp = 0", "Nur die erste Zeile ändert sich",
                                        "Es passiert nichts", "Die Tabelle wird gelöscht"),
                                "Alle Zeilen bekommen xp = 0",
                                "WHERE schränkt ein, welche Zeilen betroffen sind."),
                        learn("Womit sortierst du das Ergebnis?",
                                List.of("ORDER BY", "SORT", "GROUP BY", "WHERE"), "ORDER BY",
                                "ORDER BY position sortiert aufsteigend, mit DESC absteigend."),
                        apply("Schreibe die Abfrage, die alle Spalten aller Zeilen der Tabelle lesson liest.",
                                List.of("SELECT * FROM lesson;", "SELECT * FROM lesson", "select * from lesson;", "select * from lesson"),
                                "* steht für alle Spalten."),
                        apply("Ergänze die Bedingung, damit nur Lektionen aus Meilenstein 2 kommen:\nSELECT * FROM lesson WHERE ___;",
                                List.of("milestone = 2", "milestone=2"),
                                "WHERE spalte = wert filtert die Zeilen.")),

                lesson(4, 3, "PostgreSQL anbinden",
                        "Du kannst das Spring-Boot-Backend mit einer PostgreSQL-Datenbank verbinden.",
                        read("Abhängigkeiten", """
                                Für die Datenbank kommen zwei Einträge in `build.gradle`: Spring Data JPA für \
                                den Zugriff aus Java und der PostgreSQL-Treiber für die Verbindung.
                                """, """
                                dependencies {
                                    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
                                    runtimeOnly 'org.postgresql:postgresql'
                                }
                                """),
                        read("Verbindung konfigurieren", """
                                Wo die Datenbank liegt, steht in `application.properties`. Wie bei Port und CORS \
                                kommen die Werte aus Umgebungsvariablen, damit Passwörter nie im Git-Repository \
                                landen.

                                `ddl-auto=update` lässt Hibernate die Tabellen passend zu deinen Klassen anlegen.
                                """, """
                                spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/reksai}
                                spring.datasource.username=${DB_USER:reksai}
                                spring.datasource.password=${DB_PASSWORD}
                                spring.jpa.hibernate.ddl-auto=update
                                """),
                        read("Lokal und online", """
                                Lokal läuft PostgreSQL am einfachsten in Docker. Online bietet z. B. Render eine \
                                fertige Datenbank an, deren URL du als Umgebungsvariable einträgst.
                                """, """
                                docker run --name reksai-db -e POSTGRES_USER=reksai \\
                                  -e POSTGRES_PASSWORD=geheim -e POSTGRES_DB=reksai -p 5432:5432 -d postgres
                                """),
                        learn("Warum steht das Datenbank-Passwort in einer Umgebungsvariable?",
                                List.of("Damit es nicht im Git-Repository landet", "Weil Java keine Passwörter kann",
                                        "Damit die Datenbank schneller ist", "Weil Gradle das verlangt"),
                                "Damit es nicht im Git-Repository landet",
                                "Geheimnisse gehören nie in den Code, sondern in die Umgebung."),
                        learn("Auf welchem Port läuft PostgreSQL standardmäßig?",
                                List.of("5432", "8080", "5173", "3306"), "5432",
                                "3306 ist der Standardport von MySQL."),
                        learn("Was macht spring.jpa.hibernate.ddl-auto=update?",
                                List.of("Legt Tabellen passend zu den Entities an", "Löscht die Datenbank bei jedem Start",
                                        "Aktualisiert Spring Boot", "Lädt Daten aus dem Frontend"),
                                "Legt Tabellen passend zu den Entities an",
                                "Für den Anfang praktisch. Später nimmt man Migrationen wie Flyway."),
                        apply("Wie heißt die Eigenschaft in application.properties, in der die Datenbank-URL steht?",
                                List.of("spring.datasource.url"),
                                "Alle Verbindungsdaten beginnen mit spring.datasource.")),

                lesson(4, 4, "Entities mit JPA",
                        "Du kannst eine Java-Klasse so markieren, dass sie als Tabelle gespeichert wird.",
                        read("@Entity", """
                                Eine Entity ist eine Java-Klasse, die einer Tabelle entspricht. `@Entity` \
                                markiert sie, `@Id` den Primärschlüssel. Mit `@GeneratedValue` vergibt die \
                                Datenbank die id selbst.

                                Records gehen hier nicht: JPA braucht einen leeren Konstruktor und veränderbare \
                                Felder. Darum werden aus unseren Records später Klassen.
                                """, """
                                @Entity
                                public class ProgressEntry {

                                    @Id
                                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                                    private Long id;

                                    private int lessonId;
                                    private int xp;
                                    private int accuracy;

                                    protected ProgressEntry() {
                                    }

                                    public ProgressEntry(int lessonId, int xp, int accuracy) {
                                        this.lessonId = lessonId;
                                        this.xp = xp;
                                        this.accuracy = accuracy;
                                    }

                                    // Getter ...
                                }
                                """),
                        read("Namen in der Datenbank", """
                                Aus dem Klassennamen `ProgressEntry` wird die Tabelle `progress_entry`, aus \
                                `lessonId` die Spalte `lesson_id`. Mit `@Table` und `@Column` kannst du die \
                                Namen auch selbst festlegen.
                                """),
                        learn("Welche Annotation markiert eine Klasse als Datenbank-Tabelle?",
                                List.of("@Entity", "@Table", "@RestController", "@Component"), "@Entity",
                                "@Table ist optional und ändert nur den Tabellennamen."),
                        learn("Warum kann eine Entity kein record sein?",
                                List.of("JPA braucht einen leeren Konstruktor und veränderbare Felder",
                                        "Records können keine Zahlen speichern", "Records sind zu langsam",
                                        "Records gibt es in Java 25 nicht mehr"),
                                "JPA braucht einen leeren Konstruktor und veränderbare Felder",
                                "Hibernate erzeugt das Objekt erst leer und füllt dann die Felder."),
                        learn("Was macht @GeneratedValue an der id?",
                                List.of("Die Datenbank vergibt die id selbst", "Die id wird zufällig im Browser erzeugt",
                                        "Die id wird verschlüsselt", "Die id darf leer bleiben"),
                                "Die Datenbank vergibt die id selbst",
                                "Beim Speichern bekommt jede neue Zeile automatisch die nächste id."),
                        apply("Schreibe die Annotation, die das Feld id als Primärschlüssel markiert.",
                                List.of("@Id"),
                                "@Id kommt aus jakarta.persistence.")),

                lesson(4, 5, "Repositories",
                        "Du kannst Daten speichern und laden, ohne SQL zu schreiben.",
                        read("JpaRepository", """
                                Ein Repository ist ein Interface. Erbt es von `JpaRepository`, baut Spring die \
                                Implementierung automatisch. Die Typen in spitzen Klammern sind die Entity und \
                                der Typ ihrer id.
                                """, """
                                public interface ProgressRepository extends JpaRepository<ProgressEntry, Long> {
                                }

                                progressRepository.save(new ProgressEntry(101, 45, 100));
                                List<ProgressEntry> alle = progressRepository.findAll();
                                Optional<ProgressEntry> eins = progressRepository.findById(1L);
                                """),
                        read("Abfragen über den Methodennamen", """
                                Spring liest den Namen einer Methode und baut daraus SQL. Aus \
                                `findByLessonId` wird `WHERE lesson_id = ?`. Weitere Bausteine sind `And`, \
                                `OrderBy` und `Desc`.
                                """, """
                                public interface ProgressRepository extends JpaRepository<ProgressEntry, Long> {
                                    Optional<ProgressEntry> findByLessonId(int lessonId);
                                    List<ProgressEntry> findAllByOrderByXpDesc();
                                }
                                """),
                        read("Repository im Controller", """
                                Das Repository bekommt der Controller wie den Katalog über den Konstruktor. \
                                Spring setzt es automatisch ein (Dependency Injection).
                                """, """
                                @RestController
                                public class ProgressController {
                                    private final ProgressRepository repository;

                                    public ProgressController(ProgressRepository repository) {
                                        this.repository = repository;
                                    }

                                    @GetMapping("/api/progress")
                                    public List<ProgressEntry> all() {
                                        return repository.findAll();
                                    }
                                }
                                """),
                        learn("Was musst du für ein Repository selbst implementieren?",
                                List.of("Nichts, Spring baut die Implementierung", "Alle SQL-Befehle",
                                        "Eine Klasse pro Methode", "Den Datenbanktreiber"),
                                "Nichts, Spring baut die Implementierung",
                                "Du schreibst nur das Interface."),
                        learn("Welche Methode speichert ein Objekt?",
                                List.of("save()", "insert()", "store()", "persistNow()"), "save()",
                                "save legt neue Objekte an und aktualisiert vorhandene."),
                        learn("Was liefert findById?",
                                List.of("Ein Optional, weil es die id vielleicht nicht gibt", "Immer ein Objekt",
                                        "Eine Liste", "Einen SQL-String"),
                                "Ein Optional, weil es die id vielleicht nicht gibt",
                                "Mit orElseThrow machst du daraus z. B. eine 404-Antwort."),
                        apply("Ergänze die Typen: Repository für die Entity ProgressEntry mit id vom Typ Long.\npublic interface ProgressRepository extends JpaRepository<___> {}",
                                List.of("ProgressEntry, Long"),
                                "Erst die Entity, dann der Typ ihrer id.")),

                lesson(4, 6, "Beziehungen zwischen Tabellen",
                        "Du kannst Lektionen und Aufgaben in der Datenbank miteinander verknüpfen.",
                        read("Eins zu viele", """
                                Eine Lektion hat viele Aufgaben, jede Aufgabe gehört zu genau einer Lektion. In \
                                der Datenbank hat die Tabelle `task` dafür eine Spalte `lesson_id`, einen \
                                Fremdschlüssel.

                                In JPA beschreibst du das mit `@ManyToOne` auf der Seite der Aufgabe und \
                                `@OneToMany` auf der Seite der Lektion.
                                """, """
                                @Entity
                                public class TaskEntity {
                                    @Id
                                    private int id;

                                    @ManyToOne
                                    private LessonEntity lesson;
                                }

                                @Entity
                                public class LessonEntity {
                                    @Id
                                    private int id;

                                    @OneToMany(mappedBy = "lesson")
                                    @OrderBy("id")
                                    private List<TaskEntity> tasks;
                                }
                                """),
                        read("Entities nicht direkt ausliefern", """
                                Entities mit Beziehungen verweisen gegenseitig aufeinander. Gibst du sie direkt \
                                als JSON zurück, entsteht eine Endlosschleife. Darum wandelt der Controller sie \
                                vorher in Records um, so wie heute schon `TaskView` die Lösungen versteckt.
                                """),
                        learn("Wo steht der Fremdschlüssel bei Lektion und Aufgaben?",
                                List.of("In der Tabelle task als lesson_id", "In der Tabelle lesson als task_id",
                                        "In einer Datei", "Im Frontend"),
                                "In der Tabelle task als lesson_id",
                                "Die \"viele\"-Seite merkt sich, zu wem sie gehört."),
                        learn("Welche Annotation steht an List<TaskEntity> tasks in der Lektion?",
                                List.of("@OneToMany", "@ManyToOne", "@ManyToMany", "@Id"), "@OneToMany",
                                "Eine Lektion, viele Aufgaben."),
                        learn("Warum liefert der Controller Records statt Entities aus?",
                                List.of("Um Endlosschleifen und versteckte Felder im JSON zu vermeiden",
                                        "Weil Records schneller in der Datenbank sind", "Weil Entities kein JSON können",
                                        "Weil Vue keine Klassen versteht"),
                                "Um Endlosschleifen und versteckte Felder im JSON zu vermeiden",
                                "So bestimmt das Backend genau, was nach außen geht."),
                        apply("Welche Annotation steht an LessonEntity lesson in der Aufgabe?",
                                List.of("@ManyToOne"),
                                "Viele Aufgaben gehören zu einer Lektion.")),

                lesson(4, 7, "Fortschritt speichern",
                        "Du kannst den Lernfortschritt per POST in der Datenbank speichern. Damit ist M4 geschafft.",
                        read("Vom Browser in die Datenbank", """
                                Heute speichert `progress.ts` den Fortschritt im localStorage. Mit M4 schickt \
                                das Frontend das Ergebnis einer Lektion an das Backend, und das speichert es in \
                                der Datenbank. Neu angelegte Daten beantwortet man mit `201 Created`.
                                """, """
                                record ProgressRequest(int lessonId, int xp, int accuracy) {}

                                @PostMapping("/api/progress")
                                public ResponseEntity<ProgressEntry> save(@RequestBody ProgressRequest request) {
                                    ProgressEntry entry = repository.save(
                                            new ProgressEntry(request.lessonId(), request.xp(), request.accuracy()));
                                    return ResponseEntity.status(HttpStatus.CREATED).body(entry);
                                }
                                """),
                        read("Im Frontend", """
                                `recordResult` in `progress.ts` ruft dann statt `localStorage.setItem` die API \
                                auf. Weil alle Seiten nur `recordResult` und `lessonState` benutzen, ändert sich \
                                an den Komponenten nichts. Genau dafür ist die Logik in einer eigenen Datei.
                                """, """
                                export async function recordResult(lessonId: number, xp: number, accuracy: number) {
                                  await fetch(`${baseUrl}/api/progress`, {
                                    method: 'POST',
                                    headers: { 'Content-Type': 'application/json' },
                                    body: JSON.stringify({ lessonId, xp, accuracy }),
                                  })
                                }
                                """),
                        learn("Welcher Statuscode passt, wenn ein neuer Datensatz angelegt wurde?",
                                List.of("201 Created", "200 OK", "204 No Content", "404 Not Found"), "201 Created",
                                "201 sagt: Es gibt jetzt etwas Neues."),
                        learn("Womit setzt du im Controller einen eigenen Statuscode?",
                                List.of("ResponseEntity", "System.out.println", "@Entity", "List.of"), "ResponseEntity",
                                "ResponseEntity verpackt Status, Header und Body."),
                        learn("Warum ändern sich die Vue-Komponenten beim Umstieg nicht?",
                                List.of("Sie benutzen nur die Funktionen aus progress.ts", "Vue speichert nichts",
                                        "Weil die Datenbank im Browser läuft", "Weil localStorage und SQL gleich sind"),
                                "Sie benutzen nur die Funktionen aus progress.ts",
                                "Eine klare Schnittstelle macht den Austausch einfach."),
                        apply("Mit welcher Annotation nimmt die Methode POST-Anfragen auf /api/progress an?",
                                List.of("@PostMapping(\"/api/progress\")", "@PostMapping(value = \"/api/progress\")",
                                        "@PostMapping(path = \"/api/progress\")"),
                                "Wie @GetMapping, nur für POST."))
        ));
    }
}

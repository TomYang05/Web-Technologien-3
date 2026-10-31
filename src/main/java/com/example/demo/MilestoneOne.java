package com.example.demo;

import java.util.List;

import static com.example.demo.LessonFactory.apply;
import static com.example.demo.LessonFactory.learn;
import static com.example.demo.LessonFactory.lesson;
import static com.example.demo.LessonFactory.read;

/** Meilenstein 1: ein Spring-Boot-Backend mit einem REST-Endpoint. */
final class MilestoneOne {

    private MilestoneOne() {
    }

    static Milestone create() {
        return new Milestone(1, "Backend mit Spring Boot",
                "Ein Java-Backend, das unter einer URL eine Liste als JSON ausliefert.", true, List.of(

                lesson(1, 1, "So ist unser Projekt aufgebaut",
                        "Du weißt, aus welchen Teilen die App besteht und wie sie miteinander reden.",
                        read("Zwei Programme, eine App", """
                                Unsere App besteht aus zwei getrennten Programmen. Das Backend läuft auf einem \
                                Server und verwaltet die Daten. Das Frontend läuft im Browser und zeigt sie an.

                                Das Backend schreiben wir in Java mit Spring Boot, das Frontend in JavaScript \
                                mit Vue. Beide sind eigene Projekte mit eigenem Git-Repository.
                                """),
                        read("Wie die beiden miteinander reden", """
                                Frontend und Backend tauschen Daten über HTTP aus. Das Frontend schickt eine \
                                Anfrage (Request) an eine URL, das Backend antwortet (Response) mit Daten im \
                                JSON-Format.

                                - Request: `GET http://localhost:8080/api/lessons`
                                - Response: eine Liste von Lektionen als JSON
                                """, """
                                Browser (Vue)   --- GET /api/lessons --->   Server (Spring Boot)
                                Browser (Vue)   <--- 200 OK + JSON -----   Server (Spring Boot)
                                """),
                        read("Der Fahrplan", """
                                Wir bauen das Projekt in Meilensteinen. Jeder baut auf dem vorigen auf:

                                - M1: ein Backend mit einem REST-Endpoint
                                - M2: ein Vue-Frontend, das eine Liste anzeigt
                                - M3: das Frontend holt seine Daten vom Backend

                                Genau in dieser Reihenfolge ist auch diese App aufgebaut. Am Ende hast du \
                                alles gelernt, was in Project RekSai steckt.
                                """),
                        learn("Wo läuft das Frontend?",
                                List.of("Im Browser", "Auf dem Datenbankserver", "In der Gradle-Konsole", "Im Java-Compiler"),
                                "Im Browser",
                                "Das Frontend ist HTML, CSS und JavaScript und wird vom Browser ausgeführt."),
                        learn("In welchem Format schickt unser Backend Daten an das Frontend?",
                                List.of("JSON", "HTML", "PDF", "CSV"), "JSON",
                                "JSON ist ein Textformat für Objekte und Listen, das JavaScript direkt versteht."),
                        learn("Womit bauen wir das Backend?",
                                List.of("Java mit Spring Boot", "JavaScript mit Vue", "CSS mit Flexbox", "Python mit Django"),
                                "Java mit Spring Boot",
                                "Spring Boot ist ein Java-Framework für Server-Anwendungen."),
                        apply("Mit welcher HTTP-Methode fragt das Frontend Daten nur ab, ohne etwas zu ändern?",
                                List.of("GET", "get"),
                                "GET liest Daten. Zum Senden von Daten nimmt man POST.")),

                lesson(1, 2, "Java: Variablen & Datentypen",
                        "Du kannst in Java Werte in Variablen mit dem passenden Typ speichern.",
                        read("Variablen", """
                                Eine Variable ist ein benannter Speicherplatz für einen Wert. In Java gibst du \
                                immer den Typ mit an: erst der Typ, dann der Name, dann `=` und der Wert.

                                Jede Anweisung endet mit einem Semikolon `;`.
                                """, """
                                int alter = 20;
                                double preis = 9.99;
                                boolean fertig = false;
                                String name = "Ada";
                                """),
                        read("Die wichtigsten Typen", """
                                - `int`: ganze Zahlen wie 42 oder -7
                                - `double`: Kommazahlen wie 9.99
                                - `boolean`: genau true oder false
                                - `String`: Text in doppelten Anführungszeichen

                                `String` wird großgeschrieben, weil es eine Klasse ist. Die anderen sind \
                                einfache (primitive) Typen.
                                """),
                        learn("Welcher Datentyp speichert ganze Zahlen?",
                                List.of("int", "String", "boolean", "double"), "int",
                                "int steht für integer und speichert ganze Zahlen."),
                        learn("Welcher Datentyp speichert true oder false?",
                                List.of("char", "boolean", "int", "String"), "boolean",
                                "boolean kennt genau zwei Werte: true und false."),
                        learn("Womit endet in Java eine Anweisung?",
                                List.of(".", ":", ";", "Zeilenumbruch"), ";",
                                "Jede Anweisung wird mit einem Semikolon abgeschlossen."),
                        apply("Deklariere eine int-Variable namens alter mit dem Wert 20.",
                                List.of("int alter = 20;"),
                                "Erst der Typ, dann der Name, dann = Wert und am Ende ;"),
                        apply("Deklariere eine String-Variable name mit dem Wert Ada.",
                                List.of("String name = \"Ada\";"),
                                "String wird großgeschrieben, der Wert steht in doppelten Anführungszeichen.")),

                lesson(1, 3, "Java: Ausgabe, Bedingungen & Schleifen",
                        "Du kannst Text ausgeben, Entscheidungen treffen und Code wiederholen.",
                        read("Text ausgeben", """
                                Mit `System.out.println(...)` gibst du etwas auf der Konsole aus. Mit `+` hängst \
                                du Strings aneinander.
                                """, """
                                String name = "Ada";
                                System.out.println("Hallo " + name); // Hallo Ada
                                """),
                        read("if und else", """
                                Mit `if` läuft Code nur, wenn eine Bedingung true ist. Der `else`-Zweig läuft \
                                in allen anderen Fällen.

                                Zum Vergleichen nimmst du `==`, `<`, `>`, `<=` und `>=`. Ein einzelnes `=` \
                                weist dagegen einen Wert zu.
                                """, """
                                int punkte = 7;
                                if (punkte >= 5) {
                                    System.out.println("bestanden");
                                } else {
                                    System.out.println("nochmal üben");
                                }
                                """),
                        read("Schleifen", """
                                Eine `for`-Schleife hat drei Teile: Start, Bedingung und Schritt. Sie läuft, \
                                solange die Bedingung true ist.
                                """, """
                                for (int i = 0; i < 3; i++) {
                                    System.out.println(i); // 0, 1, 2
                                }
                                """),
                        learn("Welche Anweisung gibt in Java Text auf der Konsole aus?",
                                List.of("console.log(\"Hi\");", "System.out.println(\"Hi\");", "print(\"Hi\");", "echo \"Hi\";"),
                                "System.out.println(\"Hi\");",
                                "console.log ist JavaScript, System.out.println ist Java."),
                        learn("Mit welchem Operator prüfst du, ob zwei int-Werte gleich sind?",
                                List.of("=", "==", "===", "equals"), "==",
                                "= weist zu, == vergleicht. === gibt es in Java nicht."),
                        learn("Wie oft läuft for (int i = 0; i < 3; i++)?",
                                List.of("2-mal", "3-mal", "4-mal", "unendlich"), "3-mal",
                                "i nimmt die Werte 0, 1 und 2 an."),
                        apply("Gib den Text Hallo Welt auf der Konsole aus.",
                                List.of("System.out.println(\"Hallo Welt\");"),
                                "Strings stehen in doppelten Anführungszeichen."),
                        apply("Was wird ausgegeben?\nint x = 7;\nif (x > 5) { System.out.println(\"groß\"); } else { System.out.println(\"klein\"); }",
                                List.of("groß", "gross"),
                                "7 ist größer als 5, also läuft der if-Zweig.")),

                lesson(1, 4, "Java: Klassen & Records",
                        "Du kannst eigene Datentypen bauen, so wie unser Backend seine Lektionen beschreibt.",
                        read("Klassen", """
                                Eine Klasse ist ein Bauplan für Objekte. Sie hat Felder (Daten) und Methoden \
                                (Verhalten). Mit `new` erzeugst du ein Objekt daraus.

                                Der Konstruktor hat denselben Namen wie die Klasse. `this.title` meint das Feld \
                                des Objekts, `title` allein den Parameter.
                                """, """
                                public class Lesson {
                                    private String title;

                                    public Lesson(String title) {
                                        this.title = title;
                                    }

                                    public String getTitle() {
                                        return title;
                                    }
                                }

                                Lesson lesson = new Lesson("Variablen");
                                """),
                        read("Records", """
                                Für reine Datenobjekte gibt es `record`. Eine Zeile ersetzt Konstruktor, \
                                Getter, equals und toString. Die Werte lassen sich danach nicht mehr ändern.

                                Genau so beschreibt unser Backend seine Lektionen und Aufgaben. Achtung: Die \
                                Getter heißen wie das Feld, also `word()` statt `getWord()`.
                                """, """
                                record Vocab(int id, String word, String translation) {}

                                Vocab v = new Vocab(1, "house", "Haus");
                                System.out.println(v.word()); // house
                                """),
                        learn("Womit erzeugst du ein neues Objekt?",
                                List.of("new", "create", "make", "this"), "new",
                                "new ruft den Konstruktor auf und liefert ein neues Objekt."),
                        learn("Wie liest du beim record Vocab das Feld word aus?",
                                List.of("v.word()", "v.getWord()", "v.word", "v->word"), "v.word()",
                                "Records erzeugen Zugriffsmethoden mit dem Namen des Felds."),
                        learn("Was meint this.title im Konstruktor?",
                                List.of("Das Feld des Objekts", "Den Parameter title", "Eine neue lokale Variable", "Die Klasse Lesson"),
                                "Das Feld des Objekts",
                                "this zeigt auf das aktuelle Objekt, damit unterscheidest du Feld und Parameter."),
                        apply("Schreibe einen record Task mit den Feldern int id und String question.",
                                List.of("record Task(int id, String question) {}", "public record Task(int id, String question) {}"),
                                "record, Name, Felder in runden Klammern und ein leerer Körper {}.")),

                lesson(1, 5, "Java: Listen",
                        "Du kannst mehrere Objekte in einer Liste sammeln und durchlaufen.",
                        read("List", """
                                Mehrere Werte speicherst du in einer `List`. Mit `List.of(...)` erstellst du \
                                eine feste Liste. Der Typ in spitzen Klammern sagt, was drin ist.

                                Der erste Eintrag hat den Index 0.
                                """, """
                                List<String> namen = List.of("Ada", "Linus", "Grace");
                                System.out.println(namen.size());  // 3
                                System.out.println(namen.get(0));  // Ada
                                """),
                        read("Durch Listen laufen", """
                                Mit der for-each-Schleife gehst du jedes Element durch. Mit Streams kannst du \
                                Listen filtern und umwandeln, ohne selbst eine Schleife zu schreiben.
                                """, """
                                for (String name : namen) {
                                    System.out.println(name);
                                }

                                List<String> kurz = namen.stream()
                                        .filter(n -> n.length() <= 3)
                                        .toList(); // [Ada]
                                """),
                        learn("Was liefert namen.get(0) bei List.of(\"Ada\", \"Linus\", \"Grace\")?",
                                List.of("\"Ada\"", "\"Linus\"", "\"Grace\"", "Einen Fehler"), "\"Ada\"",
                                "Listen beginnen bei Index 0."),
                        learn("Was bedeutet List<String>?",
                                List.of("Eine Liste, die Strings enthält", "Ein String, der eine Liste ist",
                                        "Eine Liste mit genau einem String", "Ein Array aus Zeichen"),
                                "Eine Liste, die Strings enthält",
                                "Der Typ in spitzen Klammern legt fest, was in der Liste stehen darf."),
                        learn("Was liefert namen.size()?",
                                List.of("2", "3", "4", "Den letzten Namen"), "3",
                                "size() gibt die Anzahl der Elemente zurück."),
                        apply("Erstelle eine List<Integer> namens zahlen mit den Werten 1, 2 und 3.",
                                List.of("List<Integer> zahlen = List.of(1, 2, 3);"),
                                "List.of(...) erzeugt eine Liste mit den angegebenen Werten.")),

                lesson(1, 6, "Spring Boot: Das Projekt aufsetzen",
                        "Du kannst ein Spring-Boot-Projekt anlegen und starten.",
                        read("Was ist Spring Boot?", """
                                Spring Boot ist ein Java-Framework für Server. Es bringt einen eingebauten \
                                Webserver mit. Du schreibst nur deinen Code, startest das Programm und hast \
                                einen laufenden Server.
                                """),
                        read("Projekt erzeugen", """
                                Auf start.spring.io wählst du Gradle, Java und die Abhängigkeit Spring Web. \
                                Heraus kommt ein fertiges Projekt zum Herunterladen.

                                - `build.gradle`: Abhängigkeiten und Java-Version
                                - `src/main/java/...`: dein Java-Code
                                - `src/main/resources/application.properties`: Einstellungen
                                - `gradlew`: der Gradle-Wrapper, damit du Gradle nicht installieren musst
                                """),
                        read("Die Startklasse", """
                                Jedes Spring-Boot-Projekt hat eine Klasse mit `@SpringBootApplication` und einer \
                                `main`-Methode. Mit `./gradlew bootRun` startest du den Server, er läuft dann \
                                auf Port 8080.
                                """, """
                                @SpringBootApplication
                                public class DemoApplication {

                                    public static void main(String[] args) {
                                        SpringApplication.run(DemoApplication.class, args);
                                    }
                                }
                                """),
                        learn("Welche Annotation steht über der Startklasse?",
                                List.of("@SpringBootApplication", "@RestController", "@Main", "@Start"),
                                "@SpringBootApplication",
                                "Sie schaltet die automatische Konfiguration von Spring Boot ein."),
                        learn("In welcher Datei stehen die Abhängigkeiten des Projekts?",
                                List.of("build.gradle", "application.properties", "README.md", "DemoApplication.java"),
                                "build.gradle",
                                "Gradle liest dort, welche Bibliotheken heruntergeladen werden."),
                        learn("Auf welchem Port läuft Spring Boot standardmäßig?",
                                List.of("8080", "5173", "3000", "80"), "8080",
                                "5173 ist der Port des Vite-Dev-Servers fürs Frontend."),
                        apply("Mit welchem Befehl startest du das Backend über den Gradle-Wrapper?",
                                List.of("./gradlew bootRun", "gradlew bootRun", "gradlew.bat bootRun", ".\\gradlew.bat bootRun"),
                                "bootRun kompiliert das Projekt und startet den Server.")),

                lesson(1, 7, "Der erste REST-Endpoint",
                        "Du kannst einen Controller schreiben, der Daten als JSON ausliefert.",
                        read("Controller", """
                                Ein Controller nimmt HTTP-Anfragen entgegen. Mit `@RestController` über der \
                                Klasse und `@GetMapping("/pfad")` über einer Methode ist sie unter dieser URL \
                                erreichbar.

                                Was die Methode zurückgibt, wandelt Spring automatisch in JSON um.
                                """, """
                                @RestController
                                public class VocabController {

                                    record VocabEntry(int id, String word, String translation) {}

                                    @GetMapping("/api/vocabs")
                                    public List<VocabEntry> index() {
                                        return List.of(
                                                new VocabEntry(1, "house", "Haus"),
                                                new VocabEntry(2, "dog", "Hund"));
                                    }
                                }
                                """),
                        read("Was im Browser ankommt", """
                                Starte das Backend und öffne `http://localhost:8080/api/vocabs` im Browser. \
                                Aus der Liste von Records wird eine JSON-Liste von Objekten.
                                """, """
                                [
                                  { "id": 1, "word": "house", "translation": "Haus" },
                                  { "id": 2, "word": "dog", "translation": "Hund" }
                                ]
                                """),
                        read("Werte aus dem Pfad", """
                                Oft willst du ein einzelnes Objekt abfragen, zum Beispiel eine Lektion. Den \
                                variablen Teil der URL schreibst du in geschweifte Klammern und holst ihn mit \
                                `@PathVariable`.
                                """, """
                                @GetMapping("/api/lessons/{id}")
                                public Lesson lesson(@PathVariable int id) {
                                    return catalog.findLesson(id);
                                }
                                """),
                        learn("Welche Annotation macht aus einer Klasse einen REST-Controller?",
                                List.of("@RestController", "@GetMapping", "@SpringBootApplication", "@Component"),
                                "@RestController",
                                "@RestController sorgt dafür, dass Rückgabewerte als JSON geschickt werden."),
                        learn("Was passiert mit der zurückgegebenen List<VocabEntry>?",
                                List.of("Spring wandelt sie automatisch in JSON um", "Sie wird als HTML-Tabelle angezeigt",
                                        "Man muss sie selbst in einen String umwandeln", "Sie wird in eine Datei geschrieben"),
                                "Spring wandelt sie automatisch in JSON um",
                                "Die Bibliothek Jackson macht aus Records und Listen JSON."),
                        learn("Unter welcher URL ist index() erreichbar?",
                                List.of("http://localhost:8080/api/vocabs", "http://localhost:5173/api/vocabs",
                                        "http://localhost:8080/index", "http://localhost:8080/VocabController"),
                                "http://localhost:8080/api/vocabs",
                                "Der Pfad kommt aus @GetMapping, der Port vom Backend."),
                        apply("Schreibe die Annotation, die eine Methode unter dem Pfad /api/lessons per GET erreichbar macht.",
                                List.of("@GetMapping(\"/api/lessons\")", "@GetMapping(value = \"/api/lessons\")", "@GetMapping(path = \"/api/lessons\")"),
                                "@GetMapping bekommt den Pfad als String."),
                        apply("Ergänze den Parameter, damit {id} aus dem Pfad in der Variable id landet:\n@GetMapping(\"/api/lessons/{id}\")\npublic Lesson lesson(___) { ... }",
                                List.of("@PathVariable int id", "@PathVariable Integer id", "@PathVariable(\"id\") int id"),
                                "@PathVariable verbindet den Platzhalter in der URL mit dem Parameter.")),

                lesson(1, 8, "HTTP & JSON verstehen",
                        "Du verstehst Methoden, Statuscodes und JSON, die Sprache zwischen Frontend und Backend.",
                        read("HTTP-Methoden", """
                                Jede Anfrage hat eine Methode, die sagt, was passieren soll:

                                - `GET`: Daten lesen
                                - `POST`: Daten schicken oder anlegen
                                - `PUT`: Daten ersetzen
                                - `DELETE`: Daten löschen
                                """),
                        read("Statuscodes", """
                                Jede Antwort hat einen Statuscode. Er sagt, ob alles geklappt hat:

                                - `200 OK`: alles gut
                                - `201 Created`: etwas wurde angelegt
                                - `400 Bad Request`: die Anfrage war fehlerhaft
                                - `404 Not Found`: gibt es nicht
                                - `500 Internal Server Error`: Fehler im Backend

                                Unser Backend antwortet mit 404, wenn eine Lektion nicht existiert.
                                """, """
                                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lektion nicht gefunden");
                                """),
                        read("JSON", """
                                JSON kennt Objekte in `{ }` und Listen in `[ ]`. Schlüssel stehen immer in \
                                doppelten Anführungszeichen. Werte sind Strings, Zahlen, true/false, null, \
                                Objekte oder Listen.

                                Aus einem Java-Record wird ein JSON-Objekt, die Feldnamen werden zu Schlüsseln.
                                """, """
                                {
                                  "id": 101,
                                  "title": "So ist unser Projekt aufgebaut",
                                  "tasks": [ { "id": 10101, "xp": 10 } ]
                                }
                                """),
                        learn("Welcher Statuscode bedeutet \"nicht gefunden\"?",
                                List.of("200", "400", "404", "500"), "404",
                                "404 Not Found: die angefragte Ressource gibt es nicht."),
                        learn("Mit welcher Methode schickt man Daten an den Server?",
                                List.of("GET", "POST", "FETCH", "SEND"), "POST",
                                "POST schickt Daten im Body der Anfrage mit."),
                        learn("Welche Zeile ist gültiges JSON?",
                                List.of("{\"titel\": \"Variablen\", \"xp\": 10}", "{titel: 'Variablen', xp: 10}",
                                        "{\"titel\" = \"Variablen\"}", "(\"titel\": \"Variablen\")"),
                                "{\"titel\": \"Variablen\", \"xp\": 10}",
                                "In JSON stehen Schlüssel und Strings immer in doppelten Anführungszeichen."),
                        apply("Welchen Statuscode schickt der Server, wenn alles geklappt hat?",
                                List.of("200", "200 OK"),
                                "200 OK ist die Standardantwort für eine erfolgreiche Anfrage."),
                        apply("Schreibe ein JSON-Objekt mit dem Schlüssel id und dem Wert 1.",
                                List.of("{\"id\": 1}"),
                                "Geschweifte Klammern, Schlüssel in doppelten Anführungszeichen, Doppelpunkt, Wert."))
        ));
    }
}

# Project RekSai – Backend

Spring-Boot-Backend von **Project RekSai**, einer Lernplattform, die Schritt für Schritt beibringt,
wie man genau dieses Projekt baut. Es liefert den Lernpfad (Meilensteine, Lektionen, Lesetexte,
Aufgaben) als JSON und prüft die Antworten.

Das Frontend (Vue 3) liegt in
[Web-tech3-todo-frontend](https://github.com/TomYang05/Web-tech3-todo-frontend). Dort steht auch
die ausführliche Beschreibung des ganzen Projekts.

## Schnellstart

Voraussetzung: **JDK 25** (Gradle wird über den Wrapper mitgebracht).

```sh
./gradlew bootRun     # Server auf http://localhost:8080
./gradlew test        # alle Tests
./gradlew build       # Tests und ausführbares JAR in build/libs/
```

## Konfiguration

| Variable | Standard | Zweck |
| --- | --- | --- |
| `PORT` | `8080` | Server-Port, z. B. von Render vorgegeben |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:5173` | Erlaubte Frontend-URLs, komma-getrennt |

## API

| Methode | Pfad | Antwort |
| --- | --- | --- |
| GET | `/api/milestones` | Meilensteine mit ihren Lektionen und `implemented`-Status |
| GET | `/api/lessons` | Alle Lektionen in Lernreihenfolge (Zusammenfassung) |
| GET | `/api/lessons/{id}` | Lektion mit Lesetexten und Aufgaben, ohne Lösungen. `404` bei unbekannter id |
| POST | `/api/tasks/{id}/check` | Body `{"answer": "..."}` → `{correct, solution, explanation, xp}` |

Beispiel:

```sh
curl -X POST http://localhost:8080/api/tasks/10201/check \
     -H "Content-Type: application/json" -d '{"answer": "int"}'
# {"correct":true,"solution":"int","explanation":"int steht für integer ...","xp":10}
```

## Aufbau

```
src/main/java/com/example/demo/
├── DemoApplication.java      Startklasse
├── WebConfig.java            CORS für /api/** (Origins aus CORS_ALLOWED_ORIGINS)
├── LessonController.java     REST-Endpoints und Antwort-Records (ohne Lösungen)
├── LessonCatalog.java        Alle Meilensteine, Suche nach Lektion und Aufgabe
├── Milestone.java            Meilenstein: Nummer, Titel, Ziel, implemented, Lektionen
├── Lesson.java               Lektion: Ziel, Lesetexte, Aufgaben
├── LessonStep.java           Gemeinsamer Typ für Reading und Task
├── Reading.java              Lesetext mit optionalem Codebeispiel
├── Task.java                 Aufgabe, Antwortprüfung und Normalisierung
├── TaskType.java             MULTIPLE_CHOICE (Lernen) oder INPUT (Anwenden)
├── LessonFactory.java        Kleine DSL für Inhalte, vergibt IDs automatisch
└── MilestoneOne … Five.java  Die Inhalte der fünf Meilensteine
```

`implemented = false` (M4, M5) heißt: Die Lektionen sind schon in der App, im Projekt-Code ist der
Meilenstein noch nicht gebaut. Das Frontend zeigt dafür „Geplant“ an.

### Inhalte schreiben

Jede Lektion läuft gleich ab: **Lektion** (Ziel) → **Lesen** → **Lernen** → **Anwenden**.
In den `Milestone…`-Klassen sieht eine Lektion so aus:

```java
lesson(2, 8, "Listen mit v-for",
        "Du kannst für jeden Eintrag einer Liste ein Element erzeugen.",
        read("v-for", """
                Mit `v-for` erzeugt Vue für jedes Element eines Arrays ein HTML-Element.
                """, """
                <p v-for="wort in woerter" :key="wort.id">{{ wort.word }}</p>
                """),
        learn("Was gehört in :key?",
                List.of("Ein eindeutiger Wert wie die id", "Immer der Index"),
                "Ein eindeutiger Wert wie die id",
                "Über den Key erkennt Vue, welches Element welches ist."),
        apply("Ergänze das v-for ...",
                List.of("v-for=\"lesson in lessons\"", "v-for=\"lesson of lessons\""),
                "v-for=\"element in liste\""))
```

- **IDs:** Lektion = Meilenstein × 100 + Position (z. B. `208`), Aufgabe = Lektion × 100 +
  laufende Nummer (z. B. `20801`).
- **Reihenfolge:** `learn` kommt immer vor `apply`, egal in welcher Reihenfolge sie im Code stehen.
- **Lesetexte:** Leerzeilen trennen Absätze, `- ` am Zeilenanfang macht Aufzählungen,
  `` `Backticks` `` markieren Code.
- **Antworten prüfen:** Groß- und Kleinschreibung zählt, Leerzeichen um Satzzeichen nicht
  (`int x=5;` == `int x = 5 ;`). Bei `apply` kann man mehrere richtige Varianten angeben.

## Tests

| Testklasse | Prüft |
| --- | --- |
| `LessonControllerTests` | Endpoints, 404, keine Lösungen im JSON, XP-Vergabe, CORS, `implemented` |
| `LessonCatalogTests` | Inhalte: lückenlose Reihenfolge, alle Phasen in jeder Lektion, Lernen vor Anwenden, eindeutige IDs, jede Musterlösung wird akzeptiert |
| `TaskTests` | Normalisierung, falsche Antworten, MC-Lösung muss unter den Optionen sein |
| `DemoApplicationTests` | Anwendung startet |

Ein Tippfehler in einer Musterlösung oder eine fehlende Phase fällt so schon beim Bauen auf.

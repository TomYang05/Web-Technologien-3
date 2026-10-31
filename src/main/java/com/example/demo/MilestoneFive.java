package com.example.demo;

import java.util.List;

import static com.example.demo.LessonFactory.apply;
import static com.example.demo.LessonFactory.learn;
import static com.example.demo.LessonFactory.lesson;
import static com.example.demo.LessonFactory.read;

/** Meilenstein 5: Nutzerkonten mit Login, XP und Level auf dem Server. Im Projekt-Code noch geplant. */
final class MilestoneFive {

    private MilestoneFive() {
    }

    static Milestone create() {
        return new Milestone(5, "Login & Level-System",
                "Jeder Nutzer hat ein Konto, und XP und Level werden sicher auf dem Server gespeichert.", false, List.of(

                lesson(5, 1, "Wie ein Login funktioniert",
                        "Du verstehst den Unterschied zwischen Authentifizierung und Autorisierung.",
                        read("Wer bist du, was darfst du?", """
                                - Authentifizierung: Wer bist du? Das prüft der Login mit Name und Passwort.
                                - Autorisierung: Was darfst du? Zum Beispiel nur deinen eigenen Fortschritt sehen.

                                Erst kommt die Authentifizierung, dann die Autorisierung.
                                """),
                        read("Angemeldet bleiben", """
                                HTTP merkt sich nichts zwischen zwei Anfragen. Nach dem Login muss das Frontend \
                                darum bei jeder Anfrage mitschicken, wer es ist. Dafür gibt es zwei Wege:

                                - Session: Der Server merkt sich die Anmeldung, der Browser schickt ein Cookie
                                - Token: Der Server gibt einen signierten Ausweis heraus, das Frontend schickt \
                                ihn im Header mit

                                Weil Frontend und Backend bei uns getrennt laufen, nehmen wir ein Token (JWT).
                                """),
                        learn("Was prüft die Authentifizierung?",
                                List.of("Wer du bist", "Was du darfst", "Wie schnell der Server ist", "Welche Lektion offen ist"),
                                "Wer du bist",
                                "Autorisierung kommt danach und prüft Rechte."),
                        learn("Warum muss das Frontend bei jeder Anfrage zeigen, wer es ist?",
                                List.of("Weil HTTP sich nichts zwischen Anfragen merkt", "Weil Vue das verlangt",
                                        "Weil die Datenbank sonst abstürzt", "Weil CORS es vorschreibt"),
                                "Weil HTTP sich nichts zwischen Anfragen merkt",
                                "HTTP ist zustandslos, jede Anfrage steht für sich."),
                        learn("Was ist \"darf nur den eigenen Fortschritt sehen\"?",
                                List.of("Autorisierung", "Authentifizierung", "Registrierung", "Verschlüsselung"),
                                "Autorisierung",
                                "Es geht um Rechte, nicht um die Identität."),
                        apply("Welches Wort beschreibt die Prüfung \"Wer bist du?\"",
                                List.of("Authentifizierung", "authentifizierung", "Authentication", "authentication"),
                                "Authentifizierung = Identität prüfen.")),

                lesson(5, 2, "Passwörter sicher speichern",
                        "Du kannst Passwörter so speichern, dass sie selbst bei einem Datenleck geschützt sind.",
                        read("Niemals im Klartext", """
                                Passwörter landen nie so in der Datenbank, wie der Nutzer sie eingibt. Wird die \
                                Datenbank gestohlen, wären sonst alle Konten offen, oft auch auf anderen Seiten.

                                Stattdessen speichert man einen Hash: Eine Einwegfunktion macht aus dem Passwort \
                                eine Zeichenkette, aus der man es nicht zurückrechnen kann.
                                """),
                        read("BCrypt", """
                                BCrypt ist absichtlich langsam und mischt jedem Passwort einen zufälligen Salt \
                                bei. Gleiche Passwörter ergeben so verschiedene Hashes. Beim Login hasht man \
                                die Eingabe nicht neu, sondern vergleicht mit `matches`.
                                """, """
                                PasswordEncoder encoder = new BCryptPasswordEncoder();

                                String hash = encoder.encode("geheim123");
                                // $2a$10$Xb3... (jedes Mal anders)

                                boolean ok = encoder.matches("geheim123", hash); // true
                                """),
                        learn("Wie speichert man Passwörter?",
                                List.of("Als Hash, z. B. mit BCrypt", "Im Klartext", "Base64-kodiert", "Im localStorage"),
                                "Als Hash, z. B. mit BCrypt",
                                "Base64 ist keine Verschlüsselung, man kann es sofort zurückwandeln."),
                        learn("Warum ergeben gleiche Passwörter bei BCrypt verschiedene Hashes?",
                                List.of("Wegen des zufälligen Salts", "Wegen eines Fehlers", "Wegen der Uhrzeit des Servers",
                                        "Weil BCrypt die Passwörter verschlüsselt verschickt"),
                                "Wegen des zufälligen Salts",
                                "So sieht ein Angreifer nicht, wer dasselbe Passwort hat."),
                        learn("Wie prüfst du beim Login das eingegebene Passwort?",
                                List.of("encoder.matches(eingabe, hash)", "eingabe.equals(hash)",
                                        "encoder.encode(eingabe).equals(hash)", "hash.decode()"),
                                "encoder.matches(eingabe, hash)",
                                "encode erzeugt wegen des Salts jedes Mal einen anderen Hash, darum matches."),
                        apply("Erzeuge mit encoder den Hash für das Passwort aus der Variable password und speichere ihn in String hash.",
                                List.of("String hash = encoder.encode(password);"),
                                "encode gibt den fertigen Hash als String zurück.")),

                lesson(5, 3, "Nutzer und Registrierung",
                        "Du kannst Nutzerkonten in der Datenbank anlegen.",
                        read("Die User-Entity", """
                                Ein Nutzer bekommt eine eigene Tabelle. Der Name muss eindeutig sein, das \
                                Passwort steht nur als Hash darin. XP und Level gehören ab jetzt zum Nutzer.
                                """, """
                                @Entity
                                @Table(name = "app_user")
                                public class AppUser {
                                    @Id
                                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                                    private Long id;

                                    @Column(unique = true, nullable = false)
                                    private String username;

                                    @Column(nullable = false)
                                    private String passwordHash;

                                    private int xp;
                                }
                                """),
                        read("Registrieren", """
                                Die Registrierung ist ein POST. Gibt es den Namen schon, antwortet das Backend \
                                mit `409 Conflict`. Das Passwort wird vor dem Speichern gehasht und nie wieder \
                                zurückgeschickt.
                                """, """
                                record RegisterRequest(String username, String password) {}

                                @PostMapping("/api/auth/register")
                                public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
                                    if (users.existsByUsername(request.username())) {
                                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                                    }
                                    users.save(new AppUser(request.username(), encoder.encode(request.password())));
                                    return ResponseEntity.status(HttpStatus.CREATED).build();
                                }
                                """),
                        learn("Warum heißt die Tabelle app_user statt user?",
                                List.of("Weil user in PostgreSQL ein reserviertes Wort ist", "Weil es kürzer ist",
                                        "Weil Spring das so vorschreibt", "Ohne Grund"),
                                "Weil user in PostgreSQL ein reserviertes Wort ist",
                                "Reservierte Wörter als Tabellennamen führen zu SQL-Fehlern."),
                        learn("Welcher Statuscode passt, wenn der Nutzername schon vergeben ist?",
                                List.of("409 Conflict", "404 Not Found", "500 Internal Server Error", "200 OK"),
                                "409 Conflict",
                                "409 sagt: Die Anfrage passt nicht zum aktuellen Zustand."),
                        learn("Was macht @Column(unique = true)?",
                                List.of("Die Datenbank verhindert doppelte Werte", "Die Spalte wird verschlüsselt",
                                        "Die Spalte darf nur einmal gelesen werden", "Die Spalte wird ausgeblendet"),
                                "Die Datenbank verhindert doppelte Werte",
                                "So kann es denselben Nutzernamen nicht zweimal geben."),
                        apply("Wie heißt die Repository-Methode, die prüft, ob ein Nutzername schon existiert?\n(Spring baut sie aus dem Namen, Feld: username)",
                                List.of("existsByUsername"),
                                "exists + By + Feldname in PascalCase.")),

                lesson(5, 4, "Spring Security",
                        "Du kannst festlegen, welche Endpoints öffentlich sind und welche einen Login brauchen.",
                        read("Die Filterkette", """
                                Mit der Abhängigkeit `spring-boot-starter-security` ist erst einmal alles \
                                gesperrt. In einer `SecurityFilterChain` legst du fest, was offen bleibt.

                                Lektionen darf jeder sehen, Fortschritt nur, wer angemeldet ist.
                                """, """
                                @Bean
                                SecurityFilterChain security(HttpSecurity http) throws Exception {
                                    return http
                                            .cors(Customizer.withDefaults())
                                            .csrf(csrf -> csrf.disable())
                                            .authorizeHttpRequests(auth -> auth
                                                    .requestMatchers("/api/auth/**", "/api/lessons/**", "/api/milestones").permitAll()
                                                    .anyRequest().authenticated())
                                            .build();
                                }
                                """),
                        read("CORS und CSRF", """
                                Spring Security hat eigene Filter. Darum muss CORS dort mit `.cors(...)` \
                                eingeschaltet werden, sonst blockt Security die Anfragen vom Frontend.

                                CSRF-Schutz braucht man bei Cookies. Mit Tokens im Header kann man ihn \
                                ausschalten.
                                """),
                        learn("Was passiert direkt nach dem Hinzufügen von Spring Security?",
                                List.of("Alle Endpoints sind gesperrt", "Nichts ändert sich",
                                        "Nur POST ist gesperrt", "Die Datenbank wird gelöscht"),
                                "Alle Endpoints sind gesperrt",
                                "Sicher als Standard: Freigaben musst du ausdrücklich eintragen."),
                        learn("Was bedeutet .anyRequest().authenticated()?",
                                List.of("Alles andere braucht einen Login", "Alles ist öffentlich",
                                        "Nur Admins dürfen zugreifen", "Anfragen werden gezählt"),
                                "Alles andere braucht einen Login",
                                "Die Regeln gelten von oben nach unten, die erste passende gewinnt."),
                        learn("Warum steht .cors(...) in der Security-Konfiguration?",
                                List.of("Sonst blockt Security die Anfragen vom Frontend", "Damit Passwörter gehasht werden",
                                        "Weil CORS die Datenbank braucht", "Für schnellere Antworten"),
                                "Sonst blockt Security die Anfragen vom Frontend",
                                "Security filtert vor dem Controller, also auch vor der CORS-Konfiguration."),
                        apply("Ergänze, damit alle Pfade unter /api/auth/ ohne Login erreichbar sind:\n.requestMatchers(\"/api/auth/**\").___",
                                List.of("permitAll()"),
                                "permitAll erlaubt den Zugriff für alle.")),

                lesson(5, 5, "Tokens mit JWT",
                        "Du verstehst, wie ein JSON Web Token den Nutzer bei jeder Anfrage ausweist.",
                        read("Aufbau eines JWT", """
                                Ein JWT besteht aus drei Teilen, getrennt durch Punkte: Header, Payload und \
                                Signatur. Die Payload enthält z. B. den Nutzernamen und das Ablaufdatum.

                                Die Payload ist nur kodiert, nicht verschlüsselt. Jeder kann sie lesen. Die \
                                Signatur verhindert aber, dass jemand sie verändert. Darum gehören keine \
                                Geheimnisse hinein.
                                """, """
                                eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGEiLCJleHAiOjE3OTN9.sQ6s...
                                |------ Header ------|------- Payload -------|-- Signatur --|
                                """),
                        read("Der Ablauf", """
                                - Login: Frontend schickt Name und Passwort an `/api/auth/login`
                                - Backend prüft mit `matches` und antwortet mit einem Token
                                - Frontend schickt das Token bei jeder Anfrage im Header mit
                                - Backend prüft die Signatur und weiß, wer anfragt
                                """, """
                                Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
                                """),
                        learn("Aus wie vielen Teilen besteht ein JWT?",
                                List.of("3", "2", "4", "1"), "3",
                                "Header, Payload und Signatur."),
                        learn("Darf ein Passwort in die Payload eines JWT?",
                                List.of("Nein, die Payload kann jeder lesen", "Ja, sie ist verschlüsselt",
                                        "Ja, wenn es gehasht ist", "Nur bei Admins"),
                                "Nein, die Payload kann jeder lesen",
                                "Die Signatur schützt vor Änderungen, nicht vor dem Mitlesen."),
                        learn("Was verhindert die Signatur?",
                                List.of("Dass jemand das Token verändert", "Dass jemand das Token liest",
                                        "Dass das Token abläuft", "Dass CORS greift"),
                                "Dass jemand das Token verändert",
                                "Nur der Server kennt den Schlüssel zum Signieren."),
                        apply("Ergänze den Header, mit dem das Frontend das Token mitschickt:\nAuthorization: ___ <token>",
                                List.of("Bearer"),
                                "Bearer heißt: Wer dieses Token trägt, ist angemeldet.")),

                lesson(5, 6, "Login im Frontend",
                        "Du kannst ein Login-Formular bauen und geschützte Seiten absichern.",
                        read("Formular mit v-model", """
                                `v-model` verbindet ein Eingabefeld in beide Richtungen mit einem `ref`. \
                                `@submit.prevent` verhindert, dass der Browser die Seite neu lädt.
                                """, """
                                <script setup lang="ts">
                                const username = ref('')
                                const password = ref('')

                                async function login() {
                                  const response = await fetch(`${baseUrl}/api/auth/login`, {
                                    method: 'POST',
                                    headers: { 'Content-Type': 'application/json' },
                                    body: JSON.stringify({ username: username.value, password: password.value }),
                                  })
                                  token.value = (await response.json()).token
                                }
                                </script>

                                <template>
                                  <form @submit.prevent="login">
                                    <input v-model="username" autocomplete="username" />
                                    <input v-model="password" type="password" autocomplete="current-password" />
                                    <button>Anmelden</button>
                                  </form>
                                </template>
                                """),
                        read("Token mitschicken", """
                                Die Funktion `request` in `api.ts` hängt das Token an jede Anfrage. So muss \
                                keine Komponente daran denken.
                                """, """
                                const headers = token.value ? { Authorization: `Bearer ${token.value}` } : {}
                                const response = await fetch(`${baseUrl}${path}`, { ...init, headers })
                                """),
                        read("Seiten schützen", """
                                Ein Navigation Guard im Router leitet nicht angemeldete Nutzer zum Login um, \
                                bevor die Seite überhaupt lädt.
                                """, """
                                router.beforeEach((to) => {
                                  if (to.meta.requiresAuth && !token.value) {
                                    return { name: 'login' }
                                  }
                                })
                                """),
                        learn("Was macht v-model an einem Eingabefeld?",
                                List.of("Verbindet das Feld in beide Richtungen mit einem ref", "Schickt das Feld an den Server",
                                        "Macht das Feld unsichtbar", "Prüft das Passwort"),
                                "Verbindet das Feld in beide Richtungen mit einem ref",
                                "Tippen ändert den ref, den ref ändern ändert das Feld."),
                        learn("Wofür steht .prevent in @submit.prevent?",
                                List.of("Der Browser lädt die Seite beim Absenden nicht neu", "Das Formular wird gesperrt",
                                        "Das Passwort wird versteckt", "Doppelte Klicks werden verhindert"),
                                "Der Browser lädt die Seite beim Absenden nicht neu",
                                "Es ruft event.preventDefault() auf."),
                        learn("Wo prüft man am besten, ob eine Seite einen Login braucht?",
                                List.of("In router.beforeEach", "In jeder Komponente einzeln", "Im CSS", "In index.html"),
                                "In router.beforeEach",
                                "Ein zentraler Guard für alle Routen."),
                        apply("Verbinde ein Eingabefeld per v-model mit dem ref username.",
                                List.of("<input v-model=\"username\" />", "<input v-model=\"username\">", "<input v-model=\"username\"/>"),
                                "v-model=\"name des refs\"")),

                lesson(5, 7, "XP und Level auf dem Server",
                        "Du kannst XP sicher auf dem Server vergeben und daraus ein Level berechnen. Damit ist M5 geschafft.",
                        read("Dem Client nicht vertrauen", """
                                Heute rechnet das Frontend die XP zusammen. Jeder könnte im Browser einfach \
                                1000 XP eintragen. Mit Login vergibt darum das Backend die XP: Beim Prüfen einer \
                                richtigen Antwort addiert es sie direkt beim angemeldeten Nutzer.
                                """, """
                                @PostMapping("/api/tasks/{id}/check")
                                public CheckResult check(@PathVariable int id, @RequestBody CheckRequest request,
                                                         @AuthenticationPrincipal Jwt jwt) {
                                    Task task = catalog.findTask(id).orElseThrow();
                                    boolean correct = task.isCorrect(request.answer());
                                    if (correct) {
                                        users.addXp(jwt.getSubject(), task.xp());
                                    }
                                    return new CheckResult(correct, task.solution(), task.explanation(), correct ? task.xp() : 0);
                                }
                                """),
                        read("Level berechnen", """
                                Das Level ergibt sich aus den XP, zum Beispiel ein Level pro 100 XP. Man \
                                speichert es nicht extra, sondern berechnet es, dann kann es nie falsch sein.

                                Ein Endpoint `GET /api/me` liefert Name, XP und Level für die Anzeige im Header.
                                """, """
                                static int level(int xp) {
                                    return xp / 100 + 1;
                                }

                                record Me(String username, int xp, int level) {}
                                """),
                        learn("Warum vergibt das Backend die XP und nicht das Frontend?",
                                List.of("Weil man Werte aus dem Browser manipulieren kann", "Weil das Frontend nicht rechnen kann",
                                        "Weil XP geheim sind", "Weil Vue keine Zahlen speichert"),
                                "Weil man Werte aus dem Browser manipulieren kann",
                                "Alles, was im Browser passiert, kann der Nutzer verändern."),
                        learn("Welches Level hat ein Nutzer mit 250 XP bei level = xp / 100 + 1?",
                                List.of("3", "2", "25", "4"), "3",
                                "250 / 100 = 2 (ganzzahlig), plus 1 ergibt 3."),
                        learn("Warum speichert man das Level nicht als eigene Spalte?",
                                List.of("Es lässt sich aus den XP berechnen und kann so nie abweichen",
                                        "Weil Spalten teuer sind", "Weil PostgreSQL keine Zahlen kann", "Weil JPA das verbietet"),
                                "Es lässt sich aus den XP berechnen und kann so nie abweichen",
                                "Abgeleitete Werte berechnet man, statt sie doppelt zu speichern."),
                        apply("Schreibe den Ausdruck für das Level aus der Variable xp (ein Level pro 100 XP, Start bei 1).",
                                List.of("xp / 100 + 1", "1 + xp / 100"),
                                "Ganzzahlige Division rundet ab."))
        ));
    }
}

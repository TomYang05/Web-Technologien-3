package com.example.demo;

import java.util.List;

import static com.example.demo.LessonFactory.apply;
import static com.example.demo.LessonFactory.learn;
import static com.example.demo.LessonFactory.lesson;
import static com.example.demo.LessonFactory.read;

/** Meilenstein 2: ein Vue-Frontend, das eine Liste mit v-for anzeigt. */
final class MilestoneTwo {

    private MilestoneTwo() {
    }

    static Milestone create() {
        return new Milestone(2, "Frontend mit Vue",
                "Eine Vue-App, die mit Komponenten und v-for eine Liste anzeigt.", true, List.of(

                lesson(2, 1, "HTML-Grundlagen",
                        "Du kannst den Aufbau einer Webseite mit HTML beschreiben.",
                        read("Tags und Elemente", """
                                HTML beschreibt die Struktur einer Seite. Ein Element besteht aus einem \
                                öffnenden Tag, dem Inhalt und einem schließenden Tag mit `/`. Elemente können \
                                ineinander stecken.

                                Attribute wie `class` oder `href` stehen im öffnenden Tag.
                                """, """
                                <h1>Project RekSai</h1>
                                <p class="intro">Lerne Schritt für Schritt.</p>
                                <a href="/lessons/101">Los geht's</a>
                                <ul>
                                  <li>Java</li>
                                  <li>Vue</li>
                                </ul>
                                """),
                        read("Das Grundgerüst", """
                                Jede Seite hat ein `<head>` mit Infos wie dem Titel und einen `<body>` mit dem \
                                sichtbaren Inhalt.

                                In einer Vue-App ist der Body fast leer: Es gibt nur ein `<div id="app">`. Vue \
                                hängt die ganze Oberfläche später genau dort hinein.
                                """, """
                                <!DOCTYPE html>
                                <html lang="de">
                                  <head>
                                    <title>Project RekSai</title>
                                  </head>
                                  <body>
                                    <div id="app"></div>
                                    <script type="module" src="/src/main.ts"></script>
                                  </body>
                                </html>
                                """),
                        learn("Welches Tag erzeugt einen Link?",
                                List.of("<a>", "<link>", "<href>", "<url>"), "<a>",
                                "<a href=\"...\"> ist ein Link. <link> bindet z. B. CSS-Dateien ein."),
                        learn("Wofür ist das Attribut class da?",
                                List.of("Um Elemente mit CSS zu gestalten", "Um Java-Klassen einzubinden",
                                        "Um die Seite zu benennen", "Um JavaScript zu starten"),
                                "Um Elemente mit CSS zu gestalten",
                                "Über Klassen wählt CSS Elemente aus."),
                        learn("In welches Element hängt sich die Vue-App?",
                                List.of("<div id=\"app\">", "<body>", "<head>", "<script>"), "<div id=\"app\">",
                                "main.ts ruft mount('#app') auf und füllt genau dieses div."),
                        apply("Schreibe ein Listenelement mit dem Text Java.",
                                List.of("<li>Java</li>"),
                                "li steht für list item und gehört in ein <ul> oder <ol>.")),

                lesson(2, 2, "CSS: Selektoren & Farben",
                        "Du kannst Elemente auswählen und ihr Aussehen ändern.",
                        read("CSS-Regeln", """
                                Eine CSS-Regel besteht aus einem Selektor und Deklarationen in geschweiften \
                                Klammern. Jede Deklaration ist `eigenschaft: wert;`.
                                """, """
                                .card {
                                  color: #272f7a;
                                  background-color: white;
                                  font-size: 18px;
                                }
                                """),
                        read("Selektoren", """
                                - `p`: alle Elemente eines Typs
                                - `.card`: alle Elemente mit der Klasse card
                                - `#nav`: das eine Element mit der id nav

                                In Vue-Komponenten schreibst du CSS meist mit Klassen.
                                """),
                        learn("Welcher Selektor wählt alle Elemente mit der Klasse card?",
                                List.of("#card", ".card", "card", "*card"), ".card",
                                "Klassen beginnen mit einem Punkt, ids mit #."),
                        learn("Was wählt der Selektor #nav aus?",
                                List.of("Alle <nav>-Elemente", "Das Element mit der id nav",
                                        "Alle Elemente der Klasse nav", "Nichts"),
                                "Das Element mit der id nav",
                                "# wählt ein Element über seine id aus."),
                        learn("Welche Eigenschaft setzt die Hintergrundfarbe?",
                                List.of("bgcolor", "background-color", "color-background", "fill"), "background-color",
                                "color ändert die Schrift, background-color den Hintergrund."),
                        apply("Schreibe den Selektor, der alle <p>-Elemente auswählt.",
                                List.of("p"),
                                "Element-Selektoren bestehen nur aus dem Tag-Namen."),
                        apply("Schreibe die Deklaration, die die Schriftfarbe auf red setzt.",
                                List.of("color: red;", "color: red"),
                                "Eigenschaft, Doppelpunkt, Wert und am Ende ein Semikolon.")),

                lesson(2, 3, "CSS: Box-Model & Flexbox",
                        "Du kannst Abstände setzen und Elemente nebeneinander anordnen.",
                        read("Das Box-Model", """
                                Jedes Element ist eine Box. Von innen nach außen: Inhalt, `padding` \
                                (Innenabstand), `border` (Rahmen) und `margin` (Außenabstand).
                                """, """
                                .card {
                                  padding: 16px;
                                  border: 2px solid #dfe2f1;
                                  margin-bottom: 12px;
                                }
                                """),
                        read("Flexbox", """
                                Mit `display: flex` ordnet ein Container seine Kinder in einer Reihe an. \
                                `justify-content` verteilt sie entlang der Reihe, `align-items` quer dazu. \
                                Mit `flex-direction: column` stehen sie untereinander.
                                """, """
                                .topbar {
                                  display: flex;
                                  justify-content: space-between;
                                  align-items: center;
                                }
                                """),
                        learn("Welche Reihenfolge stimmt von innen nach außen?",
                                List.of("content, padding, border, margin", "content, margin, border, padding",
                                        "padding, content, border, margin", "margin, border, padding, content"),
                                "content, padding, border, margin",
                                "padding liegt innerhalb, margin außerhalb des Rahmens."),
                        learn("Welche Deklaration zentriert die Kinder entlang der Hauptachse?",
                                List.of("align-items: center;", "justify-content: center;", "text-align: center;", "margin: center;"),
                                "justify-content: center;",
                                "justify-content wirkt auf der Hauptachse, align-items auf der Querachse."),
                        learn("Wie ordnest du die Kinder untereinander statt nebeneinander an?",
                                List.of("flex-direction: column;", "flex-wrap: wrap;", "display: block;", "flex-direction: row;"),
                                "flex-direction: column;",
                                "column dreht die Hauptachse von horizontal auf vertikal."),
                        apply("Schreibe die Deklaration für 16px Innenabstand auf allen Seiten.",
                                List.of("padding: 16px;", "padding: 16px"),
                                "padding ist der Innenabstand zwischen Inhalt und Rahmen."),
                        apply("Schreibe die Deklaration, die ein Element zum Flex-Container macht.",
                                List.of("display: flex;", "display: flex"),
                                "display: flex wirkt auf die direkten Kinder des Elements.")),

                lesson(2, 4, "JavaScript-Grundlagen",
                        "Du kannst mit Variablen, Funktionen, Objekten und Arrays in JavaScript arbeiten.",
                        read("Variablen", """
                                In JavaScript gibt es keine Typangabe. `const` legt eine Variable an, die nicht \
                                neu zugewiesen werden darf, `let` eine, die sich ändern darf.
                                """, """
                                const name = 'Ada'
                                let punkte = 0
                                punkte = punkte + 10
                                console.log(typeof name) // "string"
                                """),
                        read("Funktionen", """
                                Funktionen bündeln Code. Neben der klassischen Schreibweise gibt es \
                                Arrow-Functions mit `=>`, die du in Vue ständig sehen wirst.
                                """, """
                                function add(a, b) {
                                  return a + b
                                }

                                const doppelt = (x) => x * 2
                                console.log(add(2, 3), doppelt(4)) // 5 8
                                """),
                        read("Objekte und Arrays", """
                                Objekte fassen Werte unter Namen zusammen, Arrays sind Listen. Genau so sieht \
                                später das JSON aus, das vom Backend kommt.

                                `map` wandelt jedes Element eines Arrays um und liefert ein neues Array.
                                """, """
                                const lesson = { id: 101, title: 'Variablen' }
                                console.log(lesson.title) // Variablen

                                const lessons = [lesson]
                                lessons.push({ id: 102, title: 'Schleifen' })
                                const titles = lessons.map((l) => l.title)
                                """),
                        learn("Mit welchem Schlüsselwort legst du eine Variable an, die nicht neu zugewiesen werden darf?",
                                List.of("let", "var", "const", "static"), "const",
                                "const verhindert eine neue Zuweisung, let erlaubt sie."),
                        learn("Was liefert typeof \"42\"?",
                                List.of("\"number\"", "\"string\"", "\"int\"", "\"object\""), "\"string\"",
                                "Alles in Anführungszeichen ist ein String, auch Ziffern."),
                        learn("Welche Methode hängt ein Element ans Ende eines Arrays?",
                                List.of("add()", "append()", "push()", "insert()"), "push()",
                                "push() fügt am Ende ein, pop() entfernt das letzte Element."),
                        apply("Lege mit let eine Variable punkte mit dem Wert 0 an.",
                                List.of("let punkte = 0;", "let punkte = 0"),
                                "In JavaScript ist das Semikolon optional."),
                        apply("Ergänze die Arrow-Function, damit sie ihren Parameter verdoppelt:\nconst doppelt = (x) => ___",
                                List.of("x * 2", "2 * x", "x + x"),
                                "Bei einer einzeiligen Arrow-Function wird der Ausdruck direkt zurückgegeben."),
                        apply("Wie greifst du auf das Feld title des Objekts lesson zu?",
                                List.of("lesson.title", "lesson['title']", "lesson[\"title\"]"),
                                "Mit dem Punkt greifst du auf Felder eines Objekts zu.")),

                lesson(2, 5, "Ein Vue-Projekt mit Vite",
                        "Du kannst ein Vue-Projekt anlegen, starten und dich darin zurechtfinden.",
                        read("Was ist Vue?", """
                                Vue ist ein JavaScript-Framework für Oberflächen. Du baust die Seite aus \
                                Komponenten, und Vue aktualisiert die Anzeige automatisch, wenn sich Daten \
                                ändern.

                                Vite ist das Werkzeug drumherum: ein schneller Dev-Server mit Hot Reload. \
                                Speicherst du eine Datei, siehst du die Änderung sofort im Browser.
                                """),
                        read("Projekt anlegen und starten", """
                                Mit Node.js installiert, legst du das Projekt mit einem Befehl an. Danach \
                                installierst du die Abhängigkeiten und startest den Dev-Server. Die App läuft \
                                dann unter `http://localhost:5173`.
                                """, """
                                npm create vue@latest
                                cd mein-projekt
                                npm install
                                npm run dev
                                """),
                        read("Die wichtigsten Dateien", """
                                - `index.html`: das Grundgerüst mit `<div id="app">`
                                - `src/main.ts`: startet die App und hängt sie in das div
                                - `src/App.vue`: die oberste Komponente
                                - `src/components/`: deine eigenen Komponenten
                                - `package.json`: Abhängigkeiten und Skripte wie `dev` und `build`
                                """, """
                                import { createApp } from 'vue'
                                import App from './App.vue'

                                createApp(App).mount('#app')
                                """),
                        learn("Auf welchem Port läuft der Vite-Dev-Server standardmäßig?",
                                List.of("5173", "8080", "3000", "443"), "5173",
                                "8080 ist der Port unseres Spring-Boot-Backends."),
                        learn("Was macht createApp(App).mount('#app')?",
                                List.of("Startet die Vue-App im Element mit der id app", "Lädt Daten vom Backend",
                                        "Erstellt eine neue HTML-Datei", "Installiert Vue"),
                                "Startet die Vue-App im Element mit der id app",
                                "mount hängt die App in das Element, das der Selektor findet."),
                        learn("In welcher Datei stehen die npm-Skripte wie dev und build?",
                                List.of("package.json", "index.html", "vite.config.ts", "App.vue"), "package.json",
                                "npm run <name> führt das Skript mit diesem Namen aus package.json aus."),
                        apply("Mit welchem Befehl startest du den Dev-Server?",
                                List.of("npm run dev"),
                                "npm run führt ein Skript aus package.json aus.")),

                lesson(2, 6, "Komponenten",
                        "Du kannst die Oberfläche in wiederverwendbare Komponenten zerlegen.",
                        read("Single File Components", """
                                Eine Vue-Komponente ist eine `.vue`-Datei mit drei Blöcken: `<script setup>` \
                                für die Logik, `<template>` für das HTML und `<style scoped>` für das CSS. \
                                `scoped` heißt: Das CSS gilt nur in dieser Komponente.
                                """, """
                                <script setup lang="ts">
                                const titel = 'Meine Vokabeln'
                                </script>

                                <template>
                                  <h2 class="titel">{{ titel }}</h2>
                                </template>

                                <style scoped>
                                .titel { color: #272f7a; }
                                </style>
                                """),
                        read("Komponenten einbinden", """
                                Eine Komponente importierst du im Script und benutzt sie im Template wie ein \
                                eigenes HTML-Tag.
                                """, """
                                <script setup lang="ts">
                                import Vocablist from './components/Vocablist.vue'
                                </script>

                                <template>
                                  <Vocablist />
                                </template>
                                """),
                        read("Props", """
                                Mit Props gibt eine Eltern-Komponente Daten an ein Kind weiter. Das Kind \
                                deklariert sie mit `defineProps`. So bekommt in dieser App die Aufgaben-Karte \
                                ihre Aufgabe.
                                """, """
                                <!-- LessonTitle.vue -->
                                <script setup lang="ts">
                                defineProps<{ title: string }>()
                                </script>

                                <template>
                                  <h2>{{ title }}</h2>
                                </template>

                                <!-- Verwendung -->
                                <LessonTitle title="Variablen" />
                                """),
                        learn("Welche drei Blöcke hat eine .vue-Datei?",
                                List.of("script, template, style", "head, body, footer", "html, css, js", "import, export, render"),
                                "script, template, style",
                                "Logik, Markup und Styling stehen in einer Datei zusammen."),
                        learn("Was bewirkt scoped bei <style scoped>?",
                                List.of("Das CSS gilt nur in dieser Komponente", "Das CSS gilt auf der ganzen Seite",
                                        "Das CSS wird nur im Dark Mode geladen", "Das CSS wird ignoriert"),
                                "Das CSS gilt nur in dieser Komponente",
                                "So beeinflussen sich Komponenten nicht gegenseitig."),
                        learn("Wie gibst du Daten an eine Kind-Komponente weiter?",
                                List.of("Über Props", "Über localStorage", "Über eine globale Variable", "Über das Backend"),
                                "Über Props",
                                "Props fließen von oben nach unten, vom Eltern- zum Kind-Element."),
                        apply("Binde die Komponente Vocablist im Template ein, als selbstschließendes Tag.",
                                List.of("<Vocablist />", "<Vocablist></Vocablist>"),
                                "Komponenten werden wie HTML-Tags geschrieben, meist in PascalCase.")),

                lesson(2, 7, "Reaktivität & Template-Syntax",
                        "Du kannst Daten anzeigen, an Attribute binden und auf Klicks reagieren.",
                        read("Reaktive Daten mit ref", """
                                Mit `ref` wird ein Wert reaktiv: Ändert er sich, aktualisiert Vue die Anzeige. \
                                Im Script liest und schreibst du ihn über `.value`, im Template reicht der Name.
                                """, """
                                <script setup lang="ts">
                                import { ref } from 'vue'

                                const count = ref(0)
                                function erhoehen() {
                                  count.value++
                                }
                                </script>

                                <template>
                                  <p>Klicks: {{ count }}</p>
                                </template>
                                """),
                        read("Binden und Events", """
                                - `{{ }}` zeigt einen Wert als Text an
                                - `:attribut` (kurz für `v-bind`) bindet einen Wert an ein Attribut
                                - `@event` (kurz für `v-on`) reagiert auf Ereignisse wie Klicks
                                - `v-if` zeigt ein Element nur, wenn die Bedingung true ist
                                """, """
                                <button @click="erhoehen">Klicks: {{ count }}</button>
                                <img :src="logoUrl" alt="Logo" />
                                <p v-if="count > 3">Viel geklickt!</p>
                                """),
                        learn("Wie liest du den Wert von const count = ref(0) im <script> aus?",
                                List.of("count.value", "count", "count()", "this.count"), "count.value",
                                "Im Script brauchst du .value, im Template nicht."),
                        learn("Wofür steht @click?",
                                List.of("v-on:click", "v-bind:click", "v-if:click", "v-model:click"), "v-on:click",
                                "@ ist die Kurzform von v-on und hängt einen Event-Listener an."),
                        learn("Was macht v-if?",
                                List.of("Zeigt das Element nur, wenn die Bedingung true ist", "Wiederholt das Element",
                                        "Bindet ein Attribut", "Lädt Daten nach"),
                                "Zeigt das Element nur, wenn die Bedingung true ist",
                                "So zeigt diese App z. B. die Lade-Meldung nur während des Ladens."),
                        apply("Lege eine reaktive Variable punkte mit dem Startwert 0 an.",
                                List.of("const punkte = ref(0)", "const punkte = ref(0);"),
                                "ref kommt aus 'vue' und macht den Wert reaktiv."),
                        apply("Zeige die Variable punkte im Template als Text an.",
                                List.of("{{ punkte }}"),
                                "Doppelte geschweifte Klammern geben einen Wert als Text aus.")),

                lesson(2, 8, "Listen mit v-for",
                        "Du kannst für jeden Eintrag einer Liste ein Element erzeugen. Damit ist M2 geschafft.",
                        read("v-for", """
                                Mit `v-for` erzeugt Vue für jedes Element eines Arrays ein HTML-Element. Genau \
                                so zeigt unsere Vocablist-Komponente aus M2 ihre Wörter an.
                                """, """
                                <script setup lang="ts">
                                const woerter = [
                                  { id: 1, word: 'house', translation: 'Haus' },
                                  { id: 2, word: 'cat', translation: 'Katze' },
                                ]
                                </script>

                                <template>
                                  <p v-for="wort in woerter" :key="wort.id">
                                    {{ wort.word }} – {{ wort.translation }}
                                  </p>
                                </template>
                                """),
                        read("Warum :key?", """
                                Vue braucht für jedes Element einen eindeutigen `:key`, um Änderungen effizient \
                                zu erkennen. Nimm dafür eine id aus den Daten, nicht den Index in der Liste.
                                """),
                        learn("Was gehört in :key?",
                                List.of("Ein eindeutiger Wert wie die id", "Immer der Index", "Der angezeigte Text", "Nichts, er ist optional"),
                                "Ein eindeutiger Wert wie die id",
                                "Über den Key erkennt Vue, welches Element welches ist."),
                        learn("Wie viele <li> erzeugt <li v-for=\"n in 3\">?",
                                List.of("1", "2", "3", "4"), "3",
                                "v-for mit einer Zahl zählt von 1 bis zu dieser Zahl."),
                        learn("Welche Zeile zeigt alle Lektionen als Liste an?",
                                List.of("<li v-for=\"lesson in lessons\" :key=\"lesson.id\">{{ lesson.title }}</li>",
                                        "<li v-if=\"lesson in lessons\">{{ lesson.title }}</li>",
                                        "<li :for=\"lessons\">{{ title }}</li>",
                                        "<li for each lesson>{{ lesson }}</li>"),
                                "<li v-for=\"lesson in lessons\" :key=\"lesson.id\">{{ lesson.title }}</li>",
                                "v-for mit eindeutigem :key und Zugriff auf das Feld im Template."),
                        apply("Ergänze das v-for, damit für jede Lektion in lessons ein <li> entsteht:\n<li ___ :key=\"lesson.id\">{{ lesson.title }}</li>",
                                List.of("v-for=\"lesson in lessons\"", "v-for=\"lesson of lessons\""),
                                "v-for=\"element in liste\" – der Name vor in ist frei wählbar."))
        ));
    }
}

# Clean Code: Funktionen

## Gute Funktionen nach Clean Code

* Klein und nur eine Aufgabe erfüllen
* Nur eine Abstraktionsebene enthalten
* Geringe Einrücktiefe, möglichst kein switch enthalten
* Einen guten Namen haben
* Möglichst wenie (keine!) Funktionsargumente haben
* Keine Flag-Argumente verwenden
* Keine Nebeneffekte aufweisen
* Mit Exceptions (statt Fehlercode) arbeiten

## Funktionen sollen klein sein

* Eine Funktion sollte auf einen Blick überschaubar sein.&#x20;
* Vorteil: Kleine Funktionen sind (einzeln) viel besser und schneller verständlich
* Konsequenz:&#x20;
  * Es gibt ingsgesamt viel mehr Funktionen
  * Da eine Klasse nicht zu viele Methoden haben soll, gibt es auch viel mehr (aber kleinere) Klassen
  * Beides ist positiv, nur schon z.B. bezüglich Testbarkeit

## Pro Funktion eine Aufgabe

Eine Funktion sollte eine Aufgabe erledigen. Diese Aufgabe gut erledigen und nur diese Aufgabe.&#x20;

## Nur eine Abstraktionsebene pro Funktion

Single Level of Abstraction ist schwierig zu erlernen und zu erreichen.&#x20;

## Generell: switch-Anweisung vermeiden!

Mit switch-Anweisungen

* werden Funktionen gross
* erfüllt eine Funktion typisch merh als eine Aufgabe

Mit einem switch verletzt man viele Clean Code Regeln:&#x20;

* Single Responsiblity Prinzip
* Open Close Prinzip
* Don'r repeat yourself

Lösung: switch durch polymorphe Konstrukte ersetzen

## Anzahl der Funktionsargumente minimieren

Funktionsargumente sind schwer (im Sinne von gewichtig). Aus der Perspektive des Aufrufers (Nutzers) beatrachten. Bereits ab zwei Argumenten kann man diese Vertauschen. Bei mehr als drei Argumenten hört der Spass auf. Ohne Konsultation der Dokumentation kaum mehr nutzbar. Aufrufe werden mühsam, lang und unübersichtlich.

## Empfehlung aus Clean Code

Nidaldische Funktionen - keine Argumente (ideal)

Monaldische Funktionen - ein einziges Argument (wenn nötig)

Dyadische Funktionen - zwei Argumente (es gibt Gründe)

Triadische Funktion - drei Argumente (unbedingt vermeiden!)

Polyadische Funktionen - mehr als drei Argumente (nicht machen!)

## Reduktion der Argumente

Grundsätzlich: Mehr und kleiner Klassen, mit Attributen. Häufig können dyadische Funktionen ganz einfach auf monadische Funktionen zurückgeführt werden.&#x20;

Beispiel Dayadische Funktion:

```java
summe = addiere(summand1, summand2);
```

Beispiel monadische Form:&#x20;

```java
summe = summand1.addiere(summand2);
```

Reduktion verbessert auch die Lesbarkeit und Verständlichkeit.

## Keine Flag-Argumente

Reine Flag-Argumente sollte man möglichst vermeiden. Besser die Methode in verschiedene, aber eindeutig benannte Variante aufteilen -> Auch monadische Funktionen sind nicht immer gut.

Beispiel:&#x20;

```java
writeFile(myFile, true);
```

`myfile` ist klar, aber für was ist das boolean-Flag?

Besser zwei Alternativen:

```java
writeFileOverwrite(myFile);
writeFileAppend(myFile);
```

## Nebeneffekte vermeiden

Nebeneffekte sind Lügen! Funktionen versprechen eine Aufgabe zu erfüllen, aber sie erledigen noch andere, verborgene Aufgaben. Dies führt zu seltsamen Kopplungen und Abhängigkeiten. Im Fehlerfall sind sie schwer aufzudecken.&#x20;

## Output-Argumente vermeiden

Output Argumente sind in OO-Sprachen weitgehen überflüssig geworden. Wenn ein Rückgabewert nicht ausreicht, ist es die Aufgabe von `this` als Output zu agieren. Funktionsargumente werden am natürlichsten als Input einer Funktion interpretiert.&#x20;

## Anweisungen und Abfragen trennen

Funktionen sollten entweder&#x20;

* etwas tun und damit den Zustand eines Objektes ändern
* oder antworten und Informationen eines Objektes liefern

Aber nie beides, weil das verwirrt.&#x20;

Rückgabewerte einer Funktion provozieren den Einsatz in Bedingungen, wo es leicht zu Fehlinterpretationen kommen kann.

Es müssen Abfrage und Anweisung in zwei Methoden aufgetrennt werden, z.B. `getUsername()` und `setUsername()`.

## Keine Fehlercodes, besser Exceptions

Fehlercodes sind eine Verletzung der Anweisung- / Abfrage-Trennung! Sie fördern den Einsatz von if-Anweisungen.&#x20;

Fehlercodes müssen sofort ausgewertet werden,

* führt zu tief verschachtelten Strukturen
* sehr unübersichtlich und wartungsunfreundlich
* gefahr der Code-Duplikationen beim Fehlerhandling

Fehlercodes sind Abhängigkeitsmagenten! Darum sollten Fehlerbehandlungen besser mit Exceptions statt finden. Es ist auch nicht wirklich schön, aber macht den Code übersichtlicher und besser wartbar.&#x20;

## Fehlerverarbeitung einer Aufgabe

Fehlerverarbeitung und -behandlung ist eine Aufgabe. Deshalb sollte die Fehlerbehandlung in getrennte Methoden ausgelagert werden. Wenn eine Funktion try/catch Konstrukte enhält, sollte

* sie nicht anderes machen
* das try immer das erste Statement sein
* der catch (bzw. finnaly) Block der letze Block sein

So bleibt der Blick immer auf das Wesentliche fokussiert.&#x20;

## Gute Namensgebung von Funktionen

Gute Namengebung ist und bleibt eine Kunst.&#x20;

```java
public class Elektrotechnik {
    public double berechneLeistung(double spannung, double strom) {
        …
    }
}
```

```java
leistung = berechneLeistung(230, 3.1)
```

Empfohlene Grenzwerte für Funktionen:&#x20;

* Zeilenlänge maximal 150 Zeichen.
* Weniger als 20 Zeilen Code (im Schnitt).
* Keine Funktion sollte jemals länger als 100 Zeilen sein.
* Möglichst geringe Verschachtelung, maximal zwei Ebenen!

## Uncle Bobs Funktions Smells F1 – F4

F1: Zu viele Argumente.&#x20;

* Zu viele Argumente verderben den Brei!&#x20;
* 0 sind ideal, 1 ist ok, 2 in Ausnahmen, ab 3 sind es zu viel.&#x20;

F2: Output-Argumente.&#x20;

* Widersprechen der Intuition / dem was der Leser erwartet.&#x20;
* Stattdessen den Zustand des Objektes verändern!&#x20;

F3: Flag-Argumente.&#x20;

* Flag-Argumente zur Steuerung beweisen, dass in einer Funktion mehr als eine Aufgabe realisiert wird.

F4: Tote Funktionen&#x20;

* Methoden, die nie aufgerufen werden löschen!&#x20;
* Wir haben doch alles im Versionskontrollsystem

# Clean Code



Software Entwicklung ist ein echtes Handwerk. Quellcode Kommentare machen keinen guten Code. Kommentieren Sie schlechten Code nicht - schreiben Sie ihn um! Kommentare lügen, die Wahrheit liegt im Code. Kommentare sind ein notwendiges Übel.&#x20;

### Gute Kommentare

Grundsatz: Der beste Kommentar ist derjenige, den man gar nicht zu schreiben braucht. Energie besser in guten, selbsterklärenden Code stecken.&#x20;

Notwendige akzeptable Kommentare können sein:&#x20;

* Juristische Kommentare (Copyright)
* ToDo-Kommentare (aber nur temporär!)
* verstärkende, unterstreichende Kommentare, welche Dinge hervorheben, die sonst zu unauffällig wären
* Kommentare zur (Ex-) Klärung der übergeordneten Absicht oder zur expliziten Wanung von Konseqzenzen

### Schlechte Kommentare&#x20;

* Redundante Kommentare
* Irreführende Kommentare
* vorgeschriebene oder erzwungene Kommentare
* Tagebuch- oder Changelog-Kommentare
* Positionsbezeichner und Banner
* Zuschreibungen und Nebenbemerkungen
* Auskommentierter Code
* HTML-formatierte Kommentare
* zu viel Kommentar / Information

#### Ein guter Kommentar?

```java
// Timeout in Millisekunden
int time = 100; 
```

Der Kommentar kann entfallen, wenn der Code verbessert wird:

```java
int timeoutMilliseconds = 100;
```

\-> besser Namensgebung erspart uns viele Kommentare, aber gute Namensgebung ist anspruchsvoll.&#x20;

## Iteration über sieben Grade

Jeder einzelne Grad fokussiert auf eine relativ kleine, überschaubare ausgewählte Menge von Prinzipien und Praktiken.&#x20;

### Der erste Grad - schwarz

Sie wissen nun bereits was Clean Code Developer ist, und damit den ersten, schwarzen Grad erreicht.&#x20;

### Der zweite Grad - Rot

Prinzipien:

* Don't Repeat Yourself
* Keep it simple, stupid
* Vorsicht vor Optimierungen
* Favour Compsosition over Inheritance

Praktiken:&#x20;

* Die Pfadfinderregel beachten
* Root Cause Analysis
* Ein Versionskontrollsystem einsetzen
* Einfache Refakorisierungsmuster anwenden
* Täglich reflektieren

### Der dritte Grad - Orange

Prinzipien:&#x20;

* Single Level of Abstraction
* Single Responsibility Principle
* Separation of Concerns
* Source Code Konventionen: Namensregeln, Kommentare

Praktiken:&#x20;

* Issue Tracking
* Automatisierte Integrationstests
* Lesen, Lesen, Lesen
* Reviews

### Der vierte Grad - Gelb

Prinzipien:&#x20;

* Interface Segregation Principle
* Dependency Inversion Principle&#x20;
* Information Hiding Principle

Praktiken:&#x20;

* Automatisierte Unit Tests
* Mockups
* Code Coverage Analyse
* Teilnahme an Fachveranstaltungen
* Komplexe Refaktorisierungen

### Der fünfte Grad - Grün

Prinzipien:&#x20;

* Open Close Principle
* Tell, don't ask
* Law of Demeter

Praktiken:

* Continous Integration
* Statische Codeanalysen
* Inversion of Control Container
* Erfahrung weitergeben
* Messen von Fehlern

### Der sechste Grad - Blau

Prinzipien:&#x20;

* Implementatione spiegelt Entwurf
* Entwurf und Implementation überlappen nicht
* You Ain't Gonna Need It

Praktiken:

* Contionous Integration
* Iterative Entwicklung
* Komponentenorientierung
* Test First

### Der siebte Grad - Weiss

Der weisse Grad vereinigt alle Prinzipien und Praktiken der farbigen Grade. Eine gleichschwebende Aufmerksamkeit ist jedoch sehr schwer zu halten. Darum begint der Clean Code Developer im Gradesystem nach einiger Zeit wieder von vorne. Die zyklische Wiederholung bringt stetige Verbesserung auf der Basis von überschaubaren Schwerpunkten. CCD wird somit zur verinnerlichten Einstellung.&#x20;

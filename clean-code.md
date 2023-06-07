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

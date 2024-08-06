# Game of Life

Game of Life ist ein Zellularautomat,
der von John Horton Conway 1970 erfunden wurde.

Die Welt ist ein zweidimensionales Gitter von Zellen.
Jede Zelle kann entweder lebendig oder tot sein.
Die Welt entwickelt sich in diskreten Schritten (Generationen).
In jeder Generation wird jede Zelle anhand der Anzahl ihrer lebenden Nachbarn aktualisiert.

Die Regeln des Game of Life dürfen nicht verändert werden:

1. Eine lebende Zelle lebt auch in der Folgegeneration, wenn sie entweder zwei oder drei lebende Nachbarn hat.
2. Eine tote Zelle „wird geboren“ (lebt in der Folgegeneration), wenn sie genau drei lebende Nachbarn hat.
3. Die Welt ist ein Torus, d.h. die Zellen am Rand haben Nachbarn auf der gegenüberliegenden Seite.

Mehr nachzulesen unter https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens

Die `Main` Klasse enthält die JavaFX Oberfläche.  
Hier siehst du das Game of Life in Aktion.
Du bekommst auch eine Vorstellung davon,
wie langsam die Berechnung ist.
Oben links siehst du die Zeit x̃,
die für die Berechnung einer Generation benötigt wird.

Diese Version des Game of Life ist sehr langsam.  
Wenn die Performance für deinen Rechner zu schlecht ist,
verringere `Main.WORLD_SIZE`.

## Scripts

Starten

```shell
./gradlew run
```

Testen

```shell
./gradlew test
```

Benchmark

```shell
./gradlew jmh
```

## Aufgabe

Optimiere das Game of Life auf Laufzeit und Speicherverbrauch.

Alle Klassen, sowie die Tests und der Benchmark dürfen verändert werden,
um das Game of Life zu optimieren. Die Regeln des Game of Life dürfen nicht verändert werden.

## Hinweise

- Denke an die vorgestellten Techniken im Kurs: https://tsukinoko-kun.github.io/hhn-performance/runtime/
- Multi-Threading ist möglich, jedoch nicht empfohlen.
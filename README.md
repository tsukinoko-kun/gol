# Game of Life

Das ist ein Beispiel für ein nicht optimiertes Game of Life.

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

Optimiere das Game of Life

Die Regeln des Game of Life dürfen nicht verändert werden:

1. Eine lebende Zelle lebt auch in der Folgegeneration, wenn sie entweder zwei oder drei lebende Nachbarn hat.
2. Eine tote Zelle „wird geboren“ (lebt in der Folgegeneration), wenn sie genau drei lebende Nachbarn hat.
3. Die Welt ist ein Torus, d.h. die Zellen am Rand haben Nachbarn auf der gegenüberliegenden Seite.

Mehr nachzulesen unter https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens

Alle Klassen, sowie die Tests und der Benchmark dürfen verändert werden,
um das Game of Life zu optimieren. Die Regeln des Game of Life dürfen nicht verändert werden.

## Hinweise

- Denke an die vorgestellten Techniken im Kurs: https://tsukinoko-kun.github.io/hhn-performance/runtime/
- Multi-Threading ist möglich, jedoch nicht empfohlen, da es die Komplexität extrem erhöht.
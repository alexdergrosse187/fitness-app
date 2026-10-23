# FitnessPet Backend – erster lauffähiger API-Baustein

Kotlin + Ktor. Speichert den **gleichen State-Typ wie das vorhandene FitnessPet-Frontend** in `data/fitnesspet.json`.

## Voraussetzungen
- JDK 21
- Gradle installiert oder Gradle Wrapper im bestehenden Projekt. Das ZIP enthält **keine** Gradle-Wrapper-Binaries.
- Beim ersten Start Internet für Gradle-Abhängigkeiten.

## Start
Im Ordner `backend`:

```bash
gradle run
```

Falls dein bestehendes Projekt bereits `gradlew` enthält: `./gradlew run`.

## Test

```bash
curl http://localhost:8080/api/health
curl http://localhost:8080/api/state
```

## API
- `GET /api/health`: Serverstatus
- `GET /api/state`: Aktueller Zustand (Pets, Workouts, Meals, Ziele)
- `PUT /api/state`: Gesamten Zustand als JSON speichern, z.B. den JSON-Body aus `GET /api/state`.

Die Feldnamen entsprechen exakt dem bisherigen Frontend (`pets`, `activePetId`, `workouts`, `meals`, `goal`, `calorieGoal`). Ein einzelnes aktives Tier ist aktuell erlaubt; `pets` bleibt eine Liste für die spätere Erweiterung.

## Wichtige Grenzen
- **Nur lokaler Entwicklungsserver** (`127.0.0.1`): keine Authentifizierung und nur ein gemeinsamer Zustand.
- Das vorhandene Frontend verwendet weiterhin `localStorage`. Es wird **noch nicht automatisch** mit dieser API synchronisiert. Dafür muss die Frontend-Speicherlogik angepasst werden.
- Änderungen werden als vollständiger Zustand gespeichert; keine Konfliktauflösung für parallele Clients.
- Nicht ins öffentliche Internet stellen, bevor Authentifizierung, Benutzertrennung und weitere Validierung implementiert sind.
- Der Code wurde hier erstellt, aber **nicht mit Gradle kompiliert**.

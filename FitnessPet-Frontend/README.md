# FitnessPet – Frontend-Prototyp

Ein lokal startbarer, responsiver TypeScript/Vite-Prototyp mit fünf Seiten und einem animierten, durch Fitnessaktivitäten veränderlichen 2D-Drachen.

## Start

Voraussetzung: Node.js 20.19+ oder 22.12+.

```bash
npm install
npm run dev
```

Danach die im Terminal angezeigte Adresse im Browser öffnen (meist `http://localhost:5173`).

Produktions-Build prüfen:

```bash
npm run build
npm run preview
```

## Was bereits funktioniert

- Fünf Seiten mit fester Bottom-Navigation, responsivem Layout und vertikalem Scrollen.
- Wiederverwendbarer, animierter SVG-2D-Drache mit veränderlicher Arm- und Bauchform.
- Ein aktives Tier; Datenmodell unterstützt eine `pets`-Liste und `activePetId` für spätere Mehrtier-Unterstützung.
- Trainingspläne erstellen, Übungen ansehen, Workouts abschließen/löschen; abgeschlossene Workouts verändern Drakos XP und Muskelentwicklung.
- Mahlzeiten erfassen/löschen, Tageskalorien und Makros berechnen; Mahlzeiten wirken auf Tierenergie/Körperfülle.
- Tier streicheln, füttern und ausruhen lassen.
- Statistikübersicht, Wochenziel und Kalorienziel einstellen.
- Automatische Speicherung im Browser via localStorage.

## Noch nicht enthalten

- Kotlin/Ktor-Backend, Login, Cloud-Sync und echte Freundesranglisten.
- PWA-Offline-Caching und App-Icons (das Webmanifest ist nur vorbereitet).
- Externe Ernährungsdatenbank oder echte Ernährungsempfehlungen.
- Professionelles Sprite-Rigging; der Drache ist zunächst eine eigenständige animierte SVG-Vektorfigur.
- Historische Diagramme: Die Balken enthalten klar gekennzeichnete Demo-Werte.

## GitHub / Git

Falls du bereits ein FitnessPet-Repository hast: ZIP entpacken und den Inhalt **in den bestehenden `frontend/`-Ordner** kopieren. Anschließend im Wurzelordner deines vorhandenen Git-Repositories:

```bash
git status
git add frontend
git commit -m "Add FitnessPet frontend prototype"
git push
```

Wenn du das Repository erst von GitHub auf deinen Mac holen möchtest:

```bash
git clone https://github.com/DEIN-NAME/DEIN-REPO.git
cd DEIN-REPO
```

Anschließend den entpackten Frontend-Ordner dort einfügen. **Nicht** blind vorhandene Dateien überschreiben, ohne sie zu prüfen.

Wenn noch kein Remote-Repository existiert, kannst du lokal im Projektordner starten:

```bash
git init
git add .
git commit -m "Initial FitnessPet frontend"
```

Dann erst nach dem Anlegen eines GitHub-Repositories das passende Remote setzen und pushen.

## Datenschutz

Die Testdaten liegen nur im Browserprofil auf dem jeweiligen Gerät. Sie werden nicht automatisch zwischen Geräten synchronisiert. Browserdaten löschen entfernt auch die gespeicherten FitnessPet-Daten.

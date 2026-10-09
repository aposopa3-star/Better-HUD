# Better HUD – Fabric 1.21.11

Dieses Projekt ist Quellcode, keine getestete JAR.

## Ohne lokale Installation über GitHub Actions bauen
1. Erstelle ein leeres öffentliches oder privates Repository auf GitHub.
2. Lade **den Inhalt dieses ZIP-Archivs** in das Repository hoch (auch den versteckten Ordner `.github`).
3. Öffne den Tab **Actions**, wähle **Build Better HUD Fabric**, klicke **Run workflow**.
4. Wenn der Build erfolgreich ist, lade unten unter **Artifacts** `BetterHUD-Fabric-1.21.11` herunter.
5. Entpacke das Artifact und verwende die JAR **ohne** `-sources` im Dateinamen im Minecraft-Mods-Ordner.

Hinweis: Fabric Loader und Mod Menu sind separat zu installieren. Der Code wurde hier nicht gegen Minecraft kompiliert; der erste Cloud-Build kann noch Java- oder Versionsfehler aufdecken. Der Cloud-Build stellt keine Laufzeitprüfung in Minecraft dar.

## Lokal
Java 21 und Gradle 9.2.1 installieren, dann `gradle build` oder `BUILDEN.bat` ausführen.

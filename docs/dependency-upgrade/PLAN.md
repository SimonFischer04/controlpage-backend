# Dependency-Upgrade: Plan für den Implementierungs-Agenten

Stand: 2026-11-15, Basis-Commit `18eb324` (`main`).

## Auftrag

Hebe alle Abhängigkeiten von `controlpage-backend` auf den neuesten stabilen Stand. Der Kern ist der Sprung von Spring Boot 3.4.1 auf 4.1.2; fast alles andere folgt daraus. Das Verhalten der REST-API soll gleich bleiben, bis auf die unten als „bewusst akzeptiert" gelisteten Unterschiede.

Der Zielzustand wurde in einer Wegwerf-Kopie vollständig durchgespielt. Das Ergebnis liegt als [reference-upgrade.patch](reference-upgrade.patch) neben diesem Plan und lässt sich sauber auf `18eb324` anwenden. Nutze den Patch als Referenz und Abkürzung, aber arbeite die Schritte unten trotzdem ab: Der Patch enthält weder die neuen Tests (Schritt 1) noch Docker/CI (Schritt 4).

Die vollständigen Changelogs stehen in [CHANGELOG-spring-boot.md](CHANGELOG-spring-boot.md) und [CHANGELOG-libraries.md](CHANGELOG-libraries.md).

## Zielversionen

| Abhängigkeit | Jetzt | Ziel | Wie |
|---|---|---|---|
| `spring-boot-starter-parent` | 3.4.1 | **4.1.2** | `<parent>` |
| Spring Framework | 6.2.1 | 7.0.10 | über Boot |
| Hibernate ORM | 6.6.4.Final | 7.4.13.Final | über Boot |
| Jackson | 2.18.2 | 3.1.7 (`tools.jackson`) | über Boot |
| Tomcat | 10.1.34 | 11.0.27 | über Boot |
| JUnit Jupiter / Mockito | 5.11.4 / 5.14.2 | 6.0.3 / 5.23.0 | über Boot |
| Testcontainers | 1.20.4 | 2.0.5 | über Boot, **Artefakte umbenannt** |
| `org.postgresql:postgresql` | 42.7.3 (gepinnt) | 42.7.14 | Pin entfernen, Boot verwaltet |
| `org.projectlombok:lombok` | 1.18.36 (gepinnt) | 1.18.48 | Pin entfernen, Boot verwaltet |
| `springdoc-openapi-starter-webmvc-ui` | 2.7.0 | **3.2.0** | Property |
| `mapstruct-spring-annotations` / `-extensions` | 1.1.2 | **2.0.0** | Property |
| `org.mapstruct:mapstruct` (+ processor) | 1.6.3 | 1.6.3 | bleibt (1.7.0 ist nur Beta) |
| `lombok-mapstruct-binding` | 0.2.0 | 0.2.0 | bleibt (neueste Version) |
| `maven-compiler-plugin` | 3.11.0 (gepinnt) | 3.15.0 | Pin entfernen, Boot verwaltet |
| `hibernate-enhance-maven-plugin` | 6.6.4.Final | `hibernate-maven-plugin` 7.4.13.Final | **neue Koordinaten** |
| `native-maven-plugin` | 0.10.4 | 1.1.14 | über Boot |
| `junit:junit` (JUnit 4) | 4.13.2 | entfällt | im Code unbenutzt |

Nicht hochziehen: Spring Boot 4.2.0 ist erst als RC2 verfügbar, MapStruct 1.7.0 nur als Beta2.

Der Pin `postgresql.version=42.7.3` liegt heute sogar unter der von Boot 3.4.1 verwalteten 42.7.4; zwischen 42.7.3 und 42.7.14 wurden sechs CVEs behoben.

## Ausgangslage (gemessen)

- `mvn -B clean test` auf `18eb324`: 41 Tests, 0 Fehler, 1 übersprungen, sofern eine PostgreSQL erreichbar ist.
- 9 der 20 Testklassen erben von `ControlpageIntegrationTestBase` und brauchen Docker (Testcontainers). Ohne Docker schlagen sie mit `ContainerFetch` fehl; das ist dann ein Umgebungsproblem und kein Upgrade-Fehler.
- `ViewControllerIntTest` enthält keinen einzigen Test. Der HTTP-Weg `POST /api/view` ist deshalb ungetestet, und genau dort bricht das Upgrade (siehe Schritt 1 und 2).

### Tests ohne Docker laufen lassen

Falls in deiner Umgebung kein Docker läuft, aber PostgreSQL-Binaries vorhanden sind (`/usr/lib/postgresql/16/bin`), geht es so:

1. Wegwerf-Instanz starten: `initdb` und `pg_ctl` als User `postgres` auf einem freien Port, `--auth=trust`, dann `create database trialdb`.
2. In `ControlpageIntegrationTestBase` **lokal und ohne Commit** `@Testcontainers` und das `@Container`-Feld auskommentieren.
3. `mvn -B clean test -Dspring.datasource.url=jdbc:postgresql://127.0.0.1:<port>/trialdb -Dspring.datasource.username=postgres -Dspring.datasource.password=x -Dspring.datasource.hikari.connection-timeout=5000`
4. Vor dem Commit die Testbasis zurücksetzen und prüfen, dass sie mit Testcontainers zumindest kompiliert.

Schreibe in deinen Abschlussbericht, auf welchem der beiden Wege du getestet hast.

## Schritte

Jeder Schritt ist ein eigener Commit und endet mit einem grünen `mvn -B clean test`.

### Schritt 1: Sicherheitsnetz auf dem alten Stand (noch Boot 3.4.1)

Schreibe zuerst die Tests, die das Upgrade absichern, und lass sie auf dem alten Stand grün laufen.

1. **HTTP-Roundtrip für Views.** Ein `@SpringBootTest` mit `@AutoConfigureMockMvc` auf Basis von `ControlpageIntegrationTestBase`: `POST /api/view` mit einem `FullViewDTO`, das eine `RestAction` enthält, danach `GET /api/view/all` und `GET /api/view/{id}`; erwarte 200 und denselben Inhalt. Vergleiche das JSON nicht streng nach Feldreihenfolge (`content().json(...)` ist per Default tolerant). Beispiel-Payload, im Probelauf verwendet:
   ```json
   {"id":0,"name":"Trial view","groupId":0,"fields":[[{"id":0,"viewId":0,
    "action":{"type":"REST","id":0,"runPolicy":null,"restType":"GET","url":"http://example.invalid/x","body":null},
    "title":{"id":0,"text":"Hello","horizontalAlignment":"CENTER","verticalAlignment":"CENTER","color":"#ffffff"},
    "description":"d","backgroundId":0,"rowspan":1,"colspan":1}]]}
   ```
2. **Serialisierung von `Group`.** Ein Test, der eine `Group` mit einer Kind-Gruppe und einer `BasicView` über den Spring-`ObjectMapper` serialisiert und dieses JSON erwartet (Stand heute):
   ```json
   {"id":1,"childGroups":[{"id":2,"childGroups":[],"name":"child","views":[]}],"name":"parent","views":[{"id":7,"name":"v"}]}
   ```
3. **Fehlerfall.** `POST /api/view` mit kaputtem JSON liefert 400 und `application/problem+json`.

Im selben Commit, weil auf 3.4.1 schon möglich und verhaltensneutral:

- `@MockBean` → `@MockitoBean` (`org.springframework.test.context.bean.override.mockito.MockitoBean`) in `ImageControllerIntTest` und `ViewControllerIntTest`.
- Dependency `junit:junit` aus der `pom.xml` entfernen.

### Schritt 2: Upgrade auf Spring Boot 4.1.2

Alles in diesem Schritt gehört zusammen; Zwischenstände kompilieren nicht.

**`pom.xml`**

- Parent auf `4.1.2`.
- Properties `org.projectlombok.version`, `postgresql.version` und `org.apache.maven.plugins.maven-compiler-plugin.version` löschen, ebenso die drei `<version>`-Zeilen, die sie verwenden. Das behebt nebenbei, dass Lombok als Dependency und als Annotation-Processor aus zwei verschiedenen Properties kam.
- `org.mapstruct.extensions.spring.version` → `2.0.0`, `org.springdoc.version` → `3.2.0`.
- `spring-boot-starter-web` → `spring-boot-starter-webmvc` (der alte Starter ist deprecated).
- Neu, Scope `test`: `spring-boot-starter-webmvc-test` (liefert `@WebMvcTest` und MockMvc).
- Testcontainers: `org.testcontainers:junit-jupiter` → `testcontainers-junit-jupiter`, `org.testcontainers:postgresql` → `testcontainers-postgresql`.
- Hibernate-Plugin: `org.hibernate.orm.tooling:hibernate-enhance-maven-plugin` → `org.hibernate.orm:hibernate-maven-plugin`. Goal `enhance` und `${hibernate.version}` bleiben. Lass `enableAssociationManagement=true` stehen; `enableLazyInitialization` und `enableDirtyTracking` kannst du entfernen (beide deprecated, Default seit 7.1.5 ohnehin `true`).

**Jackson 3: der eigentliche Stolperstein**

Jackson 3 lehnt `@JsonManagedReference`/`@JsonBackReference` auf Java-Records ab. `Group` ist ein Record und hängt über `Action`/`Field`/`FullView` am Deserialisierer von `FullViewDTO`. Folge ohne Fix: jeder `POST /api/view` endet in einem 500 (`InvalidDefinitionException: Cannot add back-reference to a java.lang.Record type`), obwohl alle bestehenden Tests grün sind.

Fix, im Probelauf verifiziert:

- `model/domain/Group.java`: beide `@JsonManagedReference` entfernen, `@JsonBackReference("parent-group")` durch `@JsonIgnore` ersetzen.
- `model/domain/view/BasicView.java`: `@JsonBackReference("viewGroup")` durch `@JsonIgnore` ersetzen.

Das erzeugte JSON ist danach identisch zu heute. Die Annotationen bleiben im Paket `com.fasterxml.jackson.annotation`; nur `ObjectMapper` zieht um.

**Übrige Code-Änderungen**

- `ImageControllerIntTest`: `com.fasterxml.jackson.databind.ObjectMapper` → `tools.jackson.databind.ObjectMapper`.
- Beide Controller-Tests: `@WebMvcTest` kommt jetzt aus `org.springframework.boot.webmvc.test.autoconfigure`.
- `ControlpageIntegrationTestBase`: Import `org.testcontainers.postgresql.PostgreSQLContainer`; die Klasse ist nicht mehr generisch, also `static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:latest");`.
- Der neue Roundtrip-Test aus Schritt 1: `@AutoConfigureMockMvc` liegt jetzt in `org.springframework.boot.webmvc.test.autoconfigure`.

**Prüfen**

- `mvn -B clean test` grün, inklusive der Tests aus Schritt 1.
- App starten und per HTTP prüfen: `/v3/api-docs`, `/swagger-ui.html`, `/actuator/health`, View-Roundtrip, Bild-Upload und -Download, Upload einer Textdatei (400).

### Schritt 3: Deprecations aufräumen

- `org.springframework.lang.Nullable` → `org.jspecify.annotations.Nullable` in den 16 Mapper-Dateien unter `util/mapper/`. JSpecify kommt transitiv mit.
- JSpecify-`@Nullable` ist eine Type-Use-Annotation. An zwei Stellen muss sie deshalb umziehen, sonst kompiliert es nicht („scoping construct cannot be annotated with type-use annotation"):
  - `FieldDTOToFieldMapper`: `convert(ViewController.@Nullable FieldDTO fieldDTO)`
  - `FullViewDTOToFullViewMapper`: `convert(ViewController.@Nullable FullViewDTO fullViewDTO)`
- `application.yml`: `spring.jpa.properties.hibernate.dialect` entfernen (Hibernate warnt bei jedem Start, `HHH90000025`).

### Schritt 4: Docker und CI

Unabhängig vom Code, aber das Image-Build ist heute schon kaputt:

- `Dockerfile`: `openjdk:17` existiert auf Docker Hub nicht mehr (Tag liefert 404). Ersetze durch `eclipse-temurin:17-jre`. Der `ADD` verweist auf `controlpage-backend-0.0.1-SNAPSHOT.jar`, das Projekt baut aber `2.0.0-SNAPSHOT`; korrigiere den Namen oder kopiere per Wildcard.
- `.gitlab-ci.yml`: `maven:3-openjdk-17-slim` wurde zuletzt im April 2022 aktualisiert. Ersetze durch `maven:3-eclipse-temurin-17`.

Beides konnte ich hier nicht bauen (kein Docker). Prüfe das Image-Build, wenn deine Umgebung es zulässt, und sag es sonst im Bericht.

## Bewusst akzeptierte Verhaltensänderungen

Im Probelauf gegen die alte Version verglichen. Nichts davon ändert den Inhalt der API-Antworten.

| Was | Vorher | Nachher | Zurückdrehen |
|---|---|---|---|
| OpenAPI-Spec unter `/v3/api-docs` | `"openapi": "3.0.1"` | `"openapi": "3.1.0"`; bei den drei polymorphen Schemas fehlt `"type": "object"` | `springdoc.api-docs.version=openapi_3_0` (laut springdoc-Doku, nicht getestet) |
| Feldreihenfolge im JSON bei Klassen (z. B. `Action`) | Deklarationsreihenfolge | alphabetisch; Records unverändert | `spring.jackson.use-jackson2-defaults=true` (nicht getestet) |
| `ProblemDetail`-Antworten | enthalten `"type":"about:blank"` | Feld fehlt | – |
| Start-Log | – | zwei springdoc-Warnungen, dass API-Docs und Swagger-UI per Default aktiv sind | `springdoc.api-docs.enabled` / `springdoc.swagger-ui.enabled` |
| Build-Log | – | Hibernate warnt, dass Association Management deprecated ist (`HHH90000029`) | siehe offene Entscheidungen |

Setze keine der Rückdreh-Optionen von dir aus. Wenn ein Test aus Schritt 1 an einem dieser Punkte scheitert, passe den Test an und nenne es im Bericht.

## Was der Probelauf belegt und was nicht

Belegt, jeweils alte gegen neue Version:

- Kompiliert; 41 Tests grün gegen PostgreSQL 16, mit `enableAssociationManagement=true` wie auch ganz ohne Enhancement-Optionen.
- View-Roundtrip, Bild-Upload/-Download, Fehlerfälle und CORS-Header verhalten sich gleich.
- Die neue Version startet auf einer Datenbank, die die alte angelegt und befüllt hat. `ddl-auto: update` ändert am Schema nichts (`pg_dump -s` vorher/nachher identisch), alte Views und Bilder werden identisch gelesen.

Nicht belegt:

- Lauf mit echtem Testcontainers/Docker; die Testcontainers-2-Variante der Testbasis ist nur kompiliert.
- GraalVM Native Image. Das `native-maven-plugin` springt von 0.10.4 auf 1.1.14 mit neuem Metadaten-Format und braucht GraalVM 25+. Falls Native Builds genutzt werden, gehört ein Native-Build in die Abnahme.
- Docker-Image und GitLab-CI.
- Eine Produktionsdatenbank mit echten Daten; getestet wurde nur ein kleines, frisch erzeugtes Schema.
- Der Zwischenstand nach Schritt 1 auf Boot 3.4.1; verifiziert ist der Endzustand.

## Offene Entscheidungen

Der Agent entscheidet diese nicht selbst, sondern nimmt die Vorgabe:

1. **Java-Version.** Vorgabe: bei 17 bleiben. Boot 4.1 unterstützt 17 bis 27; ein Wechsel auf 21 oder 25 ist ein eigener, kleiner Folgeschritt.
2. **`enableAssociationManagement`.** Vorgabe: bleibt `true`, damit sich am Laufzeitverhalten nichts ändert. Hibernate will das Feature entfernen; die Tests laufen auch ohne. Das Abschalten ist ein Folgeschritt, bei dem beide Seiten bidirektionaler Beziehungen im Code gepflegt werden müssen.
3. **Spring Boot 4.2.** Vorgabe: nicht jetzt. Sobald 4.2.0 final ist, als eigenes kleines Update nachziehen und dabei die springdoc-Kompatibilität prüfen.

## Nicht Teil dieses Auftrags

Beim Durchsehen aufgefallen, hier nur vermerkt:

- `dev-env-compose.yml` startet MariaDB, die Anwendung spricht PostgreSQL.
- Datenbank-Zugangsdaten stehen im Klartext in `application.yml` und `dev-env-compose.yml`.
- `ControlpageBackendApplication.main`: Der Regex `\\d*]` verlangt eine schließende eckige Klammer; ein Port-Argument wird deshalb nie erkannt.
- `application.yml` enthält `spring.data.rest.*`, obwohl Spring Data REST nicht eingebunden ist.

## Abnahme

- [ ] `mvn -B clean test` grün, mit Angabe ob über Docker oder lokale PostgreSQL
- [ ] Neue Tests aus Schritt 1 vorhanden und grün
- [ ] `mvn dependency:tree` zeigt Boot 4.1.2, Hibernate 7.4.13.Final, `tools.jackson` 3.1.7, Testcontainers 2.0.5, PostgreSQL-Treiber 42.7.14 und kein `junit:junit`
- [ ] Kein Import mehr von `org.springframework.lang.Nullable`, `org.springframework.boot.test.mock.mockito` oder `org.testcontainers.containers.PostgreSQLContainer`
- [ ] App startet; `/v3/api-docs`, `/swagger-ui.html`, `/actuator/health` liefern 200
- [ ] Abschlussbericht nennt, was aus „Nicht belegt" inzwischen geprüft wurde und was offen bleibt

Kein Pull Request auf GitHub: Laut README ist GitHub nur ein Spiegel, entwickelt wird auf der GitLab-Instanz.

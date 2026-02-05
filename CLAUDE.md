# CLAUDE.md

Java educational project with standalone examples. Each class has its own `main()` method.

## Build & Run

```bash
# Compile
javac -d ./out/production/Lessons ./src/<package>/<Class>.java

# Run
java -cp out/production/Lessons <package>.<Class>
```

## Configuration

- JDK: OpenJDK 23
- Language Level: Java 21
- Source: `src/`
- Output: `out/production/Lessons/`

## Structure

```
src/
├── tutorial/          # Core Java (no build system)
├── newfeatures/       # Java 5-21 version features
└── sample/            # Maven project with dependencies
```

### tutorial/

| Category | Packages |
|----------|----------|
| OOP | `interfaces`, `encapsulation`, `polymorphism`, `abstractclass`, `innerclass` |
| Concurrency | `thread` (executors, virtual threads, CompletableFuture, locks, semaphores) |
| Functional | `lambda`, `stream`, `predicate`, `method_reference`, `comparator` |
| Collections | `list`, `maps`, `generics`, `sort` |
| Types | `datatype`, `string`, `strings`, `numbers`, `arraydemo` |
| I/O | `fileio`, `scanner` |
| Advanced | `reflection`, `annotation`, `regex`, `exceptions` |
| Other | `date`, `timestamp`, `equals`, `constructor`, `overload`, `override`, `staticmembers` |
| Models | `models/` (vehicle, computer, common, album, city) |

### newfeatures/

`v5/` through `v25/` - features introduced in each Java version.

### sample/ (Maven)

Path: `src/sample/src/main/java/`

| Category | Packages |
|----------|----------|
| Data | `json` (Gson), `xml` (JAXB), `excel` (POI), `pdf`, `pdfsign`, `doc` |
| Utils | `utility`, `lombok`, `encrypt`, `mail`, `random`, `reflection` |
| Models | `models/`, `dto/` |
| Scheduling | `cron`, `quartz` |
| Other | `timezone`, `money`, `geo` |

## Notes

- Tutorial package: no external dependencies
- Sample package: uses Maven for dependencies
- No test framework; run main methods directly

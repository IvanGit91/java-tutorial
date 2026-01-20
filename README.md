# Java Lessons

A comprehensive collection of standalone Java examples demonstrating core concepts, language features, and evolution from Java 5 to Java 21. Each example is executable independently, making this an ideal resource for learning Java concepts through practical, hands-on code.

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Building and Running Examples](#building-and-running-examples)
- [Topics Covered](#topics-covered)
- [Contributing](#contributing)
- [License](#license)

## Overview

This educational project contains standalone Java classes organized by topic, with each class typically containing its own `main()` method for direct execution.

**Note:** These tutorials and samples were written over the years during Java development to test and learn various functionalities. As such, they are not written perfectly and may not follow all best practices—they serve primarily as learning experiments and reference examples.

The project is designed for:

- **Java learners** exploring core concepts and language features
- **Developers** looking for practical examples of specific Java functionality
- **Educators** seeking teaching materials with executable demonstrations
- **Students** preparing for Java certification or coursework

### What This Project Covers

- Fundamental Java concepts (data types, encapsulation, polymorphism)
- Object-oriented programming principles
- Concurrency and multithreading
- Java version evolution (Java 5 through Java 21 features)
- File I/O operations
- Collections and generics
- Reflection and annotations

### What This Project Does NOT Cover

- Production-ready application architecture
- Enterprise frameworks (Spring, Hibernate, etc.)
- Database integration
- Web development
- Testing frameworks (JUnit, Mockito, etc.)

## Prerequisites

- **JDK Version**: OpenJDK 23 (or compatible)
- **Language Level**: Java 21
- **IDE**: IntelliJ IDEA (recommended) or any Java IDE
- **Operating System**: Cross-platform (Linux, macOS, Windows)

## Project Structure

```
Lessons/
├── src/                              # Source directory
│   ├── tutorial/                     # Core Java tutorials
│   │   ├── thread/                   # Concurrency examples
│   │   │   ├── VirtualThead.java     # Java 21 virtual threads
│   │   │   ├── ExecutorServiceExample.java
│   │   │   ├── CompletableFuture examples
│   │   │   ├── SemaphoreDemo.java
│   │   │   ├── ReentrantLockExample.java
│   │   │   └── ...
│   │   ├── interfaces/               # Interface examples
│   │   ├── encapsulation/            # Access modifiers
│   │   │   ├── parent/
│   │   │   └── child/
│   │   ├── fileio/                   # File I/O operations
│   │   ├── string/ & strings/        # String manipulation
│   │   ├── staticmembers/            # Static variables, methods, blocks
│   │   ├── reflection/               # Reflection API
│   │   ├── generics/                 # Generic types
│   │   │   ├── s1/                   # Basic generics
│   │   │   ├── s2/                   # Wildcard examples
│   │   │   └── s3/                   # Advanced generics
│   │   ├── lambda/                   # Lambda expressions
│   │   │   ├── s1/                   # Lambda basics
│   │   │   ├── s2/                   # List processing
│   │   │   └── s3/                   # Advanced lambdas
│   │   ├── annotation/               # Custom annotations
│   │   ├── comparator/               # Comparator implementations
│   │   ├── date/                     # Date/time handling
│   │   ├── equals/                   # equals/hashCode
│   │   ├── list/                     # Collection operations
│   │   ├── maps/                     # Map examples
│   │   ├── method_reference/         # Method references (::)
│   │   ├── predicate/                # Predicate functions
│   │   │   ├── s1/
│   │   │   └── s2/
│   │   ├── regex/                    # Regular expressions
│   │   ├── scanner/                  # Input handling
│   │   │   ├── s1/
│   │   │   └── s2/
│   │   ├── stream/                   # Stream API
│   │   ├── innerclass/               # Inner classes
│   │   ├── polymorphism/             # Polymorphism
│   │   ├── abstractclass/            # Abstract classes
│   │   ├── exceptions/               # Exception handling
│   │   ├── datatype/                 # Primitive types
│   │   ├── arraydemo/                # Array operations
│   │   ├── constructor/              # Constructor examples
│   │   ├── overload/                 # Method overloading
│   │   ├── override/                 # Method overriding
│   │   ├── models/                   # Model classes
│   │   │   ├── vehicle/              # Vehicle, Engine, Wheel
│   │   │   ├── computer/             # Computer, MacbookPro
│   │   │   ├── common/               # Person, Pair
│   │   │   ├── album/                # Album classes
│   │   │   └── city/                 # City classes
│   │   ├── maths/                    # Math operations
│   │   ├── numbers/                  # Number operations
│   │   ├── timestamp/                # Timestamp handling
│   │   ├── timertest/                # Timer tasks
│   │   ├── sort/                     # Sorting examples
│   │   ├── nullpointer/              # Null handling
│   │   ├── copyreference/            # Reference copying
│   │   └── activedirectory/          # LDAP examples
│   │
│   ├── newfeatures/                  # Java version-specific features
│   │   ├── java5/                    # Generics, enums, varargs
│   │   ├── java6/                    # Scripting API
│   │   ├── java7/                    # Try-with-resources, diamond operator
│   │   ├── java8/                    # Lambda, streams, Optional
│   │   ├── java9/                    # Modules, private interface methods
│   │   ├── java10/                   # Local variable type inference (var)
│   │   ├── java11/                   # String methods, HTTP client
│   │   ├── java12/                   # Switch expressions (preview)
│   │   ├── java13/                   # Text blocks, yield (preview)
│   │   ├── java14/                   # Records (preview), pattern matching
│   │   ├── java15/                   # Sealed classes, text blocks
│   │   ├── java16/                   # Records, pattern matching for instanceof
│   │   ├── java17/                   # Sealed classes, enhanced switch
│   │   ├── java18/                   # UTF-8 by default
│   │   ├── java19/                   # Virtual threads (preview)
│   │   ├── java20/                   # Record patterns
│   │   └── java21/                   # Virtual threads, pattern matching
│   │
│   └── sample/                       # Advanced examples (Maven project)
│       └── src/main/java/
│           ├── common/               # Album, Track, Artist, Member
│           ├── models/               # Model classes
│           │   ├── common/           # Common models
│           │   └── entity/           # Entity classes
│           ├── lombok/               # Lombok examples
│           │   └── builder/          # Builder pattern
│           │       └── advancedsearch/
│           ├── utility/              # Utility classes
│           ├── json/                 # JSON processing (Gson)
│           ├── xml/                  # XML processing (JAXB)
│           ├── excel/                # Excel handling (POI)
│           ├── pdf/                  # PDF generation
│           ├── pdfsign/              # PDF signing
│           ├── doc/                  # Document utilities
│           ├── mail/                 # Email handling
│           ├── encrypt/              # Encryption examples
│           ├── cron/                 # Cron examples
│           ├── quartz/               # Quartz scheduler
│           ├── random/               # Random generation
│           ├── reflection/           # Reflection examples
│           ├── timezone/             # Timezone examples
│           ├── money/                # Money handling
│           ├── geo/                  # Geospatial
│           └── dto/                  # Data transfer objects
│
└── out/production/Lessons/           # Compiled class files
```

## Installation

1. **Clone or download the project:**
   ```bash
   git clone <repository-url> JavaLessons
   cd JavaLessons
   ```

2. **Open in IntelliJ IDEA:**
   - Open IntelliJ IDEA
   - Select `File > Open`
   - Navigate to the project directory
   - Click `OK`

3. **Configure Project SDK:**
   - Go to `File > Project Structure > Project`
   - Set `SDK` to OpenJDK 23 (or Java 23)
   - Set `Language level` to `21 - Pattern matching for switch`
   - Click `Apply` and `OK`

4. **Verify source and output directories:**
   - In `Project Structure > Modules`
   - Ensure `src` is marked as Sources Root
   - Ensure output path is `out/production/Lessons`

## Building and Running Examples

### Using IntelliJ IDEA

1. **Run a single example:**
   - Navigate to any class with a `main()` method
   - Right-click on the class file or editor
   - Select `Run 'ClassName.main()'`

2. **Build the entire project:**
   - Select `Build > Build Project` (Ctrl+F9 / Cmd+F9)

### Using Command Line

#### Compile a Single Class

```bash
# Navigate to project root
cd JavaLessons

# Compile a specific class from tutorial package
javac -d ./out/production/Lessons ./src/tutorial/package/ClassName.java

# Example: Compile data types demo
javac -d ./out/production/Lessons ./src/tutorial/datatype/DataTypes.java
```

#### Run a Compiled Class

```bash
# Run using the fully qualified class name
java -cp out/production/Lessons tutorial.package.ClassName

# Examples:
java -cp out/production/Lessons tutorial.datatype.DataTypes
java -cp out/production/Lessons tutorial.thread.VirtualThead
java -cp out/production/Lessons tutorial.lambda.s1.LambdaGame
java -cp out/production/Lessons newfeatures.java8.Java8
```

#### Compile and Run Multiple Related Classes

If a class depends on other classes in the same package:

```bash
# Compile all classes in a package
javac -d ./out/production/Lessons ./src/tutorial/thread/*.java

# Run the main class
java -cp out/production/Lessons tutorial.thread.ExecutorServiceExample
```

#### Compile Entire Project

```bash
# Find and compile all Java source files
find ./src -name "*.java" -type f -print | xargs javac -d ./out/production/Lessons
```

### Troubleshooting Common Build Issues

**Issue: `class file has wrong version`**
- Solution: Ensure you're using JDK 23 or compatible version
- Check: `java -version` and `javac -version`

**Issue: `package does not exist`**
- Solution: Compile dependent classes first, or use wildcard compilation
- Example: `javac -d ./out/production/Lessons ./src/tutorial/package/*.java`

**Issue: `could not find or load main class`**
- Solution: Verify the classpath includes `out/production/Lessons`
- Solution: Ensure the fully qualified class name matches the package structure

**Issue: Missing output directory**
- Solution: Create the output directory manually:
  ```bash
  mkdir -p ./out/production/Lessons
  ```

## Topics Covered

### Core Java Concepts (tutorial/)

- **Data Types** (`datatype/`) - Primitive types, casting, type conversion
- **Encapsulation** (`encapsulation/`) - Access modifiers, package-level access
- **Inheritance** - Parent-child relationships, method overriding
- **Polymorphism** (`polymorphism/`) - Runtime polymorphism, method overloading
- **Abstract Classes** (`abstractclass/`) - Abstract methods and classes
- **Interfaces** (`interfaces/`) - Interface implementation, default methods
- **Inner Classes** (`innerclass/`) - Nested, anonymous, and local classes
- **Generics** (`generics/`) - Generic types, wildcards, bounded types

### Java Language Features (tutorial/)

- **Arrays** (`arraydemo/`) - Array creation, manipulation, sorting
- **Strings** (`string/`, `strings/`) - String operations, formatting
- **Regular Expressions** (`regex/`) - Pattern matching and text processing
- **Reflection** (`reflection/`) - Runtime class inspection and manipulation
- **Annotations** (`annotation/`) - Custom annotations, processing
- **Exception Handling** (`exceptions/`) - Try-catch, custom exceptions
- **Static Members** (`staticmembers/`) - Static variables, methods, blocks
- **Constructors** (`constructor/`) - Constructor chaining and inheritance
- **Lambda Expressions** (`lambda/`) - Functional programming in Java
- **Method References** (`method_reference/`) - Using :: operator
- **Predicates** (`predicate/`) - Functional predicates for filtering
- **Streams** (`stream/`) - Stream API operations
- **Comparators** (`comparator/`) - Custom sorting logic
- **Date/Time** (`date/`, `timestamp/`) - Date and time handling

### Concurrency and Multithreading (tutorial/thread/)

- **Thread Basics** - Thread creation, start, and lifecycle
- **Synchronization** - Synchronized blocks and methods
- **Locks** - ReentrantLock, ReadWriteLock
- **Semaphores** - Resource access control
- **Executors** - Thread pools, ExecutorService
- **CompletableFuture** - Asynchronous programming
- **Virtual Threads** - Java 21 lightweight threads
- **Wait/Notify** - Thread communication

### File I/O (tutorial/fileio/)

- Reading and writing text files
- Buffered I/O operations
- Stream-based file processing
- File creation and manipulation

### Java Version Features (newfeatures/)

Each subdirectory demonstrates features introduced in specific Java versions:

- **Java 5**: Generics, enhanced for loop, enums, varargs
- **Java 6**: Scripting API improvements
- **Java 7**: Try-with-resources, diamond operator, strings in switch
- **Java 8**: Lambda expressions, Stream API, Optional, Date/Time API
- **Java 9**: Modules, private interface methods
- **Java 10**: Local variable type inference (`var`)
- **Java 11**: String methods, HTTP client
- **Java 12**: Switch expressions (preview)
- **Java 13**: Text blocks (preview), switch yield keyword
- **Java 14**: Records (preview), pattern matching
- **Java 15**: Text blocks, sealed classes (preview)
- **Java 16**: Records, pattern matching for instanceof
- **Java 17**: Sealed classes, enhanced switch
- **Java 18**: UTF-8 by default, simple web server
- **Java 19**: Virtual threads (preview), pattern matching enhancements
- **Java 20**: Record patterns, scoped values
- **Java 21**: Virtual threads, sequenced collections, pattern matching for switch

### Advanced Examples (sample/)

The sample package contains Maven-based examples with external dependencies:

- **JSON Processing** (`json/`) - Gson library examples
- **XML Processing** (`xml/`) - JAXB examples
- **Excel Handling** (`excel/`) - Apache POI examples
- **PDF Operations** (`pdf/`, `pdfsign/`) - PDF generation and signing
- **Document Utilities** (`doc/`) - Word document handling
- **Email Handling** (`mail/`) - JavaMail examples
- **Encryption** (`encrypt/`) - Cryptography examples
- **Scheduling** (`cron/`, `quartz/`) - Task scheduling
- **Lombok** (`lombok/`) - Lombok annotations and builders
- **Utilities** (`utility/`) - Common utility classes

### Model Classes

**Tutorial Models** (`tutorial/models/`):
- `vehicle/` - Vehicle, Engine, Wheel classes
- `computer/` - Computer, MacbookPro, ComputerUtils
- `common/` - Person, Pair
- `album/` - Album-related classes
- `city/` - City-related classes

**Sample Models** (`sample/src/main/java/models/`):
- `common/` - Pair and common types
- `entity/` - Entity classes (City, ParkingZone, Shape, etc.)

## Contributing

This is an educational project. If you'd like to contribute:

1. Add new examples following the existing structure
2. Include a `main()` method for standalone execution
3. Add clear comments explaining the concept
4. Place files in appropriate package directories
5. Test compilation and execution before submitting

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

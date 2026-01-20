# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java educational project containing standalone examples demonstrating various Java concepts. Each package represents a specific topic with executable classes containing `main()` methods.

**Note:** These tutorials and samples were written over the years during Java development to test and learn various functionalities. As such, they are not written perfectly and may not follow all best practices—they serve primarily as learning experiments and reference examples.

## Build and Run

This project uses IntelliJ IDEA with no Maven/Gradle build system for the tutorial package. The sample package uses Maven. Classes are compiled to `out/production/Lessons/`.

**Compile and run from command line:**
```bash
# Compile a specific file
javac -d ./out/production/Lessons ./src/tutorial/package/ClassName.java

# Run a class
java -cp out/production/Lessons tutorial.package.ClassName
```

**Examples:**
```bash
java -cp out/production/Lessons tutorial.datatype.DataTypes
java -cp out/production/Lessons tutorial.thread.VirtualThead
java -cp out/production/Lessons tutorial.interfaces.InterfaceDemo
java -cp out/production/Lessons tutorial.models.vehicle.Vehicle
java -cp out/production/Lessons tutorial.lambda.s1.LambdaGame
java -cp out/production/Lessons newfeatures.java8.Java8
```

## Project Configuration

- **JDK:** OpenJDK 23
- **Language Level:** Java 21
- **Source directory:** `src/`
- **Output directory:** `out/production/Lessons/`

## Code Organization

The project is organized into three main areas:

### Tutorial Package (`tutorial/`)

Core Java concepts and fundamentals organized by topic:

- **`thread/`** - Concurrency examples: synchronization, semaphores, reentrant locks, executors, virtual threads, CompletableFuture
- **`interfaces/`** - Interface implementation examples
- **`encapsulation/`** - Uses parent/child subdirectory structure to demonstrate package-level access
- **`fileio/`** - File I/O operations (reading, writing, buffered streams)
- **`string/`** and **`strings/`** - String manipulation and formatting examples
- **`staticmembers/`** - Static variables, methods, and blocks
- **`reflection/`** - Runtime class inspection and manipulation
- **`generics/`** - Generic types and wildcard examples (s1, s2, s3 sub-packages)
- **`innerclass/`** - Inner and nested class examples
- **`polymorphism/`** - Polymorphism demonstrations
- **`abstractclass/`** - Abstract class examples
- **`exceptions/`** - Exception handling examples
- **`lambda/`** - Lambda expression examples (s1, s2, s3 sub-packages)
- **`annotation/`** - Custom annotations and annotation processing
- **`comparator/`** - Comparator implementations
- **`date/`** - Date and time handling examples
- **`equals/`** - equals/hashCode demonstrations
- **`list/`** - List and collection operations
- **`maps/`** - Map examples
- **`method_reference/`** - Method reference (::) examples
- **`predicate/`** - Predicate function examples (s1, s2 sub-packages)
- **`regex/`** - Regular expression examples
- **`scanner/`** - Scanner input examples (s1, s2 sub-packages)
- **`stream/`** - Stream API examples
- **`datatype/`** - Primitive types and casting
- **`arraydemo/`** - Array operations
- **`constructor/`** - Constructor examples
- **`overload/`** - Method overloading
- **`override/`** - Method overriding
- **`maths/`** - Mathematical calculations
- **`numbers/`** - Number operations
- **`timestamp/`** - Timestamp handling
- **`timertest/`** - Timer task examples
- **`sort/`** - Sorting examples
- **`nullpointer/`** - Null handling
- **`copyreference/`** - Reference copying
- **`activedirectory/`** - LDAP integration examples

#### Tutorial Models (`tutorial/models/`)

Consolidated model classes organized by domain:
- **`vehicle/`** - Vehicle, Engine, Wheel classes
- **`computer/`** - Computer, MacbookPro, ComputerUtils classes
- **`common/`** - Person, Pair classes
- **`album/`** - Album-related classes
- **`city/`** - City-related classes

### New Features Package (`newfeatures/`)

Java version-specific features (java5 through java21), demonstrating language evolution:
- **`java5/`** through **`java21/`** - Features introduced in each Java version

### Sample Package (`sample/`)

Advanced examples using Maven structure (`src/sample/src/main/java/`):

- **`common/`** - Common classes (Album, Track, Artist, Member)
- **`models/`** - Model classes with `common/` and `entity/` sub-packages
- **`lombok/`** - Lombok examples including `builder/advancedsearch/`
- **`utility/`** - Utility classes (UtilsCommon, UtilsDate, UtilsMath, etc.)
- **`json/`** - JSON processing with Gson
- **`xml/`** - XML processing with JAXB
- **`excel/`** - Excel file handling with POI
- **`pdf/`** - PDF generation and manipulation
- **`pdfsign/`** - PDF digital signing
- **`doc/`** - Document utilities
- **`mail/`** - Email handling
- **`encrypt/`** - Encryption examples
- **`cron/`** - Cron job examples
- **`quartz/`** - Quartz scheduler examples
- **`random/`** - Random generation
- **`reflection/`** - Reflection examples
- **`timezone/`** - Timezone examples
- **`money/`** - Money handling
- **`geo/`** - Geospatial examples
- **`dto/`** - Data transfer objects

## Conventions

- Most classes are standalone examples with their own `main()` method
- Class and package names follow standard English naming conventions
- Model classes are consolidated in `tutorial/models/` and `sample/src/main/java/models/`
- Tutorial package has no external dependencies beyond JetBrains annotations
- Sample package uses Maven for dependency management
- No formal test framework; testing is done by running main methods directly

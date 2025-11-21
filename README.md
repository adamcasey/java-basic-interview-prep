# Java Interview Prep - Core Fundamentals

A simple, interactive command-line application covering essential Java concepts for technical interviews.

## What This Covers

### Core Java Concepts
- ✅ Classes and Objects
- ✅ Constructors
- ✅ Encapsulation (getters/setters)
- ✅ Collections (ArrayList, List, Map)
- ✅ Streams API
- ✅ Lambda expressions
- ✅ String manipulation
- ✅ Exception handling
- ✅ equals() and hashCode()
- ✅ toString()
- ✅ Method overloading
- ✅ For-loops and iteration
- ✅ Conditionals (if/else, switch)
- ✅ Data types (String, int, double, boolean)
- ✅ **Inheritance** (GraduateStudent extends Student)
- ✅ **Interfaces** (Comparable, Serializable, custom Gradeable)
- ✅ **File I/O** (Serialization with ObjectInputStream/ObjectOutputStream)
- ✅ **Data Validation** (IllegalArgumentException, input validation)
- ✅ **Unit Testing** (JUnit 5 with intentionally failing tests for debugging practice)
- ✅ **Static Methods** (Utility class pattern with GradeCalculator)

## How to Run

### Compile
```bash
javac -d bin src/main/java/interview/*.java
```

### Run (Interactive Mode)
```bash
java -cp bin interview.Main
```

### Run with Commands
```bash
# List all students
java -cp bin interview.Main list

# Add a student
java -cp bin interview.Main add David 22 3.6

# Find a student
java -cp bin interview.Main find Alice

# Show honor roll
java -cp bin interview.Main honors

# Show average GPA
java -cp bin interview.Main average

# Sort by GPA
java -cp bin interview.Main sort

# Remove student
java -cp bin interview.Main remove Bob

# Count students
java -cp bin interview.Main count
```

## Available Commands

| Command | Description | Example |
|---------|-------------|---------|
| `list` | Show all students | `java -cp bin interview.Main list` |
| `add <name> <age> <gpa>` | Add a new student | `java -cp bin interview.Main add John 20 3.5` |
| `addgrad <name> <age> <gpa> <thesis> <advisor> <isPhD>` | Add a graduate student | `java -cp bin interview.Main addgrad Jane 25 3.9 "AI Research" "Dr.Smith" true` |
| `find <name>` | Find student by name | `java -cp bin interview.Main find Alice` |
| `honors` | Show honor roll students | `java -cp bin interview.Main honors` |
| `average` | Calculate average GPA | `java -cp bin interview.Main average` |
| `sort` | Sort students by GPA | `java -cp bin interview.Main sort` |
| `remove <name>` | Remove a student | `java -cp bin interview.Main remove Bob` |
| `count` | Show total students | `java -cp bin interview.Main count` |
| `grade <name>` | Show detailed grade info | `java -cp bin interview.Main grade Alice` |
| `calc letter <percentage>` | Convert percentage to letter | `java -cp bin interview.Main calc letter 87` |
| `calc gpa <letter>` | Convert letter to GPA | `java -cp bin interview.Main calc gpa B+` |
| `calc required <current> <credits> <target> <remaining>` | Calculate required GPA | `java -cp bin interview.Main calc required 3.0 60 3.5 30` |
| `save [filename]` | Save students to file | `java -cp bin interview.Main save` |
| `load [filename]` | Load students from file | `java -cp bin interview.Main load` |

## Project Structure

```
java-interview-prep/
├── src/
│   ├── main/java/interview/
│   │   ├── Student.java           # Student class with Comparable & Gradeable
│   │   ├── GraduateStudent.java   # Inheritance example (extends Student)
│   │   ├── StudentManager.java    # Collections, streams, file I/O
│   │   ├── Gradeable.java         # Custom interface with default methods
│   │   ├── GradeCalculator.java   # Static utility methods
│   │   └── Main.java              # CLI with enhanced commands
│   └── test/java/interview/
│       ├── StudentTest.java           # Unit tests for Student
│       ├── GraduateStudentTest.java   # Tests for inheritance
│       ├── StudentManagerTest.java    # Tests for manager & file I/O
│       └── GradeCalculatorTest.java   # Tests for calculator
├── pom.xml                    # Maven configuration with JUnit 5
├── README.md                  # This file
└── TESTING_GUIDE.md          # Guide for debugging practice
```

## Sample Session

```bash
$ java -cp bin interview.Main

=== Student Management System ===
Type 'help' for commands, 'exit' to quit

> list

=== All Students ===
Student{name='Alice', age=20, gpa=3.8}
Student{name='Bob', age=21, gpa=3.2}
Student{name='Charlie', age=19, gpa=3.9}

> add Emma 22 4.0
Added: Emma

> honors

=== Honor Roll Students (GPA >= 3.5) ===
Student{name='Alice', age=20, gpa=3.8}
Student{name='Charlie', age=19, gpa=3.9}
Student{name='Emma', age=22, gpa=4.0}

> average
Average GPA: 3.73

> exit
Goodbye!
```

## Interview Topics You Can Discuss

1. **OOP Principles** - Encapsulation in Student class
2. **Collections Framework** - ArrayList vs LinkedList, when to use which
3. **Streams vs Loops** - Performance, readability tradeoffs
4. **equals() and hashCode()** - Contract, why override both
5. **Immutability** - Why return defensive copies
6. **Exception Handling** - Try-catch, NumberFormatException
7. **String Operations** - split(), equalsIgnoreCase()
8. **Lambda Expressions** - Method references, functional interfaces
9. **Sorting** - Comparator, natural ordering
10. **Data Validation** - Age > 0, GPA 0.0-4.0

## Unit Testing & Interview Practice

This project includes **intentionally failing unit tests** to practice debugging for technical interviews!

### Running Tests

```bash
# Using Maven
mvn test

# Run specific test class
mvn test -Dtest=StudentTest

# Run specific test method
mvn test -Dtest=StudentTest#testHonorRollBoundary
```

### Debugging Practice

The test suite includes tests that fail on purpose. Your job is to:
1. Identify why each test fails
2. Decide if it's a bug in the code or the test
3. Fix the issue and explain your reasoning

See [TESTING_GUIDE.md](TESTING_GUIDE.md) for detailed debugging instructions and interview practice tips.

### Example Failing Tests

- **Boundary condition bugs** - Testing edge cases like exactly 3.5 GPA
- **Format bugs** - toString() formatting with decimal places
- **Comparison bugs** - Secondary sorting when values are equal
- **Defensive copy bugs** - Ensuring encapsulation is maintained
- **Inheritance bugs** - Verifying overridden methods work correctly

## Interview Topics You Can Discuss

1. **OOP Principles** - Encapsulation, inheritance, polymorphism
2. **Collections Framework** - ArrayList vs LinkedList, when to use which
3. **Streams vs Loops** - Performance, readability tradeoffs
4. **equals() and hashCode()** - Contract, why override both
5. **Comparable Interface** - Natural ordering, compareTo() contract
6. **Immutability** - Why return defensive copies
7. **Exception Handling** - Try-catch, custom exceptions, validation
8. **String Operations** - split(), equalsIgnoreCase(), trim()
9. **Lambda Expressions** - Method references, functional interfaces
10. **Sorting** - Comparator, natural ordering, stability
11. **File I/O** - Serialization, ObjectInputStream/ObjectOutputStream
12. **Inheritance** - super(), method overriding, IS-A relationship
13. **Interfaces** - Default methods, multiple interface implementation
14. **Unit Testing** - JUnit annotations, assertions, test-driven development
15. **Debugging** - Reading stack traces, using debugger, systematic problem-solving

## Additional Extensions

Ideas for further practice:
- Add custom exception classes (InvalidGPAException, etc.)
- Implement CSV import/export in addition to serialization
- Add a database layer with JDBC
- Create a REST API with Spring Boot
- Add logging with SLF4J
- Implement builder pattern for Student creation
- Add validation annotations (JSR 303)
- Create a GUI with JavaFX or Swing

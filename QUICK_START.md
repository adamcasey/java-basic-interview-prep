# Quick Start Guide

Get up and running with the Java Interview Prep project in 5 minutes!

## Setup

### Compile the Project

```bash
javac -d bin src/main/java/interview/*.java
```

### Run Interactive Mode

```bash
java -cp bin interview.Main
```

You'll see:
```
Loaded existing student data.
No command provided. Starting interactive mode...

=== Student Management System ===
Type 'help' for commands, 'exit' to quit

>
```

## Try These Commands

### 1. List All Students

```bash
> list
```

### 2. Check a Student's Grade Details

```bash
> grade Alice
```

Output:
```
=== Grade Information for Alice ===
GPA: 3.8
Letter Grade: A
Passing: Yes
Honor Roll: Yes
Academic Standing: Honors
```

### 3. Add a New Student

```bash
> add Emma 22 3.6
```

### 4. Add a Graduate Student

```bash
> addgrad John 27 3.9 "Quantum Computing" "Dr.Anderson" true
```

### 5. Use the Grade Calculator

```bash
> calc letter 87
87.0% = B+

> calc gpa A-
A- = 3.7 GPA

> calc required 3.0 60 3.5 30
To reach 3.50 GPA, you need 4.00 GPA in remaining 30 credits
```

### 6. Sort Students by GPA

```bash
> sort
```

### 7. Save Your Data

```bash
> save
Saved student data.
```

### 8. Exit

```bash
> exit
Goodbye!
```

## Command-Line Mode

You can also run single commands without interactive mode:

```bash
# List students
java -cp bin interview.Main list

# Add a student
java -cp bin interview.Main add Sarah 21 3.7

# Show grade info
java -cp bin interview.Main grade Sarah

# Calculator
java -cp bin interview.Main calc letter 92

# Save data
java -cp bin interview.Main save
```

## Running Tests

### Using Maven

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=StudentTest

# Run specific test
mvn test -Dtest=StudentTest#testHonorRollBoundary
```

### What to Expect

Some tests will **intentionally fail** - this is for debugging practice! See [TESTING_GUIDE.md](TESTING_GUIDE.md) for details.

## Key Files to Review

1. **[Student.java](src/main/java/interview/Student.java)** - Basic student class with validation, Comparable, Gradeable
2. **[GraduateStudent.java](src/main/java/interview/GraduateStudent.java)** - Inheritance example
3. **[StudentManager.java](src/main/java/interview/StudentManager.java)** - Collections, streams, file I/O
4. **[Gradeable.java](src/main/java/interview/Gradeable.java)** - Custom interface with default methods
5. **[GradeCalculator.java](src/main/java/interview/GradeCalculator.java)** - Static utility methods
6. **[Main.java](src/main/java/interview/Main.java)** - CLI application
7. **[StudentTest.java](src/test/java/interview/StudentTest.java)** - Unit tests (some intentionally failing)

## Interview Topics Covered

This project demonstrates:

- ✅ Classes & Objects
- ✅ Inheritance & Polymorphism
- ✅ Interfaces (Comparable, Serializable, custom)
- ✅ Collections (ArrayList, List, Map)
- ✅ Streams & Lambda expressions
- ✅ File I/O (Serialization)
- ✅ Exception handling & validation
- ✅ equals(), hashCode(), compareTo()
- ✅ Unit testing with JUnit 5
- ✅ Static utility methods
- ✅ Default interface methods
- ✅ Defensive copying

## Common Interview Questions

### "Walk me through your Student class"

"The Student class demonstrates encapsulation with private fields and public getters/setters. It implements Serializable for file I/O, Comparable for natural ordering by GPA, and a custom Gradeable interface for grade-related operations. The constructor includes validation that throws IllegalArgumentException for invalid inputs. I override equals() and hashCode() following the contract, and implement compareTo() to sort by GPA descending, then name ascending."

### "Explain the inheritance hierarchy"

"GraduateStudent extends Student, adding thesis title, advisor, and degree type fields. It overrides isHonorRoll() to use a higher threshold (3.7 vs 3.5), demonstrating polymorphism. The constructor chains to the parent using super() and includes additional validation. Both classes implement the Gradeable interface, inheriting default methods like getLetterGrade() and getAcademicStanding()."

### "How did you implement file I/O?"

"I used Java serialization with ObjectOutputStream and ObjectInputStream. Both Student and GraduateStudent implement Serializable with serialVersionUID for version control. The StudentManager has save/load methods using try-with-resources for automatic resource management. The main method attempts to load existing data on startup, falling back to sample data if none exists."

### "Explain your testing strategy"

"I wrote comprehensive JUnit 5 tests covering normal cases, edge cases, and error cases. Intentionally, I included failing tests to practice debugging - things like boundary conditions, format issues, and defensive copy validation. This mimics real-world scenarios where you need to identify whether bugs are in the code or tests."

## Next Steps

1. **Review the code** - Read through each class to understand the implementations
2. **Run the tests** - See which ones fail and practice debugging
3. **Extend the project** - Add your own features (see README for ideas)
4. **Practice explaining** - Talk through the code as if in an interview

## Troubleshooting

### "Command not found" or "ClassNotFoundException"

Make sure you compiled first:
```bash
javac -d bin src/main/java/interview/*.java
```

### Tests won't run

Make sure you have Maven installed:
```bash
mvn --version
```

If not, install Maven or use an IDE that handles JUnit dependencies.

### File not saving/loading

Check that you have write permissions in the current directory. The default file is `students.dat` in the working directory.

## Resources

- [README.md](README.md) - Full documentation
- [FEATURES.md](FEATURES.md) - Detailed feature descriptions
- [TESTING_GUIDE.md](TESTING_GUIDE.md) - Debugging practice guide

Good luck with your interview prep!

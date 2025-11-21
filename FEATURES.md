# New Features Summary

This document summarizes all the new features added to the Java Interview Prep project.

## 1. File I/O (Serialization)

**Files**: [StudentManager.java:79-104](src/main/java/interview/StudentManager.java#L79-L104)

```java
// Save students to file
manager.save();  // saves to students.dat
manager.saveToFile("custom.dat");

// Load students from file
manager.load();  // loads from students.dat
manager.loadFromFile("custom.dat");
```

**Key concepts**:
- ObjectOutputStream / ObjectInputStream
- Serializable interface
- Try-with-resources
- Exception handling (IOException, ClassNotFoundException)
- serialVersionUID

## 2. Enhanced Data Validation

**Files**: [Student.java:19-68](src/main/java/interview/Student.java#L19-L68)

```java
// Constructor validation
public Student(String name, int age, double gpa) {
    if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (age <= 0 || age > 150) {
        throw new IllegalArgumentException("Age must be between 1 and 150");
    }
    if (gpa < 0.0 || gpa > 4.0) {
        throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
    }
    // ...
}
```

**Key concepts**:
- IllegalArgumentException for invalid input
- Null checks and empty string validation
- Range validation
- String trimming
- Fail-fast principle

## 3. Inheritance (GraduateStudent)

**Files**: [GraduateStudent.java](src/main/java/interview/GraduateStudent.java)

```java
// GraduateStudent extends Student
GraduateStudent grad = new GraduateStudent(
    "Diana", 26, 3.85,
    "Machine Learning in Healthcare",
    "Dr. Johnson",
    true  // isPhD
);

// Overridden methods
grad.isHonorRoll();  // Different threshold: 3.7 vs 3.5
grad.getDegreeType();  // Returns "PhD" or "Master's"
```

**Key concepts**:
- Inheritance (extends)
- Constructor chaining with super()
- Method overriding (@Override)
- Additional fields and behaviors
- Polymorphism

## 4. Comparable Interface

**Files**: [Student.java:99-106](src/main/java/interview/Student.java#L99-L106)

```java
// Natural ordering by GPA (descending), then name (ascending)
@Override
public int compareTo(Student other) {
    int gpaCompare = Double.compare(other.gpa, this.gpa);
    if (gpaCompare != 0) {
        return gpaCompare;
    }
    return this.name.compareToIgnoreCase(other.name);
}

// Can now use Collections.sort()
List<Student> students = manager.getAllStudents();
Collections.sort(students);
```

**Key concepts**:
- Comparable<T> interface
- compareTo() method contract
- Primary and secondary sorting
- Natural ordering vs Comparator

## 5. Custom Gradeable Interface

**Files**: [Gradeable.java](src/main/java/interview/Gradeable.java)

```java
public interface Gradeable {
    // Abstract methods
    double getGpa();
    boolean isHonorRoll();

    // Default methods (Java 8+)
    default String getLetterGrade() { /* ... */ }
    default boolean isPassing() { /* ... */ }
    default String getAcademicStanding() { /* ... */ }
}

// Usage
Student student = new Student("Alice", 20, 3.8);
System.out.println(student.getLetterGrade());  // "A"
System.out.println(student.getAcademicStanding());  // "Honors"
```

**Key concepts**:
- Interface design
- Default methods
- Multiple interface implementation
- Contract-based programming
- Code reuse through interfaces

## 6. GradeCalculator Utility Class

**Files**: [GradeCalculator.java](src/main/java/interview/GradeCalculator.java)

```java
// Static utility methods
double percentage = 87.0;
String letter = GradeCalculator.percentageToLetterGrade(percentage);  // "B+"

double gpa = GradeCalculator.letterGradeToGPA("A");  // 4.0

// Calculate what GPA is needed in remaining courses
double required = GradeCalculator.calculateRequiredGPA(
    3.0,   // current GPA
    60,    // current credits
    3.5,   // target GPA
    30     // remaining credits
);  // Returns 4.0

// Calculate weighted GPA from course grades
Map<String, Double> grades = Map.of("Math", 4.0, "English", 3.5);
Map<String, Integer> credits = Map.of("Math", 4, "English", 3);
double weightedGPA = GradeCalculator.calculateWeightedGPA(grades, credits);

// Statistics
List<Double> gpas = Arrays.asList(3.0, 3.5, 3.8, 4.0);
Map<String, Double> stats = GradeCalculator.calculateGradeStatistics(gpas);
// Returns: {mean: 3.575, median: 3.65, min: 3.0, max: 4.0}
```

**Key concepts**:
- Static utility class pattern
- Static methods
- Map usage
- Statistical calculations
- Method documentation

## 7. Unit Tests with JUnit 5

**Files**: `src/test/java/interview/*.java`

```java
@Test
public void testConstructorValid() {
    assertEquals("Alice", student.getName());
    assertEquals(20, student.getAge());
    assertEquals(3.8, student.getGpa(), 0.001);
}

@Test
public void testConstructorInvalidGPA() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Student("Bob", 20, 5.0);
    });
}
```

**Key concepts**:
- JUnit 5 annotations (@Test, @BeforeEach, @AfterEach)
- Assertions (assertEquals, assertTrue, assertThrows)
- Test lifecycle
- Test organization
- Lambda expressions in tests
- Intentionally failing tests for debugging practice

## 8. Enhanced CLI Commands

**Files**: [Main.java](src/main/java/interview/Main.java)

### New Commands

```bash
# Add graduate student
java -cp bin interview.Main addgrad Diana 26 3.85 "ML Research" "Dr.Smith" true

# Show detailed grade information
java -cp bin interview.Main grade Alice
# Output:
# === Grade Information for Alice ===
# GPA: 3.8
# Letter Grade: A
# Passing: Yes
# Honor Roll: Yes
# Academic Standing: Honors

# Grade calculator
java -cp bin interview.Main calc letter 87
# Output: 87.0% = B+

java -cp bin interview.Main calc gpa "A-"
# Output: A- = 3.7 GPA

java -cp bin interview.Main calc required 3.0 60 3.5 30
# Output: To reach 3.50 GPA, you need 4.00 GPA in remaining 30 credits

# Save/Load
java -cp bin interview.Main save
java -cp bin interview.Main load
```

## Interview Practice Features

### Intentionally Failing Tests

13 tests are designed to fail so you can practice:
- Reading test failures
- Understanding expected vs actual values
- Debugging with breakpoints
- Identifying boundary condition bugs
- Verifying defensive copies
- Testing inheritance behavior

See [TESTING_GUIDE.md](TESTING_GUIDE.md) for details on each failing test.

### What You Can Demonstrate

With these features, you can discuss:

1. **OOP Design** - Inheritance, polymorphism, encapsulation
2. **Interface Design** - When to use interfaces vs abstract classes
3. **Exception Handling** - Checked vs unchecked, fail-fast validation
4. **File I/O** - Serialization pros/cons, alternative approaches
5. **Testing** - Unit test structure, test-driven development
6. **Collections** - Sorting, compareTo vs Comparator
7. **Defensive Programming** - Input validation, defensive copies
8. **Code Organization** - Separation of concerns, utility classes
9. **Error Handling** - Try-catch-finally, try-with-resources
10. **Design Patterns** - Utility class pattern, interface segregation

## Running Everything

```bash
# Compile all code
javac -d bin src/main/java/interview/*.java

# Run interactive mode
java -cp bin interview.Main

# Run specific command
java -cp bin interview.Main list

# Run tests (requires Maven)
mvn test

# Run specific test
mvn test -Dtest=StudentTest

# Save data before exiting
java -cp bin interview.Main save
```

## Quick Feature Checklist

- [x] File I/O with serialization
- [x] Enhanced input validation with exceptions
- [x] Inheritance with GraduateStudent
- [x] Comparable interface implementation
- [x] Custom Gradeable interface with default methods
- [x] GradeCalculator utility class
- [x] Comprehensive JUnit 5 test suite
- [x] Tests with intentional bugs for debugging practice
- [x] Enhanced CLI with new commands
- [x] Auto-save/load on startup
- [x] Grade calculation utilities
- [x] Statistical analysis methods

All features are production-ready and interview-ready!

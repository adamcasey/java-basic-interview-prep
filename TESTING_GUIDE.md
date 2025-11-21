# Testing Guide for Interview Practice

This guide explains how to run the unit tests, including the **10 intentionally failing tests** designed for debugging practice during technical interviews.

## Running the Tests

### Using Maven

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=StudentTest
mvn test -Dtest=GradeCalculatorTest
mvn test -Dtest=StudentManagerTest
mvn test -Dtest=GraduateStudentTest

# Run a specific test method
mvn test -Dtest=StudentTest#testPerfectGPA
```

## Current Test Results

**Total: 90 tests, 8 failures, 2 errors**

These 10 failing tests are designed to give you real debugging practice!

## Intentionally Failing Tests

### StudentTest (3 failures, 1 error)

#### 1. testToStringFormat (FAILURE)
**File**: [StudentTest.java:122](src/test/java/interview/StudentTest.java#L122)
```
Expected: Student{name='Alice', age=20, gpa=3.80}
Actual:   Student{name='Alice', age=20, gpa=3.8}
```
**Bug**: toString() doesn't format GPA with trailing zeros
**Debug task**: Should you fix toString() to use String.format("%.2f", gpa) or fix the test expectation?

#### 2. testPerfectGPA (FAILURE)
**File**: [StudentTest.java:162](src/test/java/interview/StudentTest.java#L162)
```
Expected: A+
Actual:   A
```
**Bug**: Test expects "A+" for 4.0 GPA, but Gradeable interface returns "A"
**Debug task**: Is this a design decision? Should perfect scores get "A+"? Update interface or test?

#### 3. testCompareToFloatingPointPrecision (FAILURE)
**File**: [StudentTest.java:204](src/test/java/interview/StudentTest.java#L204)
```
Expected: 0 (equal)
Actual:   -1 (not equal)
```
**Bug**: Comparing 3.70000000001 vs 3.7 treats them as different
**Debug task**: Should compareTo() handle floating-point precision issues? How?

#### 4. testGPAUpperBoundary (ERROR)
**File**: [StudentTest.java:188](src/test/java/interview/StudentTest.java#L188)
```
Exception: IllegalArgumentException - GPA must be between 0.0 and 4.0
```
**Bug**: Test tries to set GPA to 4.01, which violates validation
**Debug task**: Is this test wrong, or should we allow GPAs slightly over 4.0?

### GradeCalculatorTest (2 failures, 1 error)

#### 5. testCalculateRequiredGPAAlreadyMet (FAILURE)
**File**: [GradeCalculatorTest.java:178](src/test/java/interview/GradeCalculatorTest.java#L178)
```
Expected: required GPA <= 0 (already met target)
Actual:   required GPA = 2.83 (positive value)
```
**Bug**: When current GPA (3.8) exceeds target (3.5), calculation still returns positive required GPA
**Debug task**: Fix calculateRequiredGPA() to handle this edge case

#### 6. testCalculateSemesterGPAWithInvalidGrades (FAILURE)
**File**: [GradeCalculatorTest.java:204](src/test/java/interview/GradeCalculatorTest.java#L204)
```
Expected: IllegalArgumentException
Actual:   No exception thrown (accepts negative grades)
```
**Bug**: calculateSemesterGPA() doesn't validate input
**Debug task**: Add validation to reject negative GPAs

#### 7. testLetterGradeToGPAWithSpaces (ERROR)
**File**: [GradeCalculatorTest.java:196](src/test/java/interview/GradeCalculatorTest.java#L196)
```
Exception: IllegalArgumentException - Invalid letter grade:  A-
```
**Bug**: Method doesn't trim whitespace from input
**Debug task**: Add trim() to letterGradeToGPA() method

### StudentManagerTest (2 failures)

#### 8. testAddNullStudent (FAILURE)
**File**: [StudentManagerTest.java:263](src/test/java/interview/StudentManagerTest.java#L263)
```
Expected: NullPointerException
Actual:   No exception thrown
```
**Bug**: addStudent() doesn't validate for null
**Debug task**: Add null check or decide if null should be allowed

#### 9. testRemoveNullName (FAILURE)
**File**: [StudentManagerTest.java:272](src/test/java/interview/StudentManagerTest.java#L272)
```
Expected: NullPointerException
Actual:   No exception thrown (likely returns false)
```
**Bug**: removeStudent() doesn't validate for null name
**Debug task**: Add null check or decide if null should be handled gracefully

### GraduateStudentTest (0 failures - all passing!)

Great news! All the inheritance and polymorphism tests are passing correctly.

## Debugging Practice Strategy

For each failing test, follow this process:

### 1. Read the Test
```bash
mvn test -Dtest=StudentTest#testPerfectGPA
```
- What is the test expecting?
- What is the actual behavior?
- What's the error message?

### 2. Understand the Intent
- Is this testing correct behavior?
- Is the test wrong or is the code wrong?
- What's the business logic here?

### 3. Locate the Bug
- Find the source method being tested
- Read the implementation
- Identify the issue

### 4. Decide on a Fix
- Should you fix the code?
- Should you fix the test?
- Is this a design decision?

### 5. Implement and Verify
- Make your changes
- Run the specific test again
- Run all tests to ensure no regressions

## Example: Debugging testToStringFormat

### Step 1: Run the test
```bash
mvn test -Dtest=StudentTest#testToStringFormat
```

### Step 2: Read the failure
```
Expected: Student{name='Alice', age=20, gpa=3.80}
Actual:   Student{name='Alice', age=20, gpa=3.8}
```

### Step 3: Find the code
Look at [Student.java:73](src/main/java/interview/Student.java#L73):
```java
@Override
public String toString() {
    return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + "}";
}
```

### Step 4: Identify the issue
The GPA (double) is concatenated directly, which doesn't guarantee 2 decimal places.

### Step 5: Fix options

**Option A: Fix the code**
```java
return String.format("Student{name='%s', age=%d, gpa=%.2f}", name, age, gpa);
```

**Option B: Fix the test**
```java
String expected = "Student{name='Alice', age=20, gpa=3.8}";  // Accept variable decimals
```

### Step 6: Make a decision
This is a design choice! Discuss in an interview:
- Consistent formatting (always 2 decimals) is better for UI/reports
- But it's extra work for a simple toString()
- What's the use case?

## Interview Discussion Topics

These failing tests cover important concepts:

### 1. Validation & Error Handling
- **testAddNullStudent**: When should you throw exceptions vs handle gracefully?
- **testCalculateSemesterGPAWithInvalidGrades**: Input validation importance
- **testGPAUpperBoundary**: Boundary condition handling

### 2. Design Decisions
- **testPerfectGPA**: Should 4.0 be "A" or "A+"? Business logic question
- **testToStringFormat**: Formatting consistency vs simplicity

### 3. Floating-Point Precision
- **testCompareToFloatingPointPrecision**: Classic floating-point comparison issue
- How to handle epsilon comparisons?

### 4. Edge Cases
- **testCalculateRequiredGPAAlreadyMet**: What happens when already above target?
- **testRemoveNullName**: How to handle invalid input?

### 5. Code Robustness
- **testLetterGradeToGPAWithSpaces**: Should inputs be sanitized?
- Defensive programming principles

## Quick Fix Summary

Here's a cheat sheet for fixing each test:

| Test | File to Fix | Quick Fix |
|------|------------|-----------|
| testToStringFormat | Student.java:73 | Use String.format("%.2f") |
| testPerfectGPA | Gradeable.java:24 or test | Add "A+" case or change test |
| testCompareToFloatingPointPrecision | Student.java:100 | Use epsilon comparison |
| testGPAUpperBoundary | Test file | Remove invalid assertion |
| testCalculateRequiredGPAAlreadyMet | GradeCalculator.java:91 | Check if already met |
| testCalculateSemesterGPAWithInvalidGrades | GradeCalculator.java:98 | Add validation |
| testLetterGradeToGPAWithSpaces | GradeCalculator.java:58 | Add .trim() |
| testAddNullStudent | StudentManager.java:17 | Add null check |
| testRemoveNullName | StudentManager.java:67 | Add null check |

## Running in Your IDE

### VSCode
1. Open test file
2. Look for "Run Test" link above each @Test method
3. Click to run individual test
4. Or use Test Explorer panel

### IntelliJ IDEA
1. Right-click test method
2. Select "Run 'testName()'"
3. Or click green arrow in gutter

## Next Steps

1. **Pick a failing test** - Start with testToStringFormat (easiest)
2. **Debug it** - Follow the strategy above
3. **Fix it** - Make your decision and implement
4. **Verify** - Run the test again
5. **Repeat** - Move to the next failing test

## Interview Simulation

Practice explaining your debugging process out loud:

> "I see testPerfectGPA is failing. The test expects 'A+' but gets 'A' for a 4.0 GPA. Let me look at the getLetterGrade() method in the Gradeable interface. I see it returns 'A' for anything >= 3.7. The question is: should we add a special case for perfect 4.0 scores? This is a design decision. If we want to distinguish perfect scores, we could add `if (gpa == 4.0) return 'A+';` but that changes the grading scale. Alternatively, we could update the test to accept 'A' as the expected value. I'd ask the product owner which grading system we're implementing."

Good luck with your interview prep! 🎯

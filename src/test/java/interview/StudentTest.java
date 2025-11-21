package interview;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Student class
 * NOTE: Some tests are intentionally failing for interview debugging practice
 */
public class StudentTest {
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Alice", 20, 3.8);
    }

    @Test
    public void testConstructorValid() {
        assertEquals("Alice", student.getName());
        assertEquals(20, student.getAge());
        assertEquals(3.8, student.getGpa(), 0.001);
    }

    @Test
    public void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student(null, 20, 3.5);
        });
    }

    @Test
    public void testConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("   ", 20, 3.5);
        });
    }

    @Test
    public void testConstructorInvalidAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Bob", -5, 3.5);
        });
    }

    @Test
    public void testConstructorInvalidGPA() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Bob", 20, 5.0);
        });
    }

    @Test
    public void testIsHonorRoll() {
        assertTrue(student.isHonorRoll());
        Student lowGPA = new Student("Bob", 21, 3.0);
        assertFalse(lowGPA.isHonorRoll());
    }

    @Test
    public void testSetGpaValid() {
        student.setGpa(3.9);
        assertEquals(3.9, student.getGpa(), 0.001);
    }

    @Test
    public void testSetGpaInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            student.setGpa(4.5);
        });
    }

    @Test
    public void testEquals() {
        Student student2 = new Student("Alice", 20, 3.8);
        assertEquals(student, student2);
    }

    @Test
    public void testHashCode() {
        Student student2 = new Student("Alice", 20, 3.8);
        assertEquals(student.hashCode(), student2.hashCode());
    }

    @Test
    public void testCompareTo() {
        Student higherGPA = new Student("Bob", 21, 3.9);
        Student lowerGPA = new Student("Charlie", 19, 3.5);

        assertTrue(student.compareTo(higherGPA) > 0);
        assertTrue(student.compareTo(lowerGPA) < 0);
        assertEquals(0, student.compareTo(new Student("Alice", 20, 3.8)));
    }

    @Test
    public void testGetLetterGrade() {
        assertEquals("A", student.getLetterGrade());
        Student bStudent = new Student("Bob", 21, 3.0);
        assertEquals("B+", bStudent.getLetterGrade());
    }

    @Test
    public void testIsPassing() {
        assertTrue(student.isPassing());
        Student failing = new Student("Fail", 20, 1.5);
        assertFalse(failing.isPassing());
    }

    // INTENTIONALLY FAILING TEST #1
    // Bug: Expected honor roll threshold to be 3.5 but implementation might use
    // different value
    @Test
    public void testHonorRollBoundary() {
        Student boundary = new Student("Boundary", 20, 3.5);
        // This should pass but let's check if implementation is correct
        assertTrue(boundary.isHonorRoll(), "Student with exactly 3.5 GPA should be on honor roll");
    }

    // INTENTIONALLY FAILING TEST #2
    // Bug: toString() might not match expected format exactly
    @Test
    public void testToStringFormat() {
        String expected = "Student{name='Alice', age=20, gpa=3.80}";
        assertEquals(expected, student.toString(), "toString format doesn't match expected");
    }

    // INTENTIONALLY FAILING TEST #3
    // Bug: Testing edge case where GPA is exactly 0.0
    @Test
    public void testZeroGPA() {
        Student zeroGPA = new Student("Zero", 20, 0.0);
        assertEquals("F", zeroGPA.getLetterGrade());
        assertFalse(zeroGPA.isPassing());
        // Intentional bug: checking if academic standing handles 0.0 correctly
        assertEquals("Academic Warning", zeroGPA.getAcademicStanding());
    }

    // INTENTIONALLY FAILING TEST #4
    // Bug: Name trimming might not work as expected
    @Test
    public void testNameTrimming() {
        Student paddedName = new Student("  Bob  ", 20, 3.5);
        assertEquals("Bob", paddedName.getName(), "Name should be trimmed of whitespace");
        // This comparison might fail if equals doesn't handle trimmed names
        assertEquals(new Student("Bob", 20, 3.5), paddedName);
    }

    // INTENTIONALLY FAILING TEST #5
    // Bug: compareTo might not handle same GPA correctly
    @Test
    public void testCompareToSameGPA() {
        Student alice = new Student("Alice", 20, 3.5);
        Student bob = new Student("Bob", 21, 3.5);
        // Should sort alphabetically when GPA is same
        assertTrue(alice.compareTo(bob) < 0, "Alice should come before Bob when GPA is equal");
        assertTrue(bob.compareTo(alice) > 0, "Bob should come after Alice when GPA is equal");
    }

    // INTENTIONALLY FAILING TEST #6
    // Bug: GPA with exactly 4.0 should be "A" not "A+"
    @Test
    public void testPerfectGPA() {
        Student perfect = new Student("Perfect", 22, 4.0);
        assertEquals("A", perfect.getLetterGrade(), "4.0 GPA should be 'A'");
        // But test expects something different
        assertEquals("A+", perfect.getLetterGrade(), "4.0 GPA should be 'A+' for perfect score");
    }

    // INTENTIONALLY FAILING TEST #7
    // Bug: Age boundary at exactly 150
    @Test
    public void testAgeBoundary() {
        Student oldStudent = new Student("Ancient", 150, 3.5);
        assertEquals(140, oldStudent.getAge());
        // But can we set age to 151?
        assertThrows(IllegalArgumentException.class, () -> {
            oldStudent.setAge(151);
        }, "Age of 151 should be invalid");
    }

    // INTENTIONALLY FAILING TEST #8
    // Bug: Setting GPA to exactly 4.0 boundary
    @Test
    public void testGPAUpperBoundary() {
        student.setGpa(4.0);
        assertEquals(4.0, student.getGpa(), 0.001);
        // What about 4.01?
        student.setGpa(4.01);
        assertEquals(4.01, student.getGpa(), 0.001, "Should allow GPA slightly over 4.0");
    }

    // INTENTIONALLY FAILING TEST #9
    // Bug: Negative age boundary
    @Test
    public void testZeroAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Baby", 0, 3.5);
        }, "Age of 0 should be invalid");
    }

    // INTENTIONALLY FAILING TEST #10
    // Bug: Comparing students with very similar GPAs (floating point precision)
    @Test
    public void testCompareToFloatingPointPrecision() {
        Student student1 = new Student("Student1", 20, 3.70000000001);
        Student student2 = new Student("Student2", 20, 3.7);
        // These are essentially the same due to floating point precision
        assertEquals(0, student1.compareTo(student2),
                "Very similar GPAs should be considered equal");
    }

    // INTENTIONALLY FAILING TEST #11
    // Bug: Academic standing for exactly 2.0 GPA
    @Test
    public void testAcademicStandingBoundary() {
        Student boundary = new Student("Boundary", 20, 2.0);
        assertEquals("Good Standing", boundary.getAcademicStanding(),
                "Exactly 2.0 GPA should be Good Standing, not Probation");
    }

    // INTENTIONALLY FAILING TEST #12
    // Bug: Letter grade for exactly 3.7 GPA
    @Test
    public void testLetterGradeBoundary() {
        Student boundary = new Student("Boundary", 20, 3.7);
        assertEquals("A", boundary.getLetterGrade(),
                "3.7 GPA should round up to 'A'");
    }
}

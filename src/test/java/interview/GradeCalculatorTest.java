package interview;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Unit tests for GradeCalculator
 * NOTE: Some tests are intentionally failing for interview debugging practice
 */
public class GradeCalculatorTest {

    @Test
    public void testCalculateWeightedGPA() {
        Map<String, Double> grades = new HashMap<>();
        grades.put("Math", 4.0);
        grades.put("English", 3.5);
        grades.put("Science", 3.8);

        Map<String, Integer> credits = new HashMap<>();
        credits.put("Math", 4);
        credits.put("English", 3);
        credits.put("Science", 4);

        double gpa = GradeCalculator.calculateWeightedGPA(grades, credits);
        assertEquals(3.8, gpa, 0.01);
    }

    @Test
    public void testPercentageToLetterGrade() {
        assertEquals("A", GradeCalculator.percentageToLetterGrade(95));
        assertEquals("B+", GradeCalculator.percentageToLetterGrade(87));
        assertEquals("C", GradeCalculator.percentageToLetterGrade(75));
        assertEquals("F", GradeCalculator.percentageToLetterGrade(50));
    }

    @Test
    public void testLetterGradeToGPA() {
        assertEquals(4.0, GradeCalculator.letterGradeToGPA("A"), 0.01);
        assertEquals(3.0, GradeCalculator.letterGradeToGPA("B"), 0.01);
        assertEquals(2.0, GradeCalculator.letterGradeToGPA("C"), 0.01);
        assertEquals(0.0, GradeCalculator.letterGradeToGPA("F"), 0.01);
    }

    @Test
    public void testCalculateRequiredGPA() {
        double required = GradeCalculator.calculateRequiredGPA(3.0, 60, 3.5, 30);
        assertEquals(4.0, required, 0.01);
    }

    @Test
    public void testCanMakeDeansList() {
        assertTrue(GradeCalculator.canMakeDeansList(3.5, 60, 30, 3.6));
        assertFalse(GradeCalculator.canMakeDeansList(2.5, 60, 30, 3.8));
    }

    @Test
    public void testCalculateSemesterGPA() {
        List<Double> grades = Arrays.asList(3.5, 3.8, 4.0, 3.2);
        double gpa = GradeCalculator.calculateSemesterGPA(grades);
        assertEquals(3.625, gpa, 0.01);
    }

    // INTENTIONALLY FAILING TEST #1
    // Bug: Division by zero or null handling issue
    @Test
    public void testCalculateWeightedGPAEmpty() {
        Map<String, Double> grades = new HashMap<>();
        Map<String, Integer> credits = new HashMap<>();
        double gpa = GradeCalculator.calculateWeightedGPA(grades, credits);
        // Might fail if not handling empty maps correctly
        assertEquals(0.0, gpa, 0.01);
    }

    // INTENTIONALLY FAILING TEST #2
    // Bug: Boundary condition at exactly 90%
    @Test
    public void testPercentageToLetterGradeBoundary() {
        // At exactly 90%, should be A- according to our scale
        assertEquals("A-", GradeCalculator.percentageToLetterGrade(90.0));
        // But what about 89.99?
        assertEquals("B+", GradeCalculator.percentageToLetterGrade(89.99));
    }

    // INTENTIONALLY FAILING TEST #3
    // Bug: Case sensitivity issue
    @Test
    public void testLetterGradeToGPACaseSensitive() {
        // Should handle lowercase
        assertEquals(4.0, GradeCalculator.letterGradeToGPA("a"), 0.01);
        assertEquals(3.0, GradeCalculator.letterGradeToGPA("b"), 0.01);
    }

    // INTENTIONALLY FAILING TEST #4
    // Bug: Invalid input should throw exception
    @Test
    public void testPercentageToLetterGradeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            GradeCalculator.percentageToLetterGrade(150);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GradeCalculator.percentageToLetterGrade(-10);
        });
    }

    // INTENTIONALLY FAILING TEST #5
    // Bug: Required GPA calculation when impossible
    @Test
    public void testCalculateRequiredGPAImpossible() {
        // Trying to go from 2.0 to 4.0 with only 10 credits remaining out of 100 total
        // Would require GPA > 4.0 which is impossible
        double required = GradeCalculator.calculateRequiredGPA(2.0, 90, 4.0, 10);
        // Should we throw exception or return value > 4.0?
        assertTrue(required > 4.0, "Should calculate that impossible GPA is needed");
    }

    // INTENTIONALLY FAILING TEST #6
    // Bug: Null list handling
    @Test
    public void testCalculateSemesterGPANull() {
        double gpa = GradeCalculator.calculateSemesterGPA(null);
        assertEquals(0.0, gpa, 0.01, "Null list should return 0.0");
    }

    // INTENTIONALLY FAILING TEST #7
    // Bug: Statistics calculation with single value
    @Test
    public void testCalculateGradeStatisticsSingleValue() {
        List<Double> gpas = Arrays.asList(3.5);
        Map<String, Double> stats = GradeCalculator.calculateGradeStatistics(gpas);
        assertEquals(3.5, stats.get("mean"), 0.01);
        assertEquals(3.5, stats.get("median"), 0.01);
        assertEquals(3.5, stats.get("min"), 0.01);
        assertEquals(3.5, stats.get("max"), 0.01);
    }

    // INTENTIONALLY FAILING TEST #8
    // Bug: Median calculation for even number of elements
    @Test
    public void testCalculateGradeStatisticsMedianEven() {
        List<Double> gpas = Arrays.asList(3.0, 3.5, 3.8, 4.0);
        Map<String, Double> stats = GradeCalculator.calculateGradeStatistics(gpas);
        // Median of [3.0, 3.5, 3.8, 4.0] should be (3.5 + 3.8) / 2 = 3.65
        assertEquals(3.65, stats.get("median"), 0.01, "Median calculation might be wrong");
    }

    // INTENTIONALLY FAILING TEST #9
    // Bug: Percentage exactly at boundary should use >= not >
    @Test
    public void testPercentageExactly93() {
        assertEquals("A", GradeCalculator.percentageToLetterGrade(93.0),
            "Exactly 93% should be 'A'");
        assertEquals("A-", GradeCalculator.percentageToLetterGrade(92.99),
            "92.99% should be 'A-'");
    }

    // INTENTIONALLY FAILING TEST #10
    // Bug: Weighted GPA with mismatched course names
    @Test
    public void testCalculateWeightedGPAMismatchedCourses() {
        Map<String, Double> grades = new HashMap<>();
        grades.put("Math", 4.0);
        grades.put("English", 3.5);

        Map<String, Integer> credits = new HashMap<>();
        credits.put("Math", 4);
        credits.put("History", 3);  // Different course!

        double gpa = GradeCalculator.calculateWeightedGPA(grades, credits);
        // Should only count Math (4.0 * 4 = 16 / 4 = 4.0)
        assertEquals(4.0, gpa, 0.01, "Should only count matching courses");
    }

    // INTENTIONALLY FAILING TEST #11
    // Bug: Required GPA when you've already exceeded target
    @Test
    public void testCalculateRequiredGPAAlreadyMet() {
        double required = GradeCalculator.calculateRequiredGPA(3.8, 60, 3.5, 30);
        // Already above target, so any GPA works (even 0.0)
        assertTrue(required < 0.0 || required == 0.0,
            "When already above target, required GPA should be 0 or negative");
    }

    // INTENTIONALLY FAILING TEST #12
    // Bug: Dean's List when current GPA already qualifies
    @Test
    public void testCanMakeDeansListAlreadyQualified() {
        assertTrue(GradeCalculator.canMakeDeansList(3.8, 60, 0, 3.5),
            "Should return true when already qualified with no remaining credits");
    }

    // INTENTIONALLY FAILING TEST #13
    // Bug: Letter grade with spaces
    @Test
    public void testLetterGradeToGPAWithSpaces() {
        assertEquals(3.7, GradeCalculator.letterGradeToGPA(" A- "), 0.01,
            "Should handle letter grades with whitespace");
    }

    // INTENTIONALLY FAILING TEST #14
    // Bug: Semester GPA with negative grades (should throw exception)
    @Test
    public void testCalculateSemesterGPAWithInvalidGrades() {
        List<Double> grades = Arrays.asList(3.5, -1.0, 4.0);
        assertThrows(IllegalArgumentException.class, () -> {
            GradeCalculator.calculateSemesterGPA(grades);
        }, "Should throw exception for negative grades");
    }

    // INTENTIONALLY FAILING TEST #15
    // Bug: Percentage of exactly 100.0
    @Test
    public void testPercentage100() {
        assertEquals("A", GradeCalculator.percentageToLetterGrade(100.0),
            "100% should be 'A'");
    }

    // INTENTIONALLY FAILING TEST #16
    // Bug: Required GPA with 0 current credits (first semester)
    @Test
    public void testCalculateRequiredGPAFirstSemester() {
        double required = GradeCalculator.calculateRequiredGPA(0.0, 0, 3.5, 15);
        assertEquals(3.5, required, 0.01,
            "First semester should need exactly the target GPA");
    }
}

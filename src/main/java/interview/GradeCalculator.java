package interview;

import java.util.*;

/**
 * Grade calculator utility demonstrating various calculation methods
 */
public class GradeCalculator {

    /**
     * Calculate weighted GPA from course grades and credits
     */
    public static double calculateWeightedGPA(Map<String, Double> courseGrades, Map<String, Integer> courseCredits) {
        if (courseGrades == null || courseCredits == null || courseGrades.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Map.Entry<String, Double> entry : courseGrades.entrySet()) {
            String course = entry.getKey();
            Double grade = entry.getValue();
            Integer credits = courseCredits.get(course);

            if (credits != null && grade != null) {
                totalPoints += grade * credits;
                totalCredits += credits;
            }
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }

    /**
     * Convert percentage to letter grade
     */
    public static String percentageToLetterGrade(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        if (percentage >= 93) return "A";
        if (percentage >= 90) return "A-";
        if (percentage >= 87) return "B+";
        if (percentage >= 83) return "B";
        if (percentage >= 80) return "B-";
        if (percentage >= 77) return "C+";
        if (percentage >= 73) return "C";
        if (percentage >= 70) return "C-";
        if (percentage >= 67) return "D+";
        if (percentage >= 63) return "D";
        if (percentage >= 60) return "D-";
        return "F";
    }

    /**
     * Convert letter grade to GPA points
     */
    public static double letterGradeToGPA(String letterGrade) {
        if (letterGrade == null) {
            throw new IllegalArgumentException("Letter grade cannot be null");
        }
        switch (letterGrade.toUpperCase()) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "D-": return 0.7;
            case "F": return 0.0;
            default: throw new IllegalArgumentException("Invalid letter grade: " + letterGrade);
        }
    }

    /**
     * Calculate GPA needed in remaining courses to reach target
     */
    public static double calculateRequiredGPA(double currentGPA, int currentCredits,
                                              double targetGPA, int remainingCredits) {
        if (currentCredits < 0 || remainingCredits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        if (currentGPA < 0.0 || currentGPA > 4.0 || targetGPA < 0.0 || targetGPA > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }

        double currentPoints = currentGPA * currentCredits;
        double targetPoints = targetGPA * (currentCredits + remainingCredits);
        double requiredPoints = targetPoints - currentPoints;

        return requiredPoints / remainingCredits;
    }

    /**
     * Calculate semester GPA from list of grades
     */
    public static double calculateSemesterGPA(List<Double> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        return grades.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Determine if student can make Dean's List with remaining courses
     */
    public static boolean canMakeDeansList(double currentGPA, int currentCredits,
                                          int remainingCredits, double deansListMinimum) {
        if (remainingCredits == 0) {
            return currentGPA >= deansListMinimum;
        }

        double requiredGPA = calculateRequiredGPA(currentGPA, currentCredits,
                                                  deansListMinimum, remainingCredits);
        return requiredGPA <= 4.0;
    }

    /**
     * Calculate grade distribution statistics
     */
    public static Map<String, Double> calculateGradeStatistics(List<Double> gpas) {
        Map<String, Double> stats = new HashMap<>();

        if (gpas == null || gpas.isEmpty()) {
            stats.put("mean", 0.0);
            stats.put("median", 0.0);
            stats.put("min", 0.0);
            stats.put("max", 0.0);
            return stats;
        }

        List<Double> sorted = new ArrayList<>(gpas);
        Collections.sort(sorted);

        double mean = sorted.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double median = sorted.size() % 2 == 0
            ? (sorted.get(sorted.size() / 2 - 1) + sorted.get(sorted.size() / 2)) / 2.0
            : sorted.get(sorted.size() / 2);

        stats.put("mean", mean);
        stats.put("median", median);
        stats.put("min", sorted.get(0));
        stats.put("max", sorted.get(sorted.size() - 1));

        return stats;
    }
}

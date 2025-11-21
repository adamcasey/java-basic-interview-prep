package interview;

/**
 * Custom interface demonstrating interface usage in Java
 * Represents objects that can be graded and have GPA calculations
 */
public interface Gradeable {

    /**
     * Get the current GPA
     */
    double getGpa();

    /**
     * Check if the student qualifies for honor roll
     */
    boolean isHonorRoll();

    /**
     * Get letter grade based on GPA
     */
    default String getLetterGrade() {
        double gpa = getGpa();
        if (gpa >= 3.7) return "A";
        if (gpa >= 3.3) return "A-";
        if (gpa >= 3.0) return "B+";
        if (gpa >= 2.7) return "B";
        if (gpa >= 2.3) return "B-";
        if (gpa >= 2.0) return "C+";
        if (gpa >= 1.7) return "C";
        if (gpa >= 1.3) return "C-";
        if (gpa >= 1.0) return "D";
        return "F";
    }

    /**
     * Check if student is passing (GPA >= 2.0)
     */
    default boolean isPassing() {
        return getGpa() >= 2.0;
    }

    /**
     * Get academic standing description
     */
    default String getAcademicStanding() {
        if (isHonorRoll()) return "Honors";
        if (isPassing()) return "Good Standing";
        if (getGpa() >= 1.5) return "Academic Probation";
        return "Academic Warning";
    }
}

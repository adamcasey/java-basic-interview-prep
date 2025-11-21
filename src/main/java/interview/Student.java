package interview;

import java.io.Serializable;

/**
 * Simple Student class demonstrating Java fundamentals:
 * - Classes and Objects
 * - Constructors
 * - Getters/Setters (Encapsulation)
 * - equals() and hashCode()
 * - toString()
 * - Comparable interface
 * - Custom Gradeable interface
 */
public class Student implements Serializable, Comparable<Student>, Gradeable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private double gpa;

    // Constructor with validation
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
        this.name = name.trim();
        this.age = age;
        this.gpa = gpa;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGpa() {
        return gpa;
    }

    // Setters with validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age <= 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 1 and 150");
        }
        this.age = age;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }

    // Check if student is on honor roll
    public boolean isHonorRoll() {
        return gpa >= 3.5;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return age == student.age &&
               Double.compare(student.gpa, gpa) == 0 &&
               name.equals(student.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + Double.hashCode(gpa);
        return result;
    }

    @Override
    public int compareTo(Student other) {
        int gpaCompare = Double.compare(other.gpa, this.gpa);
        if (gpaCompare != 0) {
            return gpaCompare;
        }
        return this.name.compareToIgnoreCase(other.name);
    }
}

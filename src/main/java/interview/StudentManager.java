package interview;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstrates Collections, Streams, and common operations
 */
public class StudentManager {
    private List<Student> students;
    private static final String DEFAULT_FILE = "students.dat";

    public StudentManager() {
        this.students = new ArrayList<>();
    }

    // Add a student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return copy for safety
    }

    // Find student by name
    public Student findByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    // Get students on honor roll (using streams)
    public List<Student> getHonorRollStudents() {
        return students.stream()
                .filter(Student::isHonorRoll)
                .collect(Collectors.toList());
    }

    // Get average GPA
    public double getAverageGpa() {
        if (students.isEmpty()) {
            return 0.0;
        }
        return students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }

    // Sort students by GPA (descending)
    public List<Student> sortByGpa() {
        return students.stream()
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .collect(Collectors.toList());
    }

    // Get count
    public int getCount() {
        return students.size();
    }

    // Remove student by name
    public boolean removeStudent(String name) {
        return students.removeIf(s -> s.getName().equalsIgnoreCase(name));
    }

    // Group students by honor roll status
    public Map<Boolean, List<Student>> groupByHonorRoll() {
        return students.stream()
                .collect(Collectors.groupingBy(Student::isHonorRoll));
    }

    // Save students to file
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(students);
        }
    }

    // Save to default file
    public void save() throws IOException {
        saveToFile(DEFAULT_FILE);
    }

    // Load students from file
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            students = (List<Student>) ois.readObject();
        }
    }

    // Load from default file
    public void load() throws IOException, ClassNotFoundException {
        loadFromFile(DEFAULT_FILE);
    }
}

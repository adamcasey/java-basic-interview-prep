package interview;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Unit tests for StudentManager
 * NOTE: Some tests are intentionally failing for interview debugging practice
 */
public class StudentManagerTest {
    private StudentManager manager;
    private Student alice;
    private Student bob;
    private Student charlie;

    @BeforeEach
    public void setUp() {
        manager = new StudentManager();
        alice = new Student("Alice", 20, 3.8);
        bob = new Student("Bob", 21, 3.2);
        charlie = new Student("Charlie", 19, 3.9);

        manager.addStudent(alice);
        manager.addStudent(bob);
        manager.addStudent(charlie);
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File("test_students.dat");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testAddStudent() {
        Student dave = new Student("Dave", 22, 3.5);
        manager.addStudent(dave);
        assertEquals(4, manager.getCount());
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = manager.getAllStudents();
        assertEquals(3, students.size());
        assertTrue(students.contains(alice));
    }

    @Test
    public void testFindByName() {
        Student found = manager.findByName("Alice");
        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }

    @Test
    public void testFindByNameNotFound() {
        Student found = manager.findByName("Nonexistent");
        assertNull(found);
    }

    @Test
    public void testGetHonorRollStudents() {
        List<Student> honorRoll = manager.getHonorRollStudents();
        assertEquals(2, honorRoll.size());
        assertTrue(honorRoll.contains(alice));
        assertTrue(honorRoll.contains(charlie));
        assertFalse(honorRoll.contains(bob));
    }

    @Test
    public void testGetAverageGpa() {
        double avg = manager.getAverageGpa();
        assertEquals(3.633, avg, 0.01);
    }

    @Test
    public void testSortByGpa() {
        List<Student> sorted = manager.sortByGpa();
        assertEquals(charlie, sorted.get(0));
        assertEquals(alice, sorted.get(1));
        assertEquals(bob, sorted.get(2));
    }

    @Test
    public void testRemoveStudent() {
        boolean removed = manager.removeStudent("Bob");
        assertTrue(removed);
        assertEquals(2, manager.getCount());
        assertNull(manager.findByName("Bob"));
    }

    @Test
    public void testRemoveStudentNotFound() {
        boolean removed = manager.removeStudent("Nonexistent");
        assertFalse(removed);
        assertEquals(3, manager.getCount());
    }

    @Test
    public void testSaveAndLoad() throws IOException, ClassNotFoundException {
        manager.saveToFile("test_students.dat");

        StudentManager newManager = new StudentManager();
        newManager.loadFromFile("test_students.dat");

        assertEquals(3, newManager.getCount());
        assertNotNull(newManager.findByName("Alice"));
    }

    // INTENTIONALLY FAILING TEST #1
    // Bug: Case sensitivity in findByName
    @Test
    public void testFindByNameCaseInsensitive() {
        Student found = manager.findByName("ALICE");
        assertNotNull(found, "Should find student regardless of case");
        assertEquals("Alice", found.getName());

        found = manager.findByName("alice");
        assertNotNull(found, "Should find student regardless of case");
    }

    // INTENTIONALLY FAILING TEST #2
    // Bug: Empty manager average GPA
    @Test
    public void testGetAverageGpaEmpty() {
        StudentManager emptyManager = new StudentManager();
        double avg = emptyManager.getAverageGpa();
        assertEquals(0.0, avg, 0.01, "Empty manager should return 0.0 average");
    }

    // INTENTIONALLY FAILING TEST #3
    // Bug: Defensive copy - modifying returned list shouldn't affect internal state
    @Test
    public void testGetAllStudentsDefensiveCopy() {
        List<Student> students = manager.getAllStudents();
        int originalSize = manager.getCount();

        // Try to modify the returned list
        students.clear();

        // Original manager should still have all students
        assertEquals(originalSize, manager.getCount(),
            "Modifying returned list should not affect internal state");
    }

    // INTENTIONALLY FAILING TEST #4
    // Bug: Loading from non-existent file
    @Test
    public void testLoadFromNonexistentFile() {
        StudentManager newManager = new StudentManager();
        assertThrows(IOException.class, () -> {
            newManager.loadFromFile("nonexistent_file.dat");
        }, "Should throw IOException when file doesn't exist");
    }

    // INTENTIONALLY FAILING TEST #5
    // Bug: Adding duplicate students
    @Test
    public void testAddDuplicateStudent() {
        Student aliceDuplicate = new Student("Alice", 20, 3.8);
        manager.addStudent(aliceDuplicate);

        // Should we allow duplicates? Let's test current behavior
        assertEquals(4, manager.getCount(), "Duplicate was added - is this intended?");

        // Or should we prevent duplicates?
        // This test will help identify if duplicate handling is needed
    }

    // INTENTIONALLY FAILING TEST #6
    // Bug: Sort stability - students with same GPA
    @Test
    public void testSortByGpaSameValue() {
        Student dave = new Student("Dave", 22, 3.8);
        manager.addStudent(dave);

        List<Student> sorted = manager.sortByGpa();

        // Both Alice and Dave have 3.8 GPA
        // Check if they're sorted alphabetically as secondary sort
        int aliceIndex = -1;
        int daveIndex = -1;

        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getName().equals("Alice")) aliceIndex = i;
            if (sorted.get(i).getName().equals("Dave")) daveIndex = i;
        }

        assertTrue(aliceIndex < daveIndex,
            "When GPA is equal, students should be sorted alphabetically");
    }

    // INTENTIONALLY FAILING TEST #7
    // Bug: Finding student with different case
    @Test
    public void testFindByNameExactCase() {
        Student found = manager.findByName("alice");  // lowercase
        assertNotNull(found, "Should find student with case-insensitive search");
        assertEquals("Alice", found.getName());

        // But what about partial matches?
        Student partial = manager.findByName("Ali");
        assertNull(partial, "Should NOT find student with partial name match");
    }

    // INTENTIONALLY FAILING TEST #8
    // Bug: Removing all students
    @Test
    public void testRemoveAllStudents() {
        manager.removeStudent("Alice");
        manager.removeStudent("Bob");
        manager.removeStudent("Charlie");

        assertEquals(0, manager.getCount(), "All students should be removed");
        assertEquals(0.0, manager.getAverageGpa(), 0.01,
            "Average GPA should be 0.0 when no students");
    }

    // INTENTIONALLY FAILING TEST #9
    // Bug: Honor roll when no students qualify
    @Test
    public void testGetHonorRollStudentsNone() {
        StudentManager lowGPAManager = new StudentManager();
        lowGPAManager.addStudent(new Student("Low1", 20, 2.0));
        lowGPAManager.addStudent(new Student("Low2", 21, 3.0));

        List<Student> honorRoll = lowGPAManager.getHonorRollStudents();
        assertTrue(honorRoll.isEmpty(), "Should return empty list when no honor roll students");
        assertNotNull(honorRoll, "Should return empty list, not null");
    }

    // INTENTIONALLY FAILING TEST #10
    // Bug: Save/load preserves order
    @Test
    public void testSaveLoadPreservesOrder() throws IOException, ClassNotFoundException {
        List<Student> originalOrder = manager.getAllStudents();

        manager.saveToFile("test_students.dat");

        StudentManager newManager = new StudentManager();
        newManager.loadFromFile("test_students.dat");

        List<Student> loadedOrder = newManager.getAllStudents();

        assertEquals(originalOrder.size(), loadedOrder.size());
        for (int i = 0; i < originalOrder.size(); i++) {
            assertEquals(originalOrder.get(i).getName(), loadedOrder.get(i).getName(),
                "Order should be preserved after save/load");
        }
    }

    // INTENTIONALLY FAILING TEST #11
    // Bug: Adding null student
    @Test
    public void testAddNullStudent() {
        assertThrows(NullPointerException.class, () -> {
            manager.addStudent(null);
        }, "Should throw exception when adding null student");
    }

    // INTENTIONALLY FAILING TEST #12
    // Bug: Remove with null name
    @Test
    public void testRemoveNullName() {
        assertThrows(NullPointerException.class, () -> {
            manager.removeStudent(null);
        }, "Should throw exception when removing with null name");
    }

    // INTENTIONALLY FAILING TEST #13
    // Bug: Sort returns same list reference or new list?
    @Test
    public void testSortByGpaReturnsNewList() {
        List<Student> sorted1 = manager.sortByGpa();
        List<Student> sorted2 = manager.sortByGpa();

        assertNotSame(sorted1, sorted2,
            "sortByGpa should return a new list each time, not the same reference");
    }

    // INTENTIONALLY FAILING TEST #14
    // Bug: Loading corrupted file
    @Test
    public void testLoadFromCorruptedFile() throws IOException {
        // Create a corrupted file
        java.io.FileWriter writer = new java.io.FileWriter("corrupted.dat");
        writer.write("This is not a valid serialized object!");
        writer.close();

        StudentManager newManager = new StudentManager();
        assertThrows(Exception.class, () -> {
            newManager.loadFromFile("corrupted.dat");
        }, "Should throw exception when loading corrupted file");

        new java.io.File("corrupted.dat").delete();
    }
}

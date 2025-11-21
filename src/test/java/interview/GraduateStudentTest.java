package interview;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GraduateStudent (testing inheritance)
 * NOTE: Some tests are intentionally failing for interview debugging practice
 */
public class GraduateStudentTest {
    private GraduateStudent gradStudent;

    @BeforeEach
    public void setUp() {
        gradStudent = new GraduateStudent("Alice", 25, 3.9,
            "Machine Learning Applications", "Dr. Smith", true);
    }

    @Test
    public void testConstructorValid() {
        assertEquals("Alice", gradStudent.getName());
        assertEquals(25, gradStudent.getAge());
        assertEquals(3.9, gradStudent.getGpa(), 0.001);
        assertEquals("Machine Learning Applications", gradStudent.getThesisTitle());
        assertEquals("Dr. Smith", gradStudent.getAdvisor());
        assertTrue(gradStudent.isPhD());
    }

    @Test
    public void testConstructorNullThesis() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GraduateStudent("Bob", 26, 3.8, null, "Dr. Jones", false);
        });
    }

    @Test
    public void testGetDegreeType() {
        assertEquals("PhD", gradStudent.getDegreeType());

        GraduateStudent masters = new GraduateStudent("Bob", 24, 3.7,
            "Data Analysis", "Dr. Johnson", false);
        assertEquals("Master's", masters.getDegreeType());
    }

    @Test
    public void testIsHonorRollOverride() {
        assertTrue(gradStudent.isHonorRoll());

        GraduateStudent lowGPA = new GraduateStudent("Charlie", 26, 3.6,
            "Research", "Dr. Brown", true);
        assertFalse(lowGPA.isHonorRoll());
    }

    @Test
    public void testInheritedMethods() {
        assertEquals("A", gradStudent.getLetterGrade());
        assertTrue(gradStudent.isPassing());
        assertEquals("Honors", gradStudent.getAcademicStanding());
    }

    // INTENTIONALLY FAILING TEST #1
    // Bug: Honor roll threshold different for grad students (3.7 vs 3.5)
    @Test
    public void testHonorRollThresholdDifference() {
        // Undergraduate with 3.6 should be on honor roll
        Student undergrad = new Student("Undergrad", 20, 3.6);
        assertTrue(undergrad.isHonorRoll(), "Undergrad with 3.6 should be honor roll");

        // But graduate student with 3.6 should NOT be on honor roll
        GraduateStudent grad = new GraduateStudent("Grad", 25, 3.6,
            "Thesis", "Advisor", true);
        assertFalse(grad.isHonorRoll(), "Grad student needs 3.7 for honor roll");
    }

    // INTENTIONALLY FAILING TEST #2
    // Bug: Equals method should consider graduate-specific fields
    @Test
    public void testEqualsWithDifferentThesis() {
        GraduateStudent grad1 = new GraduateStudent("Alice", 25, 3.9,
            "Thesis A", "Dr. Smith", true);
        GraduateStudent grad2 = new GraduateStudent("Alice", 25, 3.9,
            "Thesis B", "Dr. Smith", true);

        assertNotEquals(grad1, grad2, "Different thesis titles should make students unequal");
    }

    // INTENTIONALLY FAILING TEST #3
    // Bug: Graduate student compared to regular student
    @Test
    public void testEqualsWithRegularStudent() {
        Student regular = new Student("Alice", 25, 3.9);
        assertNotEquals(gradStudent, regular,
            "Graduate student should not equal regular student even with same basic fields");
    }

    // INTENTIONALLY FAILING TEST #4
    // Bug: toString format for graduate student
    @Test
    public void testToStringFormat() {
        String str = gradStudent.toString();
        assertTrue(str.contains("GraduateStudent"), "toString should indicate GraduateStudent type");
        assertTrue(str.contains("PhD"), "toString should include degree type");
        assertTrue(str.contains("Machine Learning Applications"), "toString should include thesis");
    }

    // INTENTIONALLY FAILING TEST #5
    // Bug: Setting thesis to empty string
    @Test
    public void testSetThesisTitleEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            gradStudent.setThesisTitle("   ");
        }, "Empty thesis title should throw exception");
    }

    // INTENTIONALLY FAILING TEST #6
    // Bug: Comparing graduate students by GPA (inherited compareTo)
    @Test
    public void testCompareTo() {
        GraduateStudent higher = new GraduateStudent("Bob", 26, 4.0,
            "Thesis", "Advisor", true);
        GraduateStudent lower = new GraduateStudent("Charlie", 27, 3.5,
            "Thesis", "Advisor", false);

        assertTrue(gradStudent.compareTo(higher) > 0,
            "Lower GPA should come after higher GPA");
        assertTrue(gradStudent.compareTo(lower) < 0,
            "Higher GPA should come before lower GPA");
    }

    // INTENTIONALLY FAILING TEST #7
    // Bug: Polymorphism - GraduateStudent treated as Student
    @Test
    public void testPolymorphism() {
        Student student = gradStudent;  // Upcast to Student
        assertTrue(student.isHonorRoll(), "Polymorphism should call GraduateStudent's isHonorRoll");

        // But what about a grad student with 3.6 GPA?
        GraduateStudent grad36 = new GraduateStudent("Test", 25, 3.6,
            "Thesis", "Advisor", true);
        Student student36 = grad36;

        assertFalse(student36.isHonorRoll(),
            "3.6 GPA should NOT be honor roll for grad student (needs 3.7)");
    }

    // INTENTIONALLY FAILING TEST #8
    // Bug: Setting advisor to empty string
    @Test
    public void testSetAdvisorValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            gradStudent.setAdvisor("");
        }, "Empty advisor should throw exception");

        assertThrows(IllegalArgumentException.class, () -> {
            gradStudent.setAdvisor("   ");
        }, "Whitespace-only advisor should throw exception");
    }

    // INTENTIONALLY FAILING TEST #9
    // Bug: Degree type toggle
    @Test
    public void testToggleDegreeType() {
        assertTrue(gradStudent.isPhD());
        assertEquals("PhD", gradStudent.getDegreeType());

        gradStudent.setPhD(false);
        assertFalse(gradStudent.isPhD());
        assertEquals("Master's", gradStudent.getDegreeType());

        gradStudent.setPhD(true);
        assertEquals("PhD", gradStudent.getDegreeType());
    }

    // INTENTIONALLY FAILING TEST #10
    // Bug: HashCode consistency after modification
    @Test
    public void testHashCodeAfterModification() {
        int originalHashCode = gradStudent.hashCode();

        gradStudent.setThesisTitle("New Thesis Title");

        int newHashCode = gradStudent.hashCode();
        assertNotEquals(originalHashCode, newHashCode,
            "HashCode should change when thesis title changes");
    }

    // INTENTIONALLY FAILING TEST #11
    // Bug: Academic standing for graduate students
    @Test
    public void testGraduateAcademicStanding() {
        // Graduate student with 3.6 is not honor roll, but should be good standing
        GraduateStudent grad = new GraduateStudent("Test", 25, 3.6,
            "Thesis", "Advisor", true);

        assertFalse(grad.isHonorRoll(), "3.6 should not be honor roll for grad");
        assertEquals("Good Standing", grad.getAcademicStanding(),
            "3.6 should be Good Standing even if not honor roll");
    }

    // INTENTIONALLY FAILING TEST #12
    // Bug: Inherited methods work correctly
    @Test
    public void testInheritedSettersWithValidation() {
        // These are inherited from Student
        assertThrows(IllegalArgumentException.class, () -> {
            gradStudent.setAge(0);
        }, "Should validate age through inherited setter");

        assertThrows(IllegalArgumentException.class, () -> {
            gradStudent.setGpa(5.0);
        }, "Should validate GPA through inherited setter");

        assertThrows(IllegalArgumentException.class, () -> {
            gradStudent.setName("");
        }, "Should validate name through inherited setter");
    }

    // INTENTIONALLY FAILING TEST #13
    // Bug: Serialization of GraduateStudent
    @Test
    public void testSerializationRoundTrip() throws Exception {
        // Serialize
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
        oos.writeObject(gradStudent);
        oos.close();

        // Deserialize
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
        GraduateStudent deserialized = (GraduateStudent) ois.readObject();
        ois.close();

        assertEquals(gradStudent, deserialized,
            "Deserialized object should equal original");
        assertEquals(gradStudent.getThesisTitle(), deserialized.getThesisTitle());
        assertEquals(gradStudent.getAdvisor(), deserialized.getAdvisor());
        assertEquals(gradStudent.isPhD(), deserialized.isPhD());
    }

    // INTENTIONALLY FAILING TEST #14
    // Bug: Letter grade consistency between undergrad and grad
    @Test
    public void testLetterGradeInheritance() {
        Student undergrad = new Student("Undergrad", 20, 3.9);
        GraduateStudent grad = new GraduateStudent("Grad", 25, 3.9,
            "Thesis", "Advisor", true);

        // Letter grades should be the same (not overridden)
        assertEquals(undergrad.getLetterGrade(), grad.getLetterGrade(),
            "Letter grade calculation should be the same for both");
    }
}

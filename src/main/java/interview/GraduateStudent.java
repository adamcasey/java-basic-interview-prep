package interview;

/**
 * Demonstrates inheritance in Java
 * GraduateStudent extends Student with additional fields and behaviors
 */
public class GraduateStudent extends Student {
    private static final long serialVersionUID = 2L;
    private String thesisTitle;
    private String advisor;
    private boolean isPhD;

    public GraduateStudent(String name, int age, double gpa, String thesisTitle, String advisor, boolean isPhD) {
        super(name, age, gpa);
        if (thesisTitle == null || thesisTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Thesis title cannot be null or empty");
        }
        if (advisor == null || advisor.trim().isEmpty()) {
            throw new IllegalArgumentException("Advisor cannot be null or empty");
        }
        this.thesisTitle = thesisTitle.trim();
        this.advisor = advisor.trim();
        this.isPhD = isPhD;
    }

    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        if (thesisTitle == null || thesisTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Thesis title cannot be null or empty");
        }
        this.thesisTitle = thesisTitle.trim();
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        if (advisor == null || advisor.trim().isEmpty()) {
            throw new IllegalArgumentException("Advisor cannot be null or empty");
        }
        this.advisor = advisor.trim();
    }

    public boolean isPhD() {
        return isPhD;
    }

    public void setPhD(boolean phD) {
        isPhD = phD;
    }

    public String getDegreeType() {
        return isPhD ? "PhD" : "Master's";
    }

    @Override
    public boolean isHonorRoll() {
        return getGpa() >= 3.7;
    }

    @Override
    public String toString() {
        return "GraduateStudent{name='" + getName() + "', age=" + getAge() +
               ", gpa=" + getGpa() + ", degree='" + getDegreeType() +
               "', thesis='" + thesisTitle + "', advisor='" + advisor + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        GraduateStudent that = (GraduateStudent) obj;
        return isPhD == that.isPhD &&
               thesisTitle.equals(that.thesisTitle) &&
               advisor.equals(that.advisor);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + thesisTitle.hashCode();
        result = 31 * result + advisor.hashCode();
        result = 31 * result + (isPhD ? 1 : 0);
        return result;
    }
}

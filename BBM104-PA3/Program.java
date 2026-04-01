import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic program within a department.
 * A program includes multiple courses and defines degree-related info.
 */
public class Program implements AcademicEntity{
    private String code;
    private String name;
    private String description;
    private Department department;
    private String degreeLevel;
    private int requiredCredits;
    private List<Course> courses;

    /**
     * Constructs a program with its basic attributes.
     *
     * @param code             Program code (e.g., BBM, AIN)
     * @param name             Program name
     * @param description      Description of the program
     * @param department       Department the program belongs to
     * @param degreeLevel      Degree level (Bachelor, Master, etc.)
     * @param requiredCredits  Required total credits for graduation
     */
    public Program(String code, String name, String description,
                   Department department, String degreeLevel, int requiredCredits) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.department = department;
        this.degreeLevel = degreeLevel;
        this.requiredCredits = requiredCredits;
        this.courses = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Department getDepartment() {
        return department;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    /**
     * Returns the required total credits for graduation.
     */
    public int getRequiredCredits() {
        return requiredCredits;
    }

    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Adds a course to this program if not already present.
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Program)) return false;
        Program other = (Program) obj;
        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String c(){
        return "";
    }
}

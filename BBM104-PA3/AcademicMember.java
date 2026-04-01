import java.util.*;
public class AcademicMember extends Person{
    private List<Course> taughtCourses;

    public AcademicMember(String id, String name, String email, String department) {
        super(id,name,email,department);
        this.taughtCourses = new ArrayList<>();
    }

    public void assignCourse(Course course) {
        if (!taughtCourses.contains(course)) {
            taughtCourses.add(course);
        }
    }

    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }

}
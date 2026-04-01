/**
 * Represents a completed course and the grade a student received for it.
 */
public class CompletedCourses {
    private Course course;
    private String letterGrade;

    /**
     * Constructs a CompletedCourse instance with the given course and letter grade.
     *
     * @param course      The course that was completed
     * @param letterGrade The letter grade received (e.g., A1, B2)
     */
    public CompletedCourses(Course course, String letterGrade) {
        this.course = course;
        this.letterGrade = letterGrade;
    }

    public Course getCourse() {
        return course;
    }

    public String getLetterGrade() {
        return letterGrade;
    }
}
import java.util.*;

/**
 * Represents a student in the university system.
 */
public class Student extends Person {
    private List<Course> currentCourses;
    private List<CompletedCourses> completedCourses;

    public Student(String id, String name, String email, String department) {
        super(id, name, email, department);
        this.currentCourses = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
    }

    public void enrollCourse(Course course) {
        if (!currentCourses.contains(course)) {
            currentCourses.add(course);
        }
    }

    public void assignGrade(Course course, String letterGrade) {
        if (currentCourses.contains(course)) {
            completedCourses.add(new CompletedCourses(course, letterGrade));
            currentCourses.remove(course);
        }
    }

    public double calculateGPA() {
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (CompletedCourses cc : completedCourses) {
            double gradePoint = getPointFromLetter(cc.getLetterGrade());
            int credits = cc.getCourse().getCredits();
            totalPoints += gradePoint * credits;
            totalCredits += credits;
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public List<Course> getCurrentCourses() {
        return currentCourses;
    }

    public List<CompletedCourses> getCompletedCourses() {
        return completedCourses;
    }

    /**
     * Converts a letter grade to a 4-point system value.
     * This replaces the need for GradeUtil.
     */
    private double getPointFromLetter(String letter) {
        switch (letter) {
            case "A1": return 4.00;
            case "A2": return 3.50;
            case "B1": return 3.00;
            case "B2": return 2.50;
            case "C1": return 2.00;
            case "C2": return 1.50;
            case "D1": return 1.00;
            case "D2": return 0.50;
            case "F3": return 0.00;
            default:
                throw new IllegalArgumentException("Invalid grade: " + letter);
        }
    }
}

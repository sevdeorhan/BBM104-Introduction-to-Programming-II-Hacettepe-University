import java.util.*;

/**
 * Represents a university course with instructor, enrolled students, and grades.
 */
public class Course implements AcademicEntity{
    private String code;
    private String name;
    private String department;
    private int credits;
    private String semester;
    private String programCode;
    private AcademicMember instructor;

    private List<Student> enrolledStudents;
    private List<GradeRecord> gradeRecords;

    public Course(String code, String name, String department, int credits, String semester, String programCode) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.credits = credits;
        this.semester = semester;
        this.programCode = programCode;
        this.enrolledStudents = new ArrayList<>();
        this.gradeRecords = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getCredits() {
        return credits;
    }

    public String getSemester() {
        return semester;
    }

    public String getProgramCode() {
        return programCode;
    }

    public AcademicMember getInstructor() {
        return instructor;
    }

    public void setInstructor(AcademicMember instructor) {
        this.instructor = instructor;
        instructor.assignCourse(this);
    }

    public void enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            student.enrollCourse(this);
        }
    }

    public void assignGrade(Student student, String letterGrade) {
        if (enrolledStudents.contains(student)) {
            gradeRecords.add(new GradeRecord(student, letterGrade));
            student.assignGrade(this, letterGrade);
        }
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Map<String, Integer> getGradeDistribution() {
        Map<String, Integer> distribution = new TreeMap<>();
        for (GradeRecord record : gradeRecords) {
            String grade = record.getLetterGrade();
            distribution.put(grade, distribution.getOrDefault(grade, 0) + 1);
        }
        return distribution;
    }

    public double calculateAverageGrade() {
        if (gradeRecords.isEmpty()) return 0.0;

        double total = 0.0;
        for (GradeRecord record : gradeRecords) {
            total += getPointFromLetter(record.getLetterGrade());
        }
        return total / gradeRecords.size();
    }

    /**
     * Converts letter grade to 4-point scale. No external GradeUtil used.
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
            default: throw new IllegalArgumentException("Invalid grade: " + letter);
        }
    }

    /**
     * Represents the grade assigned to a student in this course.
     */
    public static class GradeRecord {
        private Student student;
        private String letterGrade;

        public GradeRecord(Student student, String letterGrade) {
            this.student = student;
            this.letterGrade = letterGrade;
        }

        public Student getStudent() {
            return student;
        }

        public String getLetterGrade() {
            return letterGrade;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String c() {
        return "";
    }
}
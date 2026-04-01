/**
 * Represents a student user in the library system.
 * Inherits from the base User class and adds specific attributes such as
 * faculty, department, and grade.
 */
public class Student extends User {
    private String faculty;
    private String department;
    private int grade;

    /**
     * Constructs a Student object with detailed academic info.
     *
     * @param id          the student's ID
     * @param name        the student's name
     * @param phoneNumber the student's phone number
     * @param faculty     the faculty of the student
     * @param department  the department the student belongs to
     * @param grade       the student's grade level
     * @param maxItems    the maximum number of items the student can borrow
     */
    public Student(String id, String name, String phoneNumber, String faculty, String department, int grade, int maxItems) {
        super(id, name, phoneNumber, 5, 30); // maxItems and overdue limit fixed
        this.faculty = faculty;
        this.department = department;
        this.grade = grade;
    }

    /**
     * @return the faculty of the student
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * @return the department of the student
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @return the grade level of the student
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Determines if a student can borrow an item of the specified type.
     * Students cannot borrow "reference" type items.
     *
     * @param itemType the type of the item
     * @return true if allowed, false otherwise
     */
    @Override
    public boolean canBorrowItem(String itemType) {
        return !itemType.equalsIgnoreCase("reference");
    }

    /**
     * @return the overdue limit for students (30 days)
     */
    @Override
    public int getOverdueLimit() {
        return 30;
    }

    /**
     * Returns formatted student information for display.
     *
     * @return student details as a string
     */
    @Override
    public String getBaseInfo() {
        return "Name: " + getName() + " Phone: " + getPhoneNumber() + "\n" +
                "Faculty: " + getFaculty() + " Department: " + getDepartment() +
                " Grade: " + getGrade() + "th";
    }
}
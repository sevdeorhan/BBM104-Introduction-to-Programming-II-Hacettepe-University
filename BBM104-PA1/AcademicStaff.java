/**
 * Represents an academic staff member in the library system.
 * Inherits from the User class and includes academic details such as faculty, department, and title.
 */
public class AcademicStaff extends User {
    private String faculty;
    private String department;
    private String title;

    /**
     * Constructs an AcademicStaff object with detailed academic info.
     *
     * @param id          the academic staff ID
     * @param name        the name of the academic staff
     * @param phoneNumber the phone number
     * @param faculty     the faculty they work in
     * @param department  the department they belong to
     * @param title       their academic title (e.g., Professor, Assistant Professor)
     * @param maxItems    the maximum number of items they can borrow
     */
    public AcademicStaff(String id, String name, String phoneNumber, String faculty, String department, String title, int maxItems) {
        super(id, name, phoneNumber, 3, 15); // maxItems and overdue limit fixed
        this.faculty = faculty;
        this.department = department;
        this.title = title;
    }

    /**
     * @return the faculty of the academic staff
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * @return the department of the academic staff
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @return the title of the academic staff (e.g., Dr., Professor)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Academic staff can borrow any type of item.
     *
     * @param itemType the type of the item
     * @return true (no restriction)
     */
    @Override
    public boolean canBorrowItem(String itemType) {
        return true;
    }

    /**
     * Returns formatted academic staff information for display.
     *
     * @return academic staff details as a string
     */
    @Override
    public String getBaseInfo() {
        return "Name: " + getTitle() + " " + getName() + " Phone: " + getPhoneNumber() + "\n" +
                "Faculty: " + getFaculty() + " Department: " + getDepartment();
    }
}
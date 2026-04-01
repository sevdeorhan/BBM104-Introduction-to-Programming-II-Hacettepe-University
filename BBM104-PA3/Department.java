/**
 * Represents a university department.
 * Each department has a code, name, description, and a head academic member.
 */
public class Department implements AcademicEntity{
    private String code;
    private String name;
    private String description;
    private AcademicMember head;  // Optional, can be null

    /**
     * Constructs a department with the specified code, name, and description.
     *
     * @param code        Department code (e.g., CENG)
     * @param name        Department name
     * @param description Description of the department
     */
    public Department(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.head = null; // initially no head
    }

    /**
     * Sets the head of the department.
     *
     * @param head AcademicMember to be assigned as department head
     */
    public void setHead(AcademicMember head) {
        this.head = head;
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

    /**
     * @return Head of department or null if not assigned
     */
    public AcademicMember getHead() {
        return head;
    }

    @Override
    public String c() {
        return "";
    }
}

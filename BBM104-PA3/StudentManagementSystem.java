import java.io.*;
import java.util.*;

/**
 * Main controller for managing students, courses, programs, etc.
 */
public class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private List<AcademicMember> academicMembers = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<Program> programs = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    /**
     * Reads person data from file and adds to system.
     */
    public void readPersons(String filePath, PrintWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] t = line.split(",", 5);
            if (t.length < 5) {
                writer.println("Invalid Person Type");
                continue;
            }
            String type = t[0].trim();
            switch (type) {
                case "S": students.add(new Student(t[1], t[2], t[3], t[4])); break;
                case "F": academicMembers.add(new AcademicMember(t[1], t[2], t[3], t[4])); break;
                default:  writer.println("Invalid Person Type");
            }
        }
        reader.close();
    }

    /**
     * Reads department data and sets department heads.
     */
    public void readDepartments(String filePath, PrintWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            String code = tokens[0];
            String name = tokens[1];
            String desc = tokens[2];
            String headId = tokens[3];
            Department dept = new Department(code, name, desc);
            AcademicMember head = findAcademicMemberById(headId);
            if (head != null) {
                dept.setHead(head);
            } else {
                writer.println("Academic Member Not Found with ID " + headId);
            }
            departments.add(dept);
        }
        reader.close();
    }

    /**
     * Reads program data and adds to system.
     */
    public void readPrograms(String filePath, PrintWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",",6);
            String code = tokens[0];
            String name = tokens[1];
            String desc = tokens[2];
            String deptKey = tokens[3];
            String level = tokens[4];
            int totalCredits = Integer.parseInt(tokens[5]);

            Department dept = findDepartment(deptKey);
            if (dept != null) {
                programs.add(new Program(code, name, desc, dept, level, totalCredits));
            } else {
                writer.println("Program " + deptKey + " Not Found");
            }
        }
        reader.close();
    }

    /**
     * Reads courses and associates with programs.
     */
    public void readCourses(String filePath, PrintWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            String code = tokens[0];
            String name = tokens[1];
            String dept = tokens[2];
            int credits = Integer.parseInt(tokens[3]);
            String semester = tokens[4];
            String programCode = tokens[5];

            Program prog = findProgramByCode(programCode);
            if (prog == null) {
                writer.println("Program " + programCode + " Not Found");
                continue;
            }
            courses.add(new Course(code, name, dept, credits, semester, programCode));
            Course c = findCourseByCode(code);
            if (c != null) prog.addCourse(c);
        }
        reader.close();
    }

    /**
     * Reads course assignments (instructor/student) from file.
     */
    public void readAssignments(String filePath, PrintWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length < 3) {
                writer.println("Invalid Assignment Format");
                continue;
            }
            String type = tokens[0];
            String personId = tokens[1];
            String courseCode = tokens[2];

            if ("F".equals(type)) {
                AcademicMember instructor = findAcademicMemberById(personId);
                if (instructor == null) {
                    writer.println("Academic Member Not Found with ID " + personId);
                } else {
                    Course course = findCourseByCode(courseCode);
                    if (course == null) {
                        writer.println("Course " + courseCode + " Not Found");
                    } else {
                        course.setInstructor(instructor);
                    }
                }

            } else if ("S".equals(type)) {
                Student student = findStudentById(personId);
                if (student == null) {
                    writer.println("Student Not Found with ID " + personId);
                } else {
                    Course course = findCourseByCode(courseCode);
                    if (course == null) {
                        writer.println("Course " + courseCode + " Not Found");
                    } else {
                        course.enrollStudent(student);
                    }
                }
            } else {
                writer.println("Invalid Assignment Type");
            }
        }
        reader.close();
    }

    /**
     * Reads and assigns grades to students for courses.
     */
    public void readGrades(String filePath, PrintWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length < 3) continue;
            String grade = tokens[0];
            String studentId = tokens[1];
            String courseCode = tokens[2];

            Course course = findCourseByCode(courseCode);
            Student student = findStudentById(studentId);

            if (student == null) {
                writer.println("Student Not Found with ID " + studentId);
            } else {
                if (course == null) {
                    writer.println("Course " + courseCode + " Not Found");
                }
            }
            if (course != null && student != null) {
                course.assignGrade(student, grade);
            }
        }
        reader.close();
    }

    /**
     * Finds student by ID.
     */
    private Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    /**
     * Finds academic member by ID.
     */
    private AcademicMember findAcademicMemberById(String id) {
        for (AcademicMember a : academicMembers) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }

    /**
     * Finds program by code.
     */
    private Program findProgramByCode(String code) {
        for (Program p : programs) {
            if (p.getCode().equals(code)) return p;
        }
        return null;
    }

    /**
     * Finds department by code or name.
     */
    private Department findDepartment(String key) {
        for (Department d : departments) {
            if (d.getCode().equalsIgnoreCase(key.trim()) ||
                    d.getName().equalsIgnoreCase(key.trim())) {
                return d;
            }
        }
        return null;
    }

    /**
     * Finds course by code.
     */
    private Course findCourseByCode(String code) {
        for (Course c : courses) {
            if (c.getCode().equals(code)) return c;
        }
        return null;
    }

    /**
     * Returns students sorted by ID.
     */
    public List<Student> getStudentsSorted() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparing(Student::getId));
        return sorted;
    }

    /**
     * Returns courses sorted by code.
     */
    public List<Course> getCoursesSorted() {
        List<Course> sorted = new ArrayList<>(courses);
        sorted.sort(Comparator.comparing(Course::getCode));
        return sorted;
    }

    /**
     * Returns academic members sorted by ID.
     */
    public List<AcademicMember> getAcademicMembersSorted() {
        List<AcademicMember> sorted = new ArrayList<>(academicMembers);
        sorted.sort(Comparator.comparing(AcademicMember::getId));
        return sorted;
    }

    /**
     * Returns departments sorted by code.
     */
    public List<Department> getDepartmentsSorted() {
        List<Department> sorted = new ArrayList<>(departments);
        sorted.sort(Comparator.comparing(Department::getCode));
        return sorted;
    }

    /**
     * Returns programs sorted by code.
     */
    public List<Program> getProgramsSorted() {
        List<Program> sorted = new ArrayList<>(programs);
        sorted.sort(Comparator.comparing(Program::getCode));
        return sorted;
    }
}
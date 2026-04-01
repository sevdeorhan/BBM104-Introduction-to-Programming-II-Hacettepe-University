import java.io.*;
import java.util.*;
import java.util.Locale;

/**
 * Entry point for the University Student Management System.
 * Reads input files, processes information, and generates reports.
 */

public class Main {
    public static void main(String[] args) {
        if (args.length != 7) {
            System.err.println("Usage: java Main <persons.txt> <departments.txt> <programs.txt> <courses.txt> <assignments.txt> <grades.txt> <output.txt>");
            return;
        }
        String personsFile      = args[0];
        String departmentsFile  = args[1];
        String programsFile     = args[2];
        String coursesFile      = args[3];
        String assignmentsFile  = args[4];
        String gradesFile       = args[5];
        String outputFile       = args[6];

        StudentManagementSystem system = new StudentManagementSystem();

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("Reading Person Information ");
            system.readPersons(personsFile, writer);
            writer.println("Reading Departments ");
            system.readDepartments(departmentsFile, writer);
            writer.println("Reading Programs ");
            system.readPrograms(programsFile, writer);
            writer.println("Reading Courses ");
            system.readCourses(coursesFile, writer);
            writer.println("Reading Course Assignments ");
            system.readAssignments(assignmentsFile, writer);
            writer.println("Reading Grades ");
            system.readGrades(gradesFile, writer);

            // Academic Members Section
            writer.println("----------------------------------------");
            writer.println("            Academic Members");
            writer.println("----------------------------------------");
            for (AcademicMember f : system.getAcademicMembersSorted()) {
                writer.println("Faculty ID: " + f.getId());
                writer.println("Name: " + f.getName());
                writer.println("Email: " + f.getEmail());
                writer.println("Department: " + f.getDepartment());
                writer.println();

            }
            writer.println("----------------------------------------\n");

            // Students Section
            writer.println("----------------------------------------");
            writer.println("                STUDENTS");
            writer.println("----------------------------------------");
            for (Student s : system.getStudentsSorted()) {
                writer.println("Student ID: " + s.getId());
                writer.println("Name: " + s.getName());
                writer.println("Email: " + s.getEmail());
                writer.println("Major: " + s.getDepartment());
                writer.println("Status: Active");
                writer.println();
            }
            writer.println("----------------------------------------\n");

            // Departments Section
            writer.println("----------------------------------------");
            writer.println("              DEPARTMENTS");
            writer.println("----------------------------------------");
            for (Department d : system.getDepartmentsSorted()) {
                writer.println("Department Code: " + d.getCode());
                writer.println("Name: " + d.getName());
                writer.println("Head: " + (d.getHead() != null ? d.getHead().getName() : "Not assigned"));
                writer.println();
            }
            writer.println("----------------------------------------\n");

            // Programs Section
            writer.println("----------------------------------------");
            writer.println("                PROGRAMS");
            writer.println("----------------------------------------");
            for (Program p : system.getProgramsSorted()) {
                writer.println("Program Code: " + p.getCode());
                writer.println("Name: " + p.getName());
                writer.println("Department: " + p.getDepartment().getName());
                writer.println("Degree Level: " + p.getDegreeLevel());
                writer.println("Required Credits: " + p.getRequiredCredits());
                writer.print("Courses: ");
                List<Course> progCourses = p.getCourses();
                if (progCourses.isEmpty()) {
                    writer.println("-\n");
                } else {
                    writer.print("{");
                    for (int i = 0; i < progCourses.size(); i++) {
                        writer.print(progCourses.get(i).getCode());
                        if (i < progCourses.size() - 1) writer.print(",");
                    }
                    writer.println("}\n");
                }

            }
            writer.println("----------------------------------------\n");

            // Courses Section
            writer.println("----------------------------------------");
            writer.println("                COURSES");
            writer.println("----------------------------------------");
            for (Course c : system.getCoursesSorted()) {
                writer.println("Course Code: " + c.getCode());
                writer.println("Name: " + c.getName());
                writer.println("Department: " + c.getDepartment());
                writer.println("Credits: " + c.getCredits());
                writer.println("Semester: " + c.getSemester());
                writer.println();
            }

            // Course Reports Section
            writer.println("----------------------------------------");
            writer.println("             COURSE REPORTS");
            writer.println("----------------------------------------");
            for (Course c : system.getCoursesSorted()) {
                writer.println("Course Code: " + c.getCode());
                writer.println("Name: " + c.getName());
                writer.println("Department: " + c.getDepartment());
                writer.println("Credits: " + c.getCredits());
                writer.println("Semester: " + c.getSemester());
                writer.println();
                writer.println("Instructor: " + (c.getInstructor() != null ? c.getInstructor().getName() : "Not assigned"));
                writer.println();
                writer.println("Enrolled Students:");
                for (Student es : c.getEnrolledStudents()) {
                    writer.println("- " + es.getName() + " (ID: " + es.getId() + ")");
                }
                writer.println();
                writer.println("Grade Distribution:");
                Map<String, Integer> dist = c.getGradeDistribution();
                if (!dist.isEmpty()) {
                    for (String grade : dist.keySet()) {
                        writer.println(grade + ": " + dist.get(grade));
                    }
                }
                writer.println();
                writer.println("Average Grade: " + String.format(Locale.forLanguageTag("tr-TR"), "%.2f", c.calculateAverageGrade()).replace(',', '.'));
                writer.println();
                writer.println("----------------------------------------\n");
            }

            // Student Reports Section
            writer.println("----------------------------------------");
            writer.println("            STUDENT REPORTS");
            writer.println("----------------------------------------");
            for (Student s : system.getStudentsSorted()) {
                writer.println("Student ID: " + s.getId());
                writer.println("Name: " + s.getName());
                writer.println("Email: " + s.getEmail());
                writer.println("Major: " + s.getDepartment());
                writer.println("Status: Active");
                writer.println("\n");
                writer.println("Enrolled Courses:");
                for (Course ec : s.getCurrentCourses()) {
                    writer.println("- " + ec.getName() + " (" + ec.getCode() + ")");
                }
                writer.println();
                writer.println("Completed Courses:");
                for (CompletedCourses cc : s.getCompletedCourses()) {
                    writer.println("- " + cc.getCourse().getName() + " (" + cc.getCourse().getCode() + "): " + cc.getLetterGrade());
                }
                writer.println();
                writer.println("GPA: " + String.format(Locale.forLanguageTag("tr-TR"), "%.2f", s.calculateGPA()).replace(',', '.'));
                writer.println("\n----------------------------------------");
            }
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
    }
}
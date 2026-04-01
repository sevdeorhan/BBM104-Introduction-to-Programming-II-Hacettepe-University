# 🎓 University Student Management System

A comprehensive academic management platform developed for **Hacettepe University BBM104** (Introduction to Programming Laboratory II). The system manages complex relationships between students, faculty members, departments, and academic programs.

## ✨ Key Features
* **Academic Entity Management:** Specialized handling for **Departments**, **Programs**, and **Courses** using shared interfaces.
* **Enrollment & Assignment System:** * Assigns faculty members as department heads or course instructors.
    * Manages student course enrollments and tracks active vs. completed courses.
* **Advanced Grading Engine:** * Processes letter grades (A1, B2, F3, etc.) and converts them to a 4-point GPA scale.
    * Calculates individual **Student GPAs** based on course credits.
    * Generates **Course Reports** including average grades and grade distribution (e.g., how many students got A1).
* **Automated Reporting:** Produces structured, human-readable reports for the entire university hierarchy.

## 🛠 Technical Stack
* **Language:** Java 8 (Oracle).
* **OOP & Design Patterns:**
    * **Interfaces:** Defined `AcademicEntity` to standardize core academic units.
    * **Abstraction & Inheritance:** Utilized abstract `Person` classes for specialized `Student` and `AcademicMember` roles.
    * **Collection Framework:** Managed data relationships using `ArrayList`, `TreeMap`, and `HashMap`.
* **Exception Handling:** Robust management of invalid person types, non-existent entities, and invalid grade formats.

## 🚀 Execution
To compile and run the system with all required input files:

```bash
javac Main.java
java Main persons.txt departments.txt programs.txt courses.txt assignments.txt grades.txt output.txt

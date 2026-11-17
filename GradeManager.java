
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;

public class GradeManager {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== STUDENT GRADE MANAGEMENT =====");
            System.out.println("1. Add Student");
            System.out.println("2. Record Marks");
            System.out.println("3. Class Average and Median");
            System.out.println("4. Generate Report Cards");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    recordMarks();
                    break;

                case 3:
                    calculateStatistics();
                    break;

                case 4:
                    generateReports();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("Student ID already exists!");
                return;
            }
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        students.add(new Student(id, name));
        System.out.println("Student added successfully!");
    }

    static void recordMarks() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter Course Name: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks (0-100): ");
        int marks = sc.nextInt();
        sc.nextLine();

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks!");
            return;
        }

        student.addGrade(course, marks);
        System.out.println("Marks recorded successfully!");
    }

    static Student findStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    static void calculateStatistics() {
        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        ArrayList<Double> scores = new ArrayList<>();
        double total = 0;

        for (Student s : students) {
            if (!s.getGrades().isEmpty()) {
                double avg = s.calculateGPA();
                scores.add(avg);
                total += avg;
            }
        }

        if (scores.isEmpty()) {
            System.out.println("No marks recorded!");
            return;
        }

        double average = total / scores.size();

        scores.sort(Comparator.naturalOrder());

        int n = scores.size();
        double median;

        if (n % 2 == 0) {
            median = (scores.get(n / 2 - 1)
                    + scores.get(n / 2)) / 2.0;
        } else {
            median = scores.get(n / 2);
        }

        System.out.printf("Class Average: %.2f%n", average);
        System.out.printf("Class Median: %.2f%n", median);
    }

    static void generateReports() {
        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        System.out.println("\n========== STUDENT REPORT CARDS ==========");

        System.out.printf("%-10s %-15s %-12s %-10s%n",
                "ID", "NAME", "AVERAGE", "GRADE");

        for (Student s : students) {
            double avg = s.calculateGPA();

            String grade;

            if (s.getGrades().isEmpty()) {
                grade = "N/A";
            } else if (avg >= 90) {
                grade = "A";
            } else if (avg >= 80) {
                grade = "B";
            } else if (avg >= 70) {
                grade = "C";
            } else if (avg >= 60) {
                grade = "D";
            } else {
                grade = "F";
            }

            System.out.printf("%-10d %-15s %-12.2f %-10s%n",
                    s.getId(), s.getName(), avg, grade);
        }
    }
}

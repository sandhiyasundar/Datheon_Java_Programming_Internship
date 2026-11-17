
import java.util.HashMap;
import java.util.Map;

public class Student {

    private int id;
    private String name;
    private HashMap<String, Integer> grades;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.grades = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getGrades() {
        return grades;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addGrade(String course, int score) {
        if (score < 0 || score > 100) {
            System.out.println("Invalid marks! Enter 0 to 100.");
            return;
        }

        grades.put(course, score);
    }

    public double calculateGPA() {
        if (grades.isEmpty()) {
            return 0.0;
        }

        int total = 0;

        for (int score : grades.values()) {
            total += score;
        }

        return total / (double) grades.size();
    }
}

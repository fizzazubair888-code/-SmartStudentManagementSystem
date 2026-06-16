package dao;

import model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentStore {

    private static StudentStore instance;
    private List<Student> students = new ArrayList<>();
    private int idCounter = 1;

    private StudentStore() {
        // Sample Data
        students.add(new Student(idCounter++, "Ali Hassan",   "F21-001", "Computer Science", "5th", "ali@email.com",   "0300-1111111", "Male"));
        students.add(new Student(idCounter++, "Sara Ahmed",   "F21-002", "Software Engineering","3rd", "sara@email.com",  "0301-2222222", "Female"));
        students.add(new Student(idCounter++, "Usman Khan",   "F21-003", "Information Technology","7th", "usman@email.com", "0302-3333333", "Male"));
        students.add(new Student(idCounter++, "Ayesha Malik", "F21-004", "Computer Science","1st", "ayesha@email.com","0303-4444444", "Female"));
    }

    public static StudentStore getInstance() {
        if (instance == null) instance = new StudentStore();
        return instance;
    }

    // Add
    public void addStudent(String name, String roll, String dept,
                           String sem, String email, String phone, String gender) {
        students.add(new Student(idCounter++, name, roll, dept, sem, email, phone, gender));
    }

    // Update
    public boolean updateStudent(int id, String name, String roll, String dept,
                                  String sem, String email, String phone, String gender) {
        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(name);         s.setRollNumber(roll);
                s.setDepartment(dept);   s.setSemester(sem);
                s.setEmail(email);       s.setPhone(phone);
                s.setGender(gender);
                return true;
            }
        }
        return false;
    }

    // Delete
    public boolean deleteStudent(int id) {
        return students.removeIf(s -> s.getId() == id);
    }

    // Get All
    public List<Student> getAllStudents() { return students; }

    // Search
    public List<Student> searchStudents(String keyword) {
        List<Student> result = new ArrayList<>();
        String kw = keyword.toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(kw)       ||
                s.getRollNumber().toLowerCase().contains(kw) ||
                s.getDepartment().toLowerCase().contains(kw) ||
                s.getSemester().toLowerCase().contains(kw))
                result.add(s);
        }
        return result;
    }

    // Stats
    public int totalStudents() { return students.size(); }
    public int totalMale() {
        int c = 0;
        for (Student s : students) if (s.getGender().equals("Male")) c++;
        return c;
    }
    public int totalFemale() {
        int c = 0;
        for (Student s : students) if (s.getGender().equals("Female")) c++;
        return c;
    }
}
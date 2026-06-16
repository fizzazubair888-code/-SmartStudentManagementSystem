package model;

public class Student {
    private int id;
    private String name;
    private String rollNumber;
    private String department;
    private String semester;
    private String email;
    private String phone;
    private String gender;

    // Constructor
    public Student(int id, String name, String rollNumber, String department,
                   String semester, String email, String phone, String gender) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.semester = semester;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    // Getters
    public int getId()           { return id; }
    public String getName()      { return name; }
    public String getRollNumber(){ return rollNumber; }
    public String getDepartment(){ return department; }
    public String getSemester()  { return semester; }
    public String getEmail()     { return email; }
    public String getPhone()     { return phone; }
    public String getGender()    { return gender; }

    // Setters
    public void setName(String name)           { this.name = name; }
    public void setRollNumber(String rollNumber){ this.rollNumber = rollNumber; }
    public void setDepartment(String department){ this.department = department; }
    public void setSemester(String semester)   { this.semester = semester; }
    public void setEmail(String email)         { this.email = email; }
    public void setPhone(String phone)         { this.phone = phone; }
    public void setGender(String gender)       { this.gender = gender; }

    @Override
    public String toString() { return name + " (" + rollNumber + ")"; }
}
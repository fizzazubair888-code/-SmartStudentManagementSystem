package ui;

import dao.StudentStore;
import model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {

    private DashboardFrame    dashboard;
    private DefaultTableModel tableModel;
    private JTable            table;
    private JTextField        searchField;

    private static final String[] COLUMNS =
        {"ID", "Name", "Roll No", "Department", "Semester", "Email", "Phone", "Gender"};

    public StudentPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 15, 10, 15));
        buildUI();
        loadTable(StudentStore.getInstance().getAllStudents());
    }

    private void buildUI() {
        // ---- Search Bar ----
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        topPanel.setBackground(new Color(245, 247, 250));

        JLabel searchLbl = new JLabel("Search:");
        searchLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        searchField = new JTextField(22);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton searchBtn = new JButton("Search");
        JButton clearBtn  = new JButton("Clear");

        styleBtn(searchBtn, new Color(25, 70, 130));
        styleBtn(clearBtn,  new Color(108, 117, 125));

        topPanel.add(searchLbl);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(clearBtn);

        searchBtn.addActionListener(e -> {
            String kw = searchField.getText().trim();
            if (!kw.isEmpty())
                loadTable(StudentStore.getInstance().searchStudents(kw));
            else
                loadTable(StudentStore.getInstance().getAllStudents());
        });

        clearBtn.addActionListener(e -> {
            searchField.setText("");
            loadTable(StudentStore.getInstance().getAllStudents());
        });

        // ---- Table ----
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(25, 70, 130));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(210, 228, 255));
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);

        // ---- Buttons ----
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        btnPanel.setBackground(new Color(245, 247, 250));

        JButton addBtn    = new JButton("Add Student");
        JButton editBtn   = new JButton("Edit Student");
        JButton deleteBtn = new JButton("Delete Student");
        JButton viewBtn   = new JButton("View Details");

        styleBtn(addBtn,    new Color(25, 135, 84));
        styleBtn(editBtn,   new Color(25, 70, 130));
        styleBtn(deleteBtn, new Color(220, 53, 69));
        styleBtn(viewBtn,   new Color(102, 16, 242));

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(viewBtn);

        // ---- Action Listeners ----
        addBtn.addActionListener(e -> showDialog(null));

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            for (Student s : StudentStore.getInstance().getAllStudents())
                if (s.getId() == id) { showDialog(s); return; }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String name = (String) tableModel.getValueAt(row, 1);
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + name + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) tableModel.getValueAt(row, 0);
                StudentStore.getInstance().deleteStudent(id);
                loadTable(StudentStore.getInstance().getAllStudents());
                dashboard.refreshStats();
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            }
        });

        viewBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            for (Student s : StudentStore.getInstance().getAllStudents())
                if (s.getId() == id) { showDetails(s); return; }
        });

        add(topPanel, BorderLayout.NORTH);
        add(scroll,   BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    // ---- Load Table ----
    private void loadTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students)
            tableModel.addRow(new Object[]{
                s.getId(), s.getName(), s.getRollNumber(),
                s.getDepartment(), s.getSemester(),
                s.getEmail(), s.getPhone(), s.getGender()
            });
    }

    // ---- Add / Edit Dialog ----
    private void showDialog(Student existing) {
        JTextField nameF  = new JTextField(existing != null ? existing.getName()       : "");
        JTextField rollF  = new JTextField(existing != null ? existing.getRollNumber() : "");
        JTextField emailF = new JTextField(existing != null ? existing.getEmail()      : "");
        JTextField phoneF = new JTextField(existing != null ? existing.getPhone()      : "");

        String[] depts = {"Computer Science", "Software Engineering",
                          "Information Technology", "Electrical Engineering", "Business Administration"};
        JComboBox<String> deptCombo = new JComboBox<>(depts);
        if (existing != null) deptCombo.setSelectedItem(existing.getDepartment());

        String[] semesters = {"1st","2nd","3rd","4th","5th","6th","7th","8th"};
        JComboBox<String> semCombo = new JComboBox<>(semesters);
        if (existing != null) semCombo.setSelectedItem(existing.getSemester());

        String[] genders = {"Male", "Female"};
        JComboBox<String> genderCombo = new JComboBox<>(genders);
        if (existing != null) genderCombo.setSelectedItem(existing.getGender());

        Object[] fields = {
            "Full Name:",    nameF,
            "Roll Number:",  rollF,
            "Department:",   deptCombo,
            "Semester:",     semCombo,
            "Email:",        emailF,
            "Phone:",        phoneF,
            "Gender:",       genderCombo
        };

        String dlgTitle = existing == null ? "Add New Student" : "Edit Student";
        int result = JOptionPane.showConfirmDialog(this, fields, dlgTitle, JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        // ---- Validation ----
        String name  = nameF.getText().trim();
        String roll  = rollF.getText().trim();
        String email = emailF.getText().trim();
        String phone = phoneF.getText().trim();
        String dept  = (String) deptCombo.getSelectedItem();
        String sem   = (String) semCombo.getSelectedItem();
        String gender= (String) genderCombo.getSelectedItem();

        if (name.isEmpty() || roll.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Roll Number are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!phone.isEmpty() && !phone.matches("[0-9\\-+() ]{7,15}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StudentStore ss = StudentStore.getInstance();
        if (existing == null)
            ss.addStudent(name, roll, dept, sem, email, phone, gender);
        else
            ss.updateStudent(existing.getId(), name, roll, dept, sem, email, phone, gender);

        loadTable(ss.getAllStudents());
        dashboard.refreshStats();
        JOptionPane.showMessageDialog(this,
            existing == null ? "Student added successfully!" : "Student updated successfully!");
    }

    // ---- View Details ----
    private void showDetails(Student s) {
        String details =
            "ID:           " + s.getId()          + "\n" +
            "Name:         " + s.getName()         + "\n" +
            "Roll Number:  " + s.getRollNumber()   + "\n" +
            "Department:   " + s.getDepartment()   + "\n" +
            "Semester:     " + s.getSemester()     + "\n" +
            "Email:        " + s.getEmail()        + "\n" +
            "Phone:        " + s.getPhone()        + "\n" +
            "Gender:       " + s.getGender();
        JOptionPane.showMessageDialog(this, details, "Student Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---- Button Styling ----
    private void styleBtn(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
    }
}
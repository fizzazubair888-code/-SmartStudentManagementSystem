package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField     usernameField;
    private JPasswordField passwordField;

    // Hardcoded credentials
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    public LoginFrame() {
        setTitle("Smart Student Management System");
        setSize(430, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
    }

    private void buildUI() {
        JPanel main = new JPanel(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 70, 130));
        header.setBorder(new EmptyBorder(25, 20, 20, 20));
        JLabel titleLbl = new JLabel("Smart Student Management System", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLbl.setForeground(Color.WHITE);
        JLabel subLbl = new JLabel("Please login to continue", SwingConstants.CENTER);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLbl.setForeground(new Color(180, 210, 255));
        header.add(titleLbl, BorderLayout.CENTER);
        header.add(subLbl,   BorderLayout.SOUTH);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(25, 50, 25, 50));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0;
        JLabel uLbl = new JLabel("Username:");
        uLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(uLbl, gc);

        gc.gridx = 1;
        usernameField = new JTextField(16);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(usernameField, gc);

        gc.gridx = 0; gc.gridy = 1;
        JLabel pLbl = new JLabel("Password:");
        pLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(pLbl, gc);

        gc.gridx = 1;
        passwordField = new JPasswordField(16);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(passwordField, gc);

        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2;
        gc.insets = new Insets(16, 8, 4, 8);
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(new Color(25, 70, 130));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(new EmptyBorder(10, 20, 10, 20));
        form.add(loginBtn, gc);

        gc.gridy = 3; gc.insets = new Insets(4, 8, 0, 8);
        JLabel hint = new JLabel("Default: admin / admin123", SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(Color.GRAY);
        form.add(hint, gc);

        // Action Listeners
        loginBtn.addActionListener(e -> doLogin());
        passwordField.addActionListener(e -> doLogin());

        main.add(header, BorderLayout.NORTH);
        main.add(form,   BorderLayout.CENTER);
        setContentPane(main);
    }

    private void doLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password!",
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            new DashboardFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password!",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
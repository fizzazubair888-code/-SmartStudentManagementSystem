package ui;

import dao.StudentStore;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private JLabel totalLbl, maleLbl, femaleLbl;

    public DashboardFrame() {
        setTitle("Smart Student Management System - Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUI();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 247, 250));

        // ---- Top Bar ----
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(25, 70, 130));
        topBar.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel titleLbl = new JLabel("Smart Student Management System");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(Color.WHITE);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        topBar.add(titleLbl,  BorderLayout.WEST);
        topBar.add(logoutBtn, BorderLayout.EAST);

        // ---- Stat Cards ----
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(new Color(245, 247, 250));
        statsPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        totalLbl  = createStatCard(statsPanel, "Total Students", new Color(25, 70, 130));
        maleLbl   = createStatCard(statsPanel, "Male Students",  new Color(25, 135, 84));
        femaleLbl = createStatCard(statsPanel, "Female Students",new Color(220, 53, 69));

        // ---- Tabs ----
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabs.addTab("Students", new StudentPanel(this));

        JPanel center = new JPanel(new BorderLayout());
        center.add(statsPanel, BorderLayout.NORTH);
        center.add(tabs,       BorderLayout.CENTER);

        root.add(topBar, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
        refreshStats();
    }

    private JLabel createStatCard(JPanel parent, String label, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2, true),
            new EmptyBorder(15, 20, 15, 20)));

        JLabel valLbl = new JLabel("0", SwingConstants.CENTER);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valLbl.setForeground(color);

        JLabel nameLbl = new JLabel(label, SwingConstants.CENTER);
        nameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLbl.setForeground(Color.GRAY);

        card.add(valLbl,  BorderLayout.CENTER);
        card.add(nameLbl, BorderLayout.SOUTH);
        parent.add(card);
        return valLbl;
    }

    public void refreshStats() {
        StudentStore ss = StudentStore.getInstance();
        totalLbl.setText(String.valueOf(ss.totalStudents()));
        maleLbl.setText(String.valueOf(ss.totalMale()));
        femaleLbl.setText(String.valueOf(ss.totalFemale()));
    }
}
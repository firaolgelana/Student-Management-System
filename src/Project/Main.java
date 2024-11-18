package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * The Main class is the entry point for the School Management System application.
 * It sets up the main application window and handles navigation between different sections.
 */
public class Main implements ActionListener {
    private static JFrame frame; // Main application window
    private JButton studentButton, teacherButton, payFeesButton; // Buttons for navigation
    private static JPanel homePanel; // Panel for the home screen
    private static CardLayout cardLayout; // Layout manager for switching between panels
    public static Connection connection; // Database connection

    // Static block to initialize the connection and card layout
    static {
        connection = new SQLConnection().connect();
        cardLayout = new CardLayout();
    }

    /**
     * Constructor to set up the main application frame and panels.
     */
    public Main() {
        new SQLConnection().project(); // Custom method for project-specific setup
        frame = new JFrame("School Management System");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(cardLayout);

        JPanel leftSide = new JPanel(); // Left padding panel
        JPanel rightSide = new JPanel(); // Right padding panel
        JPanel footer = new JPanel(); // Footer panel
        JPanel header = new JPanel(); // Header panel

        // Home Panel setup
        homePanel = new ImagePanel("back2.jpg", new BorderLayout());
        studentButton = new JButton("Student");
        teacherButton = new JButton("Teacher");
        payFeesButton = new JButton("PayFees");
        studentButton.addActionListener(this); // Add action listeners to buttons
        teacherButton.addActionListener(this);
        payFeesButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(); // Panel for navigation buttons
        buttonPanel.add(studentButton);
        buttonPanel.add(teacherButton);
        buttonPanel.add(payFeesButton);

        // Footer setup
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));
        footer.add(new JLabel("© 2024 CSE Student"));
        footer.add(new JLabel(" | Version 1.0.0"));
        footer.add(new JLabel(" | Contact: https://t.me/gelanafiraol@gmail.com"));
        footer.setPreferredSize(new Dimension(homePanel.getWidth(), 50));
        footer.setBackground(Color.LIGHT_GRAY);
        homePanel.add(footer, BorderLayout.SOUTH);

        // Side panels setup
        leftSide.setPreferredSize(new Dimension(20, homePanel.getHeight()));
        rightSide.setPreferredSize(new Dimension(20, homePanel.getHeight()));
        leftSide.setBackground(Color.LIGHT_GRAY);
        rightSide.setBackground(Color.LIGHT_GRAY);
        homePanel.add(leftSide, BorderLayout.WEST);
        homePanel.add(rightSide, BorderLayout.EAST);

        // Header setup
        header.setBackground(Color.LIGHT_GRAY);
        header.add(buttonPanel, BorderLayout.NORTH);
        homePanel.add(header, BorderLayout.NORTH);

        // Adding homePanel to frame
        frame.add(homePanel, "Home");

        // Adding other panels to the frame
        JPanel studentPanel = new Student().getStudent();
        studentPanel.add(createBackButton("Home"), BorderLayout.SOUTH);
        frame.add(studentPanel, "Student");

        JPanel payFeesPanel = new PayFees().payFeesPanel();
        payFeesPanel.add(createBackButton("Home"), BorderLayout.SOUTH);
        frame.add(payFeesPanel, "PayFees");

        JPanel teacherPanel = new Teacher().StudentPanel();
        teacherPanel.add(createBackButton("Home"), BorderLayout.SOUTH);
        frame.add(teacherPanel, "Show Students");

        // Setting the frame visible
        frame.setVisible(true);
    }

    /**
     * Method to create a "Back" button to switch panels.
     * @param panelName the name of the panel to switch to.
     * @return a JButton configured to switch panels when clicked.
     */
    private JButton createBackButton(String panelName) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), panelName));
        return backButton;
    }

    /**
     * Handles button click events.
     * @param e the ActionEvent triggered by clicking a button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Student":
                cardLayout.show(frame.getContentPane(), "Student");
                break;
            case "Teacher":
                cardLayout.show(frame.getContentPane(), "Show Students");
                break;
            case "PayFees":
                cardLayout.show(frame.getContentPane(), "PayFees");
                break;
        }
    }

    /**
     * Main method to start the application.
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Main();
    }
}

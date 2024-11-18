package Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * The Student class provides functionalities to register and login students.
 * It includes a panel with buttons for registration and login, and displays
 * student information after login.
 */
public class Student extends JPanel {
    JPanel panel; // Main panel for the student section

    /**
     * Method to get the student panel with register and login functionalities.
     * @return a JPanel containing the registration and login buttons.
     */
    public JPanel getStudent() {
        JButton register = new JButton("Register"); // Register button
        JButton login = new JButton("Login"); // Login button

        JPanel leftSide = new JPanel(); // Left side padding
        JPanel rightSide = new JPanel(); // Right side padding
        JPanel footer = new JPanel(); // Footer panel
        JPanel header = new JPanel(); // Header panel

        // Home Panel with background image
        panel = new ImagePanel("back1.jpg", new BorderLayout());
        JPanel buttonPanel = new JPanel(); // Panel for buttons
        buttonPanel.add(register);
        buttonPanel.add(login);

        // Add components to the footer
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));
        footer.add(new JLabel("© 2024 CSE Student"));
        footer.add(new JLabel(" | Version 1.0.0"));
        footer.add(new JLabel(" | Contact: https://t.me/gelanafiraol@gmail.com"));
        footer.setPreferredSize(new Dimension(panel.getWidth(), 50));
        footer.setBackground(Color.LIGHT_GRAY);
        panel.add(footer, BorderLayout.SOUTH);

        // Add components to the sides
        leftSide.setPreferredSize(new Dimension(20, panel.getHeight()));
        rightSide.setPreferredSize(new Dimension(20, panel.getHeight()));
        leftSide.setBackground(Color.LIGHT_GRAY);
        rightSide.setBackground(Color.LIGHT_GRAY);
        panel.add(leftSide, BorderLayout.WEST);
        panel.add(rightSide, BorderLayout.EAST);

        // Add components to the header
        header.setBackground(Color.LIGHT_GRAY);
        header.add(buttonPanel, BorderLayout.NORTH);
        panel.add(header, BorderLayout.NORTH);

        // Register button action listener
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Input fields for registration
                JTextField fullName = new JTextField();
                JTextField id = new JTextField();
                JPasswordField password = new JPasswordField();
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("Enter Your FullName"));
                panel.add(fullName);
                panel.add(new JLabel("Enter your Id number"));
                panel.add(id);
                panel.add(new JLabel("Enter your password"));
                panel.add(password);
                int result = JOptionPane.showConfirmDialog(null, panel, "Enter Your Information", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // Validate inputs
                    if (fullName.getText().isEmpty() || new String(password.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(null, "UserName or Password is invalid!");
                        System.exit(0);
                    }
                    try {
                        // Register the student
                        registerStudent(fullName, id, password);
                        JOptionPane.showMessageDialog(null, "Student registered successfully!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error registering student: " + ex.getMessage());
                    }
                }
            }
        });

        // Login button action listener
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Input fields for login
                JTextField fullName = new JTextField();
                JPasswordField password = new JPasswordField();
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("Enter Your FullName"));
                panel.add(fullName);
                panel.add(new JLabel("Enter your password"));
                panel.add(password);
                int result = JOptionPane.showConfirmDialog(null, panel, "Enter Your Information", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // Validate inputs
                    if (fullName.getText().isEmpty() || new String(password.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(null, "UserName or Password is invalid!");
                        System.exit(0);
                    }
                    try {
                        // Login the student
                        loginStudent(fullName, password);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error logging in: " + ex.getMessage());
                    }
                }
            }
        });

        return panel;
    }

    /**
     * Method to handle student login.
     * @param fullName the student's full name.
     * @param password the student's password.
     * @throws SQLException if a database access error occurs.
     */
    private void loginStudent(JTextField fullName, JPasswordField password) throws SQLException {
        String sql = "SELECT * FROM Student WHERE FullName = ? AND Password = ?";
        Connection connection = Main.connection;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fullName.getText());
        statement.setString(2, new String(password.getPassword()));

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            // Retrieve student information
            String id = rs.getString("id");
            String name = rs.getString("FullName");
            double assessment = rs.getDouble("assessment");
            double studFinal = rs.getDouble("final");
            String grade = rs.getString("grade");

            // Column names for the table
            String[] columnNames = {"ID", "Name", "Assessment", "Final", "Grade"};
            // Data for the table
            Object[][] data = {
                {id, name, assessment, studFinal, grade}
            };

            // Create the table model and set it to the JTable
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);

            // Display the table in a JOptionPane
            JScrollPane scrollPane = new JScrollPane(table);
            JPanel studentPanel = new JPanel(new BorderLayout());
            studentPanel.add(scrollPane, BorderLayout.CENTER);
            JOptionPane.showMessageDialog(null, studentPanel, "Student Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
        }

        statement.close();
    }

    /**
     * Method to handle student registration.
     * @param fullName the student's full name.
     * @param id the student's ID.
     * @param password the student's password.
     * @throws SQLException if a database access error occurs.
     */
    private void registerStudent(JTextField fullName, JTextField id, JPasswordField password) throws SQLException {
        Connection connection = Main.connection;
        String useDb = "USE Student";
        Statement st = connection.createStatement();
        st.executeUpdate(useDb);

        String sql = "INSERT INTO Student (id, FullName, password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id.getText());
        statement.setString(2, fullName.getText());
        statement.setString(3, new String(password.getPassword()));

        statement.executeUpdate();

        statement.close();
    }
}

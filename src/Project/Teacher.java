package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * The Teacher class provides functionality to create a JPanel
 * that displays student information with assessment capabilities.
 */
public class Teacher {

    /**
     * Creates a panel that displays student information with assessment functionality.
     * @return a JPanel containing the student information and assessment buttons.
     */
    public JPanel StudentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        try {
            StudentDAO studentDAO = new StudentDAO(); // Create an instance of StudentDAO to fetch students
            ResultSet rs = studentDAO.getAllStudents(); // Fetch all students

            while (rs.next()) {
                String id = rs.getString("id");
                String fullName = rs.getString("FullName");
                JPanel studentPanel = new JPanel(new BorderLayout());
                JLabel studentLabel = new JLabel("ID: " + id + ", Name: " + fullName);
                JButton addAssessmentButton = new JButton("Add Assessment");

                // Add action listener to the "Add Assessment" button
                addAssessmentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showAddAssessmentDialog(id); // Show dialog to add assessment marks
                    }
                });

                studentPanel.add(studentLabel, BorderLayout.CENTER);
                studentPanel.add(addAssessmentButton, BorderLayout.EAST);
                panel.add(studentPanel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return panel;
    }

    /**
     * Shows a dialog for adding assessment marks for a student.
     * @param studentId the ID of the student.
     */
    private void showAddAssessmentDialog(String studentId) {
        JTextField assessmentField = new JTextField();
        JTextField finalField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Enter Assessment Mark"));
        panel.add(assessmentField);
        panel.add(new JLabel("Enter Final Mark"));
        panel.add(finalField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Marks for Student ID: " + studentId, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double assessment = Double.parseDouble(assessmentField.getText());
                double finalMark = Double.parseDouble(finalField.getText());
                double total = assessment + finalMark;
                String grade;

                // Determine grade based on total marks
                if (total >= 90) {
                    grade = "A+";
                } else if (total >= 85) {
                    grade = "A";
                } else if (total >= 80) {
                    grade = "A-";
                } else if (total >= 75) {
                    grade = "B+";
                } else if (total >= 70) {
                    grade = "B";
                } else if (total >= 65) {
                    grade = "B-";
                } else if (total >= 60) {
                    grade = "C";
                } else if (total >= 55) {
                    grade = "D";
                } else {
                    grade = "F";
                }

                addAssessmentToStudent(studentId, assessment, finalMark, grade); // Update student record with marks and grade
                JOptionPane.showMessageDialog(null, "Marks added successfully!");
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error adding marks: " + ex.getMessage());
            }
        }
    }

    /**
     * Updates the student record with assessment marks and grade.
     * @param studentId the ID of the student.
     * @param assessment the assessment mark.
     * @param finalMark the final mark.
     * @param grade the grade based on total marks.
     * @throws SQLException if a database access error occurs.
     */
    private void addAssessmentToStudent(String studentId, double assessment, double finalMark, String grade) throws SQLException {
        String sql = "USE Student";
        Statement st = Main.connection.createStatement();
        st.executeUpdate(sql);
        String query = "UPDATE student SET assessment = ?, final = ?, grade = ? WHERE id = ?";
        Connection connection = Main.connection;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1, assessment);
        statement.setDouble(2, finalMark);
        statement.setString(3, grade);
        statement.setString(4, studentId);
        statement.executeUpdate();
        statement.close();
    }
}

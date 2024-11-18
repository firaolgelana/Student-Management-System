package Project;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * The PayFees class provides functionalities to manage student fee payments.
 * It includes a panel with buttons for displaying student information and paying fees.
 */
public class PayFees {
    JPanel payfeesPanel = new JPanel(); // Main panel for the PayFees section

    /**
     * Method to create and return the PayFees panel.
     * @return a JPanel containing student information and fee payment buttons.
     */
    public JPanel payFeesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        try {
            StudentDAO studentDAO = new StudentDAO(); // Create an instance of StudentDAO to fetch students
            ResultSet rs = studentDAO.getAllStudents(); // Fetch all students

            while (rs.next()) {
                String id = rs.getString("id");
                String fullName = rs.getString("FullName");
                JPanel payfeesPanel = new JPanel(new BorderLayout());
                JLabel studentLabel = new JLabel("ID: " + id + ", Name: " + fullName);
                JButton payFeesButton = new JButton("Pay Fees");

                // Add action listener to the "Pay Fees" button
                payFeesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showPayFeesDialog(id); // Show dialog to pay fees for the student
                    }
                });

                payfeesPanel.add(studentLabel, BorderLayout.CENTER);
                payfeesPanel.add(payFeesButton, BorderLayout.EAST);
                panel.add(payfeesPanel);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace if there's an SQL exception
        }
        return panel;
    }

    /**
     * Shows a dialog for paying fees for a student.
     * @param studentId the ID of the student.
     */
    private void showPayFeesDialog(String studentId) {
        JTextField feesField = new JTextField(); // Field to input fees amount
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Enter Fees Amount"));
        panel.add(feesField);

        // Show the dialog to enter fees amount
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Fees for Student ID: " + studentId, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int fees = Integer.parseInt(feesField.getText()); // Get the entered fees amount
                payFees(studentId, fees); // Update the fees for the student
                JOptionPane.showMessageDialog(null, "Fees updated successfully!");
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error updating fees: " + ex.getMessage());
            }
        }
    }

    /**
     * Updates the fees for a student in the database.
     * @param studentId the ID of the student.
     * @param fees the amount to be paid.
     * @throws SQLException if a database access error occurs.
     */
    private void payFees(String studentId, int fees) throws SQLException {
        Connection connection = Main.connection; // Get the connection from Main class
        String sq = "USE Student"; // SQL query to use the Student database
        Statement st = connection.createStatement();
        st.executeUpdate(sq);

        String sql = "UPDATE student SET fees = fees - ? WHERE id = ?"; // SQL query to update fees
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, fees); // Set the fees parameter
        statement.setString(2, studentId); // Set the student ID parameter
        statement.executeUpdate();
        statement.close();
    }
}

/**
 * Data Access Object (DAO) class for Student operations.
 */
class StudentDAO {
    /**
     * Fetches all students from the database.
     * @return a ResultSet containing all student records.
     * @throws SQLException if a database access error occurs.
     */
    public ResultSet getAllStudents() throws SQLException {
        String query = "SELECT * FROM student"; // SQL query to select all students
        Connection connection = Main.connection; // Get the connection from Main class
        if (connection != null) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } else {
            throw new SQLException("Connection is null.");
        }
    }
}

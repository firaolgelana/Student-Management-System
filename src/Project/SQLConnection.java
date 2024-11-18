package Project;

import java.sql.*;
import javax.swing.*;

/**
 * The SQLConnection class handles the connection to a MySQL database,
 * including the creation of the database and required tables.
 */
public class SQLConnection {
    String sql; // SQL query string
    Statement st; // Statement for executing SQL queries
    Connection connection; // Connection object for database connection

    /**
     * Establishes a connection to the database.
     * @return the established Connection object, or null if the connection was not successful.
     */
    public Connection connect() {
        Connection connection;
        String url = "jdbc:mysql://localhost:3306/"; // Database URL
        JTextField userName = new JTextField(); // TextField for username input
        JPasswordField password = new JPasswordField(); // PasswordField for password input

        // Panel for authentication inputs
        JPanel authenticationPanel = new JPanel();
        authenticationPanel.setLayout(new BoxLayout(authenticationPanel, BoxLayout.Y_AXIS));
        authenticationPanel.add(new JLabel("Enter Username of your server"));
        authenticationPanel.add(userName);
        authenticationPanel.add(new JLabel("Enter your server password"));
        authenticationPanel.add(password);

        // Dialog to prompt user for database connection information
        int result = JOptionPane.showConfirmDialog(null, authenticationPanel, "Enter Connection Information", JOptionPane.OK_CANCEL_OPTION);

        // If user presses OK
        if (result == JOptionPane.OK_OPTION) {
            // Validate inputs
            if (userName.getText().isEmpty() || new String(password.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(null, "UserName or Password is invalid!");
                System.exit(0); // Exit if validation fails
            }
            try {
                // Establish connection
                connection = DriverManager.getConnection(url, userName.getText(), new String(password.getPassword()));
                return connection;
            } catch (Exception e) {
                // Handle connection failure
                JOptionPane.showMessageDialog(null, "Connection Failed: " + e.getMessage());
                System.exit(0); // Exit on failure
            }
        }
        return null; // Return null if connection not established
    }

    /**
     * Sets up the project by initializing the connection and creating the database and tables.
     */
    public void project() {
        initializeConnection(); // Initialize the database connection
        createDatabaseAndTables(); // Create database and tables
    }

    /**
     * Initializes the connection using the connection from the Main class.
     */
    private void initializeConnection() {
        connection = Main.connection;
    }

    /**
     * Creates the database and tables.
     */
    private void createDatabaseAndTables() {
        try {
            st = connection.createStatement(); // Create statement object
            createDatabase(); // Create database if not exists
            useDatabase(); // Use the created database
            createTables(); // Create required tables
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage()); // Handle SQL exceptions
        }
    }

    /**
     * Creates the database if it does not exist.
     * @throws SQLException if a database access error occurs.
     */
    private void createDatabase() throws SQLException {
        sql = "CREATE DATABASE IF NOT EXISTS Student"; // SQL query to create database
        st.executeUpdate(sql); // Execute the query
    }

    /**
     * Switches to the created database.
     * @throws SQLException if a database access error occurs.
     */
    private void useDatabase() throws SQLException {
        sql = "USE Student"; // SQL query to use the database
        st.executeUpdate(sql); // Execute the query
    }

    /**
     * Creates all necessary tables.
     * @throws SQLException if a database access error occurs.
     */
    private void createTables() throws SQLException {
        createStudentTable(); // Create student table
    }

    /**
     * Creates the student table.
     * @throws SQLException if a database access error occurs.
     */
    private void createStudentTable() throws SQLException {
        sql = "CREATE TABLE IF NOT EXISTS Student (" // SQL query to create table
                + "id VARCHAR(70) PRIMARY KEY NOT NULL, " // ID column
                + "FullName VARCHAR(255) NOT NULL, " // Full name column
                + "Password VARCHAR(255) NOT NULL," // Password column
                + "assessment INT," // Assessment column
                + "fees REAL DEFAULT 10000," // Fees column with default value
                + "grade VARCHAR(2)," // Grade column
                + "final INT);"; // Final marks column
        st.executeUpdate(sql); // Execute the query
    }
}

School Management System
A Java-based School Management System application built using Swing for the GUI and JDBC for database connectivity. This project provides functionalities for managing students, teachers, and payment of fees.

Table of Contents
Features

Technologies Used


Usage

Classes and Methods

Contributing

Features
Register and login for students

View and manage student assessments and grades

Handle payment of student fees

Interactive GUI with navigation between different sections

Technologies Used
Java

Swing (for GUI)

JDBC (for database connectivity)

MySQL (for database)

Setup and Installation
Clone the Repository:

sh
git clone https://github.com/firaolgelana/School-Management-System.git
cd school-management-system
Set Up the Database:

Make sure you have MySQL installed and running.

Create a database named Student using the following command:

sql
CREATE DATABASE Student;
Update the SQLConnection class with your MySQL username and password.

Compile and Run the Application:

sh
javac -d bin -sourcepath src src/Project/Main.java
java -cp bin Project.Main
Usage
On running the application, the main window will open with options for Student, Teacher, and Pay Fees.

Use the respective buttons to navigate to different sections.

Register or log in as a student to view and manage personal information.

Teachers can add assessments and grades for students.

Use the Pay Fees section to handle fee payments.

Classes and Methods
Main Class
Main: Sets up the main application window and handles navigation.

createBackButton: Creates a "Back" button to switch panels.

actionPerformed: Handles button click events.

Student Class
getStudent: Returns a panel with registration and login functionalities.

registerStudent: Registers a new student.

loginStudent: Handles student login and displays student information.

Teacher Class
StudentPanel: Returns a panel for managing student assessments.

showAddAssessmentDialog: Displays a dialog to add assessment marks.

addAssessmentToStudent: Updates student records with assessment marks and grades.

PayFees Class
payFeesPanel: Returns a panel for handling fee payments.

showPayFeesDialog: Displays a dialog to enter and update fees.

payFees: Updates fees for a student in the database.

SQLConnection Class
connect: Establishes a connection to the MySQL database.

project: Sets up the database and required tables.

createDatabaseAndTables: Creates the database and tables if they do not exist.

ImagePanel Class
ImagePanel: Custom JPanel to display an image as the background.

Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

Home page

![alt text](<Home page.png>)

Strudent page

![alt text](<Student page.png>)

Teacher page to add assessment

![alt text](<Teacher page.png>)

Pay fees page to pay fees

![alt text](<Pay fees page.png>)"# Student-Management-System" 
"# Student-Management-System" 

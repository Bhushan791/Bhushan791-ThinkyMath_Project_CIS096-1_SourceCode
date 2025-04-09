package math_tutor.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    // Method to validate student login credentials
    public boolean validateStudentLogin(String username, String password) {
        // SQL query to check if the student exists with the given username and password
        String query = "SELECT * FROM students WHERE username = ? AND password = ?";
        try (Connection connection = ConnectionDB.getInstance().getConnection(); // Establish connection to the database
             PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Prepare the SQL query
            // Set the parameters (username and password) for the query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();
            // Return true if a matching record is found, indicating successful login
            return resultSet.next();
        } catch (SQLException e) {
            // Handle SQL exceptions and print the stack trace
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    // Method to validate teacher login credentials
    public boolean validateTeacherLogin(String email, String password) {
        // SQL query to check if the teacher exists with the given email and password
        String query = "SELECT * FROM teachers WHERE email = ? AND password = ?";
        try (Connection connection = ConnectionDB.getInstance().getConnection(); // Establish connection to the database
             PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Prepare the SQL query
            // Set the parameters (email and password) for the query
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();
            // Return true if a matching record is found, indicating successful login
            return resultSet.next();
        } catch (SQLException e) {
            // Handle SQL exceptions and print the stack trace
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }
}

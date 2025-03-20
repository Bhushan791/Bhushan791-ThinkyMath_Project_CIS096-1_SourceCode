package math_tutor.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class RegistrationService {
    private Connection connection;

    public RegistrationService() {
        this.connection = ConnectionDB.getInstance().getConnection();
    }

    public boolean registerStudent(String name, String dob, String guardianName, String guardianContact, String username, String password) {
        String query = "INSERT INTO students (name, dob, guardian_name, guardian_contact, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, Date.valueOf(dob)); // Convert String to Date
            preparedStatement.setString(3, guardianName);
            preparedStatement.setString(4, guardianContact);
            preparedStatement.setString(5, username);
            preparedStatement.setString(6, password);
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if the record was inserted successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

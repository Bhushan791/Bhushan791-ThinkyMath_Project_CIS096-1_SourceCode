package math_tutor.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    private Connection connection;

    public LoginService() {
        this.connection = ConnectionDB.getInstance().getConnection();
    }

    public boolean validateStudentLogin(String username, String password) {
        String query = "SELECT * FROM students WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if a matching record is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean validateTeacherLogin(String email, String password) {
        String query = "SELECT * FROM teachers WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if a matching record is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

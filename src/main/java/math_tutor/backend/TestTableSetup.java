package math_tutor.backend;

import java.sql.Connection;
import java.sql.Statement;

public class TestTableSetup {
    public static void setupTestsTable() {
        try (Connection connection = ConnectionDB.getInstance().getConnection(); // Get a new connection
             Statement statement = connection.createStatement()) {

            String createTableQuery ="""
                CREATE TABLE IF NOT EXISTS tests (
                    test_id INT AUTO_INCREMENT PRIMARY KEY,
                    test_name VARCHAR(255) NOT NULL,
                    student_name VARCHAR(255) NOT NULL,
                    student_id INT NOT NULL,
                    score INT,
                    completion_date DATE
                )
            """;

            statement.executeUpdate(createTableQuery);
            System.out.println("Tests table created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}